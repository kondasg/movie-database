package moviedatabase.director;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class DirectorNotFoundException extends AbstractThrowableProblem {

    public DirectorNotFoundException(String message) {
        super(URI.create("diector/not-found"),
                "Not found",
                Status.NOT_FOUND,
                message);
    }
}
