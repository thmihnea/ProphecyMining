package by.thmihnea.prophecymining.coins.command;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.command.AbstractCommand;
import by.thmihnea.prophecymining.data.TableType;
import by.thmihnea.prophecymining.util.SQLUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class CommandGet extends AbstractCommand {

    public CommandGet(CommandSender commandSender, String[] args) {
        super(commandSender, args, "coins.get");
    }

    @Override
    public void execute() {
        CommandSender commandSender = this.getCommandSender();
        if (!(commandSender.hasPermission(this.getPermission()))) {
            this.sendMessage(Settings.LANG_NO_PERMISSION);
            return;
        }
        if (this.getArgs().length == 0) {
            this.sendMessage(Settings.LANG_SPECIFY_PLAYER);
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(this.getArgs()[0]);
        if (!(offlinePlayer.hasPlayedBefore())) {
            this.sendMessage(Settings.LANG_PLAYER_NOT_EXISTS);
            return;
        }
        String uniqueId = offlinePlayer.getUniqueId().toString();
        SQLUtil sqlUtil = ProphecyMining.getInstance().getSqlUtil();
        int coins = sqlUtil.getValue(TableType.PLAYER_COINS, "COINS", uniqueId);
        String message = Settings.LANG_DISPLAY_PLAYER_COINS;
        if (message.contains("%player%")) message = message.replace("%player%", offlinePlayer.getName());
        if (message.contains("%amount%")) message = message.replace("%amount%", String.valueOf(coins));
        this.sendMessage(message);
    }
}
