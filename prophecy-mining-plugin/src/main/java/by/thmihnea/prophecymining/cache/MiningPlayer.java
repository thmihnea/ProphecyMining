package by.thmihnea.prophecymining.cache;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.data.TableType;
import by.thmihnea.prophecymining.runnable.DataSyncTask;
import by.thmihnea.prophecymining.util.SQLUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MiningPlayer {

    private Player player;
    private SQLUtil sqlUtil;
    private DataSyncTask dataSyncTask;

    private List<String> fields = Arrays.asList(
            "COAL_MINED", "IRON_MINED",
            "GOLD_MINED", "EMERALD_MINED",
            "LAPIS_MINED", "REDSTONE_MINED",
            "DIAMOND_MINED");
    private Map<String, Integer> blocksMined;
    private int currentXp;
    private int xpToNextLevel;
    private int level;

    public MiningPlayer(Player player) {
        this.sqlUtil = ProphecyMining.getInstance().getSqlUtil();
        this.player = player;
        this.currentXp = sqlUtil.getValue(TableType.PLAYER_LEVELS, "CURRENT_XP", player.getUniqueId().toString());
        this.xpToNextLevel = sqlUtil.getValue(TableType.PLAYER_LEVELS, "XP_TO_NEXT_LEVEL", player.getUniqueId().toString());
        this.level = sqlUtil.getValue(TableType.PLAYER_LEVELS, "LEVEL", player.getUniqueId().toString());
        this.blocksMined = new HashMap<>();
        this.fields.forEach(field -> {
            int blocks = this.sqlUtil.getValue(TableType.PLAYER_DATA, field, player.getUniqueId().toString());
            this.blocksMined.put(field, blocks);
        });
        this.dataSyncTask = new DataSyncTask(player);
        MiningPlayerManager.addEntry(this);
    }

    public void setCurrentXpSQL(int value) {
        this.sqlUtil.setValue(TableType.PLAYER_LEVELS, "CURRENT_XP", this.player.getUniqueId().toString(), value);
        this.setCurrentXp(value);
    }

    public void setXpToNextLevelSQL(int value) {
        this.sqlUtil.setValue(TableType.PLAYER_LEVELS, "XP_TO_NEXT_LEVEL", this.player.getUniqueId().toString(), value);
        this.setXpToNextLevel(value);
    }

    public void setLevelSQL(int value) {
        this.sqlUtil.setValue(TableType.PLAYER_LEVELS, "LEVEL", this.player.getUniqueId().toString(), value);
        this.setLevel(value);
    }

    public void uploadData() {
        long start = System.currentTimeMillis();
        this.setCurrentXpSQL(this.currentXp);
        this.setXpToNextLevelSQL(this.xpToNextLevel);
        this.blocksMined.keySet().forEach(field -> {
            this.sqlUtil.setValue(TableType.PLAYER_DATA, field, player.getUniqueId().toString(), this.blocksMined.get(field));
        });
        ProphecyMining.getInstance().logInfo("Uploaded data for player " + this.player.getName() + ". Process took: " + (System.currentTimeMillis() - start) + "ms");
    }

    public void levelUp() {
        this.uploadData();
        int currentXp = this.currentXp;
        int xpToNextLevel = this.xpToNextLevel;
        while (currentXp >= xpToNextLevel) {
            currentXp -= xpToNextLevel;
            boolean incremental = ProphecyMining.getCfg().getBoolean("leveling.milestones.increment.enabled");
            if (incremental) {
                xpToNextLevel = ProphecyMining.getCfg().getInt("leveling.milestones.increment.increment_value") * this.level;
            } else {
                xpToNextLevel = ProphecyMining.getCfg().getInt("leveling.milestones.level_" + this.level);
            }
            if (this.level == 50) xpToNextLevel = 0;
            this.setXpToNextLevelSQL(xpToNextLevel);
            this.setLevelSQL(this.level + 1);
        }
        this.setCurrentXpSQL(currentXp);
    }

    public void setBlocksMined(String field, int value) {
        this.blocksMined.put(field, value);
    }

    public int getBlocksMined(String field) {
        return this.blocksMined.get(field);
    }
}
