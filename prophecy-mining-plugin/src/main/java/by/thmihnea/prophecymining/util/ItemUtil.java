package by.thmihnea.prophecymining.util;

import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.enchantment.EnchantmentCache;
import by.thmihnea.prophecymining.item.Drop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ItemUtil {

    public static ItemStack getItemStack(Material material, String displayName, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static void buyOne(Player player, Drop drop) {
        int price = drop.getBuyPrice();
        int coins = CoinsUtil.getCoins(player.getUniqueId().toString());
        if (coins < price) {
            String message = Settings.LANG_NOT_ENOUGH_COINS;
            player.sendMessage(message);
            return;
        }
        ItemStack itemStack = new ItemStack(drop.getItemStack());
        player.getInventory().addItem(itemStack);
        CoinsUtil.takeCoins(player.getUniqueId().toString(), drop.getBuyPrice());
        String message = Settings.LANG_BUY_MESSAGE;
        message = message.replace("%amount%", String.valueOf("1"))
                .replace("%itemName%", itemStack.getItemMeta().getDisplayName())
                .replace("%price%", LangUtil.formatNumber(price));
        player.sendMessage(message);
    }

    public static void buyAll(Player player, Drop drop) {
        int price = drop.getBuyPrice();
        int coins = CoinsUtil.getCoins(player.getUniqueId().toString());
        int amount = coins / price;
        int finalPrice = price * amount;
        if (amount == 0) {
            String message = Settings.LANG_NOT_ENOUGH_COINS;
            player.sendMessage(message);
            return;
        }
        for (int i = 1; i <= amount; i++) {
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(drop.getItemStack()));
                continue;
            }
            player.getInventory().addItem(new ItemStack(drop.getItemStack()));
        }
        CoinsUtil.takeCoins(player.getUniqueId().toString(), finalPrice);
        String message = Settings.LANG_BUY_MESSAGE;
        message = message.replace("%amount%", String.valueOf(amount))
                .replace("%itemName%", drop.getItemStack().getItemMeta().getDisplayName())
                .replace("%price%", LangUtil.formatNumber(finalPrice));
        player.sendMessage(message);
    }

    public static void sellAll(Player player, Drop drop) {
        int amount = 0;
        ItemStack dropStack = drop.getItemStack();
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            if (!(itemStack.hasItemMeta())) continue;
            if (itemStack.getItemMeta().getDisplayName().equals(dropStack.getItemMeta().getDisplayName())) {
                amount += itemStack.getAmount();
                player.getInventory().setItem(i, null);
            }
        }
        if (amount == 0) {
            player.sendMessage(Settings.LANG_NOT_ENOUGH_ITEMS);
            return;
        }
        int price = drop.getSellPrice();
        int coinsToAdd = price * amount;
        CoinsUtil.addCoins(player.getUniqueId().toString(), coinsToAdd);
        String message = Settings.LANG_SELL_MESSAGE;
        message = message.replace("%amount%", String.valueOf(amount))
                .replace("%itemName%", dropStack.getItemMeta().getDisplayName())
                .replace("%price%", LangUtil.formatNumber(coinsToAdd));
        player.sendMessage(message);
    }

    public static void sellOne(Player player, Drop drop) {
        ItemStack dropStack = drop.getItemStack();
        boolean found = false;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            if (!(itemStack.hasItemMeta())) continue;
            if (itemStack.getItemMeta().getDisplayName().equals(dropStack.getItemMeta().getDisplayName())) {
                found = true;
                itemStack.setAmount(itemStack.getAmount() - 1);
                break;
            }
        }
        int price = drop.getSellPrice();
        if (found) {
            CoinsUtil.addCoins(player.getUniqueId().toString(), price);
            String message = Settings.LANG_SELL_MESSAGE;
            message = message.replace("%amount%", String.valueOf(1))
                    .replace("%itemName%", dropStack.getItemMeta().getDisplayName())
                    .replace("%price%", LangUtil.formatNumber(price));
            player.sendMessage(message);
        } else {
            player.sendMessage(Settings.LANG_NOT_ENOUGH_ITEMS);
        }
    }

    public static List<String> getLoreWithoutEnchants(ItemStack itemStack) {
        List<String> ret = new ArrayList<>();
        if (!(itemStack.hasItemMeta())) return Collections.emptyList();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return Collections.emptyList();
        if (itemMeta.getLore() == null) return Collections.emptyList();
        for (String string : itemMeta.getLore()) {
            if (containsEnchantmentName(string)) break;
            else ret.add(string);
        }
        return ret;
    }

    public static void patchLore(ItemStack itemStack) {
        List<String> lore = new ArrayList<>(Objects.requireNonNull(getLoreWithoutEnchants(itemStack)));
        itemStack.getEnchantments().keySet().forEach(enchantment -> {
            int level = itemStack.getEnchantments().get(enchantment);
            lore.add(ChatColor.translateAlternateColorCodes('&', "&7" + EnchantUtil.getEnchantName(enchantment.getName()) + " " + level));
        });
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
    }

    public static boolean containsEnchantmentName(String string) {
        AtomicBoolean contains = new AtomicBoolean(false);
        EnchantmentCache.getCache().forEach(enchantment -> {
            if (string.contains(enchantment.getName()))
                contains.set(true);
        });
        return contains.get();
    }
}
