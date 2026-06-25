package com.crimsonashduels;

import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.Queue;

public class QueueManager {

    private final Queue<Player> queue = new LinkedList<>();
    private final MatchManager matchManager;
    private final CrimsonAshDuels plugin;

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

            Arena arena = plugin.getArena("default"); // pick default arena for now
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
}
