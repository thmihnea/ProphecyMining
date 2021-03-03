package by.thmihnea.prophecymining.util;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.data.TableType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class CoinsUtil {

    private static final SQLUtil sqlUtil = ProphecyMining.getInstance().getSqlUtil();

    public static void addCoins(String uniqueId, int amount) {
        Player player = Bukkit.getPlayer(UUID.fromString(uniqueId));
        String message = Settings.LANG_COINS_ADDED;
        if (message.contains("%amount%"))
            message = message.replace("%amount%", LangUtil.formatNumber(amount));
        player.sendMessage(message);
        int existing = getCoins(uniqueId);
        setCoins(uniqueId, existing + amount);
    }

    public static void takeCoins(String uniqueId, int amount) {
        Player player = Bukkit.getPlayer(UUID.fromString(uniqueId));
        String message = Settings.LANG_COINS_SUBTRACTED;
        if (message.contains("%amount%"))
            message = message.replace("%amount%", LangUtil.formatNumber(amount));
        player.sendMessage(message);
        int existing = getCoins(uniqueId);
        setCoins(uniqueId, existing - amount);
    }

    public static void setCoins(String uniqueId, int amount) {
        sqlUtil.setValue(TableType.PLAYER_COINS, "COINS", uniqueId, amount);
    }

    public static int getCoins(String uniqueId) {
        return sqlUtil.getValue(TableType.PLAYER_COINS, "COINS", uniqueId);
    }

    public static String getCoinsFormatted(Player player) {
        int coins = getCoins(player.getUniqueId().toString());
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (coins < 1000) {
            return String.valueOf(coins);
        }
        else if (coins < 1000000) {
            float div = (float) coins / (float) 1000;
            return df.format(div) + "k";
        } else if (coins < 1000000000) {
            float div = (float) coins / (float) 1000000;
            return df.format(div) + "M";
        } else {
            float div = (float) coins / (float) 1000000000;
            return df.format(div) + "B";
        }
    }
}
