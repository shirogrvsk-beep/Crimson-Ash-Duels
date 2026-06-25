package com.crimsonashduels.gui;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitEditorGUI {

    private final CrimsonAshDuels plugin;
    private final KitManager kitManager;

    public KitEditorGUI(CrimsonAshDuels plugin, KitManager kitManager) {
        this.plugin = plugin;
        this.kitManager = kitManager;
    }

    public void openEditor(Player player, String kitName) {
        Inventory inv = Bukkit.createInventory(null, 54, "Kit Editor: " + kitName);

        ItemStack[] kitItems = kitManager.getKit(kitName);
        if (kitItems != null) {
            inv.setContents(kitItems);
        }

        // Save button
        inv.setItem(53, new ItemStack(Material.EMERALD_BLOCK));

        player.openInventory(inv);
    }
}
