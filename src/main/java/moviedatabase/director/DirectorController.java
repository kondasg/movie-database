package moviedatabase.director;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/directors")
@AllArgsConstructor
public class DirectorController {

    private DirectorService directorService;

    @GetMapping
    public List<DirectorDTO> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    public DirectorDTO findDirectorById(@PathVariable("id") long id) {
        return directorService.findDirectorById(id);
    }

    @PostMapping
    public DirectorDTO createDirector(@RequestBody CreateDirectorCommand command) {
        return directorService.createDirector(command);
    }

    @PutMapping("/{id}")
    public DirectorDTO updateDirector(@PathVariable("id") long id, @RequestBody UpdeateDirectorCommand command) {
        return directorService.updateDirector(id, command);
    }

    @DeleteMapping("/{id}")
    public void deleteDirector(@PathVariable("id") long id) {
        directorService.deleteDirector(id);
    }
}
