package greg.app.com.hackgameprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Alex on 4/21/2015.
 */
public class PostGameActivity extends Activity
{
    Intent intent;
    Intent switchTo_FindOpp;

    Boolean won = false;

    TextView txtvw_Title;

    String title = "";

    Button btn_NextOpp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postgame_layout);

        intent = getIntent();

        won = intent.getBooleanExtra("Won", false);

        if(won)
        {
            title = "You Won!";
        }
        else
        {
            title = "You Lost!";
        }

        txtvw_Title = (TextView)findViewById(R.id.txtvw_Title);
        txtvw_Title.setText(title);

        btn_NextOpp = (Button)findViewById(R.id.btn_NextOpp);
        btn_NextOpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switchTo_FindOpp = new Intent(PostGameActivity.this, FindOpponentActivity.class);
                startActivity(switchTo_FindOpp);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}