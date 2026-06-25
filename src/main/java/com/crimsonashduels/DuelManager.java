package com.crimsonashduels;

import org.bukkit.entity.Player;
import java.util.*;

public class DuelManager {
    private final Map<Player, String> pendingTargets = new HashMap<>();
    private final Map<Player, String> lastKit = new HashMap<>();

    public void sendDuelRequest(Player sender, String targetName, String kitName) {
        pendingTargets.put(sender, targetName);
        lastKit.put(sender, kitName);
    }

    public String getPendingTarget(Player player) {
        return pendingTargets.get(player);
    }

    public String getLastKit(Player player) {
        return lastKit.getOrDefault(player, "None");
    }
}
