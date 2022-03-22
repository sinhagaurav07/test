package org.walmart.gs;

public class ReverseKGroups {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode ptr = head;
        ListNode kTail = null;

        //Head of the final, modified linked list
        ListNode newHead = null;

        //Keep going until there are noes in the list
        while(ptr != null) {
            int count = 0;
            ptr = head;
            while(count < k && ptr != null){
                ptr = ptr.next;
                count += 1;
            }

            if(count == k) {
                ListNode revHead = reverseLinkedList(head, k);

                //newHeaad is the head of the final linked list
                if(newHead == null)
                    newHead = revHead;

                //kTail is the tail of previous block of reversed k nodes
                if(kTail != null)
                    kTail.next = revHead;

                kTail = head;
                head = ptr;
            }
        }

        if(kTail != null){
            kTail.next = head;
        }
        return newHead == null ? head : newHead;
    }

    private ListNode reverseLinkedList(ListNode head, int k){
        ListNode prev = null;
        ListNode curr = head;
        while(k > 0) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
            k--;
        }
        return prev;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);
        ListNode listNode7 = new ListNode(7);
        ListNode listNode8 = new ListNode(8);
        ListNode listNode9 = new ListNode(9);
        ListNode listNode10 = new ListNode(10);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;
        listNode6.next = listNode7;
        listNode7.next = listNode8;
        listNode8.next = listNode9;
        listNode9.next = listNode10;
        ReverseKGroups reverseKGroups = new ReverseKGroups();
        ListNode listNode = reverseKGroups.reverseKGroup(listNode1, 3);
        System.out.println(listNode);
    }

}
