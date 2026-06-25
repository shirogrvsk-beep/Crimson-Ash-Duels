package com.crimsonashduels.commands;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class TopDuelsCommand implements CommandExecutor {

    private final CrimsonAshDuels plugin;

    public TopDuelsCommand(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StatsManager stats = plugin.getStatsManager();
        List<Map.Entry<String, Integer>> top = stats.getTopDuels(5);

        sender.sendMessage("§6--- Top 5 Duels Played ---");
        int rank = 1;
        for (Map.Entry<String, Integer> entry : top) {
            String name = Bukkit.getOfflinePlayer(java.util.UUID.fromString(entry.getKey())).getName();
            sender.sendMessage(rank + ". " + name + " - " + entry.getValue() + " duels");
            rank++;
        }
        return true;
    }
}
