package net.raissa.students.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.raissa.students.exceptions.StudentManagementConflictException;
import net.raissa.students.exceptions.StudentManagementNotFoundException;
import net.raissa.students.models.dtos.ApiErrorResponse;
import net.raissa.students.models.entities.AppRole;
import net.raissa.students.models.entities.AppUser;
import net.raissa.students.models.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Save new user", description = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Successfully saved the new user",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = AppUser.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The payment or file you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping(path = {"/accounts/{username}/{password}/{email}/{confirmPassword}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<?>  addNewUser(@PathVariable() String username, @PathVariable() String password,@PathVariable()  String email, @PathVariable() String confirmPassword ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.addNewUser(username,password,email,confirmPassword));
    }

    @Operation(summary = "Add role to user", description = "Add role to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Successfully add role to user"),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The payment or file you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping(path = {"/accounts/{username}/{role}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<?>  addRoleToUser(@PathVariable() String username, @PathVariable() String role) throws StudentManagementNotFoundException {
        accountService.addRoleToUser(username,role);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(summary = "Add new role", description = "Add new role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Successfully add new role",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = AppRole.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The payment or file you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping(path = {"/accounts/{role}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER') || hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?>  addNewRole(@PathVariable() String role) throws StudentManagementConflictException {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.addNewRole(role));
    }


    @Operation(summary = "Remove role from user", description = "Remove role from user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",description = "Successfully remove role from user"),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The payment or file you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @DeleteMapping(path = {"/accounts/{username}/{role}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER') || hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?>  removeRoleFromUser(@PathVariable() String username, @PathVariable() String role) throws StudentManagementNotFoundException {
        accountService.removeRoleFromUser(username,role);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    @Operation(summary = "Load user by username", description = "Load user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully load User By Username",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = AppUser.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The payment or file you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping(path = {"/accounts/{username}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<?>  loadUserByUsername(@PathVariable() String username) throws StudentManagementNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.loadUserByUsername(username));
    }
}
