package scripts.JUniCows.handlers;

import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import scripts.JUniCows.data.Constants;
import scripts.heyimjamie.sleep.Sleep;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.interaction_handling.NPCInteraction;

public class ObeliskHandler {

    public static void activateObelisk(){
        RSObject[] obelisk = Objects.find(10, Filters.Objects.nameEquals("Symbol of life").combine(Filters.Objects.inArea(Constants.UNICOW_AREA), true));
        if (obelisk.length > 0 && AccurateMouse.click(obelisk[0], "Activate")) {
            Sleep.waitCondition(() -> NPCInteraction.isConversationWindowUp(), 5000);
        }
    }
}
