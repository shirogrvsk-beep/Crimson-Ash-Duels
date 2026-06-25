package com.crimsonashduels;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class SpectatorManager {

    private final Set<Player> spectators = new HashSet<>();

    public void addSpectator(Player player) {
        spectators.add(player);
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage("You are now spectating a duel.");
    }

    public void removeSpectator(Player player) {
        spectators.remove(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage("You stopped spectating.");
    }

    public boolean isSpectator(Player player) {
        return spectators.contains(player);
    }

    public Set<Player> getSpectators() {
        return spectators;
    }
}
