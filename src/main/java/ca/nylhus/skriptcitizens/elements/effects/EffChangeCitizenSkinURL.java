package ca.nylhus.skriptcitizens.elements.effects;

import ca.nagasonic.skonic.elements.util.MojangSkinGenerator;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@Name("Set Citizen Skin - URL")
@Description("Sets the citizen with the id specified to the skin linked on the url.")
@RequiredPlugins("Citizens")
@Since("1.0.0")
@Examples("")
public class EffChangeCitizenSkinURL extends Effect {

    static {
        Skript.registerEffect(EffChangeCitizenSkinURL.class, "(change|set) (citizen|npc) %number% skin to url %string%");
    }

    private Expression<Number> id;
    private Expression<String> url;

    @Override
    protected void execute(Event e) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(id.getSingle(e).intValue());
        SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
        JsonObject data = null;
        try {
            data = MojangSkinGenerator.generateFromURL(url.getSingle(e), false);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
        String uuid = data.get("uuid").getAsString();
        JsonObject texture = data.get("texture").getAsJsonObject();
        String textureEncoded = texture.get("value").getAsString();
        String signature = texture.get("signature").getAsString();
        Bukkit.getScheduler().runTask(CitizensAPI.getPlugin(), () -> {
           try {
               trait.setSkinPersistent(uuid, signature, textureEncoded);
               Bukkit.getLogger().log(Level.INFO, "Set skin of citizen with id " + id.getSingle(e) + " to " + url.getSingle(e));
           } catch (IllegalArgumentException err) {
               Bukkit.getLogger().log(Level.SEVERE, "There was an error setting the skin of citizen with id " + id.getSingle(e) + " to " + url.getSingle(e) + err.getMessage());
           }
        });
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<Number>) exprs[0];
        url = (Expression<String>) exprs[1];
        return true;
    }
}
