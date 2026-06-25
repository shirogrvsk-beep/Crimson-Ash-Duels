package com.crimsonashduels;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class MatchManager {

    private final Map<Player, Match> activeMatches = new HashMap<>();
    private final CrimsonAshDuels plugin;

    public MatchManager(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    public void startMatch(Player p1, Player p2, Arena arena) {
        // Restore arena before duel begins
        plugin.getArenaResetManager().restoreArena(arena.getName(), arena.getPasteLocation());

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

        // Record stats
        plugin.getStatsManager().recordWin(winner);
        plugin.getStatsManager().recordLoss(loser);

        // Update ELO ratings
        plugin.getEloManager().updateElo(winner, loser);

        // Reset arena after duel
        plugin.getArenaResetManager().restoreArena(match.getArena().getName(), match.getArena().getPasteLocation());

        activeMatches.remove(match.getPlayer1());
        activeMatches.remove(match.getPlayer2());
    }
}
