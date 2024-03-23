public class User
{
    private final String userName, login, password;

    public User(String userName, String login, String password)
    {
        this.userName = userName;
        this.login = login;
        this.password = password;
    }

    public String getName()
    {
        return userName;
    }
    public String getLogin()
    {
        return login;
    }
    public String getPassword()
    {
        return password;
    }
}
