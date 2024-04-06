public class LinkedList {
    Node head;

    public void add(Passenger passenger) {
        if (this.head == null) {
            // list is empty
            this.head = new Node(passenger);
        } else {
            // list isn't empty
            Node newNode = new Node(passenger);
            newNode.next = this.head;
            this.head = newNode;
        }
    }

    public void delete(Passenger passenger) {
        Node node = this.head;

        // delete head
        if (this.head.passenger == passenger) {
            this.head = this.head.next;
            return;
        }

        // otherwise find node with passenger
        while(node.next.passenger != passenger) {
            node = node.next;
            if (node == null) {
                System.out.println("Passenger not in list to delete");
                return;
            }
        }

        // then delete node
        node.next = node.next.next;
    }
}

class Node {
    public Passenger passenger;
    public Node next;
    public Node(Passenger passenger) {
        this.passenger = passenger;
    }
}
