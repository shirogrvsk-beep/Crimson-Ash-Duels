package com.crimsonashduels.commands;

import com.crimsonashduels.Arena;
import com.crimsonashduels.DuelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelAcceptCommand implements CommandExecutor {

    private final DuelManager duelManager;

    public DuelAcceptCommand(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can accept duels.");
            return true;
        }

        Player target = (Player) sender;

        if (!duelManager.hasRequest(target)) {
            target.sendMessage("You have no pending duel requests.");
            return true;
        }

        Player challenger = duelManager.getChallenger(target);
        if (challenger == null) {
            target.sendMessage("No challenger found.");
            return true;
        }

        target.sendMessage("You accepted the duel against " + challenger.getName() + "!");
        challenger.sendMessage(target.getName() + " accepted your duel! Teleporting to arena...");

        Arena arena = Arena.defaultArena();
        arena.teleportPlayers(challenger, target);

        duelManager.removeRequest(challenger);

        return true;
    }
}
