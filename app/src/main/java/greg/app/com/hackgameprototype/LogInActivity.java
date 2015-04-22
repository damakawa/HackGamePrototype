package greg.app.com.hackgameprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alex on 4/21/2015.
 */
public class LogInActivity extends Activity
{
    //SQLThread to offload networking to it, instead of main thread
    SQLThread sqlThread;

    //Button object
    Button btn_Login;

    //Switch to a new Activity
    Intent switchTo_NewActivity;

    boolean newUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //IF new user
        if(newUser) {
            //Switch to NewUserActivity
            switchTo_NewActivity = new Intent(LogInActivity.this, NewUserActivity.class); //If it is a new user
        }
        else
        {
            //Switch to FindOpponentActivity
            switchTo_NewActivity = new Intent(LogInActivity.this, FindOpponentActivity.class); //If you have a MAC on the database
        }

        //Login Button being set to the login button in the layout
        btn_Login = (Button)findViewById(R.id.btn_Login);
        //Login Button Listener
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Go to new Activity
                startActivity(switchTo_NewActivity);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}
