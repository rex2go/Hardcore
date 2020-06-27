package eu.rex2go.hardcore.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Manages access to the MySQL database through the Hikari connection pool.
 */
public class DatabaseManager {

    @Getter
    private String host;

    @Getter
    private String database;

    @Getter
    private String user;

    @Getter
    private String password;

    @Getter
    private int port;

    @Getter
    private HikariConfig config;

    @Getter
    private HikariDataSource dataSource;

    public DatabaseManager(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = 3306; // default mysql port

        this.initHikari();
    }

    public DatabaseManager(String host, String database, String user, String password, int port) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;

        this.initHikari();
    }

    /**
     * Initiates HikariCP
     */
    private void initHikari() {
        // load config
        this.config = new HikariConfig();
        this.config.setJdbcUrl("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?serverTimezone=UTC");
        this.config.setUsername(this.user);
        this.config.setPassword(this.password);

        // initiate connection properties
        this.config.addDataSourceProperty("cachePrepStmts", "true"); // cache prepared statements
        this.config.addDataSourceProperty("prepStmtCacheSize", "250"); // amount of prepared statements to cache
        this.config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); // maximum limit of prepared statement length in the cache
        this.config.addDataSourceProperty("useServerPrepStmts", "true"); // use server-side prepared statements
        this.config.addDataSourceProperty("characterEncoding", "utf8"); // Use UTF8
        this.config.addDataSourceProperty("useUnicode", "true"); // Use UTF8
        this.config.setConnectionInitSql("SET time_zone = '+00:00'"); // Use UTC timezone

        this.config.setLeakDetectionThreshold(60 * 1000);

        this.dataSource = new HikariDataSource(this.config);
    }

    /**
     * Closes a ResultSet and a PreparedStatement
     *
     * @param rs The ResultSet to close.
     * @param ps The PreparedStatement to close.
     */
    public void closeResources(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
