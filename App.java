import java.util.Scanner;

public class App{
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        final String CLEAR = "\033[H\033[2J";
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[33;1m";
        final String RESET = "\033[0m";

        final String DASHBOARD = "Welcome to Smart Banking";
        final String CREATE_NEW_ACCOUNT = "Open New Account";
        final String DEPOSITS = "Deposit Money";
        final String WITHDROWAL = "Withdraw Money";
        final String TRANSFER = "Transfer Money";
        final String CHECK_ACCOUNT_BALANCE = "Check Account Balance";
        final String DELETE_ACCOUNT = "Drop Existing Account";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

        String[][] custDetails = new String[0][];

        String screen = DASHBOARD;

        do {
            final String APP_TITLE = String.format("%s%s%s",COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.println("\t" + APP_TITLE + "\n");

            switch(screen){
                case DASHBOARD:
                    System.out.println("\t[1]. Create New Account");
                    System.out.println("\t[2]. Deposit Money");
                    System.out.println("\t[3]. Withdraw Money");
                    System.out.println("\t[4]. Transfer Money");
                    System.out.println("\t[5]. Check Account Balance");
                    System.out.println("\t[6]. Drop Existing Account");
                    System.out.println("\t[7]. Exit\n");
                    System.out.print("\tEnter an option to continue: ");

                    int option = SCANNER.nextInt();
                    SCANNER.nextLine();

                    switch (option){
                        case 1: screen = CREATE_NEW_ACCOUNT; break;
                        case 2: screen = DEPOSITS; break;
                        case 3: screen = WITHDROWAL; break;
                        case 4: screen = TRANSFER; break;
                        case 5: screen = CHECK_ACCOUNT_BALANCE ; break;
                        case 6: screen = DELETE_ACCOUNT; break;
                        case 7: System.out.println(CLEAR); System.exit(0);
                        default: continue;
                    }
                    break;

                case CREATE_NEW_ACCOUNT:
                    String id;
                    String name;
                    double deposite;
                    boolean valid = true;
                    
                    // ID Validation                    
                    idValidation:
                    do {
                        valid = true;
                        System.out.print("\tEnter Customer ID: ");
                        id = SCANNER.nextLine().strip();

                        /* Empty */
                        if (id.isEmpty()) {
                            valid = false;
                            System.out.printf(ERROR_MSG, "ID Can't be empty");
                            continue idValidation;
                        }

                        /* Format */
                        if (!id.startsWith("SDB-") || id.length() != 9) {
                            valid = false;
                            System.out.printf(ERROR_MSG, "Invalid ID format");
                            continue idValidation;

                        } else {
                            // SDB-xxxxx => x
                            String numberPart = id.substring(5);
                            for (int i = 0; i < numberPart.length(); i++) {
                                if (!Character.isDigit(numberPart.charAt(i))) {
                                    valid = false;
                                    System.out.printf(ERROR_MSG, "Invalid ID format");
                                    continue idValidation;
                                }
                            }
                        }

                        /* Already Exists */
                        for (int row = 0; row < custDetails.length; row++) {
                            if (custDetails[row][0].equals(id)) {
                                valid = false;
                                System.out.printf(ERROR_MSG, "Student ID already exists");
                                continue idValidation;
                            }
                        }

                    } while (!valid);

                    // Name Validation
                
                    nameValidation:
                    do{
                        System.out.print("\tEnter Customer Name : ");
                        name = SCANNER.nextLine().strip();

                        if(name.isBlank()){
                            System.out.printf(ERROR_MSG,"Name Cant be Empty");
                            continue nameValidation;
                        } 

                        for (int i = 0; i < name.length(); i++) {
                            if (!(Character.isLetter(name.charAt(i)) || 
                                Character.isSpaceChar(name.charAt(i))) ) {
                                System.out.printf(ERROR_MSG, "Invalid Customer name");
                                continue nameValidation;
                            }
                        }
                        break;

                    }while(true);

                    //Initial Deposit
                    do{
                        valid =true;
                        System.out.print("\tEnter your Initial Deposit : ");
                        deposite = SCANNER.nextInt();
                        SCANNER.nextLine();

                        if(deposite<5000){
                            System.out.printf(ERROR_MSG,"Insufficient Ammount");
                            valid=false;
                        }

                    }while(!valid);

                    String[][] tempCusDetails = new String[custDetails.length+1][3];

                    for (int i = 0; i < custDetails.length; i++) {
                        for(int j=0; j< custDetails[i].length; j++){
                            tempCusDetails[i][j] = custDetails[i][j];
                        }                                             
                    }   
                    
                    tempCusDetails[tempCusDetails.length-1][0] = id;
                    tempCusDetails[tempCusDetails.length-1][1] = name;
                    tempCusDetails[tempCusDetails.length-1][2] = Double.toString(deposite);

                    custDetails = tempCusDetails;

                    System.out.println();
                    System.out.printf(SUCCESS_MSG, String.format("Account Number %s: %s has been saved successfully", id, name));
                    System.out.print("Do you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;  
                    
                // case: DEPOSITS
            
                
                default: System.exit(0);

            }

        } while (true);
    }
}