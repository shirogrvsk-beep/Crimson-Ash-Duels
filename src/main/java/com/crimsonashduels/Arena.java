package com.crimsonashduels;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Bukkit;

public class Arena {
    private final String name;
    private final Location corner1;
    private final Location corner2;
    private final Location pasteLocation;

    public Arena(String name, Location corner1, Location corner2, Location pasteLocation) {
        this.name = name;
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.pasteLocation = pasteLocation;
    }

    public String getName() { return name; }
    public Location getCorner1() { return corner1; }
    public Location getCorner2() { return corner2; }
    public Location getPasteLocation() { return pasteLocation; }

    public void teleportPlayers(Player p1, Player p2) {
        p1.teleport(pasteLocation);
        p2.teleport(pasteLocation);
    }

    public static Arena fromConfig(ConfigurationSection section) {
        String name = section.getName();
        String worldName = section.getString("world");
        org.bukkit.World world = Bukkit.getWorld(worldName);

        String[] c1 = section.getString("corner1").split(",");
        String[] c2 = section.getString("corner2").split(",");
        String[] paste = section.getString("pasteLocation").split(",");

        Location corner1 = new Location(world,
                Double.parseDouble(c1[0]), Double.parseDouble(c1[1]), Double.parseDouble(c1[2]));
        Location corner2 = new Location(world,
                Double.parseDouble(c2[0]), Double.parseDouble(c2[1]), Double.parseDouble(c2[2]));
        Location pasteLoc = new Location(world,
                Double.parseDouble(paste[0]), Double.parseDouble(paste[1]), Double.parseDouble(paste[2]));

        return new Arena(name, corner1, corner2, pasteLoc);
    }
}
