package by.thmihnea.prophecymining.region;

import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum RegionType {

    COAL(Material.COAL_ORE, "coal"),
    IRON(Material.IRON_ORE, "iron"),
    LAPIS(Material.LAPIS_ORE, "lapis"),
    GOLD(Material.GOLD_ORE, "gold"),
    REDSTONE(Material.REDSTONE_ORE, "redstone"),
    DIAMOND(Material.DIAMOND_ORE, "diamond"),
    EMERALD(Material.EMERALD_ORE, "emerald");

    private final Material material;
    private final String identifier;

    RegionType(Material material, String identifier) {
        this.material = material;
        this.identifier = identifier;
    }

    public static RegionType getFromMaterial(Material material) {
        Optional<RegionType> match = Arrays.stream(values()).filter(obj -> obj.getMaterial().equals(material)).findFirst();
        return match.orElse(null);
    }

    public static RegionType getFromIdentifier(String identifier) {
        Optional<RegionType> match = Arrays.stream(values()).filter(obj -> obj.getIdentifier().equalsIgnoreCase(identifier)).findFirst();
        return match.orElse(null);
    }
}
