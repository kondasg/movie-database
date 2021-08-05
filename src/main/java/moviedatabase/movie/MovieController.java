package moviedatabase.movie;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movies")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;


}
