// Sort a stack such that smallest items are on top, use only one other stack,
// no other data structures. CCI #3.5

const {Stack} = require('./stack')

const constructStackFromArray = (arr) => {
    const stack = new Stack()
    for (obj of arr) {
        stack.push(obj)
    }
    return stack
}


const stackSort = (stack) => {
    const extraStack = new Stack()
    if (stack.isEmpty()) {
        return
    }

    extraStack.push(stack.pop().data)

    while (!stack.isEmpty()) {
        let curVal = stack.pop().data
        while (!extraStack.isEmpty() && extraStack.peek() > curVal) {
            stack.push(extraStack.pop().data)
        }

        extraStack.push(curVal)
    }

    while (!extraStack.isEmpty()) {
        stack.push(extraStack.pop().data)
    }
}


const test = constructStackFromArray([7, 4, 3, 6, 5, 1, 2, 8, 9])
console.log('BEFORE TEST 1')
test.print()
stackSort(test)
console.log('AFTER')
test.print()


const test2 = constructStackFromArray([1, 2, 3, 4])
console.log('BEFORE TEST 2')
test2.print()
stackSort(test2)
console.log('AFTER')
test2.print()

const test3 = constructStackFromArray([4, 3, 2, 1])
console.log('BEFORE TEST 3')
test3.print()
stackSort(test3)
console.log('AFTER')
test3.print()

const test4 = constructStackFromArray([])
console.log('BEFORE TEST 4')
test4.print()
stackSort(test4)
console.log('AFTER')
test4.print()