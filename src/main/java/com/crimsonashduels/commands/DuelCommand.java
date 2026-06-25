package com.crimsonashduels.commands;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.gui.KitSelectorGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {

    private final CrimsonAshDuels plugin;

    public DuelCommand(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player challenger = (Player) sender;

        if (args.length != 1) {
            challenger.sendMessage("§cUsage: /duel <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            challenger.sendMessage("§cPlayer not found.");
            return true;
        }

        // Open kit selector GUI before sending duel request
        KitSelectorGUI gui = new KitSelectorGUI(plugin, plugin.getKitManager());
        gui.openSelector(challenger, target.getName());

        return true;
    }
}
