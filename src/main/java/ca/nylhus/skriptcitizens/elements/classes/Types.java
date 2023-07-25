package ca.nylhus.skriptcitizens.elements.classes;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;
import net.citizensnpcs.api.npc.NPC;

public class Types {

    static {
        Classes.registerClass(new ClassInfo<>(NPC.class, "npc")
                .user("npcs?")
                .name("npc")
                .description("Citizens npcs")
                .examples("last spawned npc")
                .since("1.0.0"));
    }
}
