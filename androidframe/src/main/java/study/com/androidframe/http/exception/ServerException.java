package study.com.androidframe.http.exception;

public class ServerException extends RuntimeException {
    int code;
    String message;
}