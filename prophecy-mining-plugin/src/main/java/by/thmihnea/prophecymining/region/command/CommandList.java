package by.thmihnea.prophecymining.region.command;

import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.command.AbstractCommand;
import by.thmihnea.prophecymining.region.RegionCache;
import by.thmihnea.prophecymining.util.LangUtil;
import org.bukkit.command.CommandSender;

public class CommandList extends AbstractCommand {

    public CommandList(CommandSender commandSender, String[] args) {
        super(commandSender, args, "region.list");
    }

    @Override
    public void execute() {
        if (!(this.getCommandSender().hasPermission(this.getPermission()))) {
            this.sendMessage(Settings.LANG_NO_PERMISSION);
            return;
        }
        this.sendMessage(Settings.LANG_DISPLAYING_REGIONS);
        RegionCache.getCachedRegions().forEach(region -> {
            String message = Settings.LANG_REGION_FORMAT;
            message = message.replace("%regionName%", region.getName())
                    .replace("%ores%", LangUtil.formatNumber(region.getOres().size()))
                    .replace("%stones%", LangUtil.formatNumber(region.getStones().size()))
                    .replace("%type%", region.getRegionType().getIdentifier().toUpperCase());
            this.sendMessage(message);
        });
    }
}
