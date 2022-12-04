package ro.unibuc.fmi.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", nullable = false)
    private Long tripId;

    private Long initiatedByUserId;

    private Integer groupSize;

    @Column(name = "expense_total_sum", columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal expenseTotalSum = BigDecimal.ZERO;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, mappedBy = "trips")
    @JsonIgnore
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Trip trip = (Trip) o;
        return tripId != null && Objects.equals(tripId, trip.tripId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "tripId = " + tripId + ", " +
                "initiatedByUserId = " + initiatedByUserId + ", " +
                "groupSize = " + groupSize + ", " +
                "expenseTotalSum = " + expenseTotalSum + ", " +
                "description = " + description + ")";
    }

}