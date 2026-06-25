package com.crimsonashduels.commands;

import com.crimsonashduels.CrimsonAshDuels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EloCommand implements CommandExecutor {

    private final CrimsonAshDuels plugin;

    public EloCommand(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can view ELO ratings.");
            return true;
        }

        Player player = (Player) sender;
        int elo = plugin.getEloManager().getElo(player);
        player.sendMessage("§6Your current ELO rating: §e" + elo);
        return true;
    }
}
