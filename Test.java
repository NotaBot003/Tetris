import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Queue q = new Queue();
        while (true) {
            String a = input.nextLine();
            if (!a.equals("e")) {
                q.step();
            }
            if (a == "0") {
                break;
            }
            if (a.equals("e")) {
                System.out.println("holding");
                q.hold();
            }

            System.out.print("bag: ");
            for (int i = 0; i < 7; i++) {
                System.out.print(q.bag[i] + " ");
            }
            System.out.print("\nQueue: ");
            for (int i = 0; i < 6; i++) {
                System.out.print(q.q[i] + "  ");
            }
            System.out.println();
            System.out.println("Bag index: " + q.bagIndex);
            System.out.println("hold: " + q.hold);
        }

        input.close();
    }
}
