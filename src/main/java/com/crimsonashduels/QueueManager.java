package com.crimsonashduels;

import org.bukkit.entity.Player;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueManager {

    private final MatchManager matchManager;
    private final CrimsonAshDuels plugin;
    private final Queue<Player> queue = new LinkedList<>();
    private int arenaIndex = 0;

    public QueueManager(MatchManager matchManager, CrimsonAshDuels plugin) {
        this.matchManager = matchManager;
        this.plugin = plugin;
    }

    public void joinQueue(Player player) {
        if (!queue.contains(player)) {
            queue.add(player);
            player.sendMessage("§aYou joined the duel queue!");
            tryStartMatch();
        }
    }

    public void leaveQueue(Player player) {
        queue.remove(player);
        player.sendMessage("§cYou left the duel queue.");
    }

    private void tryStartMatch() {
        if (queue.size() >= 2) {
            Player p1 = queue.poll();
            Player p2 = queue.poll();

            Arena arena = getNextArena();
            if (arena != null) {
                matchManager.startMatch(p1, p2, arena);
            } else {
                p1.sendMessage("§cNo arenas available.");
                p2.sendMessage("§cNo arenas available.");
            }
        }
    }

    private Arena getNextArena() {
        List<String> names = plugin.getArenaNames();
        if (names.isEmpty()) return null;

        String arenaName = names.get(arenaIndex);
        arenaIndex = (arenaIndex + 1) % names.size();
        return plugin.getArena(arenaName);
    }
}
