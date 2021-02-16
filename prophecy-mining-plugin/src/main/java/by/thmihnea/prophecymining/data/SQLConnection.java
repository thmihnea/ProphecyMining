package by.thmihnea.prophecymining.data;

import by.thmihnea.prophecymining.ProphecyMining;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Getter
@Setter
public class SQLConnection {

    private Connection connection;
    private String host, database, username, password;
    private int port;

    public SQLConnection(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connect();
    }

    public void connect() {
        ProphecyMining.getInstance().logInfo("Attempting to establish a connection to the database.");
        try {
            synchronized (this) {
                if (this.getConnection() != null && !(this.getConnection().isClosed())) return;
                this.setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/", this.username, this.password));
                Statement statement = this.getConnection().createStatement();
                String query = "CREATE DATABASE IF NOT EXISTS " + this.getDatabase();
                statement.executeUpdate(query);
                this.setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
            }
            ProphecyMining.getInstance().logInfo("Successfully established a connection to the database.");
            ProphecyMining.getInstance().logInfo("Host: " + this.host + ":" + this.port + " / Username: " + this.username + " | Database: " + this.database);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
