package scripts.JUniCows;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.JUniCows.data.Constants;
import scripts.JUniCows.data.Vars;
import scripts.JUniCows.handlers.*;
import scripts.JUniCows.observers.InventoryListener;
import scripts.JUniCows.observers.InventoryObserver;
import scripts.JUniCows.utils.Antiban;
import scripts.heyimjamie.abc3.ABC2;
import scripts.heyimjamie.dataformatting.ValueFormatting;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.WaitFor;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.interaction_handling.NPCInteraction;

import java.awt.*;

import static scripts.JUniCows.framework.StateHandler.getBotState;

@ScriptManifest(authors = "HeyImJamie",
        category = "Money making",
        name = "JUnicowsLocal",
        description = "Kills Unicows in the Tower of Life for Profit.")

public class JUnicows extends Script implements Painting, InventoryListener {


    @Override
    public void run() {

        Vars.get().shouldRun = true;
        Vars.get().foodName = "Trout";

        initiateObservers();

        while (Vars.get().shouldRun) {

            switch (getBotState()) {

                case INITIALIZING:
                    break;

                case LOGIN:
                    WaitFor.milliseconds(100);
                    break;

                case WALK_TO_AREA:
                    WalkingHandler.walkTo(Constants.UNICOW_AREA);
                    break;

                case BANK:
                    BankingHandler.resetInventory();
                    break;

                case LOOT:
                    LootingHandler.lootItems();
                    break;

                case KILL:
                    CombatHandler.handleCombat();
                    break;

                case HANDLE_DIALOGUE:
                    NPCInteraction.handleConversation();
                    while (CombatHandler.getValidNPC() == null) { // Waiting for cow to spawn.
                        Vars.get().status = "Waiting for Spawn.";
                        Antiban.handleIdleActions();
                    }
                    break;

                case ACTIVATE_OBELISK:
                    ObeliskHandler.activateObelisk();
                    break;

            }
            General.sleep(100, 250);
        }


    }

    @Override
    public void onPaint(Graphics g) {

        long profit = ((Vars.get().hornCount * 250) - (Vars.get().hideCount * 80));
        long profitPerHour = (int) (profit * 3600000.0D / getRunningTime());

        g.setColor(Color.WHITE);

        g.drawString("JUnicows Version 1.0", 10, 285);
        g.drawString("Runtime: " + ValueFormatting.formatTime(getRunningTime()), 10, 300);
        g.drawString("Status: " + Vars.get().status, 10, 315);
        g.drawString("Profit: " + ValueFormatting.formatValue(profit) + " (" + ValueFormatting.formatValue(profitPerHour) + "/Hr)", 10, 330);

    }

    private void initiateObservers(){
        InventoryObserver inventoryObserver = new InventoryObserver(new Condition() {
            @Override
            public boolean active() {
                return !Banking.isBankScreenOpen();
            }
        });
        inventoryObserver.addListener(this);
        inventoryObserver.start();
        General.println("Inventory Listener Thread Started.");
    }
    @Override
    public void inventoryItemGained(int id, int count) {
        if (RSItemDefinition.get(id).getName().equals("Unicorn horn")){
            Vars.get().hornCount++;
        }
    }

    @Override
    public void inventoryItemLost(int id, int count) {
        if (RSItemDefinition.get(id).getName().equals("Unicorn horn")){
            Vars.get().hornCount--;
        } else if (RSItemDefinition.get(id).getName().equals("Cowhide")){
            Vars.get().hideCount++;
        }
    }
}
