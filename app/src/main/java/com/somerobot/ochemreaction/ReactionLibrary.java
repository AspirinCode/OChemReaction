package com.somerobot.ochemreaction;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by D on 2/5/2016.
 */
public class ReactionLibrary {

    List<Reaction> reactionList = new ArrayList<>();
    private Random randomGenerator = new Random();
    int lastPickedNumber = 1;

    Context context;

    public ReactionLibrary() {}

    public void addReaction(Reaction reaction) {

        reactionList.add(reaction);

    }

    public Reaction getRandomReaction() {

        Reaction reaction;
        int pickedNumber = 1;

        // Lazy finding function, will ignore those that are not enabled or were picked last time to prevent repeats
        do {
            pickedNumber = randomGenerator.nextInt(reactionList.size());
            reaction = reactionList.get(pickedNumber);

            Log.d("getRandomReaction", "Randomed reaction: " + reaction.toString());

        } while (!reaction.isEnabled() || lastPickedNumber == pickedNumber);

        lastPickedNumber = pickedNumber;

        return reaction;
    }

    public int getLibrarySize() {
        return reactionList.size();
    }

    public Reaction getReaction(int n) {
        return reactionList.get(n);
    }

}
