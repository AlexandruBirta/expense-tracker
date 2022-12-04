package ro.unibuc.fmi.expensetracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.model.User;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
public class TripDTO {

    private Long tripId;
    private Long initiatedByUserId;
    private Integer groupSize;
    private BigDecimal expenseTotalSum;
    private String description;
    private Set<User> users;

    public TripDTO(Trip trip) {
        this.tripId = trip.getTripId();
        this.initiatedByUserId = trip.getInitiatedByUserId();
        this.groupSize = trip.getGroupSize();
        this.expenseTotalSum = trip.getExpenseTotalSum();
        this.description = trip.getDescription();
        this.users = trip.getUsers();
    }

}
