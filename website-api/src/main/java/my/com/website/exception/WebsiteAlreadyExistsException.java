package my.com.website.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class WebsiteAlreadyExistsException  extends RuntimeException {

    public WebsiteAlreadyExistsException(String message) {
        super(message);
    }
}
