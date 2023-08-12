package ca.nylhus.skriptcitizens.elements.expressions;

import ca.nylhus.skriptcitizens.SkriptCitizens;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@Name("All Citizens")
@Description({"Get a list of all citizens.",
        "\n`registered` = All currently registered citizens. (Default)",
        "\n`spawned` = All citizens which are currently spawned."})
@Examples({"loop all registered citizens:",
        "loop all citizens:",
        "loop all spawned citizens:",
        "set {_npcs::*} to all npcs"})
@Since("1.0.0")
public class ExprAllCitizens extends SimpleExpression<NPC> {

    static {
        Skript.registerExpression(ExprAllCitizens.class, NPC.class, ExpressionType.SIMPLE,
                "all [registered|:spawned] (citizen|npc)s");
    }

    private boolean spawned;

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.spawned = parseResult.hasTag("spawned");
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable NPC[] get(Event event) {
        List<NPC> allNPCs = new ArrayList<>();
        SkriptCitizens.getNPCRegistry().forEach(npc -> {
            if (!this.spawned || npc.isSpawned()) allNPCs.add(npc);
        });
        return allNPCs.toArray(new NPC[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    @NonNull
    public Class<? extends NPC> getReturnType() {
        return NPC.class;
    }

    @Override
    @NonNull
    public String toString(@Nullable Event e, boolean debug) {
        String type = this.spawned ? "spawned" : "registered";
        return "all " + type + " citizens";
    }

}
