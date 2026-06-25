package com.crimsonashduels;

import com.crimsonashduels.commands.DuelCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CrimsonAshDuels extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Crimson Ash Duels enabled!");
        getCommand("duel").setExecutor(new DuelCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("Crimson Ash Duels disabled!");
    }
}
