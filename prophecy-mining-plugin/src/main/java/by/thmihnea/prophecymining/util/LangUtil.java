package by.thmihnea.prophecymining.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class LangUtil {

    private static DecimalFormat format = new DecimalFormat("###,###,###");

    public static List<String> translateLoreColorCodes(List<String> lore) {
        return lore.stream().map(string -> ChatColor.translateAlternateColorCodes('&', string)).collect(Collectors.toList());
    }

    public static List<String> applyPlayerPlaceholder(Player player, List<String> lore) {
        return lore.stream().map(string -> string.replace("%player%", player.getName())).collect(Collectors.toList());
    }

    public static List<String> applyLevelPlaceholder(int level, List<String> lore) {
        return lore.stream().map(string -> string.replace("%level%", String.valueOf(level))).collect(Collectors.toList());
    }

    public static List<String> applyAmountPlaceholder(int amount, List<String> lore) {
        return lore.stream().map(string -> string.replace("%amount%", String.valueOf(amount))).collect(Collectors.toList());
    }

    public static List<String> applyItemNamePlaceholder(String itemName, List<String> lore) {
        return lore.stream().map(string -> string.replace("%itemName%", itemName)).collect(Collectors.toList());
    }

    public static List<String> applyPricePlaceholder(int price, List<String> lore) {
        return lore.stream().map(string -> string.replace("%price%", String.valueOf(price))).collect(Collectors.toList());
    }

    public static List<String> applyPricePlaceholder(String price, List<String> lore) {
        return lore.stream().map(string -> string.replace("%price%", String.valueOf(price))).collect(Collectors.toList());
    }

    public static void sendStringList(Player player, List<String> list) {
        list.forEach(player::sendMessage);
    }

    public static void sendStringList(CommandSender commandSender, List<String> list) { list.forEach(commandSender::sendMessage); }

    public static String formatNumber(int number) {
        return format.format(number);
    }
}
