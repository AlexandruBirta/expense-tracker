package ro.unibuc.fmi.expensetracker.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.fmi.expensetracker.dto.ExpenseDTO;
import ro.unibuc.fmi.expensetracker.dto.UserDTO;
import ro.unibuc.fmi.expensetracker.exception.ApiError;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.model.User;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "users", description = "User API")
@Validated
@RequestMapping(value = "/v1")
@CrossOrigin(origins = "http://localhost:4200")
public interface UserApi {

    @Operation(summary = "Creates an user", operationId = "createUser", tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "User already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PostMapping(value = "/users",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO createUser(@Parameter(description = "Supplied User for creation", required = true) @RequestBody @Valid User user);

    @Operation(summary = "Find user by ID", operationId = "getUserById", description = "Returns a single User", tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/users/{userId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    User getUserById(@Parameter(description = "ID of User to return", required = true) @PathVariable("userId") Long userId);

    @Operation(summary = "Find user by email", operationId = "getUserByEmail", description = "Returns a single User", tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/users/email/{email}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    User getUserByEmail(@Parameter(description = "Email of User to return", required = true) @PathVariable("email") String email);

    @Operation(summary = "Deletes an user", operationId = "deleteUser", tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/users/{userId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@Parameter(description = "ID of deleted user", required = true) @PathVariable("userId") Long userId);

    @Operation(summary = "Updates email of an user", operationId = "updateEmail", tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/users/{userId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    void updateEmail(@Parameter(description = "ID of deleted user", required = true) @PathVariable("userId") Long userId, @Parameter(description = "New email", required = true) @RequestParam("email") String email);

    @Operation(summary = "Find user payment amount for group expense", operationId = "getUserAmountToPay", description = "Returns the sum this user has to pay for an expense", tags = {"users, expenses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User or Expense not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/users/{userId}/expenses/{expenseId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    BigDecimal getUserAmountToPay(@Parameter(description = "ID of User to return", required = true) @PathVariable("userId") Long userId, @Parameter(description = "ID of Expense to return", required = true) @PathVariable("expenseId") Long expenseId);

    @Operation(summary = "Generate user expenses report", operationId = "getUserReport", description = "Returns the expenses report for a specific user", tags = {"users, expenses, report"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found or Report not generated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/users/expenses/report",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<ExpenseDTO> getExpensesReport(@Parameter(description = "Begin date for report generation") @RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                       @Parameter(description = "End date for report generation") @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    @Operation(summary = "Find trips by user email", operationId = "getUserTrips", description = "Returns a list of trips based on user email", tags = {"trips"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Trips not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/users/{email}/trips",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    List<Trip> getUserTrips(@Parameter(description = "Email of user to search trips by", required = true) @PathVariable("email") String email);

}