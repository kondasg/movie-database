package moviedatabase.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long id;
    private String title;
    private int year;
    private int length;
    private double averageRating;
}
