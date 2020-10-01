import java.sql.*;
import java.util.Properties;

public class DbProcessor implements AutoCloseable {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String TECH_LOGIN = "postgres";
    private static final String TECH_PASS = "Aviks131";

    private static String user = null;
    private Connection connection = null;
    public ResultSet resultSet = null;

    public void setQueryResult(String sql) throws SQLException {
        Statement statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        resultSet = statement.executeQuery(sql);
    }

    public void resetQueryResult() throws SQLException {
        resultSet = null;
    }

    public int numResults() throws SQLException {
        int numRes = 0;
        if (resultSet != null) {
            resultSet.last();
            numRes = resultSet.getRow();
        }
        return numRes;
    }

    public boolean hasResults() throws SQLException {
        return numResults() > 0;
    }

    public void connect(String url, String login, String pass) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", login);
        props.setProperty("password", pass);
        connection = DriverManager.getConnection(url, props);
        this.user = user;
    }

    DbProcessor() throws SQLException {
        this(DEFAULT_URL, TECH_LOGIN, TECH_PASS);
    }

    DbProcessor(String user, String pass) throws SQLException {
        this(DEFAULT_URL, user, pass);
    }

    DbProcessor(String url, String user, String pass) throws SQLException {
        connect(url, user, pass);
    }

    @Override
    public void finalize() {
        close();
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
        }
    }
}
