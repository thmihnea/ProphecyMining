package by.thmihnea.prophecymining.region.block;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class RegionBlock {

    public static void setMetaData(Block block, String type) {
        block.setMetadata("RegionBlockType", new FixedMetadataValue(ProphecyMining.getInstance(), type));
    }

    public static void removeMetaData(Block block) {
        block.removeMetadata("RegionBlockType", ProphecyMining.getInstance());
    }

    public static String getBlockType(Block block) {
        List<MetadataValue> metadataValues = block.getMetadata("RegionBlockType");
        for (MetadataValue metadataValue : metadataValues) {
            return metadataValue.asString();
        }
        return null;
    }

    public static void computeNextBlockType(Block block, Material beforeType) {
        String type = getBlockType(block);
        if (type == null) return;
        int rollBackTime = Settings.REGION_ROLLBACK_TIME;
        if (beforeType == Material.STONE && type.equalsIgnoreCase("stone")) {
            block.setType(Material.BEDROCK);
            Bukkit.getScheduler().scheduleSyncDelayedTask(ProphecyMining.getInstance(), () -> block.setType(Material.STONE), rollBackTime * 20L);
        }
        else if (beforeType.toString().contains("ORE") && type.contains("ore")) {
            block.setType(Material.COBBLESTONE);
        }
        else if (beforeType == Material.COBBLESTONE && type.contains("ore")) {
            block.setType(Material.BEDROCK);
            String typeCopy = type;
            typeCopy = typeCopy.toUpperCase();
            Material material = Material.valueOf(typeCopy);
            Bukkit.getScheduler().scheduleSyncDelayedTask(ProphecyMining.getInstance(), () -> block.setType(material), rollBackTime * 20L);
        }
    }

    public static boolean hasMetadata(Block block) {
        return getBlockType(block) != null;
    }
}
