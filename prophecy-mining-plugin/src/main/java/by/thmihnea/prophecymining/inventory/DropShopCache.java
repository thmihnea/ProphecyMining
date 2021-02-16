package by.thmihnea.prophecymining.inventory;

import by.thmihnea.prophecymining.item.Drop;

import java.util.HashMap;
import java.util.Map;

public class DropShopCache {

    private static final Map<Drop, DropShopInventoryProvider> cache = new HashMap<>();

    public static void addEntry(Drop drop, DropShopInventoryProvider dropShopInventoryProvider) {
        cache.put(drop, dropShopInventoryProvider);
    }

    public static void removeEntry(Drop drop) {
        cache.remove(drop);
    }

    public static DropShopInventoryProvider getDropShop(Drop drop) {
        return cache.get(drop);
    }

    public static Map<Drop, DropShopInventoryProvider> getCache() {
        return cache;
    }
}
