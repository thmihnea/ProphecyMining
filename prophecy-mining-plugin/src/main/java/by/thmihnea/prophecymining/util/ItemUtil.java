package by.thmihnea.prophecymining.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUtil {

    public static ItemStack getItemStack(Material material, String displayName, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static List<String> translateLoreColorCodes(List<String> lore) {
        return lore.stream().map(string -> ChatColor.translateAlternateColorCodes('&', string)).collect(Collectors.toList());
    }
}
