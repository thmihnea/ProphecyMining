package by.thmihnea.prophecymining.region.command;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.command.AbstractCommand;
import by.thmihnea.prophecymining.region.CuboidRegion;
import by.thmihnea.prophecymining.region.RegionCache;
import by.thmihnea.prophecymining.region.block.RegionBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CommandDelete extends AbstractCommand {

    public CommandDelete(CommandSender commandSender, String[] args) {
        super(commandSender, args, "region.delete");
    }

    @Override
    public void execute() {
        CommandSender commandSender = this.getCommandSender();
        if (!(commandSender instanceof Player)) return;
        Player player = (Player) commandSender;
        if (!(commandSender.hasPermission(this.getPermission()))) {
            this.sendMessage(Settings.LANG_NO_PERMISSION);
            return;
        }
        if (this.getArgs().length == 0) {
            this.sendMessage(Settings.LANG_SPECIFY_NAME);
            return;
        }
        String name = this.getArgs()[0];
        AtomicBoolean exists = new AtomicBoolean(false);
        AtomicReference<File> regionFile = new AtomicReference<>(null);
        File regionDirectory = ProphecyMining.getInstance().getRegionDirectory();
        Arrays.stream(regionDirectory.listFiles()).forEach(file -> {
            String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
            if (fileName.equalsIgnoreCase(name)) {
                exists.set(true);
                regionFile.set(file);
            }
        });
        if (!(exists.get())) {
            player.sendMessage(Settings.LANG_NO_REGION_FOUND.replace("%name%", name));
            return;
        }
        this.deleteRegion(name, regionFile);
        this.sendMessage(Settings.LANG_REGION_REMOVED.replace("%name%", name));
    }

    private void deleteRegion(String name, AtomicReference<File> regionFile) {
        CuboidRegion cuboidRegion = RegionCache.getRegionByName(name);
        cuboidRegion.getBlocks().forEach(RegionBlock::removeMetaData);
        cuboidRegion.getOres().clear();
        cuboidRegion.getStones().clear();
        regionFile.get().delete();
        RegionCache.removeRegion(cuboidRegion);
    }
}
