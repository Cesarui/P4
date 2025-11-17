import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Game logic - GUI team calls these methods
 */
public class Simulation {

    private RoomHashMap roomMap;
    private Player player;
    private List<Bot> bots;
    private int currentRoomNumber;

    public Simulation() {
        roomMap = new RoomHashMap();
        bots = new ArrayList<>();
        currentRoomNumber = 1;
        createRooms();
    }

    /**
     * Creates all 10 rooms and stores in hash map
     */
    private void createRooms() {
        roomMap.put(1, new Room(1, "What has keys but can't open locks?", "piano"));
        roomMap.put(2, new Room(2, "What gets wetter the more it dries?", "towel"));
        roomMap.put(3, new Room(3, "What has to be broken before you can use it?", "egg"));
        roomMap.put(4, new Room(4, "I'm tall when I'm young and short when I'm old.", "candle"));
        roomMap.put(5, new Room(5, "What month of the year has 28 days?", "all"));
        roomMap.put(6, new Room(6, "What is full of holes but still holds water?", "sponge"));
        roomMap.put(7, new Room(7, "What question can you never answer yes to?", "asleep"));
        roomMap.put(8, new Room(8, "What is always in front of you but can't be seen?", "future"));
        roomMap.put(9, new Room(9, "What can you break without picking it up or touching it?", "promise"));
        roomMap.put(10, new Room(10, "What goes up but never comes down?", "age"));
    }

    /**
     * GUI calls this at game start with player name
     */
    public void startGame(String playerName) {
        player = new Player(playerName);
        currentRoomNumber = 1;
    }

    /**
     * GUI calls this to add bots
     */
    public void addBot(String botName) {
        bots.add(new Bot(botName));
    }

    /**
     * GUI calls when player submits guess
     * Returns true if correct
     */
    public boolean checkGuess(String guess) {
        Room room = roomMap.get(currentRoomNumber);

        boolean correct = room.checkAnswer(guess);

        if (!correct) {
            player.numOfIncorrectGuesses++;
        }

        return correct;
    }

    /**
     * GUI calls after correct answer to move to next room
     */
    public boolean moveToNextRoom() {
        if (currentRoomNumber < 10) {
            currentRoomNumber++;
            return true;
        }
        return false; // Already at last room
    }

    /**
     * GUI calls to get current room riddle
     */
    public String getCurrentRiddle() {
        Room room = roomMap.get(currentRoomNumber);
        return room.getRiddle();
    }

    /**
     * GUI calls to display current room number
     */
    public int getCurrentRoomNumber() {
        return currentRoomNumber;
    }

    /**
     * GUI calls to get player stats
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * GUI calls to simulate bot turns
     */
    public String botTurn(Bot bot) {
        Room room = roomMap.get(currentRoomNumber);
        String[] guesses = getGuessPool(currentRoomNumber);
        return bot.attempt(room, guesses);
    }

    /**
     * Check if player won (reached room 10 and solved it)
     */
    public boolean hasPlayerWon() {
        if (currentRoomNumber == 10) {
            Room finalRoom = roomMap.get(10);
            return finalRoom.isSolved();
        }
        return false;
    }

    /**
     * GUI can call this to show rehashing
     */
    public void demonstrateRehash() {
        System.out.println("Current capacity: " + roomMap.getCapacity());

        // Add extra rooms to trigger rehash
        for (int i = 11; i <= 20; i++) {
            roomMap.put(i, new Room(i, "Test riddle " + i, "test" + i));
        }

        System.out.println("Final capacity: " + roomMap.getCapacity());
    }

    /**
     * Get list of all bots
     */
    public List<Bot> getBots() {
        return bots;
    }

    /**
     * Helper: guess pool for bots
     */
    private String[] getGuessPool(int roomNum) {
        String[][] pools = {
                {"piano", "keyboard", "door"},
                {"towel", "sponge", "sun"},
                {"egg", "nut", "glass"},
                {"candle", "pencil", "tree"},
                {"all", "february", "none"},
                {"sponge", "net", "bucket"},
                {"asleep", "dead", "no"},
                {"future", "air", "wind"},
                {"promise", "glass", "heart"},
                {"age", "price", "balloon"}
        };
        return pools[roomNum - 1];
    }

    /**
     * Console test - This can be ignored, just trying to see how it all looks.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Simulation simulation = new Simulation();

        System.out.print("Enter your name: ");
        simulation.startGame(scanner.nextLine());

        simulation.addBot("QuickBot");

        while (simulation.getCurrentRoomNumber() <= 10) {
            System.out.println("\n--- Room " + simulation.getCurrentRoomNumber() + " ---");
            System.out.println(simulation.getCurrentRiddle());
            System.out.print("Guess: ");

            String guess = scanner.nextLine();

            if (simulation.checkGuess(guess)) {
                System.out.println("Correct!");

                if (simulation.hasPlayerWon()) {
                    System.out.println("YOU WON!");
                    break;
                }

                simulation.moveToNextRoom();
            } else {
                System.out.println("Wrong! Try again.");
            }
        }

        scanner.close();
    }
}