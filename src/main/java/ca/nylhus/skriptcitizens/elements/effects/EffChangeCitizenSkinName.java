package ca.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Set Citizen Skin - Name")
@Description("Set a citizen's skin by name." +
        "Only works if citizen is a player.")
@RequiredPlugins("Citizens")
@Since("1.0.0")
@Examples("")
public class EffChangeCitizenSkinName extends Effect {
    static {
        Skript.registerEffect(EffChangeCitizenSkinName.class, "(set|change) (citizen|npc) %number% skin to %string%");
    }

    private Expression<Number> id;
    private Expression<String> name;

    @Override
    protected void execute(Event e) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
        SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
        trait.setShouldUpdateSkins(true);
        trait.setSkinName(name.getSingle(e));
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        name = (Expression<String>) exprs[1];
        return true;
    }
}
