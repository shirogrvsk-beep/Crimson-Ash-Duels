package com.crimsonashduels.commands;

import com.crimsonashduels.CrimsonAshDuels;
import com.crimsonashduels.LeaderboardGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopDuelsGUICommand implements CommandExecutor {

    private final LeaderboardGUI gui;

    public TopDuelsGUICommand(CrimsonAshDuels plugin) {
        this.gui = new LeaderboardGUI(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can view leaderboards.");
            return true;
        }
        Player player = (Player) sender;
        gui.openDuelsLeaderboard(player);
        return true;
    }
}
