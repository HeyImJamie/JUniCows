package scripts.JUniCows.framework;

import org.tribot.api2007.*;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSPlayer;
import scripts.JUniCows.data.Constants;
import scripts.JUniCows.data.Vars;
import scripts.JUniCows.handlers.CombatHandler;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.interaction_handling.NPCInteraction;

public class StateHandler {

    public enum BotState {
        INITIALIZING,
        LOGIN,
        HANDLE_DIALOGUE,
        BANK,
        WALK_TO_AREA,
        KILL,
        LOOT,
        ACTIVATE_OBELISK,
    }

    public static BotState getBotState(){

        RSPlayer player = Player.getRSPlayer();
        Vars vars = Vars.get();

        if (player == null){
            vars.status = "Intiliazing.";
            return BotState.INITIALIZING;
        }

        if (Login.getLoginState() != Login.STATE.INGAME){
            vars.status = "Logging In.";
            return BotState.LOGIN;
        }

        if (Inventory.getCount("Cowhide") == 0 || Inventory.getCount("Unicorn horn") == 0 || Inventory.getCount(Vars.get().foodName) == 0){
            vars.status = "Banking.";
            return BotState.BANK;
        }

        if (!Constants.UNICOW_AREA.contains(player.getPosition())){
            vars.status = "Walking to Area.";
            return BotState.WALK_TO_AREA;
        }

        RSGroundItem[] horns = GroundItems.find("Unicorn horn");

        if (horns.length > 0){
            vars.status = "Looting.";
            return BotState.LOOT;
        }

        if (!Combat.isAutoRetaliateOn()){
            vars.status = "Turning on Auto-retaliate.";
            return BotState.KILL;
        }

        RSNPC unicow = CombatHandler.getValidNPC();

        if (unicow != null){
            vars.status = "Killing Unicow.";
            return BotState.KILL;
        }

        if (NPCInteraction.isConversationWindowUp()){
            vars.status = "Handling Dialogue.";
            return BotState.HANDLE_DIALOGUE;
        }

        if (Inventory.getCount("Cowhide") > 0 && Inventory.getCount("Unicorn horn") > 0){
            vars.status = "Activating Obelisk.";
            return BotState.ACTIVATE_OBELISK;
        }

        return BotState.INITIALIZING;
    }

}
