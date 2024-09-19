package net.raissa.students.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import net.raissa.students.models.dtos.ApiErrorResponse;
import net.raissa.students.models.entities.AppUser;
import net.raissa.students.models.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/auth"})
@AllArgsConstructor
public class SecurityController {

    private final AuthenticationManager authenticationManager;

    private final JwtEncoder jwtEncoder;

    private final AccountService accountService;


    @Operation(summary = "Save new user", description = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Successfully saved the new user",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = AppUser.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The payment or file you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping(path = {"/accounts/{username}/{password}/{email}/{confirmPassword}"})
    /**@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")*/
    public ResponseEntity<?> addNewUser(@PathVariable() String username, @PathVariable() String password, @PathVariable()  String email, @PathVariable() String confirmPassword ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.addNewUser(username,password,email,confirmPassword));
    }

    @Operation(summary = "Login user", description = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully login user",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The payment or file you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping({"/login"})
    public Map<String, String> login(String username, String password) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder().issuedAt(instant).expiresAt(instant.plusSeconds(3600L)).subject(username).claim("scope", scope).build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(), jwtClaimsSet);
        String jwt = this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access_token", jwt);
    }
}
