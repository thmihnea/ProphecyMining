package by.thmihnea.prophecymining.enchantment;

import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentCache {

    private static final List<Enchantment> cache = new ArrayList<>();

    public static void addEnchantment(Enchantment enchantment) {
        cache.add(enchantment);
    }

    public static void removeEnchantment(Enchantment enchantment) {
        cache.remove(enchantment);
    }

    public static Enchantment getFromKey(String key) {
        for (Enchantment enchantment : cache) {
            if (enchantment.getKey().getKey().equals(key))
                return enchantment;
        }
        return null;
    }

    public static List<Enchantment> getCache() {
        return cache;
    }
}
