// Cesar Pimentel
// Gabriel Luciano

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game logic - GUI calls these methods
 */
public class Simulation {

    private RoomHashMap roomMap;
    private Player player;
    private List<Bot> bots;
    private int currentRoomNumber;
    private ArrayList<Integer> listOfRevealedHintIndexes;

    public Simulation() {
        roomMap = new RoomHashMap();
        listOfRevealedHintIndexes = new ArrayList<>();
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
     * Creates 10 more rooms for bonus round (causes rehash)
     */
    private void createBonusRooms() {
        roomMap.put(11, new Room(11, "What has hands but cannot clap?", "clock"));
        roomMap.put(12, new Room(12, "What has a head and tail but no body?", "coin"));
        roomMap.put(13, new Room(13, "What can travel around the world while staying in a corner?", "stamp"));
        roomMap.put(14, new Room(14, "What has a neck but no head?", "bottle"));
        roomMap.put(15, new Room(15, "What gets sharper the more you use it?", "brain"));
        roomMap.put(16, new Room(16, "What can run but never walks?", "water"));
        roomMap.put(17, new Room(17, "What has cities but no houses?", "map"));
        roomMap.put(18, new Room(18, "What has a ring but no finger?", "phone"));
        roomMap.put(19, new Room(19, "What flies without wings?", "time"));
        roomMap.put(20, new Room(20, "What has teeth but cannot bite?", "comb"));
    }

    /**
     * Finishes the game, calls the players finish game method
     */
    public void finishGame() {
        player.finishGame();
    }

    /**
     * goes through all fo the bots in the bots arrayList and
     * calls the method simulateBot() with said bot as a parameter
     */
    public void simulateBots() {
        for (Bot bot : bots) {
            simulateBot(bot);
        }
    }
    /**
     * Simulates a bot completing all 10 rooms.
     * The bot keeps guessing until it solves each room.
     *
     * @param bot the bot to simulate
     */
    private void simulateBot(Bot bot) {
        for (int roomNum = 1; roomNum <= 10; roomNum++) {
            Room room = roomMap.get(roomNum);
            String[] guesses = getGuessPool(roomNum);

            boolean solved = false;
            while (!solved) {
                String result = bot.attempt(room, guesses);
                if (result.contains("solved")) {
                    solved = true;
                }
            }
        }
        bot.finishGame();
    }

    public Room getCurrentRoom () {
        return roomMap.get(currentRoomNumber);
    }

    public String showHint(Room givenRoom) {
        String answer = givenRoom.getAnswer();

        if (listOfRevealedHintIndexes.size() >= answer.length()) {
            return givenRoom.getHint();
        }
        String currentHint = givenRoom.getHint();

        if (currentHint == null || currentHint.isEmpty()) {
            currentHint = createHiddenAnswer(answer.length());
        }

        //gets random index that hasn't been chosen yet (ONLY APPLIES TO CURRENT RIDDLE)
        int indexToBeRevealed = getIndexToBeRevealed(answer.length());

        char revealedChar = answer.charAt(indexToBeRevealed);
        StringBuilder hiddenAnswerBuilder = new StringBuilder(currentHint);
        hiddenAnswerBuilder.setCharAt(indexToBeRevealed, revealedChar);

        String updatedHint = hiddenAnswerBuilder.toString();
        givenRoom.setHint(updatedHint);

        player.getHint();

        if (listOfRevealedHintIndexes.size() == answer.length()) {
            player.addTimePenalty(120);
        }
        return updatedHint;
    }

    private int getIndexToBeRevealed(int size) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(0, size);

        if (listOfRevealedHintIndexes.contains(randomIndex)) {
            return getIndexToBeRevealed(size);
        }
        else {
            listOfRevealedHintIndexes.add(randomIndex);
            return randomIndex;
        }
    }

    private String createHiddenAnswer(int length) {
        return "_" + "_".repeat(length - 1);
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
        player.numOfIncorrectGuesses = 0; // Reset incorrect guesses for new room
        listOfRevealedHintIndexes.clear(); // Clears the list of the revealed letters
        Room currentRoom = roomMap.get(currentRoomNumber);
        if (currentRoom != null) {
            currentRoom.setHint("");
        }

        if (currentRoomNumber < 20) {
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
            if (finalRoom.isSolved()) {
                // Player beat first 10 rooms, add the bonus rooms
                System.out.println("OH YEAAA! Bonus rooms unlocked:)");
                createBonusRooms(); // This will trigger rehash when you add room 13
                return false; // Game continues with bonus rooms
            }
        }

        if (currentRoomNumber == 20) {
            Room finalRoom = roomMap.get(20);
            return finalRoom.isSolved(); // True win condition
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
    /*
    //Console test - This can be ignored, just trying to see how it all looks.
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
    */
}