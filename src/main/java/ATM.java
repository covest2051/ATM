import java.sql.*;

public class ATM
{
    static ATMFunctional atmF = new ATMFunctional();

    private final User_Interactions userManager;

    public ATM()
    {
        this.userManager = new User_Interactions();
    }

    public void registerWithUserManager()
    {
        userManager.registration();
    }

    public boolean authorize(String login, String password)
    {
        return userManager.authorization(login, password);
    }

    public double returnBalance(String login) throws RuntimeException
    {
        return atmF.returnBalance(login);
    }

    public void showMenu(String login) throws SQLException, InterruptedException
    {
        atmF.showMenu(login);
    }

    public void showBalance(String login)
    {
        atmF.showBalance(login);
    }

    public void takeCash(String login) throws SQLException, InterruptedException
    {
        atmF.takeCash(login);
    }

    public void boolAnotherOperation(String login) throws SQLException, InterruptedException
    {
        atmF.boolAnotherOperation(login);
    }

    public void increaseBalance(String login) throws InterruptedException, SQLException
    {
        atmF.increaseBalance(login);
    }

    public void updateCashValue(double balance, String login) throws SQLException
    {
        atmF.updateCashValue(balance, login);
    }

}
