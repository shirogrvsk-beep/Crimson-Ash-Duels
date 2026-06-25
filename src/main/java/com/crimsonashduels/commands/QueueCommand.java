package com.crimsonashduels.commands;

import com.crimsonashduels.QueueManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueueCommand implements CommandExecutor {

    private final QueueManager queueManager;

    public QueueCommand(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use the queue.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /queue <join|leave>");
            return true;
        }

        if (args[0].equalsIgnoreCase("join")) {
            queueManager.joinQueue(player);
        } else if (args[0].equalsIgnoreCase("leave")) {
            queueManager.leaveQueue(player);
        } else {
            player.sendMessage("Usage: /queue <join|leave>");
        }

        return true;
    }
}
