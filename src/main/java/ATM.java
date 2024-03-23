import java.sql.*;
import java.util.Scanner;

public class ATM
{

    private final User_Interactions userManager;

    public ATM()
    {
        this.userManager = new User_Interactions();
    }

    DBWorker worker = new DBWorker();

    Scanner scanner = new Scanner(System.in);

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
        try (Connection connection1 = DriverManager.getConnection(worker.getURL(), worker.getUSERNAME(), worker.getPASSWORD()))
        {
            String sql = "SELECT balance FROM dataofusers WHERE login = ?";
            try (PreparedStatement preparedStatement = connection1.prepareStatement(sql))
            {
                preparedStatement.setString(1, login);
                try (ResultSet resultSet = preparedStatement.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getDouble("balance");
                    } else
                    {
                        throw new RuntimeException("User not found");
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Database error");
        }
    }

    public void showMenu(String login) throws InterruptedException, SQLException
    {
        System.out.println("Нажмите цифру в соответствии с Вашим выбором\n1. Остаток на счёте\n2. Снятие наличных\n3. Пополнение счёта\n4. Завершение работы");
        byte choice = scanner.nextByte();
        switch (choice)
        {
            case 1:
                showBalance(login);
            case 2:
                takeCash(login);
            case 3:
                increaseBalance(login);
            case 4:
                System.exit(0);
        }
    }

    public void showBalance(String login)
    {
        try(Connection connection1 = DriverManager.getConnection(worker.getURL(), worker.getUSERNAME(), worker.getPASSWORD()))
        {
            double balance = 0;
            String sql = "SELECT balance FROM dataofusers WHERE login = ?";
            PreparedStatement preparedStatement = connection1.prepareStatement(sql);
            preparedStatement.setString(1, login);

            ResultSet result = preparedStatement.executeQuery();

            if(result.next())
            {
                balance = result.getDouble("balance");
                System.out.println("Баланс пользователя " + login + ": " + balance);
            }
            boolAnotherOperation(login);
        }
        catch (SQLException | InterruptedException e)
        {
            System.out.println("Ошибка при работе с БД");
            e.printStackTrace();
        }
    }

    public void takeCash(String login) throws InterruptedException, SQLException
    {
        double balance = returnBalance(login);
        System.out.println("Показать ли Ваш баланс во время снятия наличных?\n1. Да\n2. Нет");
        byte showBalanceOrNot = scanner.nextByte();
        if(showBalanceOrNot == 1)
        {
            System.out.println("Ваш баланс: " + balance);
        }
        System.out.println("Введите сумму для снятия: ");
        int cashForTaking = scanner.nextInt();
        if(balance < cashForTaking)
        {
            System.out.println("Неверная сумма для снятия!");
            takeCash(login);
        }
        else
        {
            balance -= cashForTaking;
            System.out.println("Вж-вж-вж");
            Thread.sleep(3000);
            System.out.println("Заберите Ваши деньги!");
            updateCashValue(balance, login);
            boolAnotherOperation(login);
        }

    }

    public void boolAnotherOperation(String login) throws InterruptedException, SQLException
    {
        System.out.println("Желаете провести ещё одну операцию?\n1. Да\n2. Нет");
        byte anotherOperationOrNot = scanner.nextByte();

        if(anotherOperationOrNot == 1)
        {
            showMenu(login);
        }
        else
        {
            System.exit(0);
        }

    }

    public void increaseBalance(String login) throws InterruptedException, SQLException
    {
        double balance = returnBalance(login);
        System.out.println("Введите сумму для пополнения: ");
        int cashForIncreasing = scanner.nextInt();
        balance += cashForIncreasing;

        updateCashValue(balance, login);

        boolAnotherOperation(login);

    }

    public void updateCashValue(double balance, String login) throws SQLException
    {
        Connection connection3 = worker.getConnection();

        String sql = "UPDATE dataofusers SET balance = ? WHERE login = ?;";
        PreparedStatement preparedStatement = connection3.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(balance));
        preparedStatement.setString(2, login);

        int rowsUpdated = preparedStatement.executeUpdate();

        if (rowsUpdated > 0)
        {
            System.out.println("Баланс пользователя " + login + " успешно обновлен");
        }
    }
}
