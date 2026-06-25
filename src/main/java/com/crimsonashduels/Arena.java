package com.crimsonashduels;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Arena {

    private final Location spawn1;
    private final Location spawn2;

    public Arena(World world, double x1, double y1, double z1, double x2, double y2, double z2) {
        this.spawn1 = new Location(world, x1, y1, z1);
        this.spawn2 = new Location(world, x2, y2, z2);
    }

    public void teleportPlayers(Player p1, Player p2) {
        p1.teleport(spawn1);
        p2.teleport(spawn2);
    }

    public static Arena fromConfig(ConfigurationSection section) {
        String worldName = section.getString("world");
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalStateException("World '" + worldName + "' not found!");
        }

        double x1 = section.getConfigurationSection("spawn1").getDouble("x");
        double y1 = section.getConfigurationSection("spawn1").getDouble("y");
        double z1 = section.getConfigurationSection("spawn1").getDouble("z");

        double x2 = section.getConfigurationSection("spawn2").getDouble("x");
        double y2 = section.getConfigurationSection("spawn2").getDouble("y");
        double z2 = section.getConfigurationSection("spawn2").getDouble("z");

        return new Arena(world, x1, y1, z1, x2, y2, z2);
    }
}
