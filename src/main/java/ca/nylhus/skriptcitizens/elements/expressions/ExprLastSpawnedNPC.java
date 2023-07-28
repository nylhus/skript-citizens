package ca.nylhus.skriptcitizens.elements.expressions;

import ca.nylhus.skriptcitizens.elements.effects.EffSpawnCitizen;
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
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Last Spawned Citizen")
@Description("Get the last spawned citizen.")
@Examples("set {_npc} to last spawned citizen")
@Since("1.0.0")
public class ExprLastSpawnedNPC extends SimpleExpression<NPC> {

    static {
        Skript.registerExpression(ExprLastSpawnedNPC.class, NPC.class, ExpressionType.SIMPLE,
                "last (spawned|created) (npc|citizen)");
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable NPC[] get(Event event) {
        return new NPC[]{EffSpawnCitizen.lastSpawnedNPC};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Class<? extends NPC> getReturnType() {
        return NPC.class;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "last spawned citizen";
    }

}
