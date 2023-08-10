package ca.nylhus.skriptcitizens.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@Name("Citizen from ID")
@Description("Get a citizen from ID.")
@Examples("set {_npc} to citizen from id 1")
@Since("1.0.0")
public class ExprCitizenFromID extends SimpleExpression<NPC> {

    private static final NPCRegistry NPC_REGISTRY = CitizensAPI.getNPCRegistry();

    static {
        Skript.registerExpression(ExprCitizenFromID.class, NPC.class, ExpressionType.COMBINED,
                "(citizen|npc) from id %number%");
    }

    private Expression<Number> id;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.id = (Expression<Number>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable NPC[] get(Event event) {
        Number idNum = this.id.getSingle(event);
        if (idNum != null) {
            int id = idNum.intValue();
            NPC citizen = NPC_REGISTRY.getById(id);
            return new NPC[]{citizen};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    @NonNull
    public Class<? extends NPC> getReturnType() {
        return NPC.class;
    }

    @Override
    @NonNull
    public String toString(@Nullable Event event, boolean debug) {
        return "citizen from id " + this.id.toString(event, debug);
    }

}
