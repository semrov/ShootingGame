package com.jure.semrov.shootinggame;

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
    private Menu menu;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu,menu);
        this.menu = menu;

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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        drawView.stopGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        drawView.resumeGame();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
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
                break;
            default:
                Toast.makeText(this, "Unhandled button pressed: " + v.getId(),Toast.LENGTH_LONG).show();
                break;
        }
    }
}
