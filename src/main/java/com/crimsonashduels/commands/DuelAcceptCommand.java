package com.crimsonashduels.commands;

import com.crimsonashduels.Arena;
import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.DuelManager;
import com.crimsonashduels.MatchManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelAcceptCommand implements CommandExecutor {

    private final DuelManager duelManager;
    private final MatchManager matchManager;
    private final CrimsonAshDuels plugin;

    public DuelAcceptCommand(DuelManager duelManager, MatchManager matchManager, CrimsonAshDuels plugin) {
        this.duelManager = duelManager;
        this.matchManager = matchManager;
        this.plugin = plugin;
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

        // Arena selection
        String arenaName = args.length > 0 ? args[0] : "default";
        Arena arena = plugin.getArena(arenaName);
        if (arena == null) {
            target.sendMessage("Arena '" + arenaName + "' not found.");
            return true;
        }

        target.sendMessage("You accepted the duel against " + challenger.getName() + " in arena '" + arenaName + "'!");
        challenger.sendMessage(target.getName() + " accepted your duel! Teleporting to arena '" + arenaName + "'...");

        arena.teleportPlayers(challenger, target);

        matchManager.startMatch(challenger, target);
        duelManager.removeRequest(challenger);

        return true;
    }
}
