package moviedatabase.director;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdeateDirectorCommand {

    private String name;
    private LocalDate birthday;
}
