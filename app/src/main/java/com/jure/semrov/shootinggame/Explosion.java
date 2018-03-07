package com.jure.semrov.shootinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Jure on 7.3.2018.
 */

public class Explosion
{
    private static final int width = 50, height = 50;

    // Ball's center (x,y)
    float x,y;
    int count;
    private Context mContext;

    private static Bitmap explosion;

    public Explosion(int color, Context c, float loc_x, float loc_y)
    {
        explosion = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                mContext.getResources(),R.drawable.explosion),width,height,false);

        mContext = c;
        x = loc_x;
        y = loc_y;
        count = 30;
    }

    // Explosion is drawn for about 1 second on the screen (30 times) before being removed.
    public boolean draw(Canvas canvas)
    {
        if(count-- == 0){return  false;}
        canvas.drawBitmap(explosion,x,y,null);
        return true;
    }

}
