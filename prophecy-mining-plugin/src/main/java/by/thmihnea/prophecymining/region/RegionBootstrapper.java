package by.thmihnea.prophecymining.region;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.region.block.RegionBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class RegionBootstrapper {

    public RegionBootstrapper() {
        ProphecyMining.getInstance().logInfo("Initializing the Region Bootstrapper.");
        setupSavedRegions();
    }

    public static void setupSavedRegions() {
        File regionDirectory = ProphecyMining.getInstance().getRegionDirectory();
        Arrays.stream(regionDirectory.listFiles()).forEach(RegionBootstrapper::setupRegion);
    }

    private static void setupRegion(File file) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        ProphecyMining.getInstance().logInfo("Found region file " +
                file + " in the region directory. Attempting to load it.");
        String name = cfg.get("data.regionName").toString();
        RegionType regionType = RegionType.getFromIdentifier(cfg.getString("data.regionType"));
        String worldName = cfg.getString("data.worldName");
        World world = Bukkit.getWorld(Objects.requireNonNull(worldName));
        Location minimumPoint = new Location(world, cfg.getInt("data.minimumPoint.x"), cfg.getInt("data.minimumPoint.y"), cfg.getInt("data.minimumPoint.z"));
        Location maximumPoint = new Location(world, cfg.getInt("data.maximumPoint.x"), cfg.getInt("data.maximumPoint.y"), cfg.getInt("data.maximumPoint.z"));
        CuboidRegion cuboidRegion = new CuboidRegion(minimumPoint, maximumPoint, regionType, name);
        setupBlocks(cfg, world, cuboidRegion);
        ProphecyMining.getInstance().logInfo("Successfully loaded region " + cuboidRegion.getName() + " into cached memory!");
    }

    public static void setupBlocks(FileConfiguration cfg, World world, CuboidRegion cuboidRegion) {
        cfg.getConfigurationSection("blocks").getKeys(false).forEach(key -> {
            int x = cfg.getInt("blocks." + key + ".x");
            int y = cfg.getInt("blocks." + key + ".y");
            int z = cfg.getInt("blocks." + key + ".z");
            String type = cfg.getString("blocks." + key + ".blockType");
            Block block = world.getBlockAt(x, y, z);
            setupBlock(block, cuboidRegion, type);
        });
    }

    public static void setupBlock(Block block, CuboidRegion cuboidRegion, String type) {
        Material material = cuboidRegion.getRegionType().getMaterial();
        if (type.equalsIgnoreCase("ORE")) {
            cuboidRegion.addOre(block);
            block.setType(material);
            RegionBlock.setMetaData(block, material.toString().toLowerCase());
        }
        else {
            cuboidRegion.addStone(block);
            block.setType(Material.STONE);
            RegionBlock.setMetaData(block, "stone");
        }
    }
}
