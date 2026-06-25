package com.crimsonashduels;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

public class DuelListener implements Listener {

    private final DuelManager duelManager;

    public DuelListener(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player loser = event.getEntity();
        Player killer = loser.getKiller();

        if (killer != null) {
            // End the match when one player kills the other
            Match match = new Match(killer, loser);
            match.endMatch(killer, loser);
        }
    }
}
