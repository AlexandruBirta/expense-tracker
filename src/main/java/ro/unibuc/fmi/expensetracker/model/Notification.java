package ro.unibuc.fmi.expensetracker.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    @Schema
    private Long notificationId;

    @Schema
    private String message;

    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id"
    )
    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    @Schema
    private User user;

    @Column(nullable = false)
    @CreationTimestamp
    @Schema
    private LocalDateTime insertedDate;

    @Column(nullable = false)
    @UpdateTimestamp
    @Schema
    private LocalDateTime updatedDate;

}