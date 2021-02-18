package by.thmihnea.prophecymining.placeholder;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.cache.MiningPlayerManager;
import by.thmihnea.prophecymining.util.CoinsUtil;
import by.thmihnea.prophecymining.util.LangUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    public Placeholders() {
        this.register();
        ProphecyMining.getInstance().logInfo("Total Blocks Mined placeholder has been successfully registered!");
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pm";
    }

    @Override
    public @NotNull String getAuthor() {
        return "thmihnea";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        MiningPlayer miningPlayer = MiningPlayerManager.getMiningPlayer(player);
        switch (identifier) {
            case "total_blocks_mined":
                return String.valueOf(miningPlayer.getAllBlocksMined());
            case "coal_mined":
                return String.valueOf(miningPlayer.getBlocksMined("COAL_MINED"));
            case "redstone_mined":
                return String.valueOf(miningPlayer.getBlocksMined("REDSTONE_MINED"));
            case "lapis_mined":
                return String.valueOf(miningPlayer.getBlocksMined("LAPIS_MINED"));
            case "gold_mined":
                return String.valueOf(miningPlayer.getBlocksMined("GOLD_MINED"));
            case "iron_mined":
                return String.valueOf(miningPlayer.getBlocksMined("IRON_MINED"));
            case "diamond_mined":
                return String.valueOf(miningPlayer.getBlocksMined("DIAMOND_MINED"));
            case "current_xp":
                return LangUtil.formatNumber(miningPlayer.getCurrentXp());
            case "xp_to_next_level":
                return LangUtil.formatNumber(miningPlayer.getXpToNextLevel());
            case "level":
                return String.valueOf(miningPlayer.getLevel());
            case "coins":
                return LangUtil.formatNumber(CoinsUtil.getCoins(player.getUniqueId().toString()));
            case "coins_formatted":
                return CoinsUtil.getCoinsFormatted(player);
            case "position":
                return String.valueOf(ProphecyMining.getInstance().getSqlUtil().getPosition(player));
            case "total":
                return String.valueOf(ProphecyMining.getInstance().getSqlUtil().getTotalPlayers());
            default:
                return "This placeholder doesn't exist!";
        }
    }
}
