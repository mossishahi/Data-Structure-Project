package utility;

import java.util.Comparator;

/**
 * Created by mostafa on 6/4/2017.
 */
public class Data implements Comparator<Data>, Comparable<Data> {
    public Node Node;
    public char character;

    public Data(utility.Node node, char character) {
        Node = node;
        this.character = character;
    }

    // Overriding the compareTo method
    public int compareTo(Data d) {
        return (int) this.character - (int) d.character;
    }

    // Overriding the compare method to sort the age
    public int compare(Data d, Data d1) {
        return (int) d.character - (int) d1.character;
    }
}
