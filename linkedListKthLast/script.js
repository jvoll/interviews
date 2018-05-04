class Node {
    constructor(d) {
        this.next = null
        this.data = d
    }
}

class LinkedList {
    constructor() {
        this.head = null
        this.tail = null
        this.size = 0
    }

    addItem(num) {
        const node = new Node(num)
        if (this.head == null) {
            this.head = node
            this.tail = node
        } else {
            this.tail.next = node
            this.tail = node
        }
        this.size++
    }

    // deleteKthLast(k) {
    //     // Assume we want to do nothing if k > size
    //     let indexToDelete = this.size - k
    //     if (indexToDelete < 0) return

    //     // Find the node to delete
    //     let previousNode = null
    //     let curNode = this.head
    //     while (indexToDelete > 0) {
    //         previousNode = curNode
    //         curNode = curNode.next
    //         indexToDelete--
    //     }

    //     // Do deletion -- check special case of deleting the head first
    //     if (curNode === this.head) {
    //         this.head = this.head.next
    //     } else if (curNode === this.tail) {
    //         previousNode.next = null
    //         this.tail = previousNode
    //     } else {
    //         previousNode.next = curNode.next
    //     }

    //     this.size--
    // }

    // Let's assume we don't know the size and that we want to return it, not delete it
    // knowing size makes it too easy
    findKthLast(k) {
        if (k < 1) {
            console.error(`k must be greater than 0`)
            return null
        }
        if (this.head == null) {
            console.log(`List is empty, kth last is null`)
            return null
        }
        let pointerSlow = this.head
        let pointerFast = this.head
        // Move pointerFast forward by k
        while (pointerFast !== null && k > 1) {
            pointerFast = pointerFast.next
            k--
        }
        if (k > 1) {
            console.error(`List is less than ${k} elements long`)
            return null
        }

        // Move both ahead until pointerFast reaches the end
        while (pointerFast.next !== null) {
            pointerSlow = pointerSlow.next
            pointerFast = pointerFast.next
        }
        return pointerSlow
    }

    print() {
        console.log(`Size: ${this.size}`)
        console.log(`Head: ${this.head.data} head-next: ${this.head.next.data}`)
        console.log(`Tail: ${this.tail.data} tail-next: ${this.tail.next}`)
        let curNode = this.head
        while (curNode !== null) {
            console.log(curNode.data)
            curNode = curNode.next
        }
    }
}

const createList = () => {
    const newList = new LinkedList()
    newList.addItem(1)
    newList.addItem(2)
    newList.addItem(3)
    newList.addItem(4)
    newList.addItem(5)
    newList.addItem(6)
    return newList
}

// const list = createList()
// list.print()
// const k = 0
// const kthLast = list.findKthLast(k)
// console.log(`${k}th last: ${kthLast.data}`)
// list.print()

const empty = new LinkedList()
empty.findKthLast(1)