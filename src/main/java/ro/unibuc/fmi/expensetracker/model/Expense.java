package ro.unibuc.fmi.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ro.unibuc.fmi.expensetracker.exception.ApiException;
import ro.unibuc.fmi.expensetracker.exception.ExceptionStatus;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id", nullable = false)
    private Long expenseId;

    @DecimalMin(value = "0.01")
    private BigDecimal amountPaid;

    private String description;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, mappedBy = "expenses")
    @JsonIgnore
    private Set<User> users;

    @JoinColumn(
            name = "trip_id",
            referencedColumnName = "trip_id"
    )
    @ManyToOne(
            targetEntity = Trip.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private Trip trip;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "expenseId = " + expenseId + ", " +
                "amountPaid = " + amountPaid + ", " +
                "description = " + description + ", " +
                "expenseType = " + expenseType + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Expense expense = (Expense) o;
        return expenseId != null && Objects.equals(expenseId, expense.expenseId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Getter
    public enum ExpenseType {

        PERSONAL("personal"),
        GROUP("group");

        private final String value;

        ExpenseType(String value) {
            this.value = value;
        }

        @JsonCreator
        public static ExpenseType fromValue(String text) {
            for (ExpenseType b : ExpenseType.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            throw new ApiException(ExceptionStatus.INVALID_EXPENSE_TYPE, text);
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

    }

}