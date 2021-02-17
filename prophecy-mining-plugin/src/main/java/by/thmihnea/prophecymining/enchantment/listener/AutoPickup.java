package by.thmihnea.prophecymining.enchantment.listener;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.util.EnchantUtil;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.brcdev.shopgui.shop.ShopItem;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoPickup implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;
        if (!(itemStack.getType().toString().toUpperCase().contains("PICKAXE"))) return;
        List<ItemStack> drops = new ArrayList<>(e.getBlock().getDrops());
        if (EnchantUtil.containsEnchant(itemStack, "autosell")) {
            drops.forEach(drop -> {
                ShopItem shopItem = ShopGuiPlusApi.getItemStackShopItem(drop);
                double price = shopItem.getSellPrice() * drop.getAmount();
                Economy economy = ProphecyMining.getInstance().getBootstrapper().getEconomy();
                economy.depositPlayer(player, price);
            });
            e.getBlock().setType(Material.AIR);
            e.getBlock().getDrops().clear();
            return;
        }
        if (EnchantUtil.containsEnchant(itemStack, "autopickup")) {
            e.getBlock().setType(Material.AIR);
            e.getBlock().getDrops().clear();
            drops.forEach(item -> player.getInventory().addItem(item));
            drops.clear();
        }
    }
}
