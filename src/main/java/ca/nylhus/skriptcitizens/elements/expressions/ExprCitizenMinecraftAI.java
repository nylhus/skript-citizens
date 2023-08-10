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

@Name("Citizen Minecraft AI")
@Description("Represents whether the citizen should use vanilla Minecraft AI.")
@Examples("set should use ai of {_npc} to true")
@Since("1.0.0")
public class ExprCitizenMinecraftAI extends SimplePropertyExpression<NPC, Boolean> {

    static {
        register(ExprCitizenMinecraftAI.class, Boolean.class, "[(citizen|npc)] should use [minecraft] ai", "npcs");
    }

    @Override
    public @Nullable Boolean convert(NPC npc) {
        return npc.useMinecraftAI();
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
            boolean useAI = (boolean) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.setUseMinecraftAI(useAI);
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
        return "citizen should use minecraft ai";
    }
}
