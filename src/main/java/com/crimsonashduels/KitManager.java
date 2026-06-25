package com.crimsonashduels;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KitManager {

    private final CrimsonAshDuels plugin;
    private final File kitsFile;
    private final FileConfiguration kitsConfig;
    private final Map<String, ItemStack[]> kits = new HashMap<>();

    public KitManager(CrimsonAshDuels plugin) {
        this.plugin = plugin;
        kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        if (!kitsFile.exists()) {
            plugin.saveResource("kits.yml", false);
        }
        kitsConfig = YamlConfiguration.loadConfiguration(kitsFile);
        loadKits();
    }

    private void loadKits() {
        for (String kitName : kitsConfig.getKeys(false)) {
            ItemStack[] items = ((ItemStack[]) kitsConfig.get(kitName));
            kits.put(kitName, items);
        }
    }

    public void saveKit(String name, Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        kits.put(name, contents);
        kitsConfig.set(name, contents);
        try {
            kitsConfig.save(kitsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ItemStack[] getKit(String name) {
        return kits.get(name);
    }
}
