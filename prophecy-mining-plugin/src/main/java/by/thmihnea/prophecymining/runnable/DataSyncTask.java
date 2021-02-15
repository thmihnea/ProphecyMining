package by.thmihnea.prophecymining.runnable;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.cache.MiningPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class DataSyncTask implements Runnable {

    private Player player;
    private BukkitTask task;

    public DataSyncTask(Player player) {
        this.player = player;
        Bukkit.getScheduler().runTaskTimer(ProphecyMining.getInstance(), this, 0L, 5 * Settings.MINUTE);
    }

    @Override
    public void run() {
        if (!(this.player.isOnline())) {
            this.clear();
            return;
        }
        MiningPlayer miningPlayer = MiningPlayerManager.getMiningPlayer(this.player);
        miningPlayer.uploadData();
    }

    public void clear() {
        if (this.task != null) {
            Bukkit.getScheduler().cancelTask(this.task.getTaskId());
            this.task = null;
        }
    }
}
