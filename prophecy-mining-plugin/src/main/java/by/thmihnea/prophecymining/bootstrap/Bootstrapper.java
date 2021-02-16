package by.thmihnea.prophecymining.bootstrap;

import by.thmihnea.prophecymining.Settings;
import by.thmihnea.prophecymining.coins.CoinsCommandHandler;
import by.thmihnea.prophecymining.data.SQLConnection;
import by.thmihnea.prophecymining.item.ItemCache;
import lombok.Getter;

@Getter
public class Bootstrapper {

    private SQLConnection sqlConnection;
    private ItemCache itemCache;
    private CoinsCommandHandler coinsCommandHandler;

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
    }

}
