package pac.exeptions;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by macbookair on 07.12.16.
 */
public class DefaultExceptionAttributes implements ExceptionAttributes {

    public final String TIMESTAMP = "timestamp";
    public final String STATUS = "status";
    public final String ERROR = "error";
    public final String MESSAGE = "message";
    public final String EXCEPTION = "exception";
    public final String PATH = "path";


    @Override
    public Map<String, Object> getExceptionsAttributes(Exception exception,
                                                        HttpServletRequest request, HttpStatus status) {
        Map<String, Object> exceptionAttributes = new LinkedHashMap<>();

        exceptionAttributes.put(TIMESTAMP, new Date());

        addHttpStatus(exceptionAttributes, status);
        addExceptionDetails(exceptionAttributes, exception);
        addPath(exceptionAttributes, request);

        return exceptionAttributes;
    }

    private Map<String, Object> addHttpStatus(Map<String, Object> exceptionAttributes, HttpStatus httpStatus){

        exceptionAttributes.put(STATUS, httpStatus.value());
        exceptionAttributes.put(ERROR, httpStatus.getReasonPhrase());

        return exceptionAttributes;
    }

    private Map<String, Object> addExceptionDetails(Map<String, Object> exceptionAttributes, Exception exception){

        exceptionAttributes.put(EXCEPTION, exception.getClass().getName());
        exceptionAttributes.put(MESSAGE, exception.getMessage());

        return exceptionAttributes;
    }

    private Map<String, Object> addPath(Map<String, Object> exceptionAttributes, HttpServletRequest httpServletRequest){

        exceptionAttributes.put(PATH, httpServletRequest.getServletPath());
        return exceptionAttributes;
    }

}




















