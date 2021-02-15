package by.thmihnea.prophecymining.item;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.util.ItemUtil;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class ItemCache {

    private static final List<Drop> drops = new ArrayList<>();

    public ItemCache() {
        this.init();
    }

    private void init() {
        Objects.requireNonNull(ProphecyMining.getCfg().getConfigurationSection("drops")).getKeys(false).forEach(key -> {
            String materialName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("drops." + key + ".material")));
            String displayName = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("drops." + key + ".name")));
            int chance = ProphecyMining.getCfg().getInt("drops." + key + ".chance");
            List<String> lore = ItemUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("drops." + key + ".lore"));

            Material material = Material.valueOf(materialName);

            Drop drop = new Drop(material, displayName, chance, lore);
            drops.add(drop);
        });
    }

    public static ItemStack computeDrop() {
        if (drops.size() == 0) return null;
        int i = ThreadLocalRandom.current().nextInt(drops.size());
        Drop drop = drops.get(i);
        int chance = drop.getChance();
        int hash = ThreadLocalRandom.current().nextInt(100);
        return (hash <= chance) ? drop.getItemStack() : null;
    }

}
