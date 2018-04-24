package scripts.JUniCows.handlers;

import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import scripts.JUniCows.data.Vars;
import scripts.heyimjamie.inventory.JInventory;
import scripts.heyimjamie.sleep.Sleep;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class LootingHandler {

    public static void lootItems(){
        RSGroundItem[] horns = GroundItems.find("Unicorn horn");
        if (horns.length > 0 && !Inventory.isFull() && AccurateMouse.click(horns[0], "Take")) {
            int hornCount = Inventory.getCount("Unicorn horn");
            Sleep.waitCondition(() -> Inventory.getCount("Unicorn horn") > hornCount, 5000);
            Sleep.randomSD(100, 50);

        } else if (Inventory.getCount(Vars.get().foodName) > 0) {
            RSItem[] food = Inventory.find(Vars.get().foodName);
            if (food.length > 0 && AccurateMouse.click(food[0], "Eat")){
                Sleep.waitCondition(() -> !JInventory.isFull(), 5000);
            }

        } else if (Inventory.getCount("Cowhide") > 0) {
            RSItem[] cowhide = Inventory.find("Cowhide");
            if (cowhide.length > 0 && AccurateMouse.click(cowhide[0], "Drop")){
                Sleep.waitCondition(() -> !JInventory.isFull(), 5000);
            }
        }
    }
}
