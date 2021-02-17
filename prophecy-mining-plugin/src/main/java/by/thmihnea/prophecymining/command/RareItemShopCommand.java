package by.thmihnea.prophecymining.command;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.inventory.RareItemShopInventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RareItemShopCommand implements CommandExecutor {

    public RareItemShopCommand() {
        ProphecyMining.getInstance().getCommand("rareitemshop").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Settings.LANG_ONLY_PLAYERS);
            return false;
        }
        Player player = (Player) commandSender;
        RareItemShopInventoryProvider.rareItemShop.open(player);
        return true;
    }
}
