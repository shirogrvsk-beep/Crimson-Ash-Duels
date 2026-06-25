package com.crimsonashduels;

import org.bukkit.entity.Player;
import java.util.*;

public class StatsManager {
    private final Map<UUID, Integer> wins = new HashMap<>();
    private final Map<UUID, Integer> duels = new HashMap<>();

    public int getWins(Player player) { return wins.getOrDefault(player.getUniqueId(), 0); }
    public int getDuels(Player player) { return duels.getOrDefault(player.getUniqueId(), 0); }

    public void addWin(Player player) { wins.put(player.getUniqueId(), getWins(player) + 1); }
    public void addDuel(Player player) { duels.put(player.getUniqueId(), getDuels(player) + 1); }

    public List<String> getTopWins(int limit) {
        return wins.entrySet().stream()
                .sorted(Map.Entry.<UUID,Integer>comparingByValue().reversed())
                .limit(limit)
                .map(e -> e.getKey().toString() + ": " + e.getValue())
                .toList();
    }

    public List<String> getTopDuels(int limit) {
        return duels.entrySet().stream()
                .sorted(Map.Entry.<UUID,Integer>comparingByValue().reversed())
                .limit(limit)
                .map(e -> e.getKey().toString() + ": " + e.getValue())
                .toList();
    }
}
