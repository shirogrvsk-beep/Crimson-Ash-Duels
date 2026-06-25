package com.crimsonashduels.gui;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitSelectorGUI {

    private final CrimsonAshDuels plugin;
    private final KitManager kitManager;

    public KitSelectorGUI(CrimsonAshDuels plugin, KitManager kitManager) {
        this.plugin = plugin;
        this.kitManager = kitManager;
    }

    public void openSelector(Player player, String targetName) {
        Inventory inv = Bukkit.createInventory(null, 27, "Select Kit vs " + targetName);

        int slot = 0;
        for (String kitName : kitManager.getKitNames()) {
            ItemStack icon = new ItemStack(Material.CHEST);
            icon.getItemMeta().setDisplayName("Kit: " + kitName);
            inv.setItem(slot++, icon);
        }

        // Own inventory option
        ItemStack ownInv = new ItemStack(Material.PLAYER_HEAD);
        ownInv.getItemMeta().setDisplayName("Use Own Inventory");
        inv.setItem(26, ownInv);

        player.openInventory(inv);
    }
}
