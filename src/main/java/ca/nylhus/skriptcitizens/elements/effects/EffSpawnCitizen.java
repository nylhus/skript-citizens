package ca.nylhus.skriptcitizens.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Spawn Citizen - Skript-Citizens")
@Description("Spawns a customized Citizen")
@RequiredPlugins("Citizens")
@Examples("")
@Since("1.0.0")

public class EffSpawnCitizen extends Effect {

    private Expression<Location> location;
    private Expression<EntityType> entity;
    private Expression<String> name;

    static {
        Skript.registerEffect(EffSpawnCitizen.class, "(spawn|create) [a[n]] [%-entitytype%] citizen [named %-string%] at %location%)");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        location = (Expression<Location>) exprs[1];
        entity = ((Expression<EntityType>) exprs[2]);
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Spawned " + entity.toString() + " Citizen name " + name.toString() + " at " + location.toString();
    }

    @Override
    protected void execute(Event e) {
        EntityType citizenType = (entity.getSingle(e) != null) ? entity.getSingle(e) : EntityType.PLAYER;
        String citizenName = (name.getSingle(e) != null) ? name.getSingle(e) : "";
        NPC citizen = CitizensAPI.getNPCRegistry().createNPC(citizenType, citizenName);
    }

}