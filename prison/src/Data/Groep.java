package Data;
import java.io.Serializable;

public class Groep implements Serializable {
    private String groupName = "";

    Groep(String groupName){
        this.groupName = groupName;
    }

    public String toString() {
        return this.groupName;
    }
}
