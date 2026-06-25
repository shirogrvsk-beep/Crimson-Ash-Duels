package com.crimsonashduels;

import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class QueueManager {

    private final Queue<Player> queue = new LinkedList<>();
    private final MatchManager matchManager;
    private final CrimsonAshDuels plugin;
    private int arenaIndex = 0;

    public QueueManager(MatchManager matchManager, CrimsonAshDuels plugin) {
        this.matchManager = matchManager;
        this.plugin = plugin;
    }

    public void joinQueue(Player player) {
        if (queue.contains(player)) {
            player.sendMessage("You are already in the duel queue.");
            return;
        }

        queue.add(player);
        player.sendMessage("You joined the duel queue.");

        if (queue.size() >= 2) {
            Player p1 = queue.poll();
            Player p2 = queue.poll();

            player.sendMessage("A duel has been found! " + p1.getName() + " vs " + p2.getName());

            Arena arena = getNextArena();
            arena.teleportPlayers(p1, p2);

            matchManager.startMatch(p1, p2);
        }
    }

    public void leaveQueue(Player player) {
        if (queue.remove(player)) {
            player.sendMessage("You left the duel queue.");
        } else {
            player.sendMessage("You are not in the duel queue.");
        }
    }

    public boolean isInQueue(Player player) {
        return queue.contains(player);
    }

    private Arena getNextArena() {
        List<String> arenaNames = plugin.getArenaNames();
        if (arenaNames.isEmpty()) {
            throw new IllegalStateException("No arenas configured!");
        }

        String arenaName = arenaNames.get(arenaIndex);
        arenaIndex = (arenaIndex + 1) % arenaNames.size();

        return plugin.getArena(arenaName);
    }
}
