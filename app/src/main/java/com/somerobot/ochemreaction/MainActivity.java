package com.somerobot.ochemreaction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends Activity {

    ReactionLibrary library;
    boolean isReactionSolved = false;
    ReactionView reactionView, nextReactionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front);

        // Load the views
        reactionView = (ReactionView) findViewById(R.id.reactionView);
        Log.d("onCreate", reactionView != null ? "reactionView loaded" : "reactionView failed to load");
        Button button = (Button) findViewById(R.id.button);
        Log.d("onCreate", button != null ? "button loaded" : "button failed to load");

        // Populate the library
        try {
            library = new ReactionLibrary();
            new ReactionInputParser(getApplicationContext(), library, getAssets().open(getString(R.string.reactionFile)));

        } catch (IOException e) {
            Log.d("Debug", "Failed to load " + R.string.reactionFile);
        }

        //Load the first reaction
        loadReaction();

        //Create listener to check when the screen is clicked
        //Will solve the reaction if unsolved, and load a new reaction if the reaction is solved
        View.OnClickListener clicker = new View.OnClickListener() {
            public void onClick(View arg0) {
                Log.d("Clicker", "Screen clicked");
                if (isReactionSolved) { //when true, answer has been revealed
                    loadReaction();
                } else {                //when false, answer has not been shown yet
                    reactionView.solveReaction();
                }
                isReactionSolved = !isReactionSolved;
            }
        };

        button.setOnClickListener(clicker);
    }

    //Super lazy iterative error catcher, as long as 1 reaction is valid, it will eventually resolve
    //Shouldn't even need the exception, as images should be checked when loading into library
    private void loadReaction() {

        try {
            reactionView.loadReaction(library.getRandomReaction());
        } catch (ImageNotFoundException e) {
            loadReaction();
        }
    }

/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
