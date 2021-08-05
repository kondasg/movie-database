package moviedatabase.director;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/directors")
@AllArgsConstructor
public class DirectorController {

    private DirectorService directorService;


}
