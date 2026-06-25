package com.crimsonashduels;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class DuelManager {

    private final Map<Player, Player> pendingDuels = new HashMap<>();

    public void sendRequest(Player challenger, Player target) {
        pendingDuels.put(challenger, target);
    }

    public boolean hasRequest(Player target) {
        return pendingDuels.containsValue(target);
    }

    public Player getChallenger(Player target) {
        for (Map.Entry<Player, Player> entry : pendingDuels.entrySet()) {
            if (entry.getValue().equals(target)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void removeRequest(Player challenger) {
        pendingDuels.remove(challenger);
    }
}
