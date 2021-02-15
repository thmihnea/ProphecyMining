package by.thmihnea.prophecymining.block;

import by.thmihnea.prophecymining.Settings;
import lombok.Getter;
import org.bukkit.Material;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

@Getter
public enum BlockExperience {

    COAL(Material.COAL_ORE, Settings.COAL_START, Settings.COAL_END),
    IRON(Material.IRON_ORE, Settings.IRON_START, Settings.IRON_END),
    GOLD(Material.GOLD_ORE, Settings.GOLD_START, Settings.GOLD_END),
    EMERALD(Material.EMERALD_ORE, Settings.EMERALD_START, Settings.EMERALD_END),
    LAPIS(Material.LAPIS_ORE, Settings.LAPIS_START, Settings.LAPIS_END),
    REDSTONE(Material.REDSTONE_ORE, Settings.REDSTONE_START, Settings.REDSTONE_END),
    DIAMOND(Material.DIAMOND_ORE, Settings.DIAMOND_START, Settings.DIAMOND_END);

    private final Material material;
    private final int startXp;
    private final int endXp;

    BlockExperience(Material material, int startXp, int endXp) {
        this.material = material;
        this.startXp = startXp;
        this.endXp = endXp;
    }

    public int computeExperience() {
        return (this.startXp + (int) (Math.random() * ((this.endXp - this.startXp) + 1)));
    }

    public String getFieldName() {
        return this.name().toUpperCase() + "_MINED";
    }

    public static BlockExperience getFromMaterial(Material material) {
        Optional<BlockExperience> match = Arrays.stream(values()).filter(obj -> obj.getMaterial().equals(material)).findFirst();
        return match.orElse(null);
    }

    public static boolean exists(Material material) {
        Optional<BlockExperience> match = Arrays.stream(values()).filter(obj -> obj.getMaterial().equals(material)).findFirst();
        return match.isPresent();
    }
}
