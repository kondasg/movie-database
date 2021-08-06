package moviedatabase.movie;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class MovieNotFoundException extends AbstractThrowableProblem {

    public MovieNotFoundException(String message) {
        super(URI.create("movie/not-found"),
                "Not found",
                Status.NOT_FOUND,
                message);
    }
}
