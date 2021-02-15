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

    public Drop(Material material, String displayName, int chance, List<String> lore) {
        this.itemStack = ItemUtil.getItemStack(material, displayName, lore);
        this.chance = chance;
    }
}
