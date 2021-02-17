package by.thmihnea.prophecymining.coins;

import by.thmihnea.prophecymining.Settings;

import java.util.concurrent.ThreadLocalRandom;

public class CoinsDrop {

    public static int computeDrop() {
        int chance = Settings.COINS_DROP_CHANCE;
        int hash = ThreadLocalRandom.current().nextInt(100);
        if (hash >= chance) return 0;
        return getRandom(Settings.COINS_LOWER, Settings.COINS_UPPER);
    }

    public static int computeDrop(int additionalChance) {
        int chance = Settings.COINS_DROP_CHANCE + additionalChance;
        int hash = ThreadLocalRandom.current().nextInt(100);
        if (hash >= chance) return 0;
        return getRandom(Settings.COINS_LOWER, Settings.COINS_UPPER);
    }

    private static int getRandom(int lower, int upper) {
        return (lower + (int) (Math.random() * ((upper - lower) + 1)));
    }
}
