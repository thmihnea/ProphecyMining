package by.thmihnea.prophecymining.region;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CuboidRegion {

    private final Location location1;
    private final Location location2;
    private final World world;
    private final RegionType regionType;
    private final String name;
    private final List<Block> ores;
    private final List<Block> stones;
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;

    public CuboidRegion(Location location1, Location location2, RegionType regionType, String name) {
        this.location1 = location1;
        this.location2 = location2;
        this.world = location1.getWorld();
        this.regionType = regionType;
        this.name = name;
        this.xMin = Math.min(location1.getBlockX(), location2.getBlockX());
        this.xMax = Math.max(location1.getBlockX(), location2.getBlockX());
        this.yMin = Math.min(location1.getBlockY(), location2.getBlockY());
        this.yMax = Math.max(location1.getBlockY(), location2.getBlockY());
        this.zMin = Math.min(location1.getBlockZ(), location2.getBlockZ());
        this.zMax = Math.max(location1.getBlockZ(), location2.getBlockZ());
        this.ores = new ArrayList<>();
        this.stones = new ArrayList<>();
        RegionCache.addRegion(this);
    }

    /**
     * Gets a list of blocks that are
     * inside of the given Region.
     *
     * @return all blocks inside region.
     */
    public List<Block> getBlocks() {
        final ArrayList<Block> bL = new ArrayList<>(this.getTotalBlockSize());
        for (int x = this.xMin; x <= this.xMax; ++x) {
            for (int y = this.yMin; y <= this.yMax; ++y) {
                for (int z = this.zMin; z <= this.zMax; ++z) {
                    final Block b = world.getBlockAt(x, y, z);
                    bL.add(b);
                }
            }
        }
        return bL;
    }

    /**
     * Calculates the total blocksize in
     * between the region bounds so that we can
     * attribute the ArrayList an initial value
     * in the {@link #getBlocks()}} method.
     *
     * @return total block size.
     */
    public int getTotalBlockSize() {
        return this.getHeight() * this.getXWidth() * this.getZWidth();
    }

    /**
     * Returns the maximum width on the
     * X coordinate.
     *
     * @return X width
     */
    public int getXWidth() {
        return this.xMax - this.xMin + 1;
    }

    /**
     * Returns the maximum width on the
     * Z coordinate.
     *
     * @return Z width
     */
    public int getZWidth() {
        return this.zMax - this.zMin + 1;
    }

    /**
     * Returns the height of the
     * region, also known as the distance
     * between ymax and ymin (dy)
     *
     * @return dy
     */
    public int getHeight() {
        return this.yMax - this.yMin + 1;
    }

    /**
     * Return the first location
     * parameter.
     *
     * @return {@link #location1}
     */
    public Location getLocation1() {
        return this.location1;
    }

    /**
     * Return the second location
     * parameter.
     *
     * @return {@link #location2}
     */
    public Location getLocation2() {
        return this.location2;
    }

    /**
     * Replaces all blocks in the {@link CuboidRegion}
     * which are of a certain {@link Material} type
     * to the specified {@link Material} type.
     * Used to unlock regions.
     *
     * @param to   Replace material
     */
    public void replaceAll(Material from, Material to) {
        List<Block> blockList = getBlocks();
        blockList.forEach(block -> {
            Location location = block.getLocation();
            Block toBeReplaced = location.getBlock();
            if (toBeReplaced.getType().equals(from)) {
                toBeReplaced.setType(to);
            }
        });
    }

    public void addOre(Block block) {
        this.ores.add(block);
    }

    public void remove(Block block) {
        this.ores.remove(block);
    }

    public void addStone(Block block) {
        this.stones.add(block);
    }

    public void removeStone(Block block) {
        this.stones.remove(block);
    }
}
