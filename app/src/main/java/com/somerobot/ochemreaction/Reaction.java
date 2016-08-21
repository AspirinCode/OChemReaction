package com.somerobot.ochemreaction;

import android.util.Log;

/**
 * Created by D on 2/5/2016.
 */
public class Reaction {

    String reactant, reagent, product;
    boolean enabled;

    public Reaction(boolean enabled, String reactant, String reagent, String product) {

        this.enabled = enabled;
        this.reactant = reactant;
        this.reagent = reagent;
        this.product = product;

    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getReactant() {
        return reactant;
    }

    public String getReagent() {
        return reagent;
    }

    public String getProduct() {
        return product;
    }

    public String toString() {

        return enabled + " > " + reactant + " > " + reagent + " > " + product;

    }





}
