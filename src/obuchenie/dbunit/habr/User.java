package obuchenie.dbunit.habr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.10.11
 * Time: 16:48
 * http://habrahabr.ru/blogs/java/131240/
 */
public class User {
    private Connection sqlConnection;

    public User(Connection sqlConnectopn) {
        this.sqlConnection = sqlConnectopn;
    }

    private int insertOriginalString(String originalString) throws SQLException {
        int originalStringId = 0;
        PreparedStatement psInsert = sqlConnection.
                prepareStatement(
                        "INSERT INTO original_strings (strings, date) VALUES (?, now())",
                        PreparedStatement.RETURN_GENERATED_KEYS
                );
        psInsert.setString(1, originalString);
        psInsert.execute();
        ResultSet rsInsert = psInsert.getGeneratedKeys();
        if (rsInsert.next()) {
            originalStringId = rsInsert.getInt(1);
        } else {
            throw new RuntimeException();
        }
        rsInsert.close();
        psInsert.close();
        return originalStringId;
    }

    private int insertToken(int originalStringId, String token) throws SQLException {
        int tokenId = 0;
        PreparedStatement psTokenId = sqlConnection.
                prepareStatement("SELECT id FROM tokens WHERE word = ?");
        psTokenId.setString(1, token);
        ResultSet rsToken = psTokenId.executeQuery();
        if (rsToken.next()) {
            tokenId = rsToken.getInt(1);
        } else {
            PreparedStatement psInsertToken = sqlConnection.
                    prepareStatement(
                            "INSERT INTO tokens (word) VALUES (?)",
                            PreparedStatement.RETURN_GENERATED_KEYS
                    );
            psInsertToken.setString(1, token);
            psInsertToken.execute();
            ResultSet rsInserToken = psInsertToken.getGeneratedKeys();
            if (rsInserToken.next()) {
                tokenId = rsInserToken.getInt(1);
            } else {
                throw new RuntimeException();
            }
            rsInserToken.close();
            psInsertToken.close();
        }
        rsToken.close();
        psTokenId.close();
        return tokenId;
    }

    private void linkTokenToString(int originalStringId, int tokenId) throws SQLException {
        PreparedStatement psCreateLink = sqlConnection.
                prepareStatement("INSERT INTO links (original_string_id, token_id) VALUES(?,?)");
        psCreateLink.setInt(1, originalStringId);
        psCreateLink.setInt(2, tokenId);
        psCreateLink.execute();
    }

    public void logRequestString(String requestString) throws SQLException {
        String preParsed = requestString.replaceAll("\\W+", " ");
        String[] tokens = preParsed.split(" ");
        if (tokens.length > 0) {
            int originalStringId = insertOriginalString(requestString);
            for (int i = 0, tokensLength = tokens.length; i < tokensLength; i++) {
                String token = tokens[i];
                linkTokenToString(
                        originalStringId,
                        insertToken(originalStringId, token)
                );
            }
        }
    }
}

