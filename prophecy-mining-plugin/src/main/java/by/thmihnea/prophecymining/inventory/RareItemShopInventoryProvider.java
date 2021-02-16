package by.thmihnea.prophecymining.inventory;

import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.item.Drop;
import by.thmihnea.prophecymining.item.ItemCache;
import by.thmihnea.prophecymining.util.ItemUtil;
import by.thmihnea.prophecymining.util.LangUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class RareItemShopInventoryProvider implements InventoryProvider {

    public static final SmartInventory rareItemShop = SmartInventory.builder()
            .id("rareItemShop")
            .provider(new RareItemShopInventoryProvider())
            .size(6, 9)
            .title(Settings.LANG_RARE_ITEM_SHOP_TITLE)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack borderStack = ItemUtil.getItemStack(Material.BLACK_STAINED_GLASS_PANE, " ", Collections.emptyList());
        contents.fillBorders(ClickableItem.empty(borderStack));
        ItemCache.getDrops().forEach(drop -> {
            ItemStack itemStack = new ItemStack(drop.getItemStack());
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> itemLore = itemMeta.getLore();

            String buy = Settings.LANG_BUY_TEXT.replace("%price%", LangUtil.formatNumber(drop.getBuyPrice()));
            String sell = Settings.LANG_SELL_TEXT.replace("%price%", LangUtil.formatNumber(drop.getSellPrice()));

            itemLore.add(buy);
            itemLore.add(sell);
            itemMeta.setLore(itemLore);
            itemStack.setItemMeta(itemMeta);

            contents.add(ClickableItem.of(itemStack, e -> {
                DropShopCache.getDropShop(drop).getInventory().open(player);
            }));
        });
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        return;
    }
}
