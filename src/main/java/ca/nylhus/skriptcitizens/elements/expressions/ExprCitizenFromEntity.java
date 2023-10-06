package ca.nylhus.skriptcitizens.elements.expressions;

import ca.nylhus.skriptcitizens.SkriptCitizens;
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
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@Name("Citizen from Entity")
@Description("Get a citizen from an entity.")
@Examples("set {_npc} to npc from entity target entity")
@Since("1.0.0")
public class ExprCitizenFromEntity extends SimpleExpression<NPC> {

    static {
        Skript.registerExpression(ExprCitizenFromEntity.class, NPC.class, ExpressionType.COMBINED,
                "(citizen|npc)[s] from entit(y|ies) %entities%");
    }

    private Expression<Entity> entities;

    @SuppressWarnings({"unchecked", "NullableProblems"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.entities = (Expression<Entity>) exprs[0];
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable NPC[] get(Event event) {
        List<NPC> npcs = new ArrayList<>();
        for (Entity entity : this.entities.getArray(event)) {
            NPC npc = SkriptCitizens.getNPCRegistry().getNPC(entity);
            if (npc != null) npcs.add(npc);
        }
        return npcs.toArray(new NPC[0]);
    }

    @Override
    public boolean isSingle() {
        return this.entities.isSingle();
    }

    @Override
    @NonNull
    public Class<? extends NPC> getReturnType() {
        return NPC.class;
    }

    @Override
    @NonNull
    public String toString(@Nullable Event event, boolean debug) {
        String plural = this.entities.isSingle() ? "entity " : "entities ";
        return "citizen[s] from " + plural + this.entities.toString(event, debug);
    }

}
