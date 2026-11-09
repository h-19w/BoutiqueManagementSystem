package CPS2231_FinalProject;

import java.util.Scanner;

public class MainMenu {
    public static void mainmenu(){
        System.out.println("PLease choose which type of " +
                "information you would like to manage ↴ ");
        System.out.println("1 → Boutique ");
        System.out.println("2 → Stock");
        System.out.println("3 → Product");
        System.out.println("4 → Sales");
        System.out.println("5 → Inventory");
        System.out.println("6 → Customer");
        System.out.println("7 → Quit and Exit");
        System.out.println("Enter your choice:");
    }

    public static void main(String[] args) {
        int choice;

        Scanner scanner = new Scanner(System.in);
        mainmenu();
        do {
            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Invalid option");
                    mainmenu();
                    break;
                case 1: Boutique.menu();
                    mainmenu();
                    break;
                case 2: Stock.menu();
                    mainmenu();
                    break;
                case 3: Product.menu();
                    mainmenu();
                    break;
                case 4: Sales.menu();
                    mainmenu();
                    break;
                case 5: Inventory.menu();
                    mainmenu();
                    break;
                case 6: Customer.menu();
                    mainmenu();
                    break;
                case 7:
                    System.out.println("\nTerminating the System....\n");
                    break;
                default: System.out.println("invalid option\nChoose again: "); choice = 8; break;

            }
        }while (choice != 7);

    scanner.close();
    System.out.println("Thank You!");
}}