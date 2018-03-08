package com.jure.semrov.shootinggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;


public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private int width, height;
    private DrawViewThread drawviewthread; // reference to the background thread

    Context mContext;

    // We can have multiple bullets and explosions
    // keep track of them in ArrayList
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Explosion> explosions = new ArrayList<>();
    Cannon cannon;
    AndroidGuy androidGuy;
    Score score;

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        mContext = context;

        // set that the callbacks for the SurfaceView are implemented in this class
        getHolder().addCallback(this);

        setFocusable(true);
        this.requestFocus();

        // create a cannon object
        cannon = new Cannon(Color.BLUE,context);

        // create arraylists to keep track of bullets and explosions
        bullets = new ArrayList<Bullet> ();
        explosions = new ArrayList<Explosion>();

        // create the falling Android Guy
        androidGuy = new AndroidGuy(Color.RED, mContext);

        //create score view
        score = new Score(Color.BLACK);
    }


    /*@Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }*/

    @Override
    // Called if the surface is changed
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    // Called when the surface is created.
    public void surfaceCreated(SurfaceHolder holder) {
        // Starts a new background thread which is used to update the canvas
        drawviewthread = new DrawViewThread(holder);
        drawviewthread.setRunning(true);
        drawviewthread.start();
    }

    @Override
    // This is called when the activity is shutdown. remove the background thread.
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawviewthread.setRunning(false);
        while (retry)
        {
            try
            {
                drawviewthread.join();
                retry = false;
            }
            catch (InterruptedException e){}
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        cannon.setBounds(0,0,width,height);
        androidGuy.setBounds(0,0,width,height);
        for (Bullet bullet : bullets)
        {
            bullet.setBounds(0,0,width,height);
        }
    }

    public void drawGameBoard(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        // Draw the cannon and score
        cannon.draw(canvas);
        score.draw(canvas);

        //Draw all the bullets
        for(int i = 0; i < bullets.size(); i++)
        {
            Bullet bullet = bullets.get(i);
            if(bullet != null)
            {
                bullet.draw(canvas);

                if(!bullet.move())
                {
                    bullets.remove(i);
                }
            }
        }

        // Draw all the explosions, at those locations where the bullet
        // hits the Android Guy
        for(int i = 0; i < explosions.size(); i++)
        {
            Explosion e = explosions.get(i);
            if(e != null)
            {
                if(!e.draw(canvas))
                {
                    explosions.remove(i);
                }
            }
        }
        // If the Android Guy is falling, check to see if any of the bullets
        // hit the Guy
        if (androidGuy != null) {
            androidGuy.draw(canvas);

            RectF guyRect = androidGuy.getRect();

            for (int i = 0; i < bullets.size(); i++ ) {

                // The rectangle surrounding the Guy and Bullet intersect, then it's a collision
                // Generate an explosion at that location and delete the Guy and bullet. Generate
                // a new Android Guy to fall from the top.
                if (RectF.intersects(guyRect, bullets.get(i).getRect())) {
                    explosions.add(new Explosion(Color.RED,mContext, androidGuy.getX(), androidGuy.getY()));
                    androidGuy.reset();
                    bullets.remove(i);
                    score.incrementScore();
                    break;
                }

            }

            if (!androidGuy.move()) {
                score.decrementScore();
                androidGuy.reset();
            }
        }
    }

    // Move the cannon left or right
    public void moveCannonLeft()
    {
        cannon.moveLeft();
    }
    public void moveCannonRight()
    {
        cannon.moveRight();
    }

    // Whenever the user shoots a bullet, create a new bullet moving upwards
    public void shootCannon()
    {
        bullets.add(new Bullet(Color.RED,mContext, cannon.getPosition(),(float) (height - Cannon.CANNON_HEIGHT)));
    }

    public void stopGame()
    {
        if (drawviewthread != null)
        {
            drawviewthread.setRunning(false);
        }
    }

    public void resumeGame()
    {
        if (drawviewthread != null)
        {
            drawviewthread.setRunning(true);
        }
    }

    public void releaseResources(){

    }

    // This is the code for the background thread.
    public class DrawViewThread extends Thread
    {
        private final SurfaceHolder surfaceHolder;
        private boolean threadIsRunning = true;

        public DrawViewThread(SurfaceHolder surfaceHolder)
        {
            this.surfaceHolder = surfaceHolder;
            setName("DrawViewThread");
        }

        public void setRunning (boolean running){
            threadIsRunning = running;
        }

        // This method is called when the thread starts running
        @Override
        public void run() {
            Canvas canvas = null;
            while (threadIsRunning)
            {
                try
                {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        drawGameBoard(canvas);
                    }
                    sleep(30);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if(canvas != null)
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }

        }
    }
}

