package ro.unibuc.fmi.expensetracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ro.unibuc.fmi.expensetracker.model.Expense;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.model.User;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
public class ExpenseDTO {

    private Long expenseId;
    private BigDecimal amountPaid;
    private String description;
    private Expense.ExpenseType expenseType;
    private Set<User> users;
    private Trip trip;

    public ExpenseDTO(Expense expense) {
        this.expenseId = expense.getExpenseId();
        this.amountPaid = expense.getAmountPaid();
        this.description = expense.getDescription();
        this.expenseType = expense.getExpenseType();
        this.users = expense.getUsers();
        this.trip = expense.getTrip();
    }

}