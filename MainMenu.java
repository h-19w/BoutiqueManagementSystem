import java.util.Scanner;

public class MainMenu {
    public static void mainmenu(){
        System.out.println("PLease choose which type of information you would like to manage ↴ ");
        System.out.println("1 → Boutique ");
        System.out.println("2 → Stock");
        System.out.println("3 → Product");
        System.out.println("4 → Sales");
        System.out.println("5 → Inventory");
        System.out.println("6 → Customer");
        System.out.println("7 → Exit");
        System.out.println("Enter your choice:");
    }
    public static void main(String[] args) {
        int choice;

        Boutique_1 boutique = new Boutique_1(0,"","","","","");
        Stock_2 stock = new Stock_2(0, "", 0, "", "");
        Products_3 product = new Products_3(0,"",0,"","");
        Sales_4 sale=new Sales_4(0,"",0.0,"","");
        Inventory_5 inventory = new Inventory_5(0, "", 0, "", "");
        Customer_6 customer = new Customer_6(0, "", "", "", "", "", "");

        Scanner scanner = new Scanner(System.in);
        mainmenu();
        do {
            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Invalid option");
                    mainmenu();
                    break;
                case 1: Boutique_1.menu(); break;
                case 2: Stock_2.menu(); break;
                case 3: Products_3.menu(); break;
                case 4: Sales_4.menu(); break;
                case 5: Inventory_5.menu(); break;
                case 6: Customer_6.menu(); break;
                case 7:
                    System.out.println("Exiting the program....");
                    break;
                default: System.out.println("invalid option\nChoose again: "); choice = 8; break;

            }
        }while (choice != 7);
        scanner.close();
        System.out.println("Thank You!");
    }
}