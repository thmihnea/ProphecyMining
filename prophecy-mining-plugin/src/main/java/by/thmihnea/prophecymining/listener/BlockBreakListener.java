package by.thmihnea.prophecymining.listener;

import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.block.BlockExperience;
import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.cache.MiningPlayerManager;
import by.thmihnea.prophecymining.coins.CoinsDrop;
import by.thmihnea.prophecymining.item.ItemCache;
import by.thmihnea.prophecymining.util.CoinsUtil;
import by.thmihnea.prophecymining.util.LangUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
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

        String barMessage = Settings.LANG_ACTIONBAR_MESSAGE;
        barMessage = barMessage.replace("%amount%", String.valueOf(xpToAdd))
                .replace("%current%", LangUtil.formatNumber(miningPlayer.getCurrentXp()))
                .replace("%total%", LangUtil.formatNumber(miningPlayer.getXpToNextLevel()));
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(barMessage));

        miningPlayer.setBlocksMined(blockExperience.getFieldName(), miningPlayer.getBlocksMined(blockExperience.getFieldName()) + 1);
        ItemStack itemStack = ItemCache.computeDrop();
        if (itemStack == null) return;

        String message = Settings.LANG_RARE_DROP;
        if (message.contains("%dropName%")) message = message.replace("%dropName%", itemStack.getItemMeta().getDisplayName());
        player.getInventory().addItem(itemStack);
        player.sendMessage(message);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);

        int coins = CoinsDrop.computeDrop();
        if (coins == 0) return;
        String coinsMessage = Settings.LANG_RARE_DROP_COINS;
        if (coinsMessage.contains("%amount%")) coinsMessage = coinsMessage.replace("%amount%", String.valueOf(coins));
        player.sendMessage(coinsMessage);
        CoinsUtil.addCoins(player.getUniqueId().toString(), coins);
    }
}
