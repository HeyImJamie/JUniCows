package scripts.JUniCows.utils;

import org.tribot.api.General;
import org.tribot.api.util.abc.ABCUtil;

public class Antiban extends ABCUtil {

    private static Antiban _instance = null;

    private int runPercentage = this.generateRunActivation();
    private int eatPercentage = this.generateEatAtHP();

    /**
     * Returns the instance of this ABC2.
     * @return the instance.
     */
    public static Antiban get() {
        return _instance = _instance != null ? _instance : new Antiban();
    }

    /**
     * Gets the health percentage to eat at.
     */
    public static int getEatPercentage() {
        return get().eatPercentage;
    }

    /**
     * Generates a new percentage to eat at.
     */
    public static void generateEatPercentage() {
        get().eatPercentage = get().generateEatAtHP();
    }

    /**
     * Gets the energy percentage.
     */
    public static int getRunPercentage() {
        return get().runPercentage;
    }

    /**
     * Generates a new energy percentage.
     */
    public static void generateRunPercentage() {
        get().runPercentage = get().generateRunActivation();
    }

    /**
     * Handles idle actions.
     */
    public static void handleIdleActions() {

        if (get().shouldCheckTabs()) {
            General.println("[ABC2] : Checking Tabs. ");
            get().checkTabs();
        }

        if (get().shouldCheckXP()) {
            General.println("[ABC2] : Hovering Xp. ");
            get().checkXP();
        }

        if (get().shouldExamineEntity()) {
            General.println("[ABC2] : Examining Random Entity. ");
            get().examineEntity();
        }

        if (get().shouldMoveMouse()) {
            General.println("[ABC2] : Moving Mouse. ");
            get().moveMouse();
        }

        if (get().shouldPickupMouse()) {
            General.println("[ABC2] : Picking Up Mouse. ");
            get().pickupMouse();
        }

        if (get().shouldRightClick()) {
            General.println("[ABC2] : Right Clicked.");
            get().rightClick();
        }

        if (get().shouldRotateCamera()) {
            General.println("[ABC2] : Rotating Camera. ");
            get().rotateCamera();
        }

        if (get().shouldLeaveGame()) {
            General.println("[ABC2] : Moving Mouse Off Screen. ");
            get().leaveGame();
        }
    }
}
