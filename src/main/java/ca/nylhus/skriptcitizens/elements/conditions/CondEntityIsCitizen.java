package ca.nylhus.skriptcitizens.elements.conditions;

import ca.nylhus.skriptcitizens.SkriptCitizens;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import org.bukkit.entity.Entity;
import org.eclipse.jdt.annotation.NonNull;

@Name("Entity is Citizen")
@Description("Check if an entity is a citizen.")
@Examples("if target entity is a citizen:")
@Since("1.0.0")
public class CondEntityIsCitizen extends PropertyCondition<Entity> {

    static {
        register(CondEntityIsCitizen.class, PropertyType.BE, "[a] (citizen|npc)", "entities");
    }

    @Override
    public boolean check(Entity entity) {
        return SkriptCitizens.getNPCRegistry().isNPC(entity);
    }

    @Override
    @NonNull
    protected String getPropertyName() {
        return "a citizen";
    }

}
