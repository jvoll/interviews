class StackNode {
    constructor(data) {
        this.data = data
        this.next = null
    }

    hasNext () {
        return this.next !== null
    }
}

class Stack {
    constructor() {
        this.top = null
    }

    push(data) {
        const newNode = new StackNode(data)
        newNode.next = this.top
        this.top = newNode
    }

    pop() {
        if (this.isEmpty()) throw new Error('Cannot pop from empty stack')
        const oldTop = this.top
        this.top = this.top.next
        return oldTop
    }

    peek() {
        if (this.isEmpty()) throw new Error('Cannot peek into empty stack')
        return this.top.data
    }

    isEmpty() {
        return this.top === null
    }

    print() {
        if (this.isEmpty()) {
            console.log('EMPTY STACK')
            return
        }

        console.log('TOP')
        let curNode = this.top
        while (curNode !== null) {
            console.log(curNode.data)
            curNode = curNode.next
        }
        console.log('BOTTOM')
    }
}

module.exports.Stack = Stack
