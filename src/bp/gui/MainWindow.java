package bp.gui;

import bp.config.Configuration;
import bp.gui.planner.PlannerPanel;
import bp.gui.summary.SummaryPanel;
import bp.gui.transactions.TransactionsPanel;
import bp.model.CategoryExpensesType;
import bp.model.CategoryType;
import bp.model.MonthlyExpensesAndIncomeType;
import bp.model.Summary;
import bp.repository.SummaryRepository;
import bp.repository.TransactionRepository;
import bp.services.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Random;

/**
 * Created by agnieszka on 16.06.2017.
 */
public class MainWindow extends JFrame {

    public MainWindow(TransactionService transactionService,
                      SummaryService summaryService,
                      GraphService graphService,
                      BudgetPlanner budgetPlanner) {

        TransactionsPanel transactionpanel = new TransactionsPanel(transactionService);
        PlannerPanel plannerPanel = new PlannerPanel();
        JTabbedPane table = new JTabbedPane();
        SummaryPanel summaryPanel = new SummaryPanel(summaryService, graphService);


        table.add("Transactions", transactionpanel);
        table.add("Planner", plannerPanel);
        table.add("Summary", summaryPanel);


        this.setTitle("BudgetPlanner");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);

        add(table);
        this.setVisible(true);
    }


    public static void main(String[] args) {

        TransactionRepository transactionRepository = null;
        SummaryRepository summaryRepository = null;
        try {
            transactionRepository = (TransactionRepository) Serializer.deserialize(Configuration.TRANSACTION_REPOSITORY_FILE);
            summaryRepository = (SummaryRepository) Serializer.deserialize(Configuration.SUMMARY_REPOSITORY_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (transactionRepository == null) {
            transactionRepository = new TransactionRepository();
        }
        if (summaryRepository == null) {
            summaryRepository = new SummaryRepository();
        }

        TransactionService transactionService = new TransactionService(transactionRepository);
        SummaryService summaryService = new SummaryService(summaryRepository, transactionRepository);
        GraphService graphService = new GraphService(summaryRepository);
        BudgetPlanner budgetPlanner = new BudgetPlanner(summaryRepository);

        Random random = new Random();
        for (int i = 1; i < 5; i++) {
            LocalDate date = LocalDate.of(2010 + i, random.nextInt(11) + 1, 1);
            Summary exampleSummary = new Summary(date);
            for (CategoryType categoryType : CategoryType.values()) {
                exampleSummary.addExpense(new CategoryExpensesType(date, categoryType, random.nextInt(100)));
            }
            exampleSummary.setExpensesAndIncome(
                    new MonthlyExpensesAndIncomeType(LocalDate.now(), random.nextInt(1000), random.nextInt(1000)));
            summaryService.addSummary(exampleSummary);
        }
        MainWindow window = new MainWindow(transactionService, summaryService, graphService, budgetPlanner);

        Serializer.serialize(transactionRepository, Configuration.TRANSACTION_REPOSITORY_FILE);
        Serializer.serialize(summaryRepository, Configuration.SUMMARY_REPOSITORY_FILE);

    }

}
