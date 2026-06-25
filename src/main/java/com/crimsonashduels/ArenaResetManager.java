package com.crimsonashduels;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ArenaResetManager {
    private final CrimsonAshDuels plugin;

    public ArenaResetManager(CrimsonAshDuels plugin) {
        this.plugin = plugin;
    }

    public void saveArena(String name, Location corner1, Location corner2) {
        World world = corner1.getWorld();
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

        Region region = new CuboidRegion(BukkitAdapter.adapt(world), min, max);
        Clipboard clipboard = new BlockArrayClipboard(region);

        File schemFile = new File(plugin.getDataFolder(), name + ".schem");
        ClipboardFormat format = ClipboardFormats.findByFile(schemFile);

        try (ClipboardWriter writer = format.getWriter(new FileOutputStream(schemFile))) {
            writer.write(clipboard);
            plugin.getLogger().info("Arena " + name + " saved to " + schemFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreArena(String name, Location pasteLocation) {
        File schemFile = new File(plugin.getDataFolder(), name + ".schem");
        ClipboardFormat format = ClipboardFormats.findByFile(schemFile);

        if (format == null) {
            plugin.getLogger().warning("No schematic format found for arena " + name);
            return;
        }

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(pasteLocation.getWorld()))) {
            Clipboard clipboard = format.getReader(schemFile).read();
            ClipboardHolder holder = new ClipboardHolder(clipboard);
            holder.createPaste(editSession)
                    .to(BlockVector3.at(pasteLocation.getBlockX(), pasteLocation.getBlockY(), pasteLocation.getBlockZ()))
                    .ignoreAirBlocks(false)
                    .build();
            plugin.getLogger().info("Arena " + name + " restored at " + pasteLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
