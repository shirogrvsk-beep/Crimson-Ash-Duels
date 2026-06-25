package com.crimsonashduels.listeners;

import com.crimsonashduels.CrimsonAshDuels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class KitPreviewListener implements Listener {

    private final CrimsonAshDuels plugin;

    public KitPreviewListener(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().startsWith("Preview Kit: ")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            String displayName = clicked.getItemMeta().getDisplayName();
            String kitName = event.getView().getTitle().replace("Preview Kit: ", "");

            // NOTE: You’ll want to store the target player’s name in metadata/session when opening preview
            // For now, assume DuelManager tracks pending requests with target info
            String targetName = plugin.getDuelManager().getPendingTarget(player);

            if (displayName.equalsIgnoreCase("§aConfirm Kit")) {
                plugin.getDuelManager().sendDuelRequest(player, targetName, kitName);
                player.closeInventory();
                player.sendMessage("§aDuel request sent to " + targetName + " with kit " + kitName + ".");
            } else if (displayName.equalsIgnoreCase("§cCancel")) {
                player.closeInventory();
                player.sendMessage("§eKit selection cancelled. Use /duel again to choose.");
            }
        }
    }
}
