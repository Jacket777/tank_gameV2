import java.io.Serializable;

public class Apple implements Serializable {
    private int a = 10;

    public Apple(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
