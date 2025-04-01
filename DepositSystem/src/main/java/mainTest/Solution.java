package mainTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String RESOURCES = "src/main/resources/";

    public static void exe1(final String ...args) throws IOException {

        final Path path = Paths.get(RESOURCES + args[0]);
        final List<String> lines = Files.readAllLines(path);
        double total = 0d;

        for(final String line : lines ){
            final String[] columns = line.split(",");
            final double amount = Double.parseDouble(columns[1]);

            total += amount;
        }

        System.out.println("The total for all transactions is  " + total);
    }

    public static void exe2(final String ...args) throws IOException {

        final Path path = Paths.get(RESOURCES + args[0]);
        final List<String> lines = Files.readAllLines(path);
        double total = 0d;
        final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for( final String line : lines ){
            final String[] columns = line.split(",");
            final LocalDate date = LocalDate.parse(columns[0], DATE_PATTERN);
            if(date.getMonth() == Month.JANUARY){
                final double amount = Double.parseDouble(columns[1]);
                total += amount;
            }
        }

        System.out.println("The total for all transactions in January is " + total);
    }

    public static void exe3(final String ...args) throws IOException{
       final BankStatementCSVParser bankStatementCSVParser = new BankStatementCSVParser();

       final String fileName = args[0];
       final Path path = Paths.get(RESOURCES + fileName);
       final List<String> lines = Files.readAllLines(path);

       final List<BankTransaction> bankTransactions = bankStatementCSVParser.parseLinesFrom(lines);

        System.out.println("The total for all transactions is "+ calculateTotalAmount(bankTransactions) );
        System.out.println("Transactions in January " + selectInMonth(bankTransactions,Month.JANUARY ));
    }

    public static double calculateTotalAmount(final List<BankTransaction> bankTransactions){
        double total = 0d;
        for(final BankTransaction bankTransaction : bankTransactions){
            total += bankTransaction.getAmount();
        }

        return total;
    }

    public static List<BankTransaction> selectInMonth(final List<BankTransaction> bankTransactions, Month month){
        final List<BankTransaction> bankTransactionInMonth = new ArrayList<>();
        for(final BankTransaction bankTransaction : bankTransactions){
            if(bankTransaction.getDate().getMonth() == month){
                bankTransactionInMonth.add(bankTransaction);
            }
        }
        return bankTransactionInMonth;
    }


    public static void exe4(final String ...args) throws IOException{
        final String fileName = args[0];
        final Path path = Paths.get(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);
        final BankStatementParser bankStatementParser = new BankStatementCSVParser();

        final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);
        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);

        System.out.println("The total for all transaction is " + bankStatementProcessor.calculateTotalAmount());
        System.out.println("The total for transactions in January is " + bankStatementProcessor.calculateTotalInMonth(Month.JANUARY));
        System.out.println("The total for transactions in February is " + bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY));
        System.out.println("The total salary received is " + bankStatementProcessor.calculateTotalForCategory("Salary"));
    }
}
