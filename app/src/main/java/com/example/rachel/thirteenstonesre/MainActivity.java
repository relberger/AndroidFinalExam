package com.example.rachel.thirteenstonesre;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.rachel.thirteenstonesre.ThirteenStones.getGameFromJSON;
import static com.example.rachel.thirteenstonesre.ThirteenStones.getJSONof;
import static com.example.rachel.thirteenstonesre.Utils.showInfoDialog;

public class MainActivity extends AppCompatActivity
{
    private ThirteenStones mCurrentGame;
    private TextView mStatusBar;
    private View sbContainer;
    private static final String KEY = "game";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mStatusBar = findViewById(R.id.tv_status_bar);
        sbContainer = findViewById(R.id.activity_main);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showInfoDialog(MainActivity.this, getString(R.string.info_title), mCurrentGame.getRules());
            }
        });

        startFirstGame();
    }

    private void startFirstGame()
    {
        mCurrentGame = new ThirteenStones();
        updateStatusBar();
    }

    private void startNextNewGame()
    {
        mCurrentGame.startGame();
        updateStatusBar();
    }

    private void updateStatusBar()
    {
        TextView statusBar = findViewById(R.id.tv_status_bar);
        statusBar.setText("Current Player: " + mCurrentGame.getCurrentOrWinningPlayerNumberOneOrTwo()
                + "; Stones remaining in pile: " + mCurrentGame.getPileCurrent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case (R.id.action_new_game):
                startNextNewGame();
                break;
            case (R.id.action_about):
                showInfoDialog(this, "About", "Rachel Elberger");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    public void pick123(View view)
    {
        Button button = (Button)view;
        mCurrentGame.takeTurn(Integer.parseInt(button.getText().toString()));
        updateStatusBar();

        if (mCurrentGame.isGameOver())
        {
            showInfoDialog(this, "Game Over", "The winner is player "
                    + mCurrentGame.getCurrentOrWinningPlayerNumberOneOrTwo());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {

        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(KEY, getJSONof(mCurrentGame));
    }

    /*
    crashes on rotate
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentGame = getGameFromJSON(savedInstanceState.getString(KEY));
        updateStatusBar();
    }
}
