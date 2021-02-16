package by.thmihnea.prophecymining.inventory;

import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.item.Drop;
import by.thmihnea.prophecymining.util.CoinsUtil;
import by.thmihnea.prophecymining.util.ItemUtil;
import by.thmihnea.prophecymining.util.LangUtil;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class DropShopInventoryProvider implements InventoryProvider {

    private final Drop drop;

    public DropShopInventoryProvider(Drop drop) {
        this.drop = drop;
    }

    public SmartInventory shop = SmartInventory.builder()
            .id("dropShop")
            .provider(this)
            .size(5, 9)
            .title(Settings.LANG_RARE_ITEM_SHOP_TITLE)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack borderStack = ItemUtil.getItemStack(Material.BLACK_STAINED_GLASS_PANE, " ", Collections.emptyList());
        contents.fillBorders(ClickableItem.empty(borderStack));
        ItemStack stackType = new ItemStack(this.drop.getItemStack().getType());
        ItemStack barrier = ItemUtil.getItemStack(Material.BARRIER, Settings.LANG_CLOSE_MENU, Collections.emptyList());
        ItemStack buyOne = this.getBuyOneItemStack(player, stackType);
        ItemStack buyAll = this.getBuyAllItemStack(player, stackType);
        ItemStack sellOne = this.getSellOneItemStack(player, stackType);
        ItemStack sellAll = this.getSellAllItemStack(player, stackType);
        contents.set(3, 7, ClickableItem.of(barrier, e -> RareItemShopInventoryProvider.rareItemShop.open(player)));
        contents.set(2, 2, ClickableItem.of(buyOne, e -> {
            ItemUtil.buyOne(player, this.drop);
            this.init(player, contents);
        }));
        contents.set(2, 3, ClickableItem.of(buyAll, e -> {
            ItemUtil.buyAll(player, this.drop);
            this.init(player, contents);
        }));
        contents.set(2, 5, ClickableItem.of(sellOne, e -> {
            ItemUtil.sellOne(player, this.drop);
            this.init(player, contents);
        }));
        contents.set(2, 6, ClickableItem.of(sellAll, e -> {
            ItemUtil.sellAll(player, this.drop);
            this.init(player, contents);
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }

    public SmartInventory getInventory() {
        return shop;
    }

    private List<String> getBuyLoreForOne(Player player) {
        List<String> lore = new ArrayList<>(Settings.LANG_SHOP_ITEM_BUY_LORE);
        lore = LangUtil.applyAmountPlaceholder(1, lore);
        lore = LangUtil.applyItemNamePlaceholder(this.drop.getItemStack().getItemMeta().getDisplayName(), lore);
        lore = LangUtil.applyPricePlaceholder(this.drop.getBuyPrice(), lore);
        return lore;
    }

    private List<String> getBuyLoreForAll(Player player) {
        List<String> lore = new ArrayList<>(Settings.LANG_SHOP_ITEM_BUY_LORE);
        lore = LangUtil.applyAmountPlaceholder(this.getBuyAmount(player), lore);
        lore = LangUtil.applyItemNamePlaceholder(this.drop.getItemStack().getItemMeta().getDisplayName(), lore);
        String price;
        if (this.drop.getBuyPrice() * this.getBuyAmount(player) == 0) price = ChatColor.translateAlternateColorCodes('&', "&cNOT ENOUGH MONEY");
        else price = String.valueOf(this.drop.getBuyPrice() * this.getBuyAmount(player));
        lore = LangUtil.applyPricePlaceholder(price, lore);
        return lore;
    }

    private List<String> getSellLoreForOne(Player player) {
        List<String> lore = new ArrayList<>(Settings.LANG_SHOP_ITEM_SELL_LORE);
        lore = LangUtil.applyAmountPlaceholder(1, lore);
        lore = LangUtil.applyItemNamePlaceholder(this.drop.getItemStack().getItemMeta().getDisplayName(), lore);
        lore = LangUtil.applyPricePlaceholder(this.drop.getSellPrice(), lore);
        return lore;
    }

    private List<String> getSellLoreForAll(Player player) {
        List<String> lore = new ArrayList<>(Settings.LANG_SHOP_ITEM_SELL_LORE);
        lore = LangUtil.applyAmountPlaceholder(this.getAmount(player, this.drop.getItemStack()), lore);
        lore = LangUtil.applyItemNamePlaceholder(this.drop.getItemStack().getItemMeta().getDisplayName(), lore);
        lore = LangUtil.applyPricePlaceholder(this.getSellPrice(player), lore);
        return lore;
    }

    private ItemStack getBuyAllItemStack(Player player, ItemStack stackType) {
        ItemStack itemStack = new ItemStack(stackType);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = Settings.LANG_ITEM_BUY_NAME;
        displayName = displayName.replace("%amount%", String.valueOf(this.getBuyAmount(player)))
                .replace("%itemName%", this.drop.getItemStack().getItemMeta().getDisplayName());
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(this.getBuyLoreForAll(player));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getBuyOneItemStack(Player player, ItemStack stackType) {
        ItemStack itemStack = new ItemStack(stackType);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = Settings.LANG_ITEM_BUY_NAME;
        displayName = displayName.replace("%amount%", String.valueOf(1))
                .replace("%itemName%", this.drop.getItemStack().getItemMeta().getDisplayName());
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(this.getBuyLoreForOne(player));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getSellOneItemStack(Player player, ItemStack stackType) {
        ItemStack itemStack = new ItemStack(stackType);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = Settings.LANG_ITEM_SELL_NAME;
        displayName = displayName.replace("%amount%", String.valueOf(1))
                .replace("%itemName%", this.drop.getItemStack().getItemMeta().getDisplayName());
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(this.getSellLoreForOne(player));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getSellAllItemStack(Player player, ItemStack stackType) {
        ItemStack itemStack = new ItemStack(stackType);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = Settings.LANG_ITEM_SELL_NAME;
        displayName = displayName.replace("%amount%", String.valueOf(this.getAmount(player, this.drop.getItemStack())))
                .replace("%itemName%", this.drop.getItemStack().getItemMeta().getDisplayName());
        itemMeta.setDisplayName(displayName);
        List<String> lore = this.getSellLoreForAll(player);
        if (this.getAmount(player, this.drop.getItemStack()) == 0) lore.add(Settings.LANG_NO_MATCH);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private int getAmount(Player player, ItemStack itemStack) {
        int amount = 0;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack == null || stack.getType() == Material.AIR) continue;
            if (!(stack.hasItemMeta())) continue;
            if (stack.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName()))
                amount += stack.getAmount();
        }
        return amount;
    }

    private int getBuyAmount(Player player) {
        return CoinsUtil.getCoins(player.getUniqueId().toString()) / this.drop.getBuyPrice();
    }

    private int getSellPrice(Player player) {
        return this.drop.getSellPrice() * this.getAmount(player, this.drop.getItemStack());
    }
}
