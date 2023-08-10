package ca.nylhus.skriptcitizens.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@Name("Citizen Entity")
@Description("Get the entity of a citizen.")
@Examples("set {_e} to citizen entity of {_npc}")
@Since("1.0.0")
public class ExprCitizenEntity extends SimplePropertyExpression<NPC, Entity> {

    static {
        register(ExprCitizenEntity.class, Entity.class, "(citizen|npc) entity", "npcs");
    }

    @Override
    public @Nullable Entity convert(NPC npc) {
        return npc.getEntity();
    }

    @Override
    @NonNull
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }

    @Override
    @NonNull
    protected String getPropertyName() {
        return "citizen entity";
    }

}
