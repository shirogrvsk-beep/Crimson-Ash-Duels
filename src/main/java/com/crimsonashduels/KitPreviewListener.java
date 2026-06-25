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
            String targetName = ""; // You’d store targetName in metadata/session when opening preview

            if (displayName.equalsIgnoreCase("§aConfirm Kit")) {
                plugin.getDuelManager().sendDuelRequest(player, targetName, kitName);
                player.closeInventory();
                player.sendMessage("§aDuel request sent to " + targetName + " with kit " + kitName + ".");
            } else if (displayName.equalsIgnoreCase("§cCancel")) {
                player.closeInventory();
                player.sendMessage("§eKit selection cancelled. Reopen /duel to choose again.");
            }
        }
    }
}
