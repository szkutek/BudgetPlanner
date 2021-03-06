package bp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by szkutek on 12.06.17.
 */
public class Transaction implements ITransaction {
    private UUID id;
    private TransactionType type;
    private double amount;
    private LocalDate date;
    private CategoryType category;

    public Transaction() {
        this.id = UUID.randomUUID();
    }

    public Transaction(TransactionType type, double amount, LocalDate date, CategoryType category) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public enum TransactionType {
        INCOME("Income"), EXPENSE("Expense");
        private String name;

        TransactionType(String name) {
            this.name = name;
        }

        public static TransactionType fromName(String name) {
            for (TransactionType t : TransactionType.values()) {
                if (Objects.equals(t.name, name)) {
                    return t;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public static List<String>getNames()

        {
            List<String> transactionTypeList = new ArrayList<String>();

            for (TransactionType t : TransactionType.values()) {

                transactionTypeList.add(t.name);
            }

            return transactionTypeList;
        }
    }

    public UUID getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                ", category=" + category +
                '}';
    }
}
