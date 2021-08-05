package moviedatabase.movie;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {

    private ModelMapper modelMapper;
    private MovieRepository movieRepository;


}
