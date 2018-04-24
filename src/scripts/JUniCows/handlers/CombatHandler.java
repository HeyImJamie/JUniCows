package scripts.JUniCows.handlers;

import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import scripts.JUniCows.data.Vars;
import scripts.JUniCows.utils.Antiban;
import scripts.heyimjamie.camera.ObjectHelper;
import scripts.heyimjamie.generic.JFilters;
import scripts.heyimjamie.sleep.Sleep;
import scripts.JUniCows.data.Constants;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class CombatHandler {

    public static void handleCombat(){

        if (!Combat.isAutoRetaliateOn()) {
            if (Combat.setAutoRetaliate(true)) {
                Sleep.waitCondition(() -> Combat.isAutoRetaliateOn(), 5000);
            }
        }
        
        if (Combat.isUnderAttack() || getInteractingNPC() != null) {
            RSNPC npc = getInteractingNPC();
            if (npc != null && npc.getHealthPercent() == 0)
                Sleep.waitCondition(() -> GroundItems.find("Unicorn horn").length > 0, 5000);
            else {
                Antiban.handleIdleActions();
            }
        } else
            attackUnicow();
    }

    private static void attackUnicow() {

        RSNPC unicow = getValidNPC();

        if (Game.getItemSelectionState() == 1) {
            clickInventoryTab();
            Sleep.randomSD(750, 200);
        }

        if (Game.getRunEnergy() > Vars.get().activateRunPercent) {
            Options.setRunOn(true);
            Vars.get().activateRunPercent = Antiban.getRunPercentage();
            Sleep.randomSD(750, 200);
        }

        if (unicow != null && (!unicow.isOnScreen() && !unicow.isClickable())) {
            ObjectHelper.attemptAimCamera(unicow, 20);
            Sleep.randomSD(750, 200);
        }

        if (unicow != null && (unicow.isOnScreen() && unicow.isClickable())) {
            RSGroundItem[] itemsToLoot = GroundItems.find("Unicorn horn");
            if (AccurateMouse.click(unicow, "Attack")) {
                Sleep.waitCondition(() -> Combat.isUnderAttack() || itemsToLoot.length > 0, 3000);
            }
            Sleep.randomSD(750, 200);
        }
    }


    private static void clickInventoryTab() {
        RSInterface tab = Interfaces.get(548, 50);
        if (tab != null)
            tab.click();
    }

    public static RSNPC getValidNPC() {
        RSNPC interacting = getInteractingNPC();
        if (interacting != null) {
            return interacting;
        }

        RSNPC[] available = getAvailableValidNPCS();
        return available.length > 0 ? available[0] : null;
    }

    private static RSNPC getInteractingNPC() {
        RSNPC[] interacting = NPCs.findNearest(
                Filters.NPCs.nameContains("Unicow")
                        .combine(JFilters.INTERACTING, false)
        );
        return interacting.length > 0 ? interacting[0] : null;
    }

    private static RSNPC[] getAvailableValidNPCS() {
        return NPCs.findNearest(
                Filters.NPCs.nameContains("Unicow")
                        .combine(JFilters.OUT_OF_COMBAT, false)
        );
    }

}
