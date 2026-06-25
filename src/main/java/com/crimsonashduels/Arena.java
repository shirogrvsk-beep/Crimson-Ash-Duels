package com.crimsonashduels;

import org.bukkit.Location;

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
}
