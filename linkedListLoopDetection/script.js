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

    print() {
        console.log(`Size: ${this.size}`)
        console.log(`Head: ${this.head.data} head-next: ${this.head.next.data}`)
        console.log(`Tail: ${this.tail.data} tail-next: ${this.tail.next}`)

        // Added a safety here to make sure we don't loop indefinitely if list is busted (for this problem)
        let maxPrint = 10
        let curNode = this.head
        while (curNode !== null && maxPrint > 0) {
            console.log(curNode.data)
            curNode = curNode.next
            maxPrint--
        }
    }
}

const createValidNoLoopList = () => {
    const newList = new LinkedList()
    newList.addItem(1)
    newList.addItem(2)
    newList.addItem(3)
    newList.addItem(4)
    return newList
}

const createListWithLoop = () => {
    const newList = new LinkedList()
    newList.addItem(1)
    newList.addItem(2)
    newList.addItem(3)
    newList.addItem(4)
    newList.addItem(5)
    newList.addItem(6)
    newList.addItem(7)
    newList.addItem(8)
    const startOfLoop = newList.tail
    newList.addItem(9)
    newList.tail.next = startOfLoop
    return newList
}

const createListWithSingleElementLooping = () => {
    const newList = new LinkedList()
    newList.addItem(1)
    newList.head.next = newList.head
    return newList
}

const createShortListWithElementLooping = () => {
    const newList = new LinkedList()
    newList.addItem(1)
    newList.addItem(2)
    newList.tail.next = newList.head
    return newList
}

// Return true if we've found a loop in the supplied LinkedList
const detectLoop= (ll) => {
    let p1 = ll.head
    let p2 = ll.head

    while (p2 !== null) {
        p1 = p1.next

        if (p2.next != null) {
            p2 =p2.next.next
        } else {
            return false
        }

        if (p1 === p2) {
            console.log(`Collision point: ${p1.data}`)
            // Collision point is the same number of steps as the head from the start
            // of the loop
            p1 = ll.head
            while (p1 !== p2) {
                p1 = p1.next
                p2 = p2.next
            }
            console.log(`Start of loop: ${p1.data}`)
            return true
        }
    }
    return false
}

const loopedList = createListWithLoop()
loopedList.print()
console.log(detectLoop(loopedList))