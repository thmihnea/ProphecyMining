package by.thmihnea.prophecymining.command;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.inventory.EnchantingInventoryProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchanterCommand implements CommandExecutor {

    public EnchanterCommand() {
        ProphecyMining.getInstance().getCommand("enchanter").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Settings.LANG_ONLY_PLAYERS);
            return false;
        }
        Player player = (Player) commandSender;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!(itemStack.getType().toString().toUpperCase().contains("PICKAXE"))) {
            player.sendMessage(Settings.LANG_HOLD_PICKAXE);
            return false;
        }
        EnchantingInventoryProvider enchanting = new EnchantingInventoryProvider(itemStack);
        enchanting.open(player);
        return true;
    }
}
