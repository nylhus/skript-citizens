package ca.nylhus.skriptcitizens;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.citizensnpcs.Metrics;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public final class SkriptCitizens extends JavaPlugin {

    private static SkriptCitizens instance;
    private static SkriptAddon addon;
    private static Logger logger;
    private static NPCRegistry npcRegistry;

    public static void info(String message) {
        logger.info(message);
    }

    public static SkriptCitizens getInstance() {
        return instance;
    }

    public static NPCRegistry getNPCRegistry() {
        return npcRegistry;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        logger = this.getLogger();
        npcRegistry = CitizensAPI.getNPCRegistry();
        try {
            addon.loadClasses("ca.nylhus.skriptcitizens");
            addon.setLanguageFileDirectory("lang");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Metrics metrics = new Metrics(this, 19228);
        SkriptCitizens.info("Skript-Citizens has been enabled.");

    }

}
