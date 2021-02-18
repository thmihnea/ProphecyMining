package by.thmihnea.prophecymining;

import by.thmihnea.prophecymining.util.LangUtil;
import net.milkbowl.vault.chat.Chat;
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
    public static final int LUCK_BONUS_CHANCE_PER_LEVEL = ProphecyMining.getCfg().getInt("enchantments.luck_bonus_chance_per_level");
    public static final int ENCHANTMENT_AUTOPICKUP_PRICE = ProphecyMining.getCfg().getInt("enchantments.autopickup.price");
    public static final int ENCHANTMENT_AUTOSELL_PRICE = ProphecyMining.getCfg().getInt("enchantments.autosell.price");
    public static final int ENCHANTMENT_LUCK_PRICE = ProphecyMining.getCfg().getInt("enchantments.luck.price");
    public static final int ENCHANTMENT_DRILL_PRICE = ProphecyMining.getCfg().getInt("enchantments.drill.price");
    public static final int ENCHANTMENT_FORTUNE_PRICE = ProphecyMining.getCfg().getInt("enchantments.fortune.price");
    public static final int ENCHANTMENT_EFFICIENCY_PRICE = ProphecyMining.getCfg().getInt("enchantments.efficiency.price");
    public static final int ENCHANTMENT_UNBREAKING_PRICE = ProphecyMining.getCfg().getInt("enchantments.unbreaking.price");
    public static final int ENCHANTMENT_MENDING_PRICE = ProphecyMining.getCfg().getInt("enchantments.mending.price");
    public static final int REGION_ROLLBACK_TIME = ProphecyMining.getCfg().getInt("region.time");

    /**
     * Language variables.
     */
    public static final List<String> LANG_LEVELUP_MESSAGE = LangUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("lang.levelup_message"));
    public static final List<String> LANG_COINS_HELP_MESSAGE = LangUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("lang.coins_help_message"));
    public static final List<String> LANG_SHOP_ITEM_BUY_LORE = LangUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("lang.shop_item_buy_lore"));
    public static final List<String> LANG_SHOP_ITEM_SELL_LORE = LangUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("lang.shop_item_sell_lore"));
    public static final List<String> LANG_REGION_HELP_MESSAGE = LangUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("lang.region_help_message"));
    public static final String LANG_REGION_FORMAT = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.region_format")));
    public static final String LANG_DISPLAYING_REGIONS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.displaying_regions")));
    public static final String LANG_REGION_CREATED = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.region_created")));
    public static final String LANG_REGION_REMOVED = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.region_removed")));
    public static final String LANG_NO_REGION_FOUND = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.region_not_found")));
    public static final String LANG_SPECIFY_NAME = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.specify_name")));
    public static final String LANG_SPECIFY_TYPE = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.specify_type")));
    public static final String LANG_TYPE_INVALID = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.type_invalid")));
    public static final String LANG_INCOMPLETE_REGION = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.incomplete_region")));
    public static final String LANG_NEED_SELECTION_FIRST = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.need_selection_first")));
    public static final String LANG_NAME_ALREADY_EXISTS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.name_already_exists")));
    public static final String LANG_INVENTORY_FULL = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.inventory_full")));
    public static final String LANG_NO_MATCH = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.no_match")));
    public static final String LANG_ONLY_PLAYERS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.only_players")));
    public static final String LANG_CLOSE_MENU = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.close_menu")));
    public static final String LANG_ITEM_BUY_NAME = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.shop_item_buy_name")));
    public static final String LANG_ITEM_SELL_NAME = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.shop_item_sell_name")));
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
    public static final String LANG_RARE_ITEM_SHOP_TITLE = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.rareitemshop_title")));
    public static final String LANG_BUY_TEXT = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.buy_text")));
    public static final String LANG_SELL_TEXT = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.sell_text")));
    public static final String LANG_NOT_ENOUGH_COINS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.not_enough_coins")));
    public static final String LANG_BUY_MESSAGE = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.buy_message")));
    public static final String LANG_NOT_ENOUGH_ITEMS = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.not_enough_items")));
    public static final String LANG_SELL_MESSAGE = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.sell_message")));
    public static final String LANG_ENCHANTMENT_SHOP = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.enchantment_shop_title")));
    public static final String LANG_HOLD_PICKAXE = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.require_hold_pickaxe")));
    public static final String LANG_COINS_SUBTRACTED = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.coins_subtracted")));
    public static final String LANG_COINS_ADDED = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.coins_added")));
    public static final String LANG_MAX_LEVEL = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.max_level")));
    public static final String LANG_ENCHANTMENTS_BUY = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("enchantments.buy")));
    public static final String LANG_ENCHANTMENTS_NAME = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("enchantments.name")));
    public static final String LANG_NOT_ENOUGH_MONEY = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ProphecyMining.getCfg().getString("lang.not_enough_money")));

    /**
     * Runnable utils.
     */
    public static final long MINUTE = 1200L;
}
