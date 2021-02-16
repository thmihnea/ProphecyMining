package by.thmihnea.prophecymining;

import by.thmihnea.prophecymining.util.LangUtil;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Objects;

public class Settings {

    /**
     * MySQL related variables.
     */
    public static final String SQL_HOST = ProphecyMining.getCfg().getString("MySQL.host");
    public static final int SQL_PORT = ProphecyMining.getCfg().getInt("MySQL.port");
    public static final String SQL_DATABASE = ProphecyMining.getCfg().getString("MySQL.database");
    public static final String SQL_USERNAME = ProphecyMining.getCfg().getString("MySQL.username");
    public static final String SQL_PASSWORD = ProphecyMining.getCfg().getString("MySQL.password");

    /**
     * Blocks.
     */
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
    public static final int COINS_DROP_CHANCE = ProphecyMining.getCfg().getInt("coins.drop_chance");
    public static final int COINS_LOWER = ProphecyMining.getCfg().getInt("coins.lower_bound");
    public static final int COINS_UPPER = ProphecyMining.getCfg().getInt("coins.upper_bound");

    /**
     * Language variables.
     */
    public static final List<String> LANG_LEVELUP_MESSAGE = LangUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("lang.levelup_message"));
    public static final List<String> LANG_COINS_HELP_MESSAGE = LangUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("lang.coins_help_message"));
    public static final String LANG_RARE_DROP_COINS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.rare_drop_coins")));
    public static final String LANG_RARE_DROP = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.rare_drop")));
    public static final String LANG_DISPLAY_PLAYER_COINS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.display_coins")));
    public static final String LANG_ADD_COINS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.add_coins")));
    public static final String LANG_REMOVE_COINS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.remove_coins")));
    public static final String LANG_SET_COINS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.set_coins")));
    public static final String LANG_PLAYER_NOT_EXISTS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.target_does_not_exist")));
    public static final String LANG_SPECIFY_PLAYER = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.coins_specify_player")));
    public static final String LANG_SPECIFY_AMOUNT = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.coins_specify_amount")));
    public static final String LANG_SPECIFY_VALID_AMOUNT = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.coins_specify_valid_amount")));
    public static final String LANG_NO_PERMISSION = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.no_permission")));
    public static final String LANG_COMMAND_NOT_EXISTS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.command_not_found")));
    public static final String LANG_ACTIONBAR_MESSAGE = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.actionbar")));

    /**
     * Runnable utils.
     */
    public static final long MINUTE = 1200L;
}
