package ca.jvoll.playground;

import java.util.List;

public class RemoveNthFromEndOfLinkedList {

    public static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof  Node)) return false;
            Node otherNode = (Node) other;

            if (this.value != otherNode.value) return false;

            // Handle this.next being null, other.next being null will be caught on recursive call
            if (this.next == null) {
              if (otherNode.next == null) return true; // Both null, great!
              return false; // this.next is null and other.next is not, therefore !=
            }

            return this.next.equals(otherNode.next);
        }

        @Override
        public String toString() {
            return "List node. Value = " + value;
        }
    }

    // Will throw NPE if you give it a null Integer!
    Node linkedListFromList(List<Integer> list) {
        if (list == null || list.size() < 1) return null;

        Node head = new Node(list.get(0));

        Node last = head;
        for(int i = 1; i < list.size(); i++) {
            Node current = new Node(list.get(i));
            last.next = current;
            last = current;
        }

        return head;
    }

    // Return head node after nth has been removed
    public Node removeNthFromEnd(Node head, int n) {
        if (head == null) return null;

        Node nBehind = null;
        Node cur = head;

        while (cur != null) {
            if (n == 0) {
                nBehind = head;
            } else if (n < 0) {
                nBehind = nBehind.next;
            }
            cur = cur.next;
            n--;
        }

        // Special case: delete first element
        if (n == 0) return head.next;

        // Special case: n was greater than length of list, return unmodified list
        if (nBehind == null) return head;

        // Update reference for nBehind to delete next node
        nBehind.next = nBehind.next.next;

        return head;
    }
}
