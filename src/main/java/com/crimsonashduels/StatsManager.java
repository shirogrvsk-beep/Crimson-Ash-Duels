package com.crimsonashduels;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatsManager {

    private final Map<UUID, Integer> wins = new HashMap<>();
    private final Map<UUID, Integer> losses = new HashMap<>();
    private final Map<UUID, Integer> duelsPlayed = new HashMap<>();

    public void recordWin(Player player) {
        UUID id = player.getUniqueId();
        wins.put(id, wins.getOrDefault(id, 0) + 1);
        duelsPlayed.put(id, duelsPlayed.getOrDefault(id, 0) + 1);
    }

    public void recordLoss(Player player) {
        UUID id = player.getUniqueId();
        losses.put(id, losses.getOrDefault(id, 0) + 1);
        duelsPlayed.put(id, duelsPlayed.getOrDefault(id, 0) + 1);
    }

    public int getWins(Player player) {
        return wins.getOrDefault(player.getUniqueId(), 0);
    }

    public int getLosses(Player player) {
        return losses.getOrDefault(player.getUniqueId(), 0);
    }

    public int getDuelsPlayed(Player player) {
        return duelsPlayed.getOrDefault(player.getUniqueId(), 0);
    }
}
