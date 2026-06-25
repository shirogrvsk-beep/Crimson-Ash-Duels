package com.crimsonashduels.commands;

import com.crimsonashduels.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {

    private final DuelManager duelManager;

    public DuelCommand(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player challenger = (Player) sender;

        if (args.length != 1) {
            challenger.sendMessage("Usage: /duel <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            challenger.sendMessage("Player not found.");
            return true;
        }

        duelManager.sendRequest(challenger, target);

        challenger.sendMessage("You challenged " + target.getName() + " to a duel!");
        target.sendMessage(challenger.getName() + " has challenged you to a duel! Type /duelaccept to fight.");

        return true;
    }
}
