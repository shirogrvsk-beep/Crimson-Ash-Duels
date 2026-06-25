package com.crimsonashduels;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

public class DuelListener implements Listener {

    private final MatchManager matchManager;

    public DuelListener(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player loser = event.getEntity();
        Player killer = loser.getKiller();

        if (killer != null && matchManager.isInMatch(loser)) {
            Match match = matchManager.getMatch(loser);
            matchManager.endMatch(match, killer, loser);
        }
    }
}
