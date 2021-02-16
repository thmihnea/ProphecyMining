package by.thmihnea.prophecymining.item;

import by.thmihnea.prophecymining.util.ItemUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public class Drop {

    private ItemStack itemStack;
    private int chance;
    private int buyPrice;
    private int sellPrice;

    public Drop(Material material, String displayName, int chance, List<String> lore, int buyPrice, int sellPrice) {
        this.itemStack = ItemUtil.getItemStack(material, displayName, lore);
        this.chance = chance;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
