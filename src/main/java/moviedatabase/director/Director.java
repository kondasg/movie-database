package moviedatabase.director;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moviedatabase.movie.Movie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "directors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthday;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
    private List<Movie> movies = new ArrayList<>();

    public Director(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }
}
