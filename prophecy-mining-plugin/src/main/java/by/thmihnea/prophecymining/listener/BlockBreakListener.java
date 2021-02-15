package by.thmihnea.prophecymining.listener;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.block.BlockExperience;
import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.cache.MiningPlayerManager;
import by.thmihnea.prophecymining.item.ItemCache;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (!(BlockExperience.exists(block.getType()))) return;

        BlockExperience blockExperience = BlockExperience.getFromMaterial(block.getType());
        Player player = e.getPlayer();
        int xpToAdd = blockExperience.computeExperience();
        MiningPlayer miningPlayer = MiningPlayerManager.getMiningPlayer(player);

        miningPlayer.setCurrentXp(miningPlayer.getCurrentXp() + xpToAdd);
        if (miningPlayer.getCurrentXp() >= miningPlayer.getXpToNextLevel()) {
            miningPlayer.levelUp();
        }

        miningPlayer.setBlocksMined(blockExperience.getFieldName(), miningPlayer.getBlocksMined(blockExperience.getFieldName()) + 1);
        ItemStack itemStack = ItemCache.computeDrop();
        if (itemStack == null) return;
        player.getInventory().addItem(itemStack);
    }
}
