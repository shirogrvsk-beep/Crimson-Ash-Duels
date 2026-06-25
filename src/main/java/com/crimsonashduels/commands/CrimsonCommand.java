package com.crimsonashduels.commands;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.KitManager;
import com.crimsonashduels.gui.KitEditorGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrimsonCommand implements CommandExecutor {

    private final CrimsonAshDuels plugin;
    private final KitManager kitManager;
    private final KitEditorGUI kitEditorGUI;

    public CrimsonCommand(CrimsonAshDuels plugin, KitManager kitManager) {
        this.plugin = plugin;
        this.kitManager = kitManager;
        this.kitEditorGUI = new KitEditorGUI(plugin, kitManager);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("crimsonashduels.admin")) {
            player.sendMessage("§cAdmin only.");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage("§cUsage: /crimson kit <create|editor> <name>");
            return true;
        }

        String action = args[0];
        String sub = args[1];

        if (action.equalsIgnoreCase("kit")) {
            if (args[1].equalsIgnoreCase("create") && args.length == 3) {
                String kitName = args[2];
                kitManager.saveKit(kitName, player);
                player.getInventory().clear(); // sacrifice inventory
                player.sendMessage("§aKit " + kitName + " created and saved.");
                return true;
            } else if (args[1].equalsIgnoreCase("editor") && args.length == 3) {
                String kitName = args[2];
                kitEditorGUI.openEditor(player, kitName);
                return true;
            }
        }

        return false;
    }
}
