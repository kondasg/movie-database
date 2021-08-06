package moviedatabase.director;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
    public List<DirectorDTO> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find director by ID")
    public DirectorDTO findDirectorById(@PathVariable("id") long id) {
        return directorService.findDirectorById(id);
    }

    @PostMapping
    @Operation(summary = "Create director")
    public DirectorDTO createDirector(@Valid  @RequestBody CreateDirectorCommand command) {
        return directorService.createDirector(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update director by ID")
    public DirectorDTO updateDirector(@PathVariable("id") long id, @Valid @RequestBody UpdateDirectorCommand command) {
        return directorService.updateDirector(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete director by ID")
    public void deleteDirector(@PathVariable("id") long id) {
        directorService.deleteDirector(id);
    }
}
