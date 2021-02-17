package by.thmihnea.prophecymining.enchantment;

import by.thmihnea.prophecymining.ProphecyMining;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EnchantmentManager {

    public static void register() {
        EnchantmentCache.getCache().forEach(enchantment -> {
            boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);
            if (!(registered)) {
                ProphecyMining.getInstance().logInfo("Found Enchantment " + enchantment.getName() + " which isn't registered. Attempting registering it.");
                registerEnchantment(enchantment);
            }
        });
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        long start = System.currentTimeMillis();
        try {
            Class<?> clazz = Enchantment.class;
            Field field = clazz.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
            ProphecyMining.getInstance().logSevere("Couldn't register enchantment " + enchantment.getName() + ".");
        }
        if (registered) {
            ProphecyMining.getInstance().logInfo("Successfully registered enchantment " + enchantment.getName() + "! Process took: " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
