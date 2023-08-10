package ca.nylhus.skriptcitizens.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@Name("Citizen ID")
@Description("Get the ID of a citizen.")
@Examples("set {_id} to citizen id of last spawned citizen")
@Since("1.0.0")
public class ExprCitizenID extends SimplePropertyExpression<NPC,Number> {

    static {
        register(ExprCitizenID.class, Number.class, "(citizen|npc) id", "npcs");
    }

    @Override
    public @Nullable Number convert(NPC npc) {
        return npc.getId();
    }

    @Override
    @NonNull
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    @NonNull
    protected String getPropertyName() {
        return "citizen id";
    }

}
