package com.crimsonashduels.gui;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitPreviewGUI {

    private final CrimsonAshDuels plugin;
    private final KitManager kitManager;

    public KitPreviewGUI(CrimsonAshDuels plugin, KitManager kitManager) {
        this.plugin = plugin;
        this.kitManager = kitManager;
    }

    public void openPreview(Player player, String kitName, String targetName) {
        Inventory inv = Bukkit.createInventory(null, 54, "Preview Kit: " + kitName);

        ItemStack[] kitItems = kitManager.getKit(kitName);
        if (kitItems != null) {
            for (int i = 0; i < kitItems.length && i < 45; i++) {
                inv.setItem(i, kitItems[i]);
            }
        }

        // Confirm button
        ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
        confirm.getItemMeta().setDisplayName("§aConfirm Kit");
        inv.setItem(49, confirm);

        // Cancel button
        ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK);
        cancel.getItemMeta().setDisplayName("§cCancel");
        inv.setItem(53, cancel);

        player.openInventory(inv);
    }
}
