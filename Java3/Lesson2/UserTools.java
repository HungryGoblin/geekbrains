import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTools {

    private static final String TMPL_USER_BY_LOGIN = "SELECT * FROM _user WHERE _user.login = '%s'";

    private static DbProcessor dbProcessor = null;

    public static void setUser(String login) throws SQLException {
        dbProcessor = new DbProcessor();
        dbProcessor.setQueryResult(String.format(TMPL_USER_BY_LOGIN, login));
    }

    public static void setUser(String login, String password) throws Exception {
        setUser(login);
        dbProcessor.resultSet.last();
        if (!CryptoTools.checkHash(password, dbProcessor.resultSet.getString("password"))) {
            if (dbProcessor != null) dbProcessor.close();
            throw new RuntimeException("Incorrect password");
        }
    }

    private static void resetPassword(@NotNull ResultSet userResultSet, @NotNull String password) throws Exception {
        userResultSet.last();
        userResultSet.updateString("password", CryptoTools.getHash(password));
        userResultSet.updateRow();
    }

    // admin reset
    public static void resetPassword(String login, @NotNull String password) throws Exception {
        try {
            setUser(login);
            dbProcessor.resultSet.last();
            resetPassword(dbProcessor.resultSet, password);
        } finally {
            if (dbProcessor != null) dbProcessor.close();
        }
    }

    // user reset
    public static void resetPassword(
            @NotNull String login, @NotNull String currPass, @NotNull String newPass) throws Exception {
        try {
            setUser(login, currPass);
            if (!CryptoTools.checkHash(currPass, dbProcessor.resultSet.getString("password")))
                throw new RuntimeException(String.format("Can't reset password: Incorrect password"));
            resetPassword(login, newPass);
        } finally {
            if (dbProcessor != null) dbProcessor.close();
        }
    }
}
