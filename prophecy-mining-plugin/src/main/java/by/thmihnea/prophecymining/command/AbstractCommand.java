package by.thmihnea.prophecymining.command;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@Getter
public abstract class AbstractCommand {

    private final CommandSender commandSender;
    private final String[] args;
    private final String permission;

    public AbstractCommand(CommandSender commandSender, String[] args, String permission) {
        this.commandSender = commandSender;
        this.args = args;
        this.permission = permission;
    }

    public void sendMessage(String message) {
        this.commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public abstract void execute();
}
