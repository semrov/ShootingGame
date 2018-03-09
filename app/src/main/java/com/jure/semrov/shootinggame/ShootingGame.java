package com.jure.semrov.shootinggame;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class ShootingGame extends AppCompatActivity implements View.OnClickListener {

    private ImageButton leftButton, shootButton, rightButton;
    private DrawView drawView;
    private boolean play_music;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a reference to the Custom View
        drawView = findViewById(R.id.drawView);

        // Get reference to the buttons and set their onClickListeners
        leftButton = findViewById(R.id.moveLeftButton);
        leftButton.setOnClickListener(this);
        rightButton = findViewById(R.id.moveRightButton);
        rightButton.setOnClickListener(this);
        shootButton = findViewById(R.id.shootButton);
        shootButton.setOnClickListener(this);

        // Set the context of the SoundEffects singleton class
        //  Add context to the SoundEffects class
        SoundEffects.INSTANCE.initSounds(this);

        //create media player instance for playing music while playing the game
        mediaPlayer = MediaPlayer.create(this,R.raw.braincandy);
        mediaPlayer.setLooping(true);
        play_music = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu,menu);
        if(play_music)
        {
            menu.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_up_white_24dp);
        }
        else
        {
            menu.findItem(R.id.action_sound).setIcon(R.drawable.ic_volume_off_white_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as there is specified a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_sound)
        {
            if(play_music)
            {
                mediaPlayer.pause();
                item.setIcon(R.drawable.ic_volume_off_white_24dp);
            }
            else
            {
                mediaPlayer.start();
                item.setIcon(R.drawable.ic_volume_up_white_24dp);
            }

            play_music = !play_music;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if(play_music)
        {
            mediaPlayer.pause();
        }
        drawView.stopGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawView.resumeGame();
        if(play_music)
        {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy()
    {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        play_music = false;

        super.onDestroy();
    }

    @Override
    public void onClick(View v)
    {
        // Using the View's ID to distinguish which button was clicked
        switch (v.getId())
        {
            case R.id.moveLeftButton:
                drawView.moveCannonLeft();
                break;
            case R.id.moveRightButton:
                drawView.moveCannonRight();
                break;
            case R.id.shootButton:
                drawView.shootCannon();
                SoundEffects.INSTANCE.play_sound(SoundEffects.SOUND_SHOOT);
                break;
            default:
                Toast.makeText(this, "Unhandled button pressed: " + v.getId(),Toast.LENGTH_LONG).show();
                break;
        }
    }
}
