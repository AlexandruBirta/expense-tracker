package ro.unibuc.fmi.expensetracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ro.unibuc.fmi.expensetracker.model.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;

    public UserDTO(User savedUser) {
        this.userId = savedUser.getUserId();
        this.firstName = savedUser.getFirstName();
        this.lastName = savedUser.getLastName();
        this.email = savedUser.getEmail();
    }
}
