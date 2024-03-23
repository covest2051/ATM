import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args) throws InterruptedException, SQLException
    {
        DBWorker worker = new DBWorker();
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        System.out.println("1.Sign in\n2.Log in");
        byte regOrAuthorize = scanner.nextByte();
        scanner.nextLine(); // Вызов сканера чтобы он считал оставшуюся строку после nextInt

        switch (regOrAuthorize)
        {
            case 1:
                atm.registration();
                break;
            case 2:
            {
                System.out.println("Введите логин: ");
                String checkLogin = scanner.nextLine();
                System.out.println("Введите пароль: ");
                String checkPassword = scanner.nextLine();
                if(atm.authorization(checkLogin, checkPassword))
                {
                    System.out.println("Вы успешно авторизовались!");
                    atm.showMenu(checkLogin);
                }
                else
                {
                    System.out.println("Авторизация прошла неудачно!");
                    System. exit(0);
                }
                break;
            }
        }
    }
}


