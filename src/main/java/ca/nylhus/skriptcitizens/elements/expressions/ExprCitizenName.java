package ca.nylhus.skriptcitizens.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@Name("Citizen Name")
@Description("Represents the name of a citizen. Full name is the name of the citizen, non-full is the name without colors.")
@Examples({"set citizen name of {_npc} to \"&aBob\"",
        "if npc full name of {_npc} contains \"&aBob\":"})
@Since("1.0.0")
public class ExprCitizenName extends SimplePropertyExpression<NPC, String> {

    static {
        register(ExprCitizenName.class, String.class, "(citizen|npc) [:full] name", "npcs");
    }

    private boolean full;

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.full = parseResult.hasTag("full");
        return super.init(exprs, matchedPattern, isDelayed, parseResult);
    }

    @Override
    public @Nullable String convert(NPC npc) {
        return this.full ? npc.getFullName() : npc.getName();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET) return CollectionUtils.array(String.class);
        return null;
    }

    @SuppressWarnings({"NullableProblems", "ConstantValue"})
    @Override
    public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
        if (delta != null && delta[0] instanceof String) {
            String name = (String) delta[0];
            for (NPC npc : getExpr().getArray(event)) {
                npc.setName(name);
            }
        }
    }

    @Override
    @NonNull
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    @NonNull
    protected String getPropertyName() {
        String full = this.full ? " full" : "";
        return "citizen" + full + " name";
    }

}
