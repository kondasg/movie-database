package moviedatabase.director;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectorService {

    private ModelMapper modelMapper;
    private DirectorRepository directorRepository;


}
