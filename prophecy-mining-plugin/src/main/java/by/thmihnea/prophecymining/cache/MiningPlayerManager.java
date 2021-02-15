package by.thmihnea.prophecymining.cache;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiningPlayerManager {

    public static final Map<UUID, MiningPlayer> cache = new HashMap<>();

    public static void addEntry(MiningPlayer miningPlayer) {
        cache.put(miningPlayer.getPlayer().getUniqueId(), miningPlayer);
    }

    public static void removeEntry(Player player) {
        cache.remove(player.getUniqueId());
    }

    public static MiningPlayer getMiningPlayer(Player player) {
        return cache.get(player.getUniqueId());
    }

    public static boolean contains(Player player) {
        return cache.containsKey(player.getUniqueId());
    }

    public static Map<UUID, MiningPlayer> getCache() {
        return cache;
    }
}
