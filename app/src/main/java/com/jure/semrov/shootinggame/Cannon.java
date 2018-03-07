package com.jure.semrov.shootinggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Jure on 7.3.2018.
 */

public class Cannon
{
    private static final int WIDTH_LR = 30;

    // Cannon's center (x,y)
    float x=-1,y=-1;
    // Cannon's step in  x direction
    int lowerX, lowerY, upperX, upperY;
    // Cannon's step in  x direction
    float stepX = 15;
    // The paint style, color used for drawing
    Paint paint;

    private Context mContext;

    public Cannon(int color, Context c)
    {
        paint = new Paint();
        paint.setColor(color);
        mContext = c;
    }

    public void setBounds(int lowerX, int lowerY, int upperX, int upperY)
    {
        this.lowerX = lowerX;
        this.lowerY = lowerY;
        this.upperX = upperX;
        this.upperY = upperY;

        x = upperX/2;
        y = upperY;
    }

    public void moveLeft()
    {
        // Get new (x,y) position of the canvas by moving it left
        // when the left button is clicked. Ensure that it does not
        // move off the screen.
        if(x - WIDTH_LR > 0)
        {
            x -= stepX;
        }
    }

    public void moveRight()
    {
        // Get new (x,y) position of the canvas by moving it right
        // when the right button is clicked. Ensure that it does not
        // move off the screen.
        if(x + WIDTH_LR < upperX)
        {
            x += stepX;
        }
    }

    public float getPosition() {
        return x;
    }

    // Draw the cannon on the canvas
    public void draw(Canvas canvas)
    {
        canvas.drawLine(x,y-100,x,y,paint);
        canvas.drawRect(x-WIDTH_LR,y-10,x+WIDTH_LR,y,paint);
        canvas.drawRect(x-10,y-40,x+10,y,paint);
    }

}
