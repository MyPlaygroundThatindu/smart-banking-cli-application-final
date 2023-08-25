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

     public static String manageSystem(){
        
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
            default: break;
        }
        return screen;
    }

    public static void createNewAccount(){

        //ID Validation

        String accountIdName =String.format("SDB%05d", (idArray.size() + 1));
        idArray.add(accountIdName);
        System.out.println("New Account ID : "+accountIdName);

        // Name Validation

        String name;                 
        loop:
        do{
            System.out.print("Enter Customer Name : ");
            name = SCANNER.nextLine().strip();

            if(name.isBlank()){
                System.out.printf(ERROR_MSG,"Name Cant be Empty");
                continue;
            } 
            for (int i = 0; i < name.length(); i++) {
                if (!(Character.isLetter(name.charAt(i)) || 
                    Character.isSpaceChar(name.charAt(i))) ) {
                    System.out.printf(ERROR_MSG, "Invalid Customer name");
                    continue loop;
                }
            }
            break;

        }while(true);

        nameArray.add(name);

        //Initial Deposit

        boolean valid;
        Double initialDeposit;
        do{
            valid =true;
            System.out.print("Enter your Initial Deposit : ");
            initialDeposit = SCANNER.nextDouble();
            SCANNER.nextLine();

            if(initialDeposit<5000){
                System.out.printf(ERROR_MSG,"Insufficient Ammount");
                valid=false;
            }

        }while(!valid);

        depositArray.add(initialDeposit);
        //System.out.println(depositArray);
        System.out.printf(SUCCESS_MSG, String.format("Account Number %s: %s has been saved successfully", accountIdName, name));

    }

     public static void continueDeposit(){
        do{
            String checkId =accountNoValidation("To Deposit");
            if(checkId.length()>0){
                int checkingIndex=idArray.indexOf(checkId);
                Double currentBlance =depositArray.get(checkingIndex);
                System.out.printf("Your Account current Balance: Rs%.2f\n",currentBlance);
                
                loop:
                do{
                    System.out.print("Enter Your Deposit Ammount : ");
                    Double newDeposit =SCANNER.nextDouble();
                    SCANNER.nextLine();
                    if(newDeposit<500){
                        System.out.printf(ERROR_MSG,"Minumam Deposit is Rs.500");
                        System.out.print("Do You Want to continue (Y/N): ");
                        if(SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue loop;
                        break;
                    }
                    currentBlance+=newDeposit;
                    depositArray.set(checkingIndex,currentBlance);
                    System.out.printf(SUCCESS_MSG,"Deposit Successfull new Balance : "+currentBlance);
                    
                    
                    break;

                }while(true);
            
            }else{
            System.out.printf(ERROR_MSG,"Invalid Account Number");
            System.out.print("Do you want to continue (Y/n)? ");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))continue;
            }
            System.out.print("Do you want to continue depositing (Y/n)? ");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))continue;
            break;

        }while(true);    

    }

    public static void withrowAmmount(){
        do{
            int checkingAccount=-1;
            Double currentBlance=-1.0;
            double newWithdrow=-1.0;

            String checkId =accountNoValidation("to Withdrow");//

            if(checkId.length()>0){
                checkingAccount=accountChecking(checkId,"Withdrow");//positive if accont can withdrow
            }
            if(checkingAccount>= 0){
                currentBlance =depositArray.get(checkingAccount);
            }
            
            if(checkingAccount>=0&&currentBlance>0){
                newWithdrow=possibilityChecking(currentBlance,"Withdrow");//check About withdrowing ammount
            }
            if(newWithdrow>0){
                currentBlance-=newWithdrow;
                depositArray.set(checkingAccount,currentBlance);
                System.out.printf(SUCCESS_MSG,"Withdrow Successfull new Balance : "+currentBlance);
            }

            System.out.print("Do you want to continue Withdrowing (Y/n)? ");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))continue;
            break;

        }while(true);
        
        
    }

    public static void transferAmmount(){
        do{
            int checkingAccount1=-1;
            Double currentBlance1=-1.0;
            double newWithdrow=-1.0;
            String checkId1 =accountNoValidation("to Transfer");
            String checkId2 =accountNoValidation("to Resive");
            if(checkId1.length()>0){
                checkingAccount1=accountChecking(checkId1,"Transfer");//positive if accont can withdrow
            }
            if(checkingAccount1>= 0){
                currentBlance1 =depositArray.get(checkingAccount1);
            }
            int checkingAccount=idArray.indexOf(checkId2);
            double currentBalance2=depositArray.get(checkingAccount);
            System.out.printf("Resiving Account current Balance: Rs%.2f\n",currentBalance2);
            
            if(checkingAccount1>=0){
                newWithdrow=possibilityChecking(currentBlance1,"From RS ");//check About withdrowing ammount
            } 
            if(newWithdrow>0&&currentBlance1>=0){
                System.out.printf(ERROR_MSG,"%2 deduct for transfering");
                currentBlance1-=(newWithdrow*1.02);
                currentBalance2+=(newWithdrow*1.02);
            }
            System.out.println("From Account Balance: "+currentBlance1);   
            System.out.println("To Account Balance: "+currentBalance2); 

            System.out.printf(SUCCESS_MSG,"Transfer Successfull");
            System.out.print("Do you want to continue Tranfering (Y/n)? ");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))continue;
            break;
        }while(true);

 
    }

    public static void checkAccountBalance(){
        do{
            String checkId =accountNoValidation("to check Balance");//
            if(checkId.length()>0){
                int checkingAccount=idArray.indexOf(checkId);
                String name =nameArray.get(checkingAccount);
                double accountBalance =depositArray.get(checkingAccount);
                System.out.printf(SUCCESS_MSG,"Account Owner Name: "+name);
                System.out.printf(SUCCESS_MSG,"Current Account Balance: "+accountBalance);
                double possibleBalanceToWithdrow=accountBalance-500;
                System.out.printf(SUCCESS_MSG,"Possible Withdrow Ammount: "+possibleBalanceToWithdrow); 
            }

            System.out.print("Do you want to continue Checking (Y/n)? ");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))continue;
            break;


        }while(true);
        
        
        
    }

    public static void deleteAccount(){
        do{
            String checkId=accountNoValidation("to delete");
            int checkingAccount=idArray.indexOf(checkId);
            idArray.remove(checkingAccount);
            nameArray.remove(checkingAccount);
            depositArray.remove(checkingAccount);

            System.out.printf(SUCCESS_MSG,"Account Deleted successfully");
            System.out.print("Do you want to continue Checking (Y/n)? ");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y"))continue;
            break;


        }while(true);

    }

}