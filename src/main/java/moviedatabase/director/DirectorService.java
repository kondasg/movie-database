package moviedatabase.director;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class DirectorService {

    private ModelMapper modelMapper;
    private DirectorRepository directorRepository;

    public List<DirectorDTO> getAllDirectors() {
        Type targetListType = new TypeToken<List<DirectorDTO>>() {}.getType();
        return modelMapper.map(directorRepository.findAll(), targetListType);
    }

    public DirectorDTO findDirectorById(long id) {
        return modelMapper.map(getDirectorById(id), DirectorDTO.class);
    }

    public DirectorDTO createDirector(CreateDirectorCommand command) {
        Director director = new Director(command.getName(), command.getBirthday());
        directorRepository.save(director);
        return modelMapper.map(director, DirectorDTO.class);
    }

    @Transactional
    public DirectorDTO updateDirector(long id, UpdeateDirectorCommand command) {
        Director director = getDirectorById(id);
        director.setName(command.getName());
        director.setBirthday(command.getBirthday());
        return modelMapper.map(director, DirectorDTO.class);
    }

    public void deleteDirector(long id) {
        getDirectorById(id);
        directorRepository.deleteById(id);
    }

    private Director getDirectorById(long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new DirectorNotFoundException("Director id not found: " + id));
    }
}
