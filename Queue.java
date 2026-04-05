// Lists
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Queue {
    List<Character> charList = new ArrayList<>(Arrays.asList('i','l','j','z','s','o','t'));
    char[] bag = new char[7];
    int bagIndex = 7;
    char hold = 'b';
    char[] q = {'b', 'b', 'b', 'b', 'b', 'b'};


    private void shuffleQueue() {
        Collections.shuffle(charList);
        for (int i = 0; i < 7; i++) {
            bag[i] = charList.get(i);
        }
    }

    public char[] getBag() {
        return bag;
    }

    public void step() {
        for (int  i = 0; i < 5; i++) {
            q[i] = q[i + 1];
        }

        if (bagIndex == 7) {
            shuffleQueue();
            bagIndex = 0;
        }
        q[5] = bag[bagIndex];
        bagIndex++;
    }

    public void hold() {
        if (hold == 'b') {
            hold = q[0];
            step();
        } else {
            char middleMan = hold;
            hold = q[0];
            q[0] = middleMan;
        }
    }
}