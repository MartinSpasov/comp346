public class PopException extends Exception {
    public PopException()
    {
        super("Cannot pop the stack since it is empty.");
    }
}
