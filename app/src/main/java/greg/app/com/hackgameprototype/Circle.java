package greg.app.com.hackgameprototype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;import java.lang.Override;

/*
 * Created by Alex on 4/21/2015.
 * This is the class that helps draw a circle on the frameLayout view(container, whatever).
 */

public class Circle extends View
{
	public float mX;    //Location X
    public float mY;    //Location Y

    public int mR;      //Radius


    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    //construct new ball object
    public Circle(Context context, float x, float y, int r)
    {
        super(context);

        //Sets Color
        mPaint.setARGB(255, 255, 0, 0);// alpha, red, green, blue (RED)

        this.mX = x;    //Sets LocationX to param X
        this.mY = y;    //Sets LocationY to param Y
        this.mR = r;    //Sets Radius to param r
    }
    	
    //called by invalidate()	
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mR, mPaint);
    }
}
