package com.crimsonashduels.commands;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.EloManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class TopEloGUICommand implements CommandExecutor {

    private final CrimsonAshDuels plugin;

    public TopEloGUICommand(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can view leaderboards.");
            return true;
        }

        Player viewer = (Player) sender;
        EloManager elo = plugin.getEloManager();
        List<Map.Entry<String, Integer>> top = elo.getTopElo(5);

        Inventory inv = Bukkit.createInventory(null, 27, "Top ELO");

        int slot = 11;
        for (Map.Entry<String, Integer> entry : top) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(java.util.UUID.fromString(entry.getKey()));
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta = skull.getItemMeta();
            meta.setDisplayName("§e" + player.getName());
            meta.setLore(List.of("§7ELO: " + entry.getValue()));
            skull.setItemMeta(meta);
            inv.setItem(slot, skull);
            slot++;
        }

        viewer.openInventory(inv);
        return true;
    }
}
