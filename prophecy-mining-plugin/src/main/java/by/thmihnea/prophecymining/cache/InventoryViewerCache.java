package by.thmihnea.prophecymining.cache;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryViewerCache {

    private static final List<Inventory> cache = new ArrayList<>();

    public static void addEntry(Inventory inventory) {
        cache.add(inventory);
    }

    public static void removeEntry(Inventory inventory) {
        cache.remove(inventory);
    }

    public static boolean contains(Inventory inventory) {
        return cache.contains(inventory);
    }

    public static List<Inventory> getCache() {
        return cache;
    }
}
