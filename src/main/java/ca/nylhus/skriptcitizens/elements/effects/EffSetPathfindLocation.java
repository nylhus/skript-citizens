package ca.nylhus.skriptcitizens.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.ai.Navigator;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Set Citizen Pathfind Location")
@Description("Makes a Citizen move/pathfind to a set location")
@Examples("")
@Since("1.0.0")

public class EffSetPathfindLocation extends Effect {

    private Expression<Location> location;
    private Expression<Number> speed;
    private Expression<NPC> npc;

    static {
        Skript.registerEffect(EffSetPathfindLocation.class, "set pathfind location of %npc% to %location%", "move %npc% to %location%");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npc = (Expression<NPC>) exprs[0];
        location = (Expression<Location>) exprs[1];
        speed = (Expression<Number>) exprs[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return npc + " is now pathfinding to " + location;
    }

    @Override
    protected void execute(Event e) {
        NPC citizen = npc.getSingle(e);
        Location target = location.getSingle(e);
        Number moveSpeed = (speed.getSingle(e) != null) ? speed.getSingle(e) : 1;
        if (citizen != null && target != null) {
            Navigator navigator = citizen.getNavigator();
            navigator.getDefaultParameters().baseSpeed(moveSpeed.floatValue());
            navigator.setTarget(target);
        }
    }
}