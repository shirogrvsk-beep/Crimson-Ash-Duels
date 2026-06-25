package com.crimsonashduels;

import com.crimsonashduels.commands.DuelCommand;
import com.crimsonashduels.commands.DuelAcceptCommand;
import com.crimsonashduels.commands.SpectateCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CrimsonAshDuels extends JavaPlugin {

    private DuelManager duelManager;
    private MatchManager matchManager;
    private CooldownManager cooldownManager;
    private SpectatorManager spectatorManager;
    private Map<String, Arena> arenas = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("Crimson Ash Duels enabled!");

        saveDefaultConfig();
        loadArenas();

        duelManager = new DuelManager();
        matchManager = new MatchManager();
        cooldownManager = new CooldownManager(30); // 30-second cooldown
        spectatorManager = new SpectatorManager();

        getCommand("duel").setExecutor(new DuelCommand(duelManager, cooldownManager));
        getCommand("duelaccept").setExecutor(new DuelAcceptCommand(duelManager, matchManager, this));
        getCommand("spectate").setExecutor(new SpectateCommand(spectatorManager));

        getServer().getPluginManager().registerEvents(new DuelListener(matchManager), this);
    }

    private void loadArenas() {
        ConfigurationSection section = getConfig().getConfigurationSection("arenas");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Arena arena = Arena.fromConfig(section.getConfigurationSection(key));
                arenas.put(key, arena);
                getLogger().info("Loaded arena: " + key);
            }
        }
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }

    @Override
    public void onDisable() {
        getLogger().info("Crimson Ash Duels disabled!");
    }
}
