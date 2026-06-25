package com.crimsonashduels;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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

    public static Arena defaultArena() {
        World world = Bukkit.getWorld("world");
        if (world == null) {
            throw new IllegalStateException("World 'world' not found!");
        }
        // Change these coordinates to your actual arena
        return new Arena(world, 100, 65, 100, 110, 65, 100);
    }
}
