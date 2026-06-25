package com.crimsonashduels;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime; // in milliseconds

    public CooldownManager(long cooldownSeconds) {
        this.cooldownTime = cooldownSeconds * 1000;
    }

    public boolean isOnCooldown(Player player) {
        UUID id = player.getUniqueId();
        if (!cooldowns.containsKey(id)) return false;

        long lastUsed = cooldowns.get(id);
        return (System.currentTimeMillis() - lastUsed) < cooldownTime;
    }

    public void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public long getRemaining(Player player) {
        UUID id = player.getUniqueId();
        if (!cooldowns.containsKey(id)) return 0;

        long lastUsed = cooldowns.get(id);
        long elapsed = System.currentTimeMillis() - lastUsed;
        long remaining = cooldownTime - elapsed;
        return Math.max(0, remaining / 1000);
    }
}
