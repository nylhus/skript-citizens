package ca.nylhus.skriptcitizens.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@Name("Citizen Despawn/Respawn")
@Description({"Despawning a citizen will remove it from the world, but won't deregister it.",
        "Respawning a citizen will spawn it back into the world."})
@Examples({"despawn last spawned npc", "respawn last spawned npc at player"})
@Since("1.0.0")
public class EffDespawn extends Effect {

    static {
        Skript.registerEffect(EffDespawn.class, "[(citizen|npc)] despawn %npcs%",
                "[(citizen|npc)] respawn %npcs% %direction% %location%");
    }

    private int pattern;
    private Expression<NPC> npcs;
    private Expression<Location> location;

    @SuppressWarnings({"unchecked", "NullableProblems"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.pattern = matchedPattern;
        this.npcs = (Expression<NPC>) exprs[0];
        if (matchedPattern == 1) {
            this.location = Direction.combine((Expression<? extends Direction>) exprs[1], (Expression<? extends Location>) exprs[2]);
        }
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void execute(Event event) {
        Location location = this.location != null ? this.location.getSingle(event) : null;
        for (NPC npc : this.npcs.getArray(event)) {
            if (this.pattern == 0) {
                npc.despawn(DespawnReason.PLUGIN);
            } else if (location != null) {
                npc.spawn(location, SpawnReason.PLUGIN);
            }
        }
    }

    @Override
    @NonNull
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0) {
            return "citizen despawn " + this.npcs.toString(event, debug);
        }
        return "citizen respawn " + this.npcs.toString(event, debug) + this.location.toString(event, debug);
    }

}
