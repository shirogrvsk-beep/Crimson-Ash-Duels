package com.crimsonashduels;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EloManager {

    private final CrimsonAshDuels plugin;
    private File eloFile;
    private FileConfiguration eloConfig;

    private static final int DEFAULT_ELO = 1000; // starting rating
    private static final int K_FACTOR = 32;      // sensitivity of rating changes

    public EloManager(CrimsonAshDuels plugin) {
        this.plugin = plugin;
        createFile();
    }

    private void createFile() {
        eloFile = new File(plugin.getDataFolder(), "elo.yml");
        if (!eloFile.exists()) {
            try {
                eloFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        eloConfig = YamlConfiguration.loadConfiguration(eloFile);
    }

    public void saveElo() {
        try {
            eloConfig.save(eloFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getElo(Player player) {
        return eloConfig.getInt(player.getUniqueId() + ".elo", DEFAULT_ELO);
    }

    public void updateElo(Player winner, Player loser) {
        int winnerElo = getElo(winner);
        int loserElo = getElo(loser);

        double expectedWin = 1.0 / (1.0 + Math.pow(10, (loserElo - winnerElo) / 400.0));
        double expectedLoss = 1.0 / (1.0 + Math.pow(10, (winnerElo - loserElo) / 400.0));

        int newWinnerElo = (int) Math.round(winnerElo + K_FACTOR * (1 - expectedWin));
        int newLoserElo = (int) Math.round(loserElo + K_FACTOR * (0 - expectedLoss));

        eloConfig.set(winner.getUniqueId() + ".elo", newWinnerElo);
        eloConfig.set(loser.getUniqueId() + ".elo", newLoserElo);

        saveElo();

        winner.sendMessage("§aYour new ELO: " + newWinnerElo);
        loser.sendMessage("§cYour new ELO: " + newLoserElo);
    }
}
