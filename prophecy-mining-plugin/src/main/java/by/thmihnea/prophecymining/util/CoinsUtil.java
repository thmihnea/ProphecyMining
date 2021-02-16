package by.thmihnea.prophecymining.util;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.data.TableType;

public class CoinsUtil {

    private static final SQLUtil sqlUtil = ProphecyMining.getInstance().getSqlUtil();

    public static void addCoins(String uniqueId, int amount) {
        int existing = getCoins(uniqueId);
        setCoins(uniqueId, existing + amount);
    }

    public static void takeCoins(String uniqueId, int amount) {
        int existing = getCoins(uniqueId);
        setCoins(uniqueId, existing - amount);
    }

    public static void setCoins(String uniqueId, int amount) {
        sqlUtil.setValue(TableType.PLAYER_COINS, "COINS", uniqueId, amount);
    }

    public static int getCoins(String uniqueId) {
        return sqlUtil.getValue(TableType.PLAYER_COINS, "COINS", uniqueId);
    }
}
