package by.thmihnea.prophecymining.coins.command;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.command.AbstractCommand;
import by.thmihnea.prophecymining.data.TableType;
import by.thmihnea.prophecymining.util.LangUtil;
import by.thmihnea.prophecymining.util.NumberUtil;
import by.thmihnea.prophecymining.util.SQLUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class CommandTake extends AbstractCommand {

    public CommandTake(CommandSender commandSender, String[] args) {
        super(commandSender, args, "coins.take");
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
        if (this.getArgs().length == 1) {
            this.sendMessage(Settings.LANG_SPECIFY_AMOUNT);
            return;
        }
        String coins = this.getArgs()[1];
        if (!(NumberUtil.isNumber(coins))) {
            this.sendMessage(Settings.LANG_SPECIFY_VALID_AMOUNT);
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(this.getArgs()[0]);
        if (!(offlinePlayer.hasPlayedBefore())) {
            this.sendMessage(Settings.LANG_PLAYER_NOT_EXISTS);
            return;
        }
        int amount = Integer.parseInt(coins);
        if (amount < 0) {
            this.sendMessage(Settings.LANG_SPECIFY_VALID_AMOUNT);
            return;
        }
        String uniqueId = offlinePlayer.getUniqueId().toString();
        SQLUtil sqlUtil = ProphecyMining.getInstance().getSqlUtil();
        int existing = sqlUtil.getValue(TableType.PLAYER_COINS, "COINS", uniqueId);
        int finalCoins = existing - amount;
        if (finalCoins < 0) finalCoins = 0;
        sqlUtil.setValue(TableType.PLAYER_COINS, "COINS", uniqueId, finalCoins);
        String message = Settings.LANG_REMOVE_COINS;
        if (message.contains("%player%")) message = message.replace("%player%", offlinePlayer.getName());
        if (message.contains("%amount%")) message = message.replace("%amount%", LangUtil.formatNumber(amount));
        this.sendMessage(message);
    }
}
