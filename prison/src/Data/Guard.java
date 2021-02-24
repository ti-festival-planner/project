package Data;
import java.io.Serializable;

public class Guard implements Serializable {
    private String name = "";

    Guard(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
