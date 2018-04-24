package scripts.JUniCows.handlers;

import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.heyimjamie.sleep.Sleep;
import scripts.heyimjamie.webwalker_logic.WebWalker;
import scripts.heyimjamie.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class WalkingHandler {

    public static boolean walkTo(RSArea area) {

        RSObject[] trapdoor = Objects.find(5, "Trapdoor");
        if (trapdoor.length > 0) {
            if (AccurateMouse.click(trapdoor[0], "Open", "Climb-down"))
                Sleep.randomSD(750, 300);
        } else if (Player.getPosition().getY() < 4000) {
            WebWalker.walkTo(new RSTile(2649, 3213, 0));
        } else
            Walking.walkPath(Walking.randomizePath(pathToObelisk, 3, 3));
        return area.contains(Player.getPosition());
    }

    private static RSTile[] pathToObelisk = {
            new RSTile(3038, 4377, 0),
            new RSTile(3035, 4387, 0),
            new RSTile(3034, 4390, 0),
            new RSTile(3028, 4398, 0),
            new RSTile(3027, 4400, 0),
            new RSTile(3020, 4405, 0),
            new RSTile(3019, 4407, 0)
    };
}
