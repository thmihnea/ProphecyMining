package by.thmihnea.prophecymining.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class LangUtil {

    public static List<String> translateLoreColorCodes(List<String> lore) {
        return lore.stream().map(string -> ChatColor.translateAlternateColorCodes('&', string)).collect(Collectors.toList());
    }

    public static List<String> applyPlayerPlaceholder(Player player, List<String> lore) {
        return lore.stream().map(string -> string.replace("%player%", player.getName())).collect(Collectors.toList());
    }

    public static List<String> applyLevelPlaceholder(int level, List<String> lore) {
        return lore.stream().map(string -> string.replace("%level%", String.valueOf(level))).collect(Collectors.toList());
    }

    public static void sendStringList(Player player, List<String> list) {
        list.forEach(player::sendMessage);
    }
}
