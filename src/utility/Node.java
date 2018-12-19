package utility;

import java.util.*;

/**
 * Created by mostafa on 1/15/2017.
 */

public class Node {
    char character;
    ArrayList<Data> data = new ArrayList<Data>();
    boolean isLeaf;
    private Object object = null;
    public Object removeObject(String key) {
        if (key.length() == 0 && this.object != null) {
            Object instance = this.object;
            this.object = null;
            return instance;
        }
        char childChar = key.charAt(0);
        Data instanceData = null;
        for (Data d : data) {
            if (d.Node.character == childChar)
                instanceData = d;
        }
        if (instanceData == null)
            return null;
        String nextString = key.substring(1);
        Object object = instanceData.Node.removeObject(nextString);
        if (instanceData.Node == null)
            data.remove(instanceData);
        return object;
    }
    public boolean isEmpty() {
        return (this.object == null && data.size() == 0);
    }

    public Node() {
    }

    public Node(char c) {
        this.character = c;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void getItems(ArrayList<Object> items) {
        if (this.object != null)
            items.add(object);
        for (Data d : data) {
            d.Node.getItems(items);
        }
    }
}

