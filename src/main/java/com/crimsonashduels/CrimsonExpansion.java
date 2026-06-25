package com.crimsonashduels.placeholders;

import com.crimsonashduels.CrimsonAshDuels;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class CrimsonExpansion extends PlaceholderExpansion {

    private final CrimsonAshDuels plugin;

    public CrimsonExpansion(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "crimson";
    }

    @Override
    public String getAuthor() {
        return "YourName";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // keep registered after reload
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";

        switch (identifier.toLowerCase()) {
            case "wins":
                return String.valueOf(plugin.getStatsManager().getWins(player));
            case "duels":
                return String.valueOf(plugin.getStatsManager().getDuels(player));
            case "elo":
                return String.valueOf(plugin.getEloManager().getElo(player));
            case "kit":
                return plugin.getDuelManager().getLastKit(player);
            default:
                return null;
        }
    }
}
