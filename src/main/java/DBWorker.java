import java.sql.*;

public class DBWorker extends Main
{
    Statement statement;

    private final String URL = "jdbc:mysql://localhost:3306/users_data";
    private final String USERNAME = "root";
    private final String PASSWORD = "nml227614AS!";

    public String getURL() { return URL; }
    public String getUSERNAME()  { return USERNAME; }
    public String getPASSWORD() { return PASSWORD; }

    private Connection connection;

    public DBWorker()
    {
        try
        {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void registerUser(User newUser)
    {
        try
        {
            statement.execute("INSERT INTO dataofusers(name, login, password) VALUES('" + newUser.getName() + "', '" + newUser.getLogin() + "', '" + newUser.getPassword() + "')");
        }
            catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public Connection getConnection()
    {
        return connection;
    }
}
