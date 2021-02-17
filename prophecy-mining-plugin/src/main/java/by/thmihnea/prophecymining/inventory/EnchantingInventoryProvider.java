package by.thmihnea.prophecymining.inventory;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.cache.InventoryViewerCache;
import by.thmihnea.prophecymining.enchantment.EnchantmentCache;
import by.thmihnea.prophecymining.enchantment.EnchantmentManager;
import by.thmihnea.prophecymining.util.CoinsUtil;
import by.thmihnea.prophecymining.util.EnchantUtil;
import by.thmihnea.prophecymining.util.ItemUtil;
import by.thmihnea.prophecymining.util.LangUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class EnchantingInventoryProvider implements InventoryProvider {

    private ItemStack itemStack;

    public EnchantingInventoryProvider(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public SmartInventory enchantShop = SmartInventory.builder()
            .id("enchant")
            .provider(this)
            .size(5, 9)
            .title(Settings.LANG_ENCHANTMENT_SHOP)
            .build();

    public void open(Player player) {
       InventoryViewerCache.addEntry(this.enchantShop.open(player));
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack borderStack = ItemUtil.getItemStack(Material.BLACK_STAINED_GLASS_PANE, " ", Collections.emptyList());
        this.fill(contents, borderStack);
        ItemStack showStack = new ItemStack(this.itemStack);
        contents.set(1, 4, ClickableItem.empty(showStack));
        EnchantmentCache.getCache().forEach(enchantment -> {
            Enchantment finalEnchantment = Enchantment.getByKey(enchantment.getKey());

            String key = enchantment.getKey().getKey();
            String name = EnchantUtil.getEnchantName(finalEnchantment.getName());

            List<String> enchantLore = LangUtil.translateLoreColorCodes(ProphecyMining.getCfg().getStringList("enchantments." + key + ".lore"));
            int price = ProphecyMining.getCfg().getInt("enchantments." + key + ".price");
            String buy = Settings.LANG_ENCHANTMENTS_BUY.replace("%price%", LangUtil.formatNumber(price));
            enchantLore.add(buy);

            String displayName = Settings.LANG_ENCHANTMENTS_NAME.replace("%enchantName%", name);

            if (CoinsUtil.getCoins(player.getUniqueId().toString()) < price) {
                enchantLore.add(Settings.LANG_NOT_ENOUGH_MONEY);
            }

            ItemStack book = ItemUtil.getItemStack(Material.ENCHANTED_BOOK, displayName, enchantLore);

            contents.add(ClickableItem.of(book, e -> {
                EnchantUtil.applyEnchantment(player, this.itemStack, price, finalEnchantment);
                ItemUtil.patchLore(this.itemStack);
                this.open(player);
            }));
        });
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void fill(InventoryContents contents, ItemStack borderStack) {
        contents.fillBorders(ClickableItem.empty(borderStack));
        contents.fillRow(1, ClickableItem.empty(borderStack));
        contents.fillColumn(1, ClickableItem.empty(borderStack));
        contents.fillColumn(7, ClickableItem.empty(borderStack));
        contents.set(3, 2, ClickableItem.empty(borderStack));
        contents.set(3, 6, ClickableItem.empty(borderStack));
    }
}
