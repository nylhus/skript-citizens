package ca.nylhus.skriptcitizens;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.citizensnpcs.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public final class SkriptCitizens extends JavaPlugin {

    private static SkriptCitizens instance;
    private SkriptAddon addon;
    private static Logger logger;

    public static void info(String message) {
        logger.info(message);
    }

    public static SkriptCitizens getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    @Override
    public void onEnable() {

        instance = this;
        addon = Skript.registerAddon(this);
        logger = this.getLogger();
        try {
            addon.loadClasses("ca.nylhus.skriptcitizens");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Metrics metrics = new Metrics(this, 19228);
        SkriptCitizens.info("Skript-Citizens has been enabled.");

    }

}