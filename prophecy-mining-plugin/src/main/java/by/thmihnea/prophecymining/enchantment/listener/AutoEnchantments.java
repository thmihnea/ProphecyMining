package by.thmihnea.prophecymining.enchantment.listener;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.coins.CoinsDrop;
import by.thmihnea.prophecymining.util.EnchantUtil;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.brcdev.shopgui.shop.ShopItem;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AutoEnchantments implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;
        if (!(itemStack.getType().toString().toUpperCase().contains("PICKAXE"))) return;
        List<ItemStack> drops = new ArrayList<>(e.getBlock().getDrops());
        if (EnchantUtil.containsEnchant(itemStack, "autosell")) {
            drops.forEach(drop -> {
                this.applyFortune(itemStack, drop);
                this.sellDrop(player, drop);
            });
            block.setType(Material.AIR);
            block.getDrops().clear();
            return;
        }
        if (EnchantUtil.containsEnchant(itemStack, "autopickup")) {
            block.setType(Material.AIR);
            block.getDrops().clear();
            drops.forEach(drop -> {
                this.applyFortune(itemStack, drop);
                player.getInventory().addItem(drop);
            });
            drops.clear();
        }
    }

    private void applyFortune(ItemStack itemStack, ItemStack drop) {
        if (EnchantUtil.containsEnchant(itemStack, "fortune")) {
            int level = itemStack.getEnchantments().get(EnchantUtil.getEnchantmentByKey(itemStack, "fortune"));
            drop.setAmount(getDropsAmount(level));
        }
    }

    private void sellDrop(Player player, ItemStack drop) {
        ShopItem shopItem = ShopGuiPlusApi.getItemStackShopItem(drop);
        double price = shopItem.getSellPrice() * drop.getAmount();
        Economy economy = ProphecyMining.getInstance().getBootstrapper().getEconomy();
        economy.depositPlayer(player, price);
    }

    private int getDropsAmount(int level) {
        return CoinsDrop.getRandom(1, level + 1);
    }
}
