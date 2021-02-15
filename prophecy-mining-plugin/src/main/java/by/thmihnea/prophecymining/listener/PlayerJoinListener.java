package by.thmihnea.prophecymining.listener;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.util.SQLUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        SQLUtil sqlUtil = ProphecyMining.getInstance().getSqlUtil();
        if (!(sqlUtil.exists(player.getUniqueId()))) {
            sqlUtil.initPlayer(player);
        }
        new MiningPlayer(player);
    }
}
