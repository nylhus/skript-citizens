package ca.nylhus.skriptcitizens.classes;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.coll.CollectionUtils;
import ch.njol.skript.util.EnumUtils;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.SpawnReason;
import net.citizensnpcs.api.npc.NPC;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@SuppressWarnings("unused")
public class Types {

    static {
        Classes.registerClass(new ClassInfo<>(NPC.class, "npc")
                .user("npcs?")
                .name("Citizens NPC")
                .description("Represents a Citizens NPC.")
                .examples("last spawned npc", "delete last spawned npc")
                .since("1.0.0")
                .parser(new Parser<NPC>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    @NonNull
                    public String toString(NPC npc, int flags) {
                        return "citizen with id " + npc.getId();
                    }

                    @Override
                    @NonNull
                    public String toVariableNameString(NPC npc) {
                        return "citizen:" + npc.getId();
                    }
                })
                .changer(new Changer<NPC>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
                        if (mode == ChangeMode.DELETE) return CollectionUtils.array();
                        return null;
                    }

                    @SuppressWarnings("NullableProblems")
                    @Override
                    public void change(NPC[] what, @Nullable Object[] delta, ChangeMode mode) {
                        if (mode == ChangeMode.DELETE) {
                            for (NPC npc : what) {
                                npc.destroy();
                            }
                        }
                    }
                }));

        EnumUtils<SpawnReason> SPAWN_REASON_ENUM = new EnumUtils<>(SpawnReason.class, "spawnreasons");
        Classes.registerClass(new ClassInfo<>(SpawnReason.class, "npcspawnreason")
                .user("npc ?spawn ?reasons?")
                .name("Citizens Spawn Reason")
                .description("Represents the reasons a Citizen will spawn.")
                .usage(SPAWN_REASON_ENUM.getAllNames())
                .examples("if event-npcspawnreason = chunk load:")
                .since("1.0.0")
                .parser(new Parser<SpawnReason>() {

                    @SuppressWarnings("NullableProblems")
                    @Override
                    public @Nullable SpawnReason parse(String string, ParseContext context) {
                        return SPAWN_REASON_ENUM.parse(string);
                    }

                    @Override
                    @NonNull
                    public String toString(SpawnReason spawnReason, int flags) {
                        return SPAWN_REASON_ENUM.toString(spawnReason, flags);
                    }

                    @Override
                    @NonNull
                    public String toVariableNameString(SpawnReason spawnReason) {
                        return "npcspawnreason:" + toString(spawnReason, 0);
                    }
                }));

        EnumUtils<DespawnReason> DESPAWN_REASON_ENUM = new EnumUtils<>(DespawnReason.class, "despawnreasons");
        Classes.registerClass(new ClassInfo<>(DespawnReason.class, "npcdespawnreason")
                .user("npc ?despawn ?reasons?")
                .name("Citizens Despawn Reason")
                .description("Represents the reasons a Citizen will despawn.")
                .user(DESPAWN_REASON_ENUM.getAllNames())
                .examples("if event-npcdespawnreason = chunk unload:")
                .since("1.0.0")
                .parser(new Parser<DespawnReason>() {

                    @SuppressWarnings("NullableProblems")
                    @Override
                    public @Nullable DespawnReason parse(String s, ParseContext context) {
                        return DESPAWN_REASON_ENUM.parse(s);
                    }

                    @Override
                    @NonNull
                    public String toString(DespawnReason despawnReason, int flags) {
                        return DESPAWN_REASON_ENUM.toString(despawnReason, flags);
                    }

                    @Override
                    @NonNull
                    public String toVariableNameString(DespawnReason despawnReason) {
                        return "npcdespawnreason:" + toString(despawnReason, 0);
                    }
                }));
    }

}
