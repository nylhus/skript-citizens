package ca.nylhus.skriptcitizens.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.bukkitutil.EntityUtils;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Spawn Citizen - Skript-Citizens")
@Description("Spawns a customized Citizen")
@RequiredPlugins("Citizens")
@Examples("")
@Since("1.0.0")
public class EffSpawnCitizen extends Effect {

    public static NPC lastSpawnedNPC;

    static {
        Skript.registerEffect(EffSpawnCitizen.class, "(spawn|create) [a[n]] [%-entitydata%] citizen [named %-string%] at %location%");
    }

    private Expression<EntityData<?>> entityType;
    private Expression<String> name;
    private Expression<Location> location;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        entityType = ((Expression<EntityData<?>>) exprs[0]);
        name = (Expression<String>) exprs[1];
        location = (Expression<Location>) exprs[2];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void execute(Event event) {
        EntityType citizenType = EntityType.PLAYER;
        if (this.entityType != null) {
            EntityData<?> entityData = this.entityType.getSingle(event);
            if (entityData != null) citizenType = EntityUtils.toBukkitEntityType(entityData);
        }
        String citizenName = (this.name != null && this.name.getSingle(event) != null) ? this.name.getSingle(event) : "";
        Location location = this.location.getSingle(event);
        if (location != null) {
            lastSpawnedNPC = CitizensAPI.getNPCRegistry().createNPC(citizenType, citizenName, location);
        } else {
            lastSpawnedNPC = CitizensAPI.getNPCRegistry().createNPC(citizenType, citizenName);
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString(@Nullable Event e, boolean debug) {
        String type = this.entityType != null ? this.entityType.toString(e, debug) : "";
        String name = this.name != null ? (" named " + this.name.toString(e, debug)) : "";
        return "create " + type + " citizen" + name + " at " + this.location.toString();
    }

}
