package dev.olatejulian.javaapiexample.shared.application;

import java.util.ArrayList;
import java.util.List;

import dev.olatejulian.javaapiexample.shared.application.dto.ResponseErrorDto;
import dev.olatejulian.javaapiexample.shared.application.factory.ResponseErrorDtoFactory;

public final class ExceptionService {
    public static List<ResponseErrorDto> toListOfResponseErrorDto(List<Exception> exceptions) {
        return exceptions.stream().map(ResponseErrorDtoFactory::fromException).toList();
    }

    private final List<Exception> exceptions;

    public ExceptionService() {
        this.exceptions = new ArrayList<>();
    }

    public <R, E extends Exception> R executeOrAddException(FunctionWithThrowsHandler<R, E> handler) {
        R result = null;

        try {
            result = handler.handle();
        } catch (Exception e) {
            this.addException(e);
        }

        return result;
    }

    public <E extends Exception> void throwComposeException(ComposeExceptionHandler<E> mainException,
            List<Class<? extends Exception>> exceptions) throws E {
        var exceptionsFiltered = this.filterExceptionsByClasses(exceptions);

        if (Boolean.TRUE.equals(hasExceptions(exceptionsFiltered))) {
            var message = getComposeExceptionMessage(exceptionsFiltered);

            throw mainException.handle(message, exceptionsFiltered);
        }
    }

    private void addException(Exception e) {
        this.exceptions.add(e);
    }

    private List<Exception> filterExceptionsByClasses(List<Class<? extends Exception>> exceptionClasses) {
        return this.exceptions.stream().filter(exception -> exceptionClasses.contains(exception.getClass())).toList();
    }

    private static String getComposeExceptionMessage(List<Exception> exceptions) {
        return exceptions.stream().map(exception -> String.format("%s: %s%n",
                exception.getClass().getSimpleName(), exception.getMessage())).reduce("", String::concat);
    }

    private static boolean hasExceptions(List<Exception> exceptions) {
        return !exceptions.isEmpty();
    }

    @FunctionalInterface
    public interface FunctionWithThrowsHandler<R, E extends Exception> {
        public R handle() throws E;
    }

    @FunctionalInterface
    public interface ComposeExceptionHandler<E extends Exception> {
        public E handle(String message, List<Exception> exceptions);
    }
}
