package com.crimsonashduels;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class StatsManager {

    private final CrimsonAshDuels plugin;
    private File statsFile;
    private FileConfiguration statsConfig;

    public StatsManager(CrimsonAshDuels plugin) {
        this.plugin = plugin;
        createFile();
    }

    private void createFile() {
        statsFile = new File(plugin.getDataFolder(), "stats.yml");
        if (!statsFile.exists()) {
            try {
                statsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        statsConfig = YamlConfiguration.loadConfiguration(statsFile);
    }

    public void saveStats() {
        try {
            statsConfig.save(statsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordWin(Player player) {
        UUID id = player.getUniqueId();
        int wins = statsConfig.getInt(id + ".wins", 0);
        int duels = statsConfig.getInt(id + ".duels", 0);

        statsConfig.set(id + ".wins", wins + 1);
        statsConfig.set(id + ".duels", duels + 1);
        saveStats();
    }

    public void recordLoss(Player player) {
        UUID id = player.getUniqueId();
        int losses = statsConfig.getInt(id + ".losses", 0);
        int duels = statsConfig.getInt(id + ".duels", 0);

        statsConfig.set(id + ".losses", losses + 1);
        statsConfig.set(id + ".duels", duels + 1);
        saveStats();
    }

    public int getWins(Player player) {
        return statsConfig.getInt(player.getUniqueId() + ".wins", 0);
    }

    public int getLosses(Player player) {
        return statsConfig.getInt(player.getUniqueId() + ".losses", 0);
    }

    public int getDuelsPlayed(Player player) {
        return statsConfig.getInt(player.getUniqueId() + ".duels", 0);
    }

    // Reset all stats for a new season
    public void resetSeason() {
        for (String key : statsConfig.getKeys(false)) {
            statsConfig.set(key + ".wins", 0);
            statsConfig.set(key + ".losses", 0);
            statsConfig.set(key + ".duels", 0);
        }
        saveStats();
        plugin.getLogger().info("Player stats have been reset for the new season!");
    }
}
