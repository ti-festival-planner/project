package Util;

import java.io.Serializable;

public class Guard implements Serializable {

    private String name;

    /**
     * constructs a guard object
     * @param name the name of the guard
     */
    Guard(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
