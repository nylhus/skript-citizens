package ca.nylhus.skriptcitizens.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@Name("Citizen Protection")
@Description("Represents whether the citizen is protected from damage/entity target events. True by default.")
@Examples("set npc protection of {_n} to false")
@Since("1.0.0")
public class ExprCitizenProtection extends SimplePropertyExpression<NPC, Boolean> {

    static {
        register(ExprCitizenProtection.class, Boolean.class, "(citizen|npc) protection", "npcs");
    }

    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.isProtected();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET) return CollectionUtils.array(Boolean.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
        if (delta != null && delta[0] instanceof Boolean) {
            boolean protection = (boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.setProtected(protection);
            }
        }
    }

    @Override
    @NonNull
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    @NonNull
    protected String getPropertyName() {
        return "citizen protection";
    }

}
