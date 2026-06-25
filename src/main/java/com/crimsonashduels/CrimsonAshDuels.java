package com.crimsonashduels;

import com.crimsonashduels.commands.DuelCommand;
import com.crimsonashduels.commands.DuelAcceptCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CrimsonAshDuels extends JavaPlugin {

    private DuelManager duelManager;

    @Override
    public void onEnable() {
        getLogger().info("Crimson Ash Duels enabled!");

        duelManager = new DuelManager();

        getCommand("duel").setExecutor(new DuelCommand(duelManager));
        getCommand("duelaccept").setExecutor(new DuelAcceptCommand(duelManager));

        // Register event listener
        getServer().getPluginManager().registerEvents(new DuelListener(duelManager), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Crimson Ash Duels disabled!");
    }
}
