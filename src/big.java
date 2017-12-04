public class big {
    private static big ourInstance = new big();

    public static big getInstance() {
        return ourInstance;
    }

    private big() {
    }
}
