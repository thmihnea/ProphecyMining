package by.thmihnea.prophecymining;

public class Settings {

    public static final String SQL_HOST = ProphecyMining.getCfg().getString("MySQL.host");
    public static final int SQL_PORT = ProphecyMining.getCfg().getInt("MySQL.port");
    public static final String SQL_DATABASE = ProphecyMining.getCfg().getString("MySQL.database");
    public static final String SQL_USERNAME = ProphecyMining.getCfg().getString("MySQL.username");
    public static final String SQL_PASSWORD = ProphecyMining.getCfg().getString("MySQL.password");

    public static final int COAL_START = ProphecyMining.getCfg().getInt("xp.coal.start");
    public static final int COAL_END = ProphecyMining.getCfg().getInt("xp.coal.end");
    public static final int IRON_START = ProphecyMining.getCfg().getInt("xp.iron.start");
    public static final int IRON_END = ProphecyMining.getCfg().getInt("xp.iron.end");
    public static final int GOLD_START = ProphecyMining.getCfg().getInt("xp.gold.start");
    public static final int GOLD_END = ProphecyMining.getCfg().getInt("xp.gold.end");
    public static final int EMERALD_START = ProphecyMining.getCfg().getInt("xp.emerald.start");
    public static final int EMERALD_END = ProphecyMining.getCfg().getInt("xp.emerald.end");
    public static final int LAPIS_START = ProphecyMining.getCfg().getInt("xp.lapis.start");
    public static final int LAPIS_END = ProphecyMining.getCfg().getInt("xp.lapis.end");
    public static final int REDSTONE_START = ProphecyMining.getCfg().getInt("xp.redstone.start");
    public static final int REDSTONE_END = ProphecyMining.getCfg().getInt("xp.redstone.end");
    public static final int DIAMOND_START = ProphecyMining.getCfg().getInt("xp.diamond.start");
    public static final int DIAMOND_END = ProphecyMining.getCfg().getInt("xp.diamond.end");

    public static final long MINUTE = 1200L;
}
