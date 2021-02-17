package by.thmihnea.prophecymining.listener;

import by.thmihnea.prophecymining.cache.InventoryViewerCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (!(InventoryViewerCache.contains(inventory))) return;
        ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;
        if (itemStack.getType().toString().toUpperCase().contains("PICKAXE")) {
            e.setCancelled(true);
        }
    }
}
