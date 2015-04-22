package greg.app.com.hackgameprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Alex on 4/21/2015.
 */
public class PreGameActivity extends Activity
{
    Button btn_Hack;

    Intent switchTo_Game;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregame_layout);

        btn_Hack = (Button)findViewById(R.id.btn_Hack);
        //Attack Button Listener
        btn_Hack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switchTo_Game = new Intent(PreGameActivity.this, GameActivity.class); //Switch to Finding Opponents
                startActivity(switchTo_Game);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}