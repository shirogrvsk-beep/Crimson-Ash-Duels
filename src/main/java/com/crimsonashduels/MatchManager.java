package com.crimsonashduels;

import org.bukkit.entity.Player;

public class MatchManager {
    public Match startMatch(Player p1, Player p2, Arena arena, String kitName) {
        arena.teleportPlayers(p1, p2);
        Match match = new Match(p1, p2, arena, kitName);
        // TODO: apply kits, track stats, etc.
        return match;
    }
}
