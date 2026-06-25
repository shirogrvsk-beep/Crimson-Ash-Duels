package com.crimsonashduels;

import org.bukkit.entity.Player;

public class Match {
    private final Player player1;
    private final Player player2;
    private final Arena arena;
    private final String kitName;

    public Match(Player player1, Player player2, Arena arena, String kitName) {
        this.player1 = player1;
        this.player2 = player2;
        this.arena = arena;
        this.kitName = kitName;
    }

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Arena getArena() { return arena; }
    public String getKitName() { return kitName; }
}
