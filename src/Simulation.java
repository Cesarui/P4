import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Simple example to show how Room is used.

// Although I used an ArrayList, it was only for example purposes. A hashmap will still be used!

public class Simulation {
    public static void main(String[] args) {
        List<Room> rooms = createRooms();

        Scanner scanner = new Scanner(System.in);

        System.out.println("The Riddle Race\n");

        for (Room room : rooms) {
            System.out.println("You are in Room " + room.getRoomNumber());
            System.out.println("Riddle: " + room.getRiddle());
            System.out.println("Your guess: ");

            String guess = scanner.nextLine();
            boolean correct = room.checkAnswer(guess);

            if (correct) {
                System.out.println("LESSS GOO, you got it right!! Moving on now...\n");
            }
            else {
                System.out.println("You got it wrong:(");
            }

            System.out.println("Room solved: " + room.isSolved());
        }

        System.out.println("\nDemo is over, this was to show the logic of Room");
    }




    private static List<Room> createRooms() {
        List<Room> rooms = new ArrayList<>();

        rooms.add(new Room(1, "What has keys but can't open locks?", "piano"));
        rooms.add(new Room(2, "What gets wetter the more it dries?", "towel"));
        rooms.add(new Room(3, "What has to be broken before you can use it?", "egg"));

        return rooms;
    }
}
