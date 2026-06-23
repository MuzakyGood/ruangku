package program.process;

public class ReservationException extends Exception
{
    public ReservationException() { super(); }
    public ReservationException(String message) { super(message); }
    public ReservationException(Throwable cause) { super(cause); }
    public ReservationException(String message, Throwable cause) { super(message, cause); }
    public ReservationException(
        String message,
        Throwable cause, 
        boolean enableSuppression, 
        boolean writableStackTrace)
    {
       super(message, cause, enableSuppression, writableStackTrace);
    }
}