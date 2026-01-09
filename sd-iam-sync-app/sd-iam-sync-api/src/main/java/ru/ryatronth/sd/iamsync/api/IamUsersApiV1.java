package ru.ryatronth.sd.iamsync.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ryatronth.sd.iamsync.api.filter.IamUserFilters;
import ru.ryatronth.sd.iamsync.api.filter.SearchMode;
import ru.ryatronth.sd.iamsync.dto.IamUserDto;

import java.util.UUID;

@Tag(
    name = "IAM Sync Users",
    description = "API для получения пользователей, синхронизированных из IAM (Keycloak)"
)
public interface IamUsersApiV1 {

    String BASE_PATH = "/api/v1/users";

    @Operation(
        summary = "Получить пользователя по id",
        description = "Возвращает пользователя по идентификатору. Если пользователь не найден — 404."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Пользователь найден",
        content = @Content(schema = @Schema(implementation = IamUserDto.class))
    )
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/{id}")
    ResponseEntity<IamUserDto> getUserById(
        @Parameter(
            name = "id",
            description = "UUID пользователя",
            in = ParameterIn.PATH,
            required = true,
            schema = @Schema(format = "uuid")
        )
        @PathVariable("id") UUID id);

    @Operation(
        summary = "Получить пользователя по username",
        description = "Возвращает пользователя по username. Если пользователь не найден — 404."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Пользователь найден",
        content = @Content(schema = @Schema(implementation = IamUserDto.class))
    )
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/by-username")
    ResponseEntity<IamUserDto> getUserByUsername(
        @Parameter(
            name = "username",
            description = "Username пользователя",
            in = ParameterIn.QUERY,
            required = true
        )
        @RequestParam("username") String username);

    @Operation(
        summary = "Получить список пользователей",
        description = "Возвращает постраничный список пользователей. Поддерживает фильтрацию query-параметрами и Pageable."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Страница пользователей",
        content = @Content(schema = @Schema(implementation = Page.class))
    )
    @GetMapping
    ResponseEntity<Page<IamUserDto>> getUsers(
        @ParameterObject IamUserFilters filters,
        @ParameterObject Pageable pageable);

}
