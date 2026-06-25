package com.crimsonashduels.commands;

import com.crimsonashduels.StatsManager;
import com.crimsonashduels.CrimsonAshDuels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    private final CrimsonAshDuels plugin;

    public StatsCommand(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can view stats.");
            return true;
        }

        Player player = (Player) sender;
        StatsManager stats = plugin.getStatsManager();

        player.sendMessage("§6--- Duel Stats ---");
        player.sendMessage("Wins: " + stats.getWins(player));
        player.sendMessage("Losses: " + stats.getLosses(player));
        player.sendMessage("Duels Played: " + stats.getDuelsPlayed(player));

        return true;
    }
}
