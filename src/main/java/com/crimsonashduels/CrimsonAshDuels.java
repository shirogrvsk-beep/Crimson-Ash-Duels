package com.crimsonashduels;

import com.crimsonashduels.commands.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class CrimsonAshDuels extends JavaPlugin {

    private DuelManager duelManager;
    private MatchManager matchManager;
    private CooldownManager cooldownManager;
    private SpectatorManager spectatorManager;
    private QueueManager queueManager;
    private StatsManager statsManager;
    private EloManager eloManager;
    private ArenaResetManager arenaResetManager;
    private Map<String, Arena> arenas = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("Crimson Ash Duels enabled!");

        saveDefaultConfig();
        loadArenas();

        duelManager = new DuelManager();
        matchManager = new MatchManager(this);
        cooldownManager = new CooldownManager(30);
        spectatorManager = new SpectatorManager();
        queueManager = new QueueManager(matchManager, this);
        statsManager = new StatsManager(this);
        eloManager = new EloManager(this);
        arenaResetManager = new ArenaResetManager(this);

        // Register commands
        getCommand("duel").setExecutor(new DuelCommand(duelManager, cooldownManager));
        getCommand("duelaccept").setExecutor(new DuelAcceptCommand(duelManager, matchManager, this));
        getCommand("spectate").setExecutor(new SpectateCommand(spectatorManager));
        getCommand("queue").setExecutor(new QueueCommand(queueManager));
        getCommand("stats").setExecutor(new StatsCommand(this));
        getCommand("topwins").setExecutor(new TopWinsCommand(this));
        getCommand("topduels").setExecutor(new TopDuelsCommand(this));
        getCommand("topwinsgui").setExecutor(new TopWinsGUICommand(this));
        getCommand("topduelsgui").setExecutor(new TopDuelsGUICommand(this));
        getCommand("elo").setExecutor(new EloCommand(this));
        getCommand("topelo").setExecutor(new TopEloCommand(this));
        getCommand("topelogui").setExecutor(new TopEloGUICommand(this));
        getCommand("resetseason").setExecutor((sender, command, label, args) -> {
            if (!sender.hasPermission("crimsonashduels.admin")) {
                sender.sendMessage("§cYou do not have permission to reset the season.");
                return true;
            }
            statsManager.resetSeason();
            eloManager.resetSeason();
            sender.sendMessage("§6Season has been reset! All stats and ELO cleared.");
            return true;
        });
        getCommand("savearena").setExecutor(new SaveArenaCommand(this));
        getCommand("restorearena").setExecutor(new RestoreArenaCommand(this));
        getCommand("rotatearenas").setExecutor((sender, command, label, args) -> {
            if (!sender.hasPermission("crimsonashduels.admin")) {
                sender.sendMessage("§cYou do not have permission to rotate arenas.");
                return true;
            }
            rotateArenas();
            sender.sendMessage("§6Arena rotation triggered.");
            return true;
        });

        // Register events
        getServer().getPluginManager().registerEvents(new DuelListener(matchManager), this);

        // Schedule automatic arena rotation
        long intervalMinutes = getConfig().getLong("arena-rotation-interval-minutes", 60);
        getServer().getScheduler().runTaskTimer(this,
            this::rotateArenas,
            20L * 60 * intervalMinutes,
            20L * 60 * intervalMinutes);
    }

    private void loadArenas() {
        ConfigurationSection section = getConfig().getConfigurationSection("arenas");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Arena arena = Arena.fromConfig(section.getConfigurationSection(key));
                arenas.put(key, arena);
                getLogger().info("Loaded arena: " + key);

                File schemFile = new File(getDataFolder(), key + ".schem");
                if (!schemFile.exists()) {
                    getLogger().info("No schematic found for arena " + key + ", saving now...");
                    arenaResetManager.saveArena(key, arena.getCorner1(), arena.getCorner2());
                }
            }
        }
    }

    public void rotateArenas() {
        getLogger().info("Arena rotation triggered.");
        for (String arenaName : getArenaNames()) {
            Arena arena = getArena(arenaName);
            arenaResetManager.restoreArena(arenaName, arena.getPasteLocation());
        }
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }

    public List<String> getArenaNames() {
        return new ArrayList<>(arenas.keySet());
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public EloManager getEloManager() {
        return eloManager;
    }

    public ArenaResetManager getArenaResetManager() {
        return arenaResetManager;
    }

    @Override
    public void onDisable() {
        statsManager.saveStats();
        eloManager.saveElo();
        getLogger().info("Crimson Ash Duels disabled!");
    }
}
