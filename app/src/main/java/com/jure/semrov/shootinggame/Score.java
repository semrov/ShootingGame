package com.jure.semrov.shootinggame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by Jure on 7.3.2018.
 */

public class Score
{
    private Paint paint;
    private int score;

    public Score(int color)
    {
        paint = new Paint();
        // Set the font face and size of drawing text
        paint.setTypeface(Typeface.MONOSPACE);
        paint.setTextSize(24);
        paint.setColor(color);

        score = 0;
    }

    public void incrementScore() {
        score++;
    }

    public void decrementScore() {
        if(score > 0)
            score--;
    }

    public int getScore() { return score; }

    public void draw(Canvas canvas)
    {
        // draw score text on the canvas.
        canvas.drawText("Score: " + score, 10,30,paint);
    }
}
