package scripts.JUniCows.handlers;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import scripts.JUniCows.data.Constants;
import scripts.JUniCows.data.Vars;
import scripts.heyimjamie.inventory.JInventory;
import scripts.heyimjamie.sleep.Sleep;
import scripts.heyimjamie.webwalker_logic.WebWalker;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.WalkingCondition;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class BankingHandler {

    public static void resetInventory() {

        if (Banking.isInBank())
            walkToBank();
        else
            sortInventory();

    }

    private static void walkToBank() {
        if (Constants.UNICOW_AREA.contains(Player.getPosition())) {
            RSItem[] ardyTele = Inventory.find("Ardougne teleport");
            if (JInventory.contains("Ardougne teleport") && AccurateMouse.click(ardyTele[0], "Break")) {
                Sleep.waitCondition(() -> !Constants.UNICOW_AREA.contains(Player.getPosition()), 5000);
                Sleep.randomSD(750, 300);
            }
        } else
            WebWalker.walkToBank(new WalkingCondition() {
                @Override
                public State action() {
                    if (Banking.isInBank())
                        return State.EXIT_OUT_WALKER_SUCCESS;
                    return State.CONTINUE_WALKER;
                }
            });
        General.sleep(100, 500);
    }

    private static void sortInventory() {
        if (!Banking.isBankScreenOpen() && Banking.openBank())
            Sleep.waitCondition(() -> Banking.isBankScreenOpen(), 3000);
        else if (Inventory.getCount("Unicorn horn") > 10)
            Banking.deposit(0, "Unicorn horn");
        else if (Inventory.getCount(Vars.get().foodName) < 3){
            if (Banking.withdraw(3 - Inventory.getCount(Vars.get().foodName), Vars.get().foodName))
                Sleep.waitCondition(() -> Inventory.getCount(Vars.get().foodName) == 3, 2000);
        } else if (Inventory.getCount("Cowhide") < 10){
            if (Banking.withdraw(10 - Inventory.getCount("Cowhide"), "Cowhide"))
                Sleep.waitCondition(() -> Inventory.getCount(Vars.get().foodName) == 10, 2000);
        } else if (Inventory.getCount("Unicorn horn") < 10){
            if (Banking.withdraw(10 - Inventory.getCount("Unicorn horn"), "Unicorn horn"))
                Sleep.waitCondition(() -> Inventory.getCount(Vars.get().foodName) == 10, 2000);
        }
    }
}
