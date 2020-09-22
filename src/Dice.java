public class Dice {
    private static final int MAX = 6;

    public static int Roll() {
        return (int) (Math.random() * MAX) + 1;
    }
}
