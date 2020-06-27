package eu.rex2go.hardcore.manager;

import com.zaxxer.hikari.HikariDataSource;
import eu.rex2go.hardcore.Hardcore;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChatManager {

    @Getter
    private ArrayList<String> badWordList = new ArrayList<>();

    @Getter
    private boolean chatEnabled = true;

    public ChatManager() {
        loadBadWords();
    }

    private void loadBadWords() {
        try {
            DatabaseManager databaseManager = Hardcore.getDatabaseManager();

            if (databaseManager != null) {
                HikariDataSource dataSource = databaseManager.getDataSource();

                if (dataSource != null) {
                    Connection connection = dataSource.getConnection();

                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM `badwords`");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String badWord = rs.getString("badword");

                        badWordList.add(badWord);
                    }

                    databaseManager.closeResources(rs, ps);
                    connection.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String filter(String message) {
        for (String badWord : badWordList) {
            if (message.toLowerCase().contains(badWord)) {
                for (String msg : message.split(" ")) {
                    if (msg.toLowerCase().contains(badWord)) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i = 0; i < msg.length(); i++) {
                            stringBuilder.append("*");
                        }

                        message = message.replaceAll(msg, stringBuilder.toString());
                    }
                }
            }
        }

        return message;
    }
}

