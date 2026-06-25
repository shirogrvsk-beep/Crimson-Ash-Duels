package com.crimsonashduels;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    // Leaderboard helpers
    public List<Map.Entry<String, Integer>> getTopWins(int limit) {
        Map<String, Integer> winsMap = new HashMap<>();
        for (String key : statsConfig.getKeys(false)) {
            winsMap.put(key, statsConfig.getInt(key + ".wins", 0));
        }
        return winsMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Map.Entry<String, Integer>> getTopDuels(int limit) {
        Map<String, Integer> duelsMap = new HashMap<>();
        for (String key : statsConfig.getKeys(false)) {
            duelsMap.put(key, statsConfig.getInt(key + ".duels", 0));
        }
        return duelsMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
