package by.thmihnea.prophecymining.data;

import lombok.Getter;

@Getter
public enum TableType {

    PLAYER_DATA("player_data"),
    PLAYER_LEVELS("player_levels"),
    PLAYER_COINS("player_coins");

    private final String name;

    TableType(String name) {
        this.name = name;
    }
}
