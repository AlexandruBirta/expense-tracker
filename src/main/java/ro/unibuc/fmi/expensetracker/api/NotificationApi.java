package ro.unibuc.fmi.expensetracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.fmi.expensetracker.exception.ApiError;
import ro.unibuc.fmi.expensetracker.model.Notification;

import java.util.List;


@Tag(name = "notifications", description = "Notification API")
@Validated
@RequestMapping(value = "/v1")
@CrossOrigin(origins = "http://localhost:4200")
public interface NotificationApi {


    @Operation(summary = "Find notifications by user ID", operationId = "findAllByUserId", description = "Returns all notifications by user ID", tags = {"notifications"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/notifications/{userId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<Notification> findAllByUserId(@Parameter(description = "ID of User to find notifications by", required = true) @PathVariable("userId") Long userId);

}