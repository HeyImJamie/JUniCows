package scripts.JUniCows.data;

import java.util.HashMap;

public class Vars {

    private Vars() {}
    private static final Vars VARS = new Vars();
    public static Vars get() { return VARS; }

    public HashMap<String,Integer> bankCache = null;

    //booleans
    public boolean shouldRun;

    //Paint / Info Tracking
    public String status = "Loading.";

    public String foodName;

    public int hornCount, hideCount;

    //ABCL
    public int hpToEat;
    public int activateRunPercent;

}
