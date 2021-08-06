package moviedatabase.director;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdeateDirectorCommand {

    @NotBlank(message = "The director's name cannot be empty")
    @Size(min = 3, max = 255, message = "The length of the director's name must be between 3 and 255 characters")
    @Schema(description = "Director's name", example = "Steven Spielberg")
    private String name;
    @Schema(description = "Director's birthday", example = "1980-01-01")
    private LocalDate birthday;
}
