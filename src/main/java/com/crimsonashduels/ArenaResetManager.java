package com.crimsonashduels;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ArenaResetManager {

    private final CrimsonAshDuels plugin;

    public ArenaResetManager(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    public void saveArena(String name, Location corner1, Location corner2) {
        try {
            World weWorld = BukkitAdapter.adapt(corner1.getWorld());
            BlockVector3 min = BlockVector3.at(
                    Math.min(corner1.getBlockX(), corner2.getBlockX()),
                    Math.min(corner1.getBlockY(), corner2.getBlockY()),
                    Math.min(corner1.getBlockZ(), corner2.getBlockZ())
            );
            BlockVector3 max = BlockVector3.at(
                    Math.max(corner1.getBlockX(), corner2.getBlockX()),
                    Math.max(corner1.getBlockY(), corner2.getBlockY()),
                    Math.max(corner1.getBlockZ(), corner2.getBlockZ())
            );

            File file = new File(plugin.getDataFolder(), name + ".schem");
            ClipboardFormat format = ClipboardFormat.SCHEMATIC;
            try (ClipboardWriter writer = format.getWriter(new FileOutputStream(file))) {
                Clipboard clipboard = new com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard(
                        new com.sk89q.worldedit.math.BlockVector3(min.getX(), min.getY(), min.getZ()),
                        new com.sk89q.worldedit.math.BlockVector3(max.getX(), max.getY(), max.getZ())
                );
                writer.write(clipboard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreArena(String name, Location pasteLocation) {
        try {
            File file = new File(plugin.getDataFolder(), name + ".schem");
            ClipboardFormat format = ClipboardFormat.SCHEMATIC;
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                Clipboard clipboard = reader.read();
                World weWorld = BukkitAdapter.adapt(pasteLocation.getWorld());
                try (EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance().newEditSession(weWorld)) {
                    ClipboardHolder holder = new ClipboardHolder(clipboard);
                    holder.createPaste(editSession)
                            .to(BlockVector3.at(pasteLocation.getBlockX(), pasteLocation.getBlockY(), pasteLocation.getBlockZ()))
                            .ignoreAirBlocks(false)
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
