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
import ro.unibuc.fmi.expensetracker.dto.UserDTO;
import ro.unibuc.fmi.expensetracker.exception.ApiError;
import ro.unibuc.fmi.expensetracker.model.User;

import javax.validation.Valid;
import java.math.BigDecimal;

@Tag(name = "users", description = "User API")
@Validated
@RequestMapping(value = "/v1")
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

}