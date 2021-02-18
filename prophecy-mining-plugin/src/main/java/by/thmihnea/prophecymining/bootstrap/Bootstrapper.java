package by.thmihnea.prophecymining.bootstrap;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.coins.CoinsCommandHandler;
import by.thmihnea.prophecymining.command.EnchanterCommand;
import by.thmihnea.prophecymining.command.RareItemShopCommand;
import by.thmihnea.prophecymining.data.SQLConnection;
import by.thmihnea.prophecymining.item.ItemCache;
import by.thmihnea.prophecymining.placeholder.Placeholders;
import by.thmihnea.prophecymining.region.RegionBootstrapper;
import by.thmihnea.prophecymining.region.command.RegionCommandHandler;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public class Bootstrapper {

    private SQLConnection sqlConnection;
    private ItemCache itemCache;
    private CoinsCommandHandler coinsCommandHandler;
    private RegionCommandHandler regionCommandHandler;
    private RareItemShopCommand rareItemShopCommand;
    private EnchanterCommand enchanterCommand;
    private Economy economy;
    private RegionBootstrapper regionBootstrapper;
    private Placeholders placeholders;

    public Bootstrapper() {
        this.init();
    }

    public void init() {
        this.sqlConnection = new SQLConnection(Settings.SQL_HOST,
                Settings.SQL_PORT,
                Settings.SQL_DATABASE,
                Settings.SQL_USERNAME,
                Settings.SQL_PASSWORD);
        this.itemCache = new ItemCache();
        this.coinsCommandHandler = new CoinsCommandHandler();
        this.rareItemShopCommand = new RareItemShopCommand();
        this.enchanterCommand = new EnchanterCommand();
        this.placeholders = new Placeholders();
        this.regionCommandHandler = new RegionCommandHandler();
        RegionBootstrapper.setupSavedRegions();
        if (!(this.setupEconomy())) {
            ProphecyMining.getInstance().logSevere("Vault API was not loaded! Please double-check everything!");
        }
    }

    private boolean setupEconomy() {
        if (ProphecyMining.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = ProphecyMining.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.economy = rsp.getProvider();
        return this.economy != null;
    }

}
