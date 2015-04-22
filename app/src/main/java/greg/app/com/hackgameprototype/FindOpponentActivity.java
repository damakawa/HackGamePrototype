package greg.app.com.hackgameprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Alex on 4/21/2015.
 * This is the activity that shows the findopponent_layout.xml
 *
 * This is where we will have our wifi adapter, gather all people
 * that show themselves on wifi direct. And screen them against the
 * database's list of MAC addresses.
 */
public class FindOpponentActivity extends Activity
{
    //Initializes all potential TextViews in this layout.
    TextView txtvw_Username, txtvw_Level, txtvw_Cash, txtvw_LongLat;

    //Initializes Button object
    Button btn_Attack;

    //Username that is passed from login/newuser
    String username = "";

    Circle enemyBlip = null;                        //Initializing circle object.
    android.graphics.PointF enemyLocation = null;   //Sets up .x and .y floating point object.
    int radius;                                     //Radius of circle object.

    //Display screen width and height
    int mScrWidth, mScrHeight;

    //If enemy is selected(get username, and other info)
    boolean enemySelected = false;
    //Amount of pixel feathering around the circle
    int touchBuffer = 20; //pixels

    Handler RedrawHandler = new Handler();  //so redraw occurs in main thread
    Timer mTmr = null;                      //Timer Object, for game Tick(FPS)
    TimerTask mTsk = null;                  //Timer Task

    //Intent to gather previous intent's data
    Intent intent;
    //Intent to switch to PreGameActivity
    Intent switchTo_PreGame;

    //GPS OBJECT!
    GPSLocation gps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findopponent_layout);

        //Gather previous intent's data
        intent = getIntent();
        //Set username to the previous intent's username
        username = intent.getStringExtra("Username");

        //Sets txtvw_Username to the layout "txtvw_Username"
        txtvw_Username = (TextView)findViewById(R.id.txtvw_Username);
        txtvw_Username.setText("Username: " + username);//Update text with actual username

        //create pointer to findOpp screen
        final FrameLayout findOppView = (FrameLayout) findViewById(R.id.frameLayout);

        //get screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        //Screen Variables
        mScrWidth = display.getWidth();
        mScrHeight = display.getHeight();

        radius = mScrWidth / 20;
        enemyLocation = new android.graphics.PointF();
        enemyLocation.x = mScrWidth / 2 + ((mScrWidth / 5) * 1);
        enemyLocation.y = mScrHeight / 4;
        //create enemy blip
        enemyBlip = new Circle(this, enemyLocation.x, enemyLocation.y, radius);

        findOppView.addView(enemyBlip); //add ball to main screen
        enemyBlip.invalidate(); //call onDraw in BallView

        //Attack Button being set to the attack button in the layout
        btn_Attack = (Button)findViewById(R.id.btn_Attack);
        //Attack Button Listener
        btn_Attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTo_PreGame = new Intent(FindOpponentActivity.this, PreGameActivity.class); //Switch to PreGame
                btn_Attack.setEnabled(false);
                btn_Attack.setText("Attack!");
                startActivity(switchTo_PreGame);
            }
        });

        //listener for touch event
        findOppView.setOnTouchListener(new android.view.View.OnTouchListener()
        {
            public boolean onTouch(android.view.View v, android.view.MotionEvent e)
            {
                if(e.getX() > enemyLocation.x - touchBuffer &&
                        e.getX() < enemyLocation.x + radius + touchBuffer &&
                        e.getY() > enemyLocation.y - touchBuffer &&
                        e.getY() < enemyLocation.y + radius + touchBuffer)
                {
                    enemySelected = true;

                    btn_Attack.setEnabled(true);

                    btn_Attack.setText("Attack: (Username)");
                }
                else
                {
                    enemySelected = false;

                    btn_Attack.setEnabled(false);

                    btn_Attack.setText("Attack!");
                }

                return true;
            }
        });
    }

    @Override
    public void onResume()
    {//create timer to have as a game Tick(FPS) and to show(update) drawn objects
        mTmr = new Timer();
        mTsk = new TimerTask()
        {
            public void run()
            {
                //redraw objects on radar. Must run in background thread to prevent thread lock.
                RedrawHandler.post(new Runnable() {
                    public void run() {
                        enemyBlip.invalidate();
                    }});
            }
        };
        mTmr.schedule(mTsk,10,10); //start timer
        super.onResume();
    }
}