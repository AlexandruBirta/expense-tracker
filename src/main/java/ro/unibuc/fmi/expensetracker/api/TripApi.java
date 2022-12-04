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
import ro.unibuc.fmi.expensetracker.dto.TripDTO;
import ro.unibuc.fmi.expensetracker.exception.ApiError;
import ro.unibuc.fmi.expensetracker.model.Expense;
import ro.unibuc.fmi.expensetracker.model.Trip;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Tag(name = "trips", description = "Trip API")
@Validated
@RequestMapping(value = "/v1")
public interface TripApi {

    @Operation(summary = "Creates a trip", operationId = "createTrip", tags = {"trips"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Trip already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PostMapping(value = "/trips",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    Trip createTrip(@Parameter(description = "Supplied Trip for creation", required = true) @RequestBody @Valid Trip trip);

    @Operation(summary = "Creates a trip expense", operationId = "createTrip", tags = {"trips, expenses, users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Trip or user not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PostMapping(value = "/trips/{tripId}/expenses",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    Expense createTripExpense(@Parameter(description = "ID of Trip", required = true) @PathVariable("tripId") Long tripId, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Expense to attach to trip", required = true) @RequestBody Expense expense, @Parameter(description = "User IDs", required = true) @RequestParam List<Long> userId);

    @Operation(summary = "Find trip by ID", operationId = "getTripById", description = "Returns a single Trip", tags = {"trips"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Trip not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/trips/{tripId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Trip getTripById(@Parameter(description = "ID of Trip to return", required = true) @PathVariable("tripId") Long tripId);

    @Operation(summary = "Add members to trip", operationId = "addMembersToTrip", description = "Adds multiple members of maximum group size to trip", tags = {"trips, users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Trip or users not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PutMapping(value = "/trips/{tripId}/add",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    TripDTO addMembersToTrip(@Parameter(description = "ID of Trip", required = true) @PathVariable("tripId") Long tripId, @Parameter(description = "User IDs", required = true) @RequestParam List<Long> userId);

    @Operation(summary = "Deletes an trip", operationId = "deleteTrip", tags = {"trips"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Trip not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/trips/{tripId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrip(@Parameter(description = "ID of deleted trip", required = true) @PathVariable("tripId") Long tripId);

    @Operation(summary = "Get trip total sum", operationId = "getTripTotalSum", description = "Returns the sum of every expense in the trip", tags = {"trips"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Trip not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/trips/{tripId}/expenseTotalSum",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    BigDecimal getTripTotalSum(@Parameter(description = "ID of Trip", required = true) @PathVariable("tripId") Long tripId);

}