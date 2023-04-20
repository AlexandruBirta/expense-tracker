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
import ro.unibuc.fmi.expensetracker.model.Expense;

@Tag(name = "expenses", description = "Expense API")
@Validated
@RequestMapping(value = "/v1")
@CrossOrigin(origins = "http://localhost:4200")
public interface ExpenseApi {

    @Operation(summary = "Find expense by ID", operationId = "getExpenseById", description = "Returns a single Expense", tags = {"expenses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Expense not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(value = "/expenses/{expenseId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    Expense getExpenseById(@Parameter(description = "ID of Expense to return", required = true) @PathVariable("expenseId") Long expenseId);

    @Operation(summary = "Deletes an expense", operationId = "deleteExpense", tags = {"expenses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Expense not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @DeleteMapping(value = "/expenses/{expenseId}",
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteExpense(@Parameter(description = "ID of deleted expense", required = true) @PathVariable("expenseId") Long expenseId);

}