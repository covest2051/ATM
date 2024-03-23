import java.sql.*;
import java.util.Scanner;

public class User_Interactions
{
    DBWorker worker = new DBWorker();
    private static final ATM atm = new ATM();

    Scanner scanner = new Scanner(System.in);
    Connection connection = worker.getConnection();

    public void registration()
    {
        try (Statement statement = connection.createStatement())
        {
            System.out.println("Регистрация:");

            System.out.println("Введите Ваше имя: ");
            String userName = scanner.nextLine();
            System.out.println("Придумайте логин: ");
            String checkLogin = scanner.nextLine();


            try (Connection connection = DriverManager.getConnection(worker.getURL(), worker.getUSERNAME(), worker.getPASSWORD())) {
                String sql = "SELECT * FROM dataofusers WHERE login = ?";
                PreparedStatement statement2 = connection.prepareStatement(sql);
                statement2.setString(1, checkLogin);

                ResultSet result = statement2.executeQuery();

                while (result.next())
                {
                    System.out.println("Данный логин уже занят, пожалуйста, придумайте другой.");
                    registration();
                }
            } catch (SQLException e) // Проверка логина на уникальность
            {
                System.out.println("Ошибка при работе с БД");
                e.printStackTrace();
            }

            System.out.println("Придумайте пароль: ");
            String password = scanner.nextLine();

            User newUser = new User(userName, checkLogin, password);
            worker.registerUser(newUser);
            System.out.println("Регистрация прошла успешно!");
            atm.showMenu(checkLogin);
        } catch (SQLException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean authorization(String login, String password)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users_data", "root", "nml227614AS!");
            String sql = "SELECT * FROM dataofusers WHERE login = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, login);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery(); // Выполняем SQL-запрос и получаем результаты
            return rs.next(); // Если результаты есть, то возвращаем true, иначе - false

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
