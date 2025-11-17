// Cesar Pimentel

/**
 * Custom HashMap: Room Number (key), Room Object (value)
 * Simple implementation with custom hash function and rehashing
 */
public class RoomHashMap {

    private static class Node {
        int key;              // Room number
        Room value;           // Room object
        Node next;

        Node(int key, Room value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    public RoomHashMap() {
        this.capacity = 16;
        this.table = new Node[capacity];
        this.size = 0;
    }

    /**
     * Custom hash function for room numbers
     * Uses prime multiplication to reduce collisions
     */
    private int hash(int roomNumber) {
        int hash = roomNumber * 31;  // 31 is a prime number
        return Math.abs(hash) % capacity;
    }

    /**
     * Add or update a room in the map
     */
    public void put(int roomNumber, Room room) {
        int index = hash(roomNumber);
        Node current = table[index];

        // Check if key already exists - update it
        while (current != null) {
            if (current.key == roomNumber) {
                current.value = room;
                return;
            }
            current = current.next;
        }

        // Add new node at beginning of chain
        Node newNode = new Node(roomNumber, room);
        newNode.next = table[index];
        table[index] = newNode;
        size++;

        // Check if rehashing needed
        if ((double) size / capacity >= LOAD_FACTOR) {
            rehash();
        }
    }

    /**
     * Get room by room number
     */
    public Room get(int roomNumber) {
        int index = hash(roomNumber);
        Node current = table[index];

        while (current != null) {
            if (current.key == roomNumber) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Remove a room from the map
     */
    public Room remove(int roomNumber) {
        int index = hash(roomNumber);
        Node current = table[index];
        Node prev = null;

        while (current != null) {
            if (current.key == roomNumber) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }

        return null;
    }

    /**
     * Rehashing doubles capacity and redistributes all entries
     */
    private void rehash() {
        System.out.println("Rehashing! Old capacity: " + capacity);

        Node[] oldTable = table;
        capacity *= 2;
        table = new Node[capacity];
        size = 0;

        // Re-add all entries with new hash values
        for (Node head : oldTable) {
            while (head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }

        System.out.println("New capacity: " + capacity);
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean containsKey(int roomNumber) {
        return get(roomNumber) != null;
    }
}
