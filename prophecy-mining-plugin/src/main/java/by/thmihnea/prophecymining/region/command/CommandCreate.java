package by.thmihnea.prophecymining.region.command;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.command.AbstractCommand;
import by.thmihnea.prophecymining.region.CuboidRegion;
import by.thmihnea.prophecymining.region.RegionBootstrapper;
import by.thmihnea.prophecymining.region.RegionType;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandCreate extends AbstractCommand {

    public CommandCreate(CommandSender commandSender, String[] args) {
        super(commandSender, args, "region.create");
    }

    @Override
    public void execute() {
        CommandSender commandSender = this.getCommandSender();
        if (!(commandSender instanceof Player)) return;
        Player player = (Player) commandSender;
        if (!(commandSender.hasPermission(this.getPermission()))) {
            this.sendMessage(Settings.LANG_NO_PERMISSION);
            return;
        }
        if (this.getArgs().length == 0) {
            this.sendMessage(Settings.LANG_SPECIFY_NAME);
            return;
        }
        if (this.getArgs().length == 1) {
            this.sendMessage(Settings.LANG_SPECIFY_TYPE);
            return;
        }
        String name = this.getArgs()[0];
        File regionDirectory = ProphecyMining.getInstance().getRegionDirectory();
        boolean exists = this.exists(name, regionDirectory);
        if (exists) {
            this.sendMessage(Settings.LANG_NAME_ALREADY_EXISTS.replace("%name%", name));
            return;
        }
        String type = this.getArgs()[1];
        RegionType regionType = RegionType.getFromIdentifier(type);
        if (regionType == null) {
            this.sendMessage(Settings.LANG_TYPE_INVALID.replace("%arg%", type));
            return;
        }
        Location l1, l2;
        try {
            LocalSession localSession = WorldEdit.getInstance().getSessionManager().findByName(player.getName());
            if (localSession == null) {
                this.sendMessage(Settings.LANG_NEED_SELECTION_FIRST);
                return;
            }
            World world = localSession.getSelectionWorld();
            Region region = localSession.getSelection(world);
            if (region == null) {
                this.sendMessage(Settings.LANG_NEED_SELECTION_FIRST);
                return;
            }
            l1 = new Location(player.getWorld(), region.getMinimumPoint().getBlockX(), region.getMinimumPoint().getBlockY(), region.getMinimumPoint().getBlockZ());
            l2 = new Location(player.getWorld(), region.getMaximumPoint().getBlockX(), region.getMaximumPoint().getBlockY(), region.getMaximumPoint().getBlockZ());
        } catch (IncompleteRegionException e) {
            this.sendMessage(Settings.LANG_INCOMPLETE_REGION);
            return;
        }
        CuboidRegion cuboidRegion = new CuboidRegion(l1, l2, regionType, name);
        File regionFile = new File("plugins/" + ProphecyMining.getInstance().getName() + "/regions/" + name + ".yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(regionFile);
        try {
            regionFile.createNewFile();
            this.saveRegionToFile(name, type, l1, l2, cuboidRegion, regionFile, fileConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*CompletableFuture.runAsync(() -> {
            cuboidRegion.getBlocks().forEach(block -> {
                this.saveBlock(cuboidRegion, regionFile, fileConfiguration, block);
            });
        }).thenRun(() -> {
            this.sendMessage(Settings.LANG_REGION_CREATED.replace("%name%", name));
            ProphecyMining.getInstance().logInfo("It is recommended that you reload the server after the implementation of a region!");
        });*/
        AtomicInteger counter = new AtomicInteger(1);
        cuboidRegion.getBlocks().forEach(block -> {
            if (block.getType() != Material.SPONGE && block.getType() != Material.STONE) return;
            this.addBlockToRegionFile(counter, block, fileConfiguration);
            try {
                fileConfiguration.save(regionFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter.set(counter.get() + 1);
        });
        this.sendMessage(Settings.LANG_REGION_CREATED.replace("%name%", name));
        ProphecyMining.getInstance().logInfo("It is recommended that you reload the server after the implementation of a region!");
    }

    /*private void saveBlock(CuboidRegion cuboidRegion, File regionFile, FileConfiguration fileConfiguration, Block block) {
        AtomicInteger counter = new AtomicInteger(1);
        if (block.getType() != Material.SPONGE && block.getType() != Material.STONE) return;
        this.addBlockToRegionFile(counter, block, fileConfiguration);
        try {
            fileConfiguration.save(regionFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        counter.set(counter.get() + 1);
    }*/

    private boolean exists(String name, File regionDirectory) {
        AtomicBoolean exists = new AtomicBoolean(false);
        Arrays.stream(regionDirectory.listFiles()).forEach(file -> {
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
            if (fileName.equalsIgnoreCase(name)) exists.set(true);
        });
        return exists.get();
    }

    private void saveRegionToFile(String name, String type, Location l1, Location l2, CuboidRegion cuboidRegion, File regionFile, FileConfiguration fileConfiguration) throws IOException {
        fileConfiguration.set("data.regionName", name);
        fileConfiguration.set("data.regionType", type);
        fileConfiguration.set("data.worldName", cuboidRegion.getWorld().getName());
        fileConfiguration.set("data.minimumPoint.x", l1.getBlockX());
        fileConfiguration.set("data.minimumPoint.y", l1.getBlockY());
        fileConfiguration.set("data.minimumPoint.z", l1.getBlockZ());
        fileConfiguration.set("data.maximumPoint.x", l2.getBlockX());
        fileConfiguration.set("data.maximumPoint.y", l2.getBlockY());
        fileConfiguration.set("data.maximumPoint.z", l2.getBlockZ());
        fileConfiguration.save(regionFile);
    }

    private void addBlockToRegionFile(AtomicInteger counter, org.bukkit.block.Block block, FileConfiguration fileConfiguration) {
        String blockType = "ORE";
        if (block.getType() == Material.STONE)
            blockType = "STONE";
        String path = "blocks." + counter.get();
        fileConfiguration.set(path + ".x", block.getLocation().getBlockX());
        fileConfiguration.set(path + ".y", block.getLocation().getBlockY());
        fileConfiguration.set(path + ".z", block.getLocation().getBlockZ());
        fileConfiguration.set(path + ".blockType", blockType);
    }
}
