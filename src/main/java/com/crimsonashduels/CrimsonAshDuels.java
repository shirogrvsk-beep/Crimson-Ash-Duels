package com.crimsonashduels;

import com.crimsonashduels.commands.*;
import com.crimsonashduels.listeners.KitSelectorListener;
import com.crimsonashduels.listeners.KitPreviewListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class CrimsonAshDuels extends JavaPlugin {

    // ... existing fields ...

    @Override
    public void onEnable() {
        getLogger().info("Crimson Ash Duels enabled!");

        saveDefaultConfig();
        loadArenas();

        duelManager = new DuelManager();
        matchManager = new MatchManager(this);
        cooldownManager = new CooldownManager(getConfig().getInt("duel-request-cooldown", 30));
        spectatorManager = new SpectatorManager();
        queueManager = new QueueManager(matchManager, this);
        statsManager = new StatsManager(this);
        eloManager = new EloManager(this);
        arenaResetManager = new ArenaResetManager(this);
        kitManager = new KitManager(this);

        // Register commands
        getCommand("duel").setExecutor(new DuelCommand(this));
        // ... other command registrations ...
        getCommand("crimson").setExecutor(new CrimsonCommand(this, kitManager));

        // Register events
        getServer().getPluginManager().registerEvents(new DuelListener(matchManager), this);
        getServer().getPluginManager().registerEvents(new KitSelectorListener(this, kitManager), this);
        getServer().getPluginManager().registerEvents(new KitPreviewListener(this), this);

        // Schedule automatic arena rotation
        long intervalMinutes = getConfig().getLong("arena-rotation-interval-minutes", 60);
        getServer().getScheduler().runTaskTimer(this,
            this::rotateArenas,
            20L * 60 * intervalMinutes,
            20L * 60 * intervalMinutes);
    }

    // ... rest of class unchanged ...
}
