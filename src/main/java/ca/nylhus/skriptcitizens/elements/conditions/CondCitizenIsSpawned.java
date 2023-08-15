package ca.nylhus.skriptcitizens.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import net.citizensnpcs.api.npc.NPC;
import org.eclipse.jdt.annotation.NonNull;

@Name("Citizen Is Spawned")
@Description("Check if a citizen is spawned. If it is not spawned that means it's registered but not in the world.")
@Examples("if citizen from id 1 is spawned:")
@Since("1.0.0")
public class CondCitizenIsSpawned extends PropertyCondition<NPC> {

    static {
        register(CondCitizenIsSpawned.class, PropertyType.BE, "spawned", "npcs");
    }

    @Override
    public boolean check(NPC npc) {
        return npc.isSpawned();
    }

    @Override
    @NonNull
    protected String getPropertyName() {
        return "spawned";
    }

}
