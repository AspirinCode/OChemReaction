package com.somerobot.ochemreaction;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

/**
 * Created by D on 2/5/2016.
 */
public class ReactionView extends LinearLayout {

    ImageView reactant, reagent, product, unknownImage;
    Drawable unknownDrawable;
    Context context;


    public ReactionView(Context context) {
        super(context);
        init(context);
    }

    public ReactionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReactionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /*
    public ReactionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
*/
    private void init(Context context) {
        this.context = context;

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) context.getSystemService(infService);
        li.inflate(R.layout.reaction, this, true);

        reactant = (ImageView) findViewById(R.id.reactant);
        reagent = (ImageView) findViewById(R.id.reagent);
        product = (ImageView) findViewById(R.id.product);

        Log.d("ReactionView", "ReactionView initialized");
    }


    public void solveReaction() {
        unknownImage.setImageDrawable(unknownDrawable);
    }

    private void setupReaction(final ImageView image, int replacementFileId) {

        unknownImage = image;
        unknownDrawable = image.getDrawable();
        image.setImageResource(replacementFileId);

    }

    public void loadReaction(Reaction reaction) throws ImageNotFoundException {

        Log.d("loadReaction", reaction.getReactant() + " > " + reaction.getReagent() + " > " + reaction.getProduct());

        reactant.setImageResource(getImageId(reaction.getReactant()));
        reagent.setImageResource(getImageId(reaction.getReagent()));
        product.setImageResource(getImageId(reaction.getProduct()));

        Random rand = new Random();
        int n = rand.nextInt(6);

        switch (n) {
            case 0:
                setupReaction(reactant, getImageId(context.getString(R.string.moleculeUnknownImage))); // 1 of 6 chance
                break;
            case 1:
            case 2:
            case 3:
                setupReaction(reagent, getImageId(context.getString(R.string.reagentUnknownImage)));  // 3 of 6 chance
                break;
            case 4:
            case 5:
            default:
                setupReaction(product, getImageId(context.getString(R.string.moleculeUnknownImage)));   // 2 of 6 chance, plus if somehow the random breaks bounds
                break;

        }

    }

    private int getImageId(String filename) throws ImageNotFoundException {

        int resId = context.getResources().getIdentifier(filename, "drawable", context.getPackageName());

        try {
            if (resId == 0) ;
        } catch (Resources.NotFoundException e) {
            throw new ImageNotFoundException("Could not find: " + filename);
        }

        return resId;
    }

}
