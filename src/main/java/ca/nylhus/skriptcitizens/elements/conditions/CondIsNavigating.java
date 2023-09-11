package ca.nylhus.skriptcitizens.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import net.citizensnpcs.api.npc.NPC;

@Name("Citizen Is Navigating")
@Description("Checks if a citizen is navigating")
@Examples("if last spawned citizen is navigating:")
@Since("1.0.0")
public class CondIsNavigating extends PropertyCondition<NPC> {

    static {
        register(CondIsNavigating.class, PropertyType.BE, "navigating", "npcs");
    }
    @Override
    public boolean check(NPC npc) {
        return npc.getNavigator().isNavigating();
    }

    @Override
    protected String getPropertyName() {
        return "navigating";
    }
}
