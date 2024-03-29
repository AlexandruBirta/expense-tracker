package ro.unibuc.fmi.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Schema
    private Long tripId;

    @Schema
    private Long initiatedByUserId;

    @Schema
    private Integer groupSize;

    @Column(name = "expense_total_sum", columnDefinition = "Decimal(10,2) default '0.00'")
    @Schema
    private BigDecimal expenseTotalSum = BigDecimal.ZERO;

    @Schema
    private String description;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }, mappedBy = "trips")
    @JsonIgnore
    @Schema
    private Set<User> users;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime insertedDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

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