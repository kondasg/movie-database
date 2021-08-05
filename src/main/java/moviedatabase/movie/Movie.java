package moviedatabase.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moviedatabase.director.Director;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    private String title;
    private int year;
    private int length;

    @ElementCollection
    @CollectionTable(name = "movies_ratings", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "rating")
    private List<Integer> ratings = new ArrayList<>();

    @Transient
    private double averageRating;

    public Movie(Director director, String title, int year, int length) {
        this.director = director;
        this.title = title;
        this.year = year;
        this.length = length;
    }

    public void addRating(int rating) {
        ratings.add(rating);
    }

    @PostLoad
    public void calculateAverageRating() {
        averageRating = ratings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }
}
