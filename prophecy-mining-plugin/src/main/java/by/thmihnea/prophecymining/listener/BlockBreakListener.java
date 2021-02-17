package by.thmihnea.prophecymining.listener;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.block.BlockExperience;
import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.cache.MiningPlayerManager;
import by.thmihnea.prophecymining.coins.CoinsDrop;
import by.thmihnea.prophecymining.item.ItemCache;
import by.thmihnea.prophecymining.util.CoinsUtil;
import by.thmihnea.prophecymining.util.EnchantUtil;
import by.thmihnea.prophecymining.util.ItemUtil;
import by.thmihnea.prophecymining.util.LangUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

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

        this.sendBarMessage(player, xpToAdd, miningPlayer);
        miningPlayer.setBlocksMined(blockExperience.getFieldName(), miningPlayer.getBlocksMined(blockExperience.getFieldName()) + 1);

        int additionalChance = this.getAdditionalChance(player);
        ItemStack itemStack = ItemCache.computeDrop(additionalChance);

        if (itemStack != null) {
            this.sendDropMessage(player, itemStack);
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            boolean sell = this.canSell(itemInHand);
            player.getInventory().addItem(itemStack);
            if (sell) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(ProphecyMining.getInstance(), () -> {
                    ItemUtil.sellOne(player, Objects.requireNonNull(ItemCache.getFromItemStack(itemStack)));
                }, 3L);
            }
        }
        int coins = CoinsDrop.computeDrop(additionalChance);
        if (coins == 0) return;
        this.sendCoinsMessage(player, coins);
        CoinsUtil.addCoins(player.getUniqueId().toString(), coins);
    }

    private boolean canSell(ItemStack itemInHand) {
        boolean sell = false;
        if (itemInHand.getType().toString().toUpperCase().contains("PICKAXE")) {
            if (EnchantUtil.containsEnchant(itemInHand, "autosell")) sell = true;
        }
        return sell;
    }

    private void sendCoinsMessage(Player player, int coins) {
        String coinsMessage = Settings.LANG_RARE_DROP_COINS;
        if (coinsMessage.contains("%amount%")) coinsMessage = coinsMessage.replace("%amount%", String.valueOf(coins));
        player.sendMessage(coinsMessage);
    }

    private void sendDropMessage(Player player, ItemStack drop) {
        String message = Settings.LANG_RARE_DROP;
        if (message.contains("%dropName%"))
            message = message.replace("%dropName%", drop.getItemMeta().getDisplayName());
        player.sendMessage(message);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
    }

    private int getAdditionalChance(Player player) {
        int additionalChance = 0;
        if (EnchantUtil.containsEnchant(player.getInventory().getItemInMainHand(), "luck")) {
            ItemStack inHand = player.getInventory().getItemInMainHand();
            additionalChance = Settings.LUCK_BONUS_CHANCE_PER_LEVEL * (inHand.getEnchantments().get(EnchantUtil.getEnchantmentByKey(inHand, "luck")));
        }
        return additionalChance;
    }

    private void sendBarMessage(Player player, int xpToAdd, MiningPlayer miningPlayer) {
        String barMessage = Settings.LANG_ACTIONBAR_MESSAGE;
        barMessage = barMessage.replace("%amount%", String.valueOf(xpToAdd))
                .replace("%current%", LangUtil.formatNumber(miningPlayer.getCurrentXp()))
                .replace("%total%", LangUtil.formatNumber(miningPlayer.getXpToNextLevel()));
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(barMessage));
    }
}
