package moviedatabase.director;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDirectorCommand {

    @NotBlank(message = "The director's name cannot be empty")
    @Size(min = 3, max = 255, message = "The length of the director's name must be between 3 and 255 characters")
    private String name;
    private LocalDate birthday;
}
