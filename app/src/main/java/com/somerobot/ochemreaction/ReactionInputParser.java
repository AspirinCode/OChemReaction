package com.somerobot.ochemreaction;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by D on 2/5/2016.
 */
public class ReactionInputParser {

    ReactionLibrary library;
    Context context;


    public ReactionInputParser(Context context, ReactionLibrary library, InputStream file) {

        this.library = library;
        this.context = context;

        Scanner sc = new Scanner(file); //load file
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            parseReactionInput(line);   //parse it
        }
        sc.close(); //close it
    }

    private void parseReactionInput(String reaction) {

        //Lines in the reaction should be in the form of {'e' or 'd' for enabled or disable}>{reactant image name}>{reagent image name}>{product image name}
        String[] reactionParts = reaction.split(">");
        Log.d("parseReactionInput", "Got: " + reactionParts[0] + ">" + reactionParts[1] + ">" + reactionParts[2] + ">" + reactionParts[3]);

        if(!testImage(context, reactionParts[1])) return;
        if(!testImage(context, reactionParts[2])) return;
        if(!testImage(context, reactionParts[3])) return;

        library.addReaction(new Reaction(reactionParts[0].equals("e"), reactionParts[1], reactionParts[2], reactionParts[3]));

    }

    //Check if the image is valid in drawables
    private boolean testImage (Context context, String s) {

        boolean exists = context.getResources().getIdentifier(s, "drawable", context.getPackageName()) != 0 ? true : false;

        if (!exists) Log.d("parseReactionInput","Failed to load: " + s);

        return exists;
    }




}
