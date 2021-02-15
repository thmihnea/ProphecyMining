package by.thmihnea.prophecymining.util;

import by.thmihnea.prophecymining.ProphecyMining;
import by.thmihnea.prophecymining.data.SQLConnection;
import by.thmihnea.prophecymining.data.TableType;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class SQLUtil {

    private final SQLConnection sqlConnection;

    public SQLUtil(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement preparedStatement = this.getSqlConnection()
                    .getConnection().prepareStatement("SELECT * FROM `player_data` WHERE UUID = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void initPlayer(Player player) {
        try {
            PreparedStatement preparedStatement = this.getSqlConnection()
                    .getConnection().prepareStatement("SELECT * FROM `player_data` WHERE UUID = ?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!(this.exists(player.getUniqueId()))) {
                ProphecyMining.getInstance().logInfo("Found player " + player.getName() + " who doesn't exist in our records. Attempting to initialize him.");
                this.setup(player);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void setupDefaults() {
        try {
            PreparedStatement preparedStatement1 = this.getSqlConnection()
                    .getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `player_data` (UUID varchar(256), COAL_MINED bigint(255), " +
                            "IRON_MINED bigint(255), GOLD_MINED bigint(255), " +
                            "EMERALD_MINED bigint(255), LAPIS_MINED bigint(255), " +
                            "REDSTONE_MINED bigint(255), DIAMOND_MINED bigint(255))");
            PreparedStatement preparedStatement2 = this.getSqlConnection()
                    .getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `player_levels` (UUID varchar(256), LEVEL int(255), CURRENT_XP bigint(255), XP_TO_NEXT_LEVEL bigint(255))");
            List<PreparedStatement> statements = Arrays.asList(preparedStatement1, preparedStatement2); // in case I ever add more
            statements.forEach(statement -> {
                try {
                    statement.executeUpdate();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            });
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    protected void setup(Player player) {
        try {
            PreparedStatement preparedStatement1 = this.getInitializationStatement(player);
            PreparedStatement preparedStatement2 = this.getXpInitializationStatement(player);
            preparedStatement1.execute();
            preparedStatement2.execute();
            ProphecyMining.getInstance().logInfo("Successfully added player " + player.getName() + " to our database records.");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    protected PreparedStatement getInitializationStatement(Player player) throws SQLException {
        PreparedStatement preparedStatement = this.getSqlConnection()
                .getConnection().prepareStatement("INSERT INTO `player_data` (UUID, COAL_MINED, IRON_MINED, " +
                        "GOLD_MINED, EMERALD_MINED, " +
                        "LAPIS_MINED, REDSTONE_MINED, " +
                        "DIAMOND_MINED) VALUE (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, player.getUniqueId().toString());
        for (int i = 1; i <= 7; i++) {
            preparedStatement.setInt(i + 1, 0);
        }
        return preparedStatement;
    }

    protected PreparedStatement getXpInitializationStatement(Player player) throws SQLException {
        PreparedStatement preparedStatement = this.getSqlConnection()
                .getConnection().prepareStatement("INSERT INTO `player_levels` (UUID, LEVEL, " +
                        "CURRENT_XP, XP_TO_NEXT_LEVEL) VALUE (?, ?, ?, ?)");
        preparedStatement.setString(1, player.getUniqueId().toString());
        preparedStatement.setInt(2, 1);
        preparedStatement.setInt(3, 0);
        boolean incremental = ProphecyMining.getCfg().getBoolean("leveling.milestones.increment.enabled");
        if (incremental)
            preparedStatement.setInt(4, ProphecyMining.getCfg().getInt("leveling.milestones.increment.increment_value"));
        else
            preparedStatement.setInt(4, ProphecyMining.getCfg().getInt("leveling.milestones.level_1"));
        return preparedStatement;
    }

    public int getValue(TableType tableType, String field, String uniqueId) {
        String statement = "SELECT * FROM `" + tableType.getName() + "` WHERE UUID = ?";
        try {
            PreparedStatement preparedStatement = this.getSqlConnection()
                    .getConnection().prepareStatement(statement);
            preparedStatement.setString(1, uniqueId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(field);
            else return -1;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return -1;
        }
    }

    public void setValue(TableType tableType, String field, String uniqueId, int value) {
        String statement = "UPDATE " + tableType.getName() + " SET " + field + " = ? WHERE UUID = ?";
        try {
            PreparedStatement preparedStatement = this.getSqlConnection()
                    .getConnection().prepareStatement(statement);
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, uniqueId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
