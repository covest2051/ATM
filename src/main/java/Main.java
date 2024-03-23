import java.sql.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws InterruptedException, SQLException
    {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);
        ATMFunctional atmF = new ATMFunctional();

        System.out.println("1.Sign in\n2.Log in");
        byte regOrAuthorize = scanner.nextByte();
        scanner.nextLine(); // Вызов сканера чтобы он считал оставшуюся строку после nextInt

        switch (regOrAuthorize)
        {
            case 1:
                atm.registerWithUserManager();
                break;
            case 2:
            {
                System.out.println("Введите логин: ");
                String checkLogin = scanner.nextLine();
                System.out.println("Введите пароль: ");
                String checkPassword = scanner.nextLine();
                if(atm.authorize(checkLogin, checkPassword))
                {
                    System.out.println("Вы успешно авторизовались!");
                    atmF.showMenu(checkLogin);
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


