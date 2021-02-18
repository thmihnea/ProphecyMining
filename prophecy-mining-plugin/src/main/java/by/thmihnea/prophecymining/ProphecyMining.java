package by.thmihnea.prophecymining;

import by.thmihnea.prophecymining.bootstrap.Bootstrapper;
import by.thmihnea.prophecymining.cache.MiningPlayer;
import by.thmihnea.prophecymining.enchantment.EnchantmentManager;
import by.thmihnea.prophecymining.enchantment.EnchantmentWrapper;
import by.thmihnea.prophecymining.enchantment.listener.AutoEnchantments;
import by.thmihnea.prophecymining.listener.*;
import by.thmihnea.prophecymining.util.SQLUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class ProphecyMining extends AbstractPlugin {

    private long TIME_ENABLED;

    private static ProphecyMining instance;

    private Bootstrapper bootstrapper;

    private SQLUtil sqlUtil;

    private final List<Listener> listenerList = Arrays.asList(
            new PlayerJoinListener(),
            new PlayerQuitListener(),
            new BlockBreakListener(),
            new AutoEnchantments(),
            new InventoryCloseListener(),
            new InventoryClickListener()
    );

    /**
     * This is the main starting method of the plugin. Replaces onEnable.
     * {@link org.bukkit.plugin.java.JavaPlugin#onEnable()}
     */
    @Override
    protected void start() {
        this.logInfo("Attempting to enable plugin modules.");
        this.checkDepend();
        this.setupTime();
        this.setupInstance();
        this.setupFiles();
        this.registerEvents(this.listenerList);
        this.bootstrapper = new Bootstrapper();
        this.sqlUtil = new SQLUtil(this.bootstrapper.getSqlConnection());
        this.sqlUtil.setupDefaults();
        this.setupObjects();
        this.setupEnchantments();
        EnchantmentManager.register();
        this.logInfo("Plugin successfully enabled. Process took: " + this.getTimeEnabled() + "ms");
    }

    /**
     * This is the main stopping method of the plugin. Replaces onDisable.
     * {@link org.bukkit.plugin.java.JavaPlugin#onDisable()}
     */
    @Override
    protected void stop() {
        this.logInfo("The plugin has been successfully disabled.");
    }

    public static ProphecyMining getInstance() {
        return instance;
    }

    public Bootstrapper getBootstrapper() {
        return this.bootstrapper;
    }

    public SQLUtil getSqlUtil() {
        return this.sqlUtil;
    }

    private void setupInstance() {
        instance = this;
    }

    private void setupTime() {
        this.TIME_ENABLED = System.currentTimeMillis();
    }

    private long getTimeEnabled() {
        return System.currentTimeMillis() - this.TIME_ENABLED;
    }

    private void setupObjects() { Bukkit.getOnlinePlayers().forEach(MiningPlayer::new); }

    private void checkDepend() {
        this.logInfo("Checking dependencies...");
        if (!(Bukkit.getPluginManager().isPluginEnabled("SmartInvs"))) {
            this.logSevere("SmartInvs dependency was not found! Disabling ProphecyMining.");
            this.setEnabled(false);
        }
        else if (!(Bukkit.getPluginManager().isPluginEnabled("Vault"))) {
            this.logSevere("Vault dependency was not found! Disabling ProphecyMining.");
            this.setEnabled(false);
        }
        else if (!(Bukkit.getPluginManager().isPluginEnabled("ShopGUIPlus"))) {
            this.logSevere("ShopGUI+ dependency was not found! Disabling ProphecyMining.");
            this.setEnabled(false);
        }
        else if (!(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))) {
            this.logSevere("PlaceholderAPI dependency was not found! Disabling ProphecyMining.");
            this.setEnabled(false);
        }
    }

    private void setupEnchantments() {
        EnchantmentWrapper EFFICIENCY = new EnchantmentWrapper("efficiency", "Efficiency", 10);
        EnchantmentWrapper UNBREAKING = new EnchantmentWrapper("unbreaking", "Unbreaking", 10);
        EnchantmentWrapper FORTUNE = new EnchantmentWrapper("fortune", "Fortune", 3);
        EnchantmentWrapper MENDING = new EnchantmentWrapper("mending", "Mending", 1);
        EnchantmentWrapper AUTO_PICKUP = new EnchantmentWrapper("autopickup", "Auto Pickup", 1);
        EnchantmentWrapper AUTO_SELL = new EnchantmentWrapper("autosell", "Auto Sell", 1);
        EnchantmentWrapper LUCK = new EnchantmentWrapper("luck", "Luck", 5);
        EnchantmentWrapper DRILL = new EnchantmentWrapper("drill", "Drill", 1);
    }
}
