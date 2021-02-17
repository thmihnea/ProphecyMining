package by.thmihnea.prophecymining.util;

import by.thmihnea.prophecymining.Settings;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;

public class EnchantUtil {

    public static boolean canEnchant(Enchantment enchantment, ItemStack itemStack) {
        if (!(itemStack.getEnchantments().containsKey(enchantment))) return true;
        if (enchantment.getKey().equals(Enchantment.DIG_SPEED.getKey())
            || enchantment.getKey().equals(Enchantment.LUCK.getKey())
            || enchantment.getKey().equals(Enchantment.DURABILITY.getKey())) return true;
        int currentLevel = itemStack.getEnchantments().get(enchantment);
        return currentLevel < enchantment.getMaxLevel();
    }

    public static void applyEnchantment(Player player, ItemStack itemStack, int coins, Enchantment enchantment) {
        int existing = CoinsUtil.getCoins(player.getUniqueId().toString());
        if (existing < coins) {
            player.sendMessage(Settings.LANG_NOT_ENOUGH_COINS);
            return;
        }
        if (!(canEnchant(enchantment, itemStack))) {
            player.sendMessage(Settings.LANG_MAX_LEVEL);
            return;
        }
        CoinsUtil.takeCoins(player.getUniqueId().toString(), coins);
        int level = 0;
        if (itemStack.getEnchantments().containsKey(enchantment)) level = itemStack.getEnchantments().get(enchantment);
        itemStack.addUnsafeEnchantment(enchantment, level + 1);
    }

    public static Enchantment getEnchantmentByKey(ItemStack itemStack, String key) {
        for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
            if (enchantment.getKey().getKey().equals(key))
                return enchantment;
        }
        return null;
    }

    public static boolean containsEnchant(ItemStack itemStack, String key) {
        AtomicBoolean contains = new AtomicBoolean(false);
        itemStack.getEnchantments().keySet().forEach(enchantment -> {
            if (enchantment.getKey().getKey().equals(key))
                contains.set(true);
        });
        return contains.get();
    }

    public static String getEnchantName(String arg) {
        switch (Enchantment.getByName(arg).getName()) {
            case "ARROW_DAMAGE":
                return "Power";
            case "ARROW_FIRE":
                return "Flame";
            case "ARROW_INFINITE":
                return "Infinity";
            case "ARROW_KNOCKBACK":
                return "Punch";
            case "BINDING_CURSE":
                return "Curse of Binding";
            case "DAMAGE_ALL":
                return "Sharpness";
            case "DAMAGE_ARTHROPODS":
                return "Bane of Arthropods";
            case "DAMAGE_UNDEAD":
                return "Smite";
            case "DEPTH_STRIDER":
                return "Depth Strider";
            case "DIG_SPEED":
                return "Efficiency";
            case "DURABILITY":
                return "Unbreaking";
            case "FIRE_ASPECT":
                return "Fire Aspect";
            case "FROST_WALKER":
                return "Frost Walker";
            case "KNOCKBACK":
                return "Knockback";
            case "LOOT_BONUS_BLOCKS":
                return "Fortune";
            case "LOOT_BONUS_MOBS":
                return "Looting";
            case "LUCK":
                return "Luck of the Sea";
            case "LURE":
                return "Lure";
            case "MENDING":
                return "Mending";
            case "OXYGEN":
                return "Respiration";
            case "PROTECTION_ENVIRONMENTAL":
                return "Protection";
            case "PROTECTION_EXPLOSIONS":
                return "Blast Protection";
            case "PROTECTION_FALL":
                return "Feather Falling";
            case "PROTECTION_FIRE":
                return "Fire Protection";
            case "PROTECTION_PROJECTILE":
                return "Projectile Protection";
            case "SILK_TOUCH":
                return "Silk Touch";
            case "SWEEPING_EDGE":
                return "Sweeping Edge";
            case "THORNS":
                return "Thorns";
            case "VANISHING_CURSE":
                return "Cure of Vanishing";
            case "WATER_WORKER":
                return "Aqua Affinity";
            default:
                return arg;
        }
    }
}
