package com.crimsonashduels;

import com.crimsonashduels.commands.DuelCommand;
import com.crimsonashduels.commands.DuelAcceptCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CrimsonAshDuels extends JavaPlugin {

    private DuelManager duelManager;
    private MatchManager matchManager;

    @Override
    public void onEnable() {
        getLogger().info("Crimson Ash Duels enabled!");

        duelManager = new DuelManager();
        matchManager = new MatchManager();

        getCommand("duel").setExecutor(new DuelCommand(duelManager));
        getCommand("duelaccept").setExecutor(new DuelAcceptCommand(duelManager, matchManager));

        getServer().getPluginManager().registerEvents(new DuelListener(matchManager), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Crimson Ash Duels disabled!");
    }
}
