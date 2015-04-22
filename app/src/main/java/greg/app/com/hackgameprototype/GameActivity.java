package greg.app.com.hackgameprototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;



public class GameActivity extends Activity {

    private Intent switchTo_PostGame;       //This changes that activity

    private final int mCodeSize = 4;        //This is the size of the code to be solved
    private final int mMistakeLimit = 3;    //This is the limit of mistakes before a reset
    private final int mTimeLimit = 40;      //This is the soft time limit (user can still finish)

    private CountDownTimer countDownTimer;  //The timer that will count down for the time limit
    private int mTimeRemaining;             //A variable to store remaining time upon finishing
    private int mCompleteTime;              //Int to store the calculated time to complete

    public TextView mCodeText;              //TextView to display the Code/Mask
    public TextView mTimerText;             //TextView for displaying time

    private Button Btn0, Btn1, Btn2, Btn3, Btn4, Btn5, //All the number pad buttons
            Btn6, Btn7, Btn8, Btn9;

    private static Vibrator vibrator;       //The vibrator to set of phone vibrations

    private int mGuess;                     //Int to hold the players guess when a button is pressed
    private int mMistakeCount = 0;          //Counter for player mistakes
    private int mGuessIndex = 0;            //Index of code currently being worked on

    private char[] mCodeMask;               //character array to mask the code being displayed
    private String mMaskString = "";        //The string that will be used to display the code/mask

    private int[] mCodeArray;               //An int array to hold the code
    private String mCodeString = "";        //The string used to output the code into the log for testing

    boolean mGameFinished = false;          //A boolean for when the game is finsihed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout2);
        relativeLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);

        //These 3 lines initialize the Mask String
        mCodeText = (TextView)findViewById(R.id.codeTextView);
        mCodeMask = new char[mCodeSize];
        setmMaskString("start");

        //Initialize the timer textView
        mTimerText = (TextView)findViewById(R.id.timerTextView);

        //***********************************************************************
        //All buttons are initialized and upon clicking a button, the functions *
        // to set the mGuess variable and to check the guess are called         *
        //***********************************************************************
        Btn0 = (Button)findViewById(R.id.button0);
        Btn0.setBackgroundResource(android.R.drawable.btn_default);
        Btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(0);
                checkGuess();
            }
        });
        Btn1 = (Button)findViewById(R.id.button1);
        Btn1.setBackgroundResource(android.R.drawable.btn_default);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(1);
                checkGuess();
            }
        });
        Btn2 = (Button)findViewById(R.id.button2);
        Btn2.setBackgroundResource(android.R.drawable.btn_default);
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(2);
                checkGuess();
            }
        });
        Btn3 = (Button)findViewById(R.id.button3);
        Btn3.setBackgroundResource(android.R.drawable.btn_default);
        Btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(3);
                checkGuess();
            }
        });
        Btn4 = (Button)findViewById(R.id.button4);
        Btn4.setBackgroundResource(android.R.drawable.btn_default);
        Btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(4);
                checkGuess();
            }
        });
        Btn5 = (Button)findViewById(R.id.button5);
        Btn5.setBackgroundResource(android.R.drawable.btn_default);
        Btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(5);
                checkGuess();
            }
        });
        Btn6 = (Button)findViewById(R.id.button6);
        Btn6.setBackgroundResource(android.R.drawable.btn_default);
        Btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(6);
                checkGuess();
            }
        });
        Btn7 = (Button)findViewById(R.id.button7);
        Btn7.setBackgroundResource(android.R.drawable.btn_default);
        Btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(7);
                checkGuess();
            }
        });
        Btn8 = (Button)findViewById(R.id.button8);
        Btn8.setBackgroundResource(android.R.drawable.btn_default);
        Btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(8);
                checkGuess();
            }
        });
        Btn9 = (Button)findViewById(R.id.button9);
        Btn9.setBackgroundResource(android.R.drawable.btn_default);
        Btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmGuess(9);
                checkGuess();
            }
        });

        //***********************************************************
        //This block allocates an array for the code, and randomly  *
        // fills each index of the array with a digit 0-9           *
        //***********************************************************
        mCodeArray = new int[mCodeSize];
        for (int i = 0; i < mCodeSize; i++) {
            Random r = new Random();
            mCodeArray[i] = r.nextInt(10);
            mCodeString += mCodeArray[i];
        }
        Log.d("MyTag", "Current Code: " + mCodeString);
    }

    @Override
    public void onResume() {
        super.onResume();

        //***********************************************************************
        //The timer is initialized with the number of seconds specified by      *
        // the mTimeLimit variable, and the increment of each tick specified    *
        //***********************************************************************
         countDownTimer = new CountDownTimer((mTimeLimit * 1000), 1000) {

            //On each tick the screen timer is updated, and the remaining time is updated
            public void onTick(long millisUntilFinished) {
                mTimerText.setText(" Time Limit: " + (millisUntilFinished / 1000));
                mTimeRemaining = (int)(millisUntilFinished / 1000);
            }

             //If the timer is allowed to finish before the code is solved,
             // display a you lose message and set time remaining to 0
            public void onFinish() {
                mTimerText.setText("YOU SUCK");
                mTimeRemaining = 0;
                changeActivity(false);
            }
        }.start();
    }

    //This function is the setter for a user's guess, called at each button press
    public void setmGuess(int guess) {
        mGuess = guess;
        Log.d("MyTag", "Current Guess: " + mGuess);
    }

    //Go to post game activity
    public void changeActivity(Boolean won)
    {
        switchTo_PostGame = new Intent(GameActivity.this, PostGameActivity.class); //If it is a new user
        switchTo_PostGame.putExtra("Won", won); //This will change when people hack each other
        startActivity(switchTo_PostGame);
    }

    //This is the function to check the users guess
    public void checkGuess() {

        //If the game is finished, ignore button presses
        if (!mGameFinished) {

            //The guess is correct
            if (mGuess == mCodeArray[mGuessIndex]) { //Correct Guess

                //call this function to flash the buttons green
                setButtonColor("correct");

                //reset the mistake counter
                mMistakeCount = 0;

                //This block runs if the final digit is guessed
                if (mGuessIndex == (mCodeSize - 1)) {

                    //cancel the countdown and calculate the complete time
                    countDownTimer.cancel();
                    mCompleteTime = mTimeLimit - mTimeRemaining;

                    //*******************************************************************
                    //display a default message if the user didn't finish in time, or   *
                    //display how long they took if they finished in the time limit     *
                    //*******************************************************************
                    if (mTimeRemaining == 0) {
                        mTimerText.setText("DONE IN " + mTimeLimit + " SECONDS OR MORE");
                    }
                    else {
                        mTimerText.setText("DONE IN " + mCompleteTime + " SECONDS");
                    }

                    //set the game finished bool to true
                    mGameFinished = true;

                    //*******************************************************************************
                    //Convert the guess to a character and store it in the mask character array.    *
                    //I had to add 48 to get it to the correct ASCII code for the digits,           *
                    //I know it is dirty, and I am a little sorry, but not sorry enough to change it*
                    //*******************************************************************************
                    mCodeMask[mGuessIndex] = (char)(mGuess + 48);

                    //set the mask to the finish state, displaying the fully solved code
                    setmMaskString("finish");
                }
                //if it isn't the final index but correct
                else {
                    //Update the mask array
                    mCodeMask[mGuessIndex] = (char)(mGuess + 48);

                    //move the guess index up
                    mGuessIndex++;

                    //set the mask string to continue
                    setmMaskString("continue");
                }
            }
            else { //If incorrect Guess

                //increment mistake count
                mMistakeCount++;

                //if the mistake limit has just been reached
                if (mMistakeCount == mMistakeLimit) {

                    //only do the reset animation if you are not on the first index
                    if (mGuessIndex != 0) {

                        //if it isn't the first index, set the button color to reset
                        setButtonColor("reset");
                    }

                    //reset the guess index and mistake counter
                    mGuessIndex = 0;
                    mMistakeCount = 0;

                    //reset the code mask back to start state
                    setmMaskString("start");
                }
            }
        }
    }

    //This function sets the mask string
    public void setmMaskString(String state) {
        //The string must be reset to empty
        mMaskString = "";

        //If start, string becomes "_ ? ? ?"
        if (state.equals("start")) {
            for (int i = 0; i < mCodeSize; i++) {
                if (i == mGuessIndex)
                    mCodeMask[i] = '_';
                else
                    mCodeMask[i] = '?';
                mMaskString += mCodeMask[i];
                mMaskString += " ";
            }
        //If continue move the underscore over and reveal the solved digit
        }else if (state.equals("continue")) {
            for (int i = 0; i < mCodeSize; i++) {
                if (i == mGuessIndex)
                    mCodeMask[i] = '_';
                mMaskString += mCodeMask[i];
                mMaskString += " ";
            }
        //If finish, reveal the full code
        }else if (state.equals("finish")){
            for (int i = 0; i < mCodeSize; i++) {
                mMaskString += mCodeMask[i];
                mMaskString += " ";
            }

            changeActivity(true);//Change activity
        }

        //Display the new mask string
        mCodeText.setText(mMaskString);
    }

    //This function performs the button animations for a reset and a correct answer
    public void setButtonColor(String state) {

        //Get the vibrator ready
        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        //if a reset is needed
        if (state.equals("reset")) {

            //set all buttons to red
            Btn0.setBackgroundColor(Color.RED);
            Btn1.setBackgroundColor(Color.RED);
            Btn2.setBackgroundColor(Color.RED);
            Btn3.setBackgroundColor(Color.RED);
            Btn4.setBackgroundColor(Color.RED);
            Btn5.setBackgroundColor(Color.RED);
            Btn6.setBackgroundColor(Color.RED);
            Btn7.setBackgroundColor(Color.RED);
            Btn8.setBackgroundColor(Color.RED);
            Btn9.setBackgroundColor(Color.RED);

            //vibrate for 150 milliseconds
            vibrator.vibrate(150);

            //***********************************************************************************
            //Use a handler to perform a delayed reset of the buttons to there default color.   *
            //This allows the buttons to only be red for 150 milliseconds                       *
            //***********************************************************************************
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    Btn0.setBackgroundResource(android.R.drawable.btn_default);
                    Btn1.setBackgroundResource(android.R.drawable.btn_default);
                    Btn2.setBackgroundResource(android.R.drawable.btn_default);
                    Btn3.setBackgroundResource(android.R.drawable.btn_default);
                    Btn4.setBackgroundResource(android.R.drawable.btn_default);
                    Btn5.setBackgroundResource(android.R.drawable.btn_default);
                    Btn6.setBackgroundResource(android.R.drawable.btn_default);
                    Btn7.setBackgroundResource(android.R.drawable.btn_default);
                    Btn8.setBackgroundResource(android.R.drawable.btn_default);
                    Btn9.setBackgroundResource(android.R.drawable.btn_default);
                }
            }, 150);
        //if the correct state
        } else if (state.equals("correct")) {

            //set buttons to green
            Btn0.setBackgroundColor(Color.GREEN);
            Btn1.setBackgroundColor(Color.GREEN);
            Btn2.setBackgroundColor(Color.GREEN);
            Btn3.setBackgroundColor(Color.GREEN);
            Btn4.setBackgroundColor(Color.GREEN);
            Btn5.setBackgroundColor(Color.GREEN);
            Btn6.setBackgroundColor(Color.GREEN);
            Btn7.setBackgroundColor(Color.GREEN);
            Btn8.setBackgroundColor(Color.GREEN);
            Btn9.setBackgroundColor(Color.GREEN);

            //Like above return them to normal after 150 milliseconds
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Btn0.setBackgroundResource(android.R.drawable.btn_default);
                    Btn1.setBackgroundResource(android.R.drawable.btn_default);
                    Btn2.setBackgroundResource(android.R.drawable.btn_default);
                    Btn3.setBackgroundResource(android.R.drawable.btn_default);
                    Btn4.setBackgroundResource(android.R.drawable.btn_default);
                    Btn5.setBackgroundResource(android.R.drawable.btn_default);
                    Btn6.setBackgroundResource(android.R.drawable.btn_default);
                    Btn7.setBackgroundResource(android.R.drawable.btn_default);
                    Btn8.setBackgroundResource(android.R.drawable.btn_default);
                    Btn9.setBackgroundResource(android.R.drawable.btn_default);
                }
            }, 150);
        }
    }
}
