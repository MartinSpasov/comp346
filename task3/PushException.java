public class PushException extends Exception {
    public PushException()
    {
        super("Cannot push to the stack since it is full.");
    }
}
