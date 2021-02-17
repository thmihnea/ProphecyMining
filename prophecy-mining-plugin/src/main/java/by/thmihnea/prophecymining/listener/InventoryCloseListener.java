package by.thmihnea.prophecymining.listener;

import by.thmihnea.prophecymining.cache.InventoryViewerCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();
        if (InventoryViewerCache.contains(inventory))
            InventoryViewerCache.removeEntry(inventory);
    }
}
