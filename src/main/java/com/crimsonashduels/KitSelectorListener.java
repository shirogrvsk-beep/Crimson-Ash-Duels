package com.crimsonashduels.listeners;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.KitManager;
import com.crimsonashduels.gui.KitPreviewGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class KitSelectorListener implements Listener {

    private final CrimsonAshDuels plugin;
    private final KitManager kitManager;

    public KitSelectorListener(CrimsonAshDuels plugin, KitManager kitManager) {
        this.plugin = plugin;
        this.kitManager = kitManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().startsWith("Select Kit vs ")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            String displayName = clicked.getItemMeta().getDisplayName();
            String targetName = event.getView().getTitle().replace("Select Kit vs ", "");

            if (displayName.startsWith("Kit: ")) {
                String kitName = displayName.replace("Kit: ", "");
                // Open preview GUI instead of sending request immediately
                KitPreviewGUI preview = new KitPreviewGUI(plugin, kitManager);
                preview.openPreview(player, kitName, targetName);
            } else if (displayName.equalsIgnoreCase("Use Own Inventory")) {
                plugin.getDuelManager().sendDuelRequest(player, targetName, "own");
                player.closeInventory();
                player.sendMessage("§aDuel request sent to " + targetName + " using your own inventory.");
            }
        }
    }
}
