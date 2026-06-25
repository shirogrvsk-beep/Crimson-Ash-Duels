package com.crimsonashduels;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Match {

    private final Player player1;
    private final Player player2;
    private final Map<Player, ItemStack[]> savedInventories = new HashMap<>();

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        saveInventory(player1);
        saveInventory(player2);

        clearInventory(player1);
        clearInventory(player2);
    }

    private void saveInventory(Player player) {
        savedInventories.put(player, player.getInventory().getContents());
    }

    private void clearInventory(Player player) {
        player.getInventory().clear();
    }

    public void endMatch(Player winner, Player loser) {
        restoreInventory(player1);
        restoreInventory(player2);

        winner.sendMessage("You won the duel against " + loser.getName() + "!");
        loser.sendMessage("You lost the duel against " + winner.getName() + ".");
    }

    private void restoreInventory(Player player) {
        ItemStack[] contents = savedInventories.get(player);
        if (contents != null) {
            player.getInventory().setContents(contents);
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
