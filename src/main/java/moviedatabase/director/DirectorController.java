package moviedatabase.director;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/directors")
@AllArgsConstructor
@Tag(name = "Operations on directors")
public class DirectorController {

    private DirectorService directorService;

    @GetMapping
    @Operation(summary = "List of all directors")
    @ApiResponse(responseCode = "200", description = "Successful")
    public List<DirectorDTO> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find director by ID")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "404", description = "Director ID not found")
    public DirectorDTO findDirectorById(@PathVariable("id") long id) {
        return directorService.findDirectorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create director")
    @ApiResponse(responseCode = "201", description = "Successful")
    public DirectorDTO createDirector(@Valid  @RequestBody CreateDirectorCommand command) {
        return directorService.createDirector(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update director by ID")
    @ApiResponse(responseCode = "200", description = "Successful")
    @ApiResponse(responseCode = "404", description = "Director ID not found")
    public DirectorDTO updateDirector(@PathVariable("id") long id, @Valid @RequestBody UpdateDirectorCommand command) {
        return directorService.updateDirector(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete director by ID")
    @ApiResponse(responseCode = "204", description = "Successful")
    @ApiResponse(responseCode = "404", description = "Director ID not found")
    public void deleteDirector(@PathVariable("id") long id) {
        directorService.deleteDirector(id);
    }
}
