package greg.app.com.hackgameprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alex on 4/22/2015.
 */
public class NewUserActivity extends Activity
{
    //Button Object for done Widget
    Button btn_Done;

    //The editable text field Object
    EditText username_Text;

    //Switch Activity to FindOpponentActivity
    Intent switchTo_FindOpp;

    //Username gathered from user(username_Text)
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newuser_layout);

        //Switch Activity to FindOpponentActivity
        switchTo_FindOpp = new Intent(NewUserActivity.this, FindOpponentActivity.class);

        //Set username_Text to the layout edt_Username
        username_Text = (EditText)findViewById(R.id.edt_Username);
        //Listener for username_Text
        username_Text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                //Action for when the user hits the done key on the popup keyboard.
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    handled = true;

                    //Get the text from the editText
                    username = username_Text.getText().toString();

                    //If user didn't input a username
                    if(!username.equals("")) {
                        switchTo_FindOpp.putExtra("Username", username);
                        startActivity(switchTo_FindOpp);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please enter a username.", Toast.LENGTH_SHORT).show();
                    }
                }
                return handled;
            }
        });

        //Done Button being set to the done button in the layout
        btn_Done = (Button)findViewById(R.id.btn_Done);
        //Done Button Listener
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Get the text from the editText
                username = username_Text.getText().toString();
                //If user didn't input a username
                if(!username.equals("")) {
                    switchTo_FindOpp.putExtra("Username", username);
                    startActivity(switchTo_FindOpp);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter a username.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}
