package ca.nylhus.skriptcitizens.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import net.citizensnpcs.api.npc.NPC;

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
