package by.thmihnea.prophecymining.listener;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.block.BlockExperience;
import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.cache.MiningPlayerManager;
import by.thmihnea.prophecymining.coins.CoinsDrop;
import by.thmihnea.prophecymining.item.ItemCache;
import by.thmihnea.prophecymining.region.block.RegionBlock;
import by.thmihnea.prophecymining.util.CoinsUtil;
import by.thmihnea.prophecymining.util.EnchantUtil;
import by.thmihnea.prophecymining.util.ItemUtil;
import by.thmihnea.prophecymining.util.LangUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        Block block = e.getBlock();
        Material beforeType = block.getType();
        BlockExperience blockExperience = BlockExperience.getFromMaterial(block.getType());
        Player player = e.getPlayer();
        MiningPlayer miningPlayer = MiningPlayerManager.getMiningPlayer(player);
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (blockExperience != null) {
            if (RegionBlock.hasMetadata(block)) {
                int xpToAdd = blockExperience.computeExperience();

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
                    boolean sell = this.canSell(itemInHand);
                    player.getInventory().addItem(itemStack);
                    if (sell) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(ProphecyMining.getInstance(), () -> {
                            ItemUtil.sellOne(player, Objects.requireNonNull(ItemCache.getFromItemStack(itemStack)));
                        }, 3L);
                    }
                }
                int coins = CoinsDrop.computeDrop(additionalChance);
                if (coins != 0) {
                    this.sendCoinsMessage(player, coins);
                    CoinsUtil.addCoins(player.getUniqueId().toString(), coins);
                }
            }
        }
        if (EnchantUtil.containsEnchant(itemInHand, "drill") && (miningPlayer.canUseDrill())) {
            if (RegionBlock.hasMetadata(block)) {
                miningPlayer.setUsedDrill(false);
                this.getBlocks(block, 1).forEach(b -> {
                    this.applyDrill(player, itemInHand, b);
                });
                player.playSound(player.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1.0f, 1.0f);
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(ProphecyMining.getInstance(), () -> RegionBlock.computeNextBlockType(block, beforeType), 1L);
    }

    private void applyDrill(Player player, ItemStack itemInHand, Block b) {
        if (b.getType().toString().toUpperCase().contains("ORE") || b.getType() == Material.STONE || b.getType() == Material.COBBLESTONE) {
            player.getWorld().spawnParticle(Particle.CLOUD, b.getLocation(), 0);
            BlockBreakEvent blockBreakEvent = new BlockBreakEvent(b, player);
            Bukkit.getPluginManager().callEvent(blockBreakEvent);
            if (!(EnchantUtil.containsEnchant(itemInHand, "autopickup")) &&
                !(EnchantUtil.containsEnchant(itemInHand, "autosell")))
                this.dropBlocks(player, itemInHand, b);
        }
    }

    private void dropBlocks(Player player, ItemStack itemInHand, Block b) {
        b.getDrops().forEach(drop -> {
            this.applyFortune(itemInHand, drop);
            player.getWorld().dropItemNaturally(b.getLocation(), drop);
            b.setType(Material.AIR);
        });
    }

    private void applyFortune(ItemStack itemInHand, ItemStack drop) {
        if (EnchantUtil.containsEnchant(itemInHand, "fortune")) {
            int level = itemInHand.getEnchantments().get(EnchantUtil.getEnchantmentByKey(itemInHand, "fortune"));
            drop.setAmount(getDropsAmount(level));
        }
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

    private int getDropsAmount(int level) {
        return CoinsDrop.getRandom(1, level + 1);
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

    public List<Block> getBlocks(Block start, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for (double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
            for (double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
                for (double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        blocks.remove(start);
        return blocks;
    }
}
