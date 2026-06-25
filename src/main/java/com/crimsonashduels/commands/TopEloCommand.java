package com.crimsonashduels.commands;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.EloManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class TopEloCommand implements CommandExecutor {

    private final CrimsonAshDuels plugin;

    public TopEloCommand(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        EloManager elo = plugin.getEloManager();
        List<Map.Entry<String, Integer>> top = elo.getTopElo(5);

        sender.sendMessage("§6--- Top 5 ELO Ratings ---");
        int rank = 1;
        for (Map.Entry<String, Integer> entry : top) {
            String name = Bukkit.getOfflinePlayer(java.util.UUID.fromString(entry.getKey())).getName();
            sender.sendMessage(rank + ". " + name + " - " + entry.getValue() + " ELO");
            rank++;
        }
        return true;
    }
}
