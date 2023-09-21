package aprendendo.types;

public class Tuple {
    private Object first;

    private Object second;

    public Tuple(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    public Object getFirst() {
        return first;
    }

    public Object getSecond() {
        return second;
    } 

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
