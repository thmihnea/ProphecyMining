package by.thmihnea.prophecymining.listener;

import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.cache.MiningPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (MiningPlayerManager.contains(player)) {
            MiningPlayer miningPlayer = MiningPlayerManager.getMiningPlayer(player);
            miningPlayer.uploadData();
            miningPlayer.getDataSyncTask().clear();
            MiningPlayerManager.removeEntry(player);
        }
    }
}
