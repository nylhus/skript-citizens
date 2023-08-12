package ca.nylhus.skriptcitizens.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.event.NPCSelectEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eclipse.jdt.annotation.Nullable;

@SuppressWarnings("unused")
public class SimpleEvents {

    static {
        // Events
        Skript.registerEvent("Citizen Click", SimpleEvent.class, NPCClickEvent.class,
                        "(citizen|npc) click")
                .description("Called when an NPC is clicked by a player.")
                .examples("on npc click:", "\tbroadcast \"an NPC has been clicked!\"")
                .since("1.0.0");
        Skript.registerEvent("Citizen Left Click", SimpleEvent.class, NPCLeftClickEvent.class,
                        "(citizen|npc) left click")
                .description("Called when an NPC is left-clicked by a player.")
                .examples("on npc left click:", "\tkill player")
                .since("1.0.0");

        Skript.registerEvent("Citizen Right Click", SimpleEvent.class, NPCRightClickEvent.class,
                        "(citizen|npc) right click")
                .description("Called when an NPC is right-clicked by a player.")
                .examples("on npc right click:", "\tteleport player to spawn of world \"world\"")
                .since("1.0.0");

        Skript.registerEvent("Citizen Select", SimpleEvent.class, NPCSelectEvent.class,
                        "(citizen|npc) select")
                .description("Called when an NPC is selected by a player.")
                .since("1.0.0");

        Skript.registerEvent("Citizen Despawn", SimpleEvent.class, NPCDespawnEvent.class,
                        "(citizen|npc) despawn")
                .description("Called when an NPC despawns.")
                .since("1.0.0");

        Skript.registerEvent("Citizen Spawn", SimpleEvent.class, NPCSpawnEvent.class,
                        "(citizen|npc) spawn")
                .description("Called when an NPC spawns.")
                .since("1.0.0");

        // Event Values
        EventValues.registerEventValue(NPCEvent.class, NPC.class, new Getter<NPC, NPCEvent>() {
            @Override
            public @Nullable NPC get(NPCEvent event) {
                return event.getNPC();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCClickEvent.class, Player.class, new Getter<Player, NPCClickEvent>() {
            @Override
            public @Nullable Player get(NPCClickEvent event) {
                return event.getClicker();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCSelectEvent.class, CommandSender.class, new Getter<CommandSender, NPCSelectEvent>() {
            @Override
            public @Nullable CommandSender get(NPCSelectEvent event) {
                return event.getSelector();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCDespawnEvent.class, DespawnReason.class, new Getter<DespawnReason, NPCDespawnEvent>() {
            @Override
            public @Nullable DespawnReason get(NPCDespawnEvent event) {
                return event.getReason();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCSpawnEvent.class, Location.class, new Getter<Location, NPCSpawnEvent>() {
            @Override
            public @Nullable Location get(NPCSpawnEvent event) {
                return event.getLocation();
            }
        }, EventValues.TIME_NOW);

        EventValues.registerEventValue(NPCSpawnEvent.class, SpawnReason.class, new Getter<SpawnReason, NPCSpawnEvent>() {
            @Override
            public @Nullable SpawnReason get(NPCSpawnEvent event) {
                return event.getReason();
            }
        }, EventValues.TIME_NOW);
    }

}
