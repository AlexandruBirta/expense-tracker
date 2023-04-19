package ro.unibuc.fmi.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
@Entity
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    @Schema
    private Long userId;

    @Schema(maximum = "50", required = true)
    @Column(nullable = false)
    private String firstName;

    @Schema(maximum = "50", required = true)
    @Column(nullable = false)
    private String lastName;

    @Email
    @Schema(maximum = "50", required = true)
    @Column(nullable = false, unique = true)
    private String email;

    @Schema(maximum = "50", required = true)
    @Column(nullable = false)
    private String password;

    @Schema
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime insertedDate;

    @Schema
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @ManyToMany
    @JoinTable(
            name = "user_trips",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id"))
    @JsonIgnore
    private Set<Trip> trips;

    @ManyToMany
    @JoinTable(
            name = "user_expenses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "expense_id"))
    @JsonIgnore
    private Set<Expense> expenses;

    public void addTrip(Trip trip) {
        this.trips.add(trip);
        trip.getUsers().add(this);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
        expense.getUsers().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", insertedDate=" + insertedDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

}