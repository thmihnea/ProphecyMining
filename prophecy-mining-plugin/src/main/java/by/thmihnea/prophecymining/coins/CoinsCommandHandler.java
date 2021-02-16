package by.thmihnea.prophecymining.coins;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.command.AbstractCommand;
import by.thmihnea.prophecymining.util.LangUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class CoinsCommandHandler implements CommandExecutor {

    public CoinsCommandHandler() {
        Objects.requireNonNull(ProphecyMining.getInstance().getCommand("coins")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            LangUtil.sendStringList(commandSender, Settings.LANG_COINS_HELP_MESSAGE);
            return false;
        }
        String subCommand = args[0].toLowerCase(); subCommand = subCommand.substring(0, 1).toUpperCase() + subCommand.substring(1);
        try {
            Class<?> clazz = Class.forName("by.thmihnea.prophecymining.coins.command.Command" + subCommand);
            final String[] finalArgs = new String[args.length - 1];
            System.arraycopy(args, 1, finalArgs, 0, args.length - 1);
            AbstractCommand abstractCommand = (AbstractCommand) clazz
                    .getDeclaredConstructor(CommandSender.class, String[].class)
                    .newInstance(commandSender, finalArgs);
            abstractCommand.execute();
            return true;
        } catch (ClassNotFoundException e) {
            commandSender.sendMessage(Settings.LANG_COMMAND_NOT_EXISTS);
            return false;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
