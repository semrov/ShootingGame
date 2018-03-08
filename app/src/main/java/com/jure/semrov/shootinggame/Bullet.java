package com.jure.semrov.shootinggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Jure on 7.3.2018.
 */

public class Bullet {
    float radius = 5; // Bullet's radius
    float x; // Bullet's center (x,y)
    float y;
    float stepX = 10; // Bullet's step of motion in (x,y) direction
    float stepY = 5; // gives speed of motion, larger means faster speed
    int lowerX, lowerY, upperX, upperY;
    private Paint paint; // The paint style, color used for drawing

    private Context mContext;

    // Constructor
    public Bullet(int color, Context c, float startx, float starty) {
        paint = new Paint();
        paint.setColor(color);

        mContext = c;
        x = startx;
        y = starty;
    }

    public void setBounds(int lx, int ly, int ux, int uy) {
        lowerX = lx;
        lowerY = ly;
        upperX = ux;
        upperY = uy;
    }

    // Rectangle enclosing the bullet. Used for collision detection with Guy
    public RectF getRect() {
        return new RectF(x-radius,y-radius,x+radius,y+radius);
    }

    // Move the bullet upwards by stepY every time. This creates the upward motion.
    public boolean move() {
        // Get new (x,y) position
        y -= stepY;
        // Detect when the bullet reaches the top ofhte screen
        // then remove the bullet
        if (y - radius < 0) {
            return false;
        }
        else
            return true;
    }

    // draw the bullet on the canvas
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }
}


/*
public class Bullet
{
    private static final float radius = (float) 5.0; // Bullet's radius
    // Bullet's center (x,y)
    private float x, y;
    // Bullet's step of motion in (x,y) direction float stepX, stepY; // Bullet's step of motion in (x,y) direction
    private float stepX = 10, stepY = 5;
    private int lowerX, lowerY, upperX, upperY;
    private Paint paint; // The paint style, color used for drawing
    private Context mContext;

    public Bullet(int color, Context c, float startx, float starty)
    {
        paint = new Paint();
        paint.setColor(color);
        mContext = c;
        x = startx;
        y = starty;
    }

    public void setBounds(int lowerX, int lowerY, int upperX, int upperY)
    {
        this.lowerX = lowerX;
        this.lowerY = lowerY;
        this.upperX = upperX;
        this.upperY = upperY;
    }

    // Rectangle enclosing the bullet. Used for collision detection with Guy
    public RectF getRect()
    {
        return new RectF(x-radius,y-radius,x+radius,y+radius);
    }

    // Move the bullet upwards by stepY every time. This creates the upward motion.
    public boolean move()
    {
        // Get new (x,y) position
        y-=stepY;
        // Detect when the bullet reaches the top ofhte screen
        // then remove the bullet
        return !(y - radius < 0);
    }

    // draw the bullet on the canvas
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x,y,radius,paint);
    }
}
*/
