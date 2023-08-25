import java.util.ArrayList;
import java.util.Scanner;

class Assignment6{
    final static String CLEAR = "\033[H\033[2J";
    final static String COLOR_BLUE_BOLD = "\033[34;1m";
    final static String COLOR_RED_BOLD = "\033[31;1m";
    final static String COLOR_GREEN_BOLD = "\033[33;1m";
    final static String RESET = "\033[0m";

    final static String DASHBOARD = "Welcome to Smart Banking";
    final static String CREATE_NEW_ACCOUNT = "Open New Account";
    final static String DEPOSITS = "Deposit Money";
    final static String WITHDROWAL = "Withdraw Money";
    final static String TRANSFER = "Transfer Money";
    final static String CHECK_ACCOUNT_BALANCE = "Check Account Balance";
    final static String DELETE_ACCOUNT = "Drop Existing Account";

    final static String ERROR_MSG = String.format("%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
    final static String SUCCESS_MSG = String.format("%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);
    static ArrayList<String> idArray=new ArrayList<>(0);
    static ArrayList<String> nameArray=new ArrayList<>(0);
    static ArrayList<Double> depositArray=new ArrayList<>(0);
    
    static String screen;
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        
        screen = DASHBOARD;

        do{
            final String APP_TITLE = String.format("%s%s%s",COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.println("\t" + APP_TITLE + "\n");

            switch(screen){
                case DASHBOARD:
                screen=manageSystem();
                break;

                case CREATE_NEW_ACCOUNT:
                createNewAccount();
                System.out.print("Do you want to continue adding (Y/n)? ");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                    continue;
                } 
                screen = DASHBOARD;
                break;

                case DEPOSITS:
                continueDeposit();
                screen = DASHBOARD;
                break;

                case WITHDROWAL:
                withrowAmmount();
                screen = DASHBOARD;
                break;

                case TRANSFER:
                transferAmmount();
                screen = DASHBOARD;
                break;

                case CHECK_ACCOUNT_BALANCE:
                checkAccountBalance();
                screen = DASHBOARD;
                break;

                case DELETE_ACCOUNT:
                deleteAccount();
                screen = DASHBOARD;
                break;

                default: System.exit(0);

            }
        }while(true);
    
    }
    
}