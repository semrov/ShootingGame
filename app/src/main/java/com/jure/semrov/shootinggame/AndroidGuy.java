package com.jure.semrov.shootinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Jure on 6.3.2018.
 */

public class AndroidGuy
{
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    float x; // Guy's top left corner (x,y)
    float y;
    float stepX = 10; // Guy's step in (x,y) direction
    float stepY = 5; // gives speed of motion, larger means faster speed
    int lowerX, lowerY, upperX, upperY; // boundaries

    Bitmap android_guy;
    private Context mContext;

    public AndroidGuy(int color, Context c)
    {
        mContext = c;
        android_guy = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                mContext.getResources(),R.mipmap.ic_launcher),WIDTH,HEIGHT,false);
    }

    public void setBounds(int lX,int lY,int uX, int uY)
    {
        lowerX = lX;
        lowerY = lY;
        upperX = uX;
        upperY = uY;
        reset();
    }

    public boolean move()
    {
        // Get new (x,y) position. Movement is always in vertical direction downwards
        y+=stepY;
        // Detect when the guy reaches the bottom of the screen
        return !(y + HEIGHT > upperY);
    }

    // When you reset, starts the Android Guy from a random X co-ordinate location
    // at the top of the screen again
    public void reset()
    {
        x = (float) ((upperX-50)*Math.random());
        y = 0;
    }

    // Returns the rectangle enclosing the Guy. Used for collision detection
    public RectF getRect()
    {
        return new RectF(x,y,x+WIDTH,x+HEIGHT);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(android_guy,x,y,null);
    }

}
