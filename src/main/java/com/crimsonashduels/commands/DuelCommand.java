package com.crimsonashduels.commands;

import com.crimsonashduels.CooldownManager;
import com.crimsonashduels.DuelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {

    private final DuelManager duelManager;
    private final CooldownManager cooldownManager;

    public DuelCommand(DuelManager duelManager, CooldownManager cooldownManager) {
        this.duelManager = duelManager;
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player challenger = (Player) sender;

        if (cooldownManager.isOnCooldown(challenger)) {
            challenger.sendMessage("You must wait " + cooldownManager.getRemaining(challenger) + "s before sending another duel request.");
            return true;
        }

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
        cooldownManager.setCooldown(challenger);

        challenger.sendMessage("You challenged " + target.getName() + " to a duel!");
        target.sendMessage(challenger.getName() + " has challenged you to a duel! Type /duelaccept to fight.");

        return true;
    }
}
