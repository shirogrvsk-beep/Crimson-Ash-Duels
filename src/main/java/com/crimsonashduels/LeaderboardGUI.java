package com.crimsonashduels;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class LeaderboardGUI {

    private final CrimsonAshDuels plugin;

    public LeaderboardGUI(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    public void openWinsLeaderboard(Player viewer) {
        Inventory inv = Bukkit.createInventory(null, 27, "Top Wins");

        List<Map.Entry<String, Integer>> top = plugin.getStatsManager().getTopWins(5);
        int slot = 11;
        for (Map.Entry<String, Integer> entry : top) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(java.util.UUID.fromString(entry.getKey()));
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta = skull.getItemMeta();
            meta.setDisplayName("§e" + player.getName());
            meta.setLore(List.of("§7Wins: " + entry.getValue()));
            skull.setItemMeta(meta);
            inv.setItem(slot, skull);
            slot++;
        }

        viewer.openInventory(inv);
    }

    public void openDuelsLeaderboard(Player viewer) {
        Inventory inv = Bukkit.createInventory(null, 27, "Top Duels");

        List<Map.Entry<String, Integer>> top = plugin.getStatsManager().getTopDuels(5);
        int slot = 11;
        for (Map.Entry<String, Integer> entry : top) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(java.util.UUID.fromString(entry.getKey()));
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta = skull.getItemMeta();
            meta.setDisplayName("§e" + player.getName());
            meta.setLore(List.of("§7Duels Played: " + entry.getValue()));
            skull.setItemMeta(meta);
            inv.setItem(slot, skull);
            slot++;
        }

        viewer.openInventory(inv);
    }
}
