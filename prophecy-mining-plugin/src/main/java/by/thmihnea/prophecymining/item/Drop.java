package by.thmihnea.prophecymining.item;

import by.thmihnea.prophecymining.util.ItemUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Getter
@Setter
public class Drop {

    private ItemStack itemStack;
    private int chance;
    private int buyPrice;
    private int sellPrice;
    private boolean canBuy;

    public Drop(Material material, String displayName, int chance, List<String> lore, int buyPrice, int sellPrice, boolean canBuy) {
        this.itemStack = ItemUtil.getItemStack(material, displayName, lore);
        this.patchItem();
        this.chance = chance;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.canBuy = canBuy;
    }

    private void patchItem() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.itemStack.setItemMeta(itemMeta);
        this.itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
    }
}
