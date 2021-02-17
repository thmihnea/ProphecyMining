package by.thmihnea.prophecymining.enchantment;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

@Getter
public class EnchantmentWrapper extends Enchantment {

    private final String name;
    private final String nameSpacedKey;
    private final int maxLevel;

    public EnchantmentWrapper(String nameSpacedKey, String name, int maxLevel) {
        super(NamespacedKey.minecraft(nameSpacedKey));
        this.name = name;
        this.maxLevel = maxLevel;
        this.nameSpacedKey = nameSpacedKey;
        EnchantmentCache.addEnchantment(this);
    }

    /**
     * @deprecated
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    /**
     * @deprecated
     */
    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }
}
