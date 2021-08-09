package moviedatabase.movie;

import lombok.AllArgsConstructor;
import moviedatabase.director.Director;
import moviedatabase.director.DirectorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private ModelMapper modelMapper;
    private MovieRepository movieRepository;
    private DirectorService directorService;

    public List<MovieDTO> getAllMovies() {
        Type targetListType = new TypeToken<List<MovieDTO>>() {}.getType();
        return modelMapper.map(movieRepository.findAll(), targetListType);
    }

    public MovieDTO findMovieById(long id) {
        return modelMapper.map(getMovieById(id), MovieDTO.class);
    }

    public MovieDTO createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(command.getTitle(), command.getYear(), command.getLength());
        Director director = directorService.getDirectorById(command.getDirectorId());
        movie.setDirector(director);
        movieRepository.save(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    @Transactional
    public MovieDTO updateMovie(long id, UpdateMovieCommand command) {
        Movie movie = getMovieById(id);
        movie.setTitle(command.getTitle());
        movie.setYear(command.getYear());
        movie.setLength(command.getLength());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public void deleteMovie(long id) {
        getMovieById(id);
        movieRepository.deleteById(id);
    }

    public MovieRatingsDTO getRatings(long id) {
        return modelMapper.map(getMovieById(id), MovieRatingsDTO.class);
    }

    @Transactional
    public MovieDTO addRating(long id, int rating) {
        Movie movie = getMovieById(id);
        movie.addRating(rating);
        movie.calculateAverageRating();
        return modelMapper.map(movie, MovieDTO.class);
    }

    private Movie getMovieById(long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie ID not found: " + id));
    }
}
