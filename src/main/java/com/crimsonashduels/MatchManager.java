package com.crimsonashduels;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MatchManager {

    private final Map<Player, Match> activeMatches = new HashMap<>();
    private final CrimsonAshDuels plugin;

    public MatchManager(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    public void startMatch(Player p1, Player p2, Arena arena, String kitName) {
        // Restore arena before duel begins
        plugin.getArenaResetManager().restoreArena(arena.getName(), arena.getPasteLocation());

        // Apply kit
        if (kitName.equalsIgnoreCase("own")) {
            // Players keep their own inventory
            p1.sendMessage("§eYou are using your own inventory.");
            p2.sendMessage("§eYou are using your own inventory.");
        } else {
            ItemStack[] kitItems = plugin.getKitManager().getKit(kitName);
            if (kitItems != null) {
                p1.getInventory().clear();
                p2.getInventory().clear();
                p1.getInventory().setContents(kitItems.clone());
                p2.getInventory().setContents(kitItems.clone());
                p1.sendMessage("§aKit " + kitName + " applied.");
                p2.sendMessage("§aKit " + kitName + " applied.");
            } else {
                p1.sendMessage("§cKit not found, using own inventory.");
                p2.sendMessage("§cKit not found, using own inventory.");
            }
        }

        Match match = new Match(p1, p2, arena);
        activeMatches.put(p1, match);
        activeMatches.put(p2, match);
    }

    public boolean isInMatch(Player player) {
        return activeMatches.containsKey(player);
    }

    public Match getMatch(Player player) {
        return activeMatches.get(player);
    }

    public void endMatch(Match match, Player winner, Player loser) {
        match.endMatch(winner, loser);

        plugin.getStatsManager().recordWin(winner);
        plugin.getStatsManager().recordLoss(loser);
        plugin.getEloManager().updateElo(winner, loser);

        plugin.getArenaResetManager().restoreArena(match.getArena().getName(), match.getArena().getPasteLocation());

        activeMatches.remove(match.getPlayer1());
        activeMatches.remove(match.getPlayer2());
    }
}
