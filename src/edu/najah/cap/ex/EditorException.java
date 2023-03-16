package edu.najah.cap.ex;

import java.nio.file.NoSuchFileException;

public class EditorException extends Exception {
private final ExceptionType exceptionType;
    public EditorException(String message,ExceptionType exceptionType) {
        super(message);
        this.exceptionType=exceptionType;
    }
    public ExceptionType getExceptionType(){
        return exceptionType;
    }
}
