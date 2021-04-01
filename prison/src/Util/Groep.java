package Util;

import java.io.Serializable;

public class Groep implements Serializable {

    private String groupName;

    /**
     * constructs a group object
     * @param groupName the names of the group
     */
    Groep(String groupName){
        this.groupName = groupName;
    }

    public String toString() {
        return this.groupName;
    }
}
