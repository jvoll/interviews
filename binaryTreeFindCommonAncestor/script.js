class Node {
    constructor(data) {
        this.data = data
        this.left = null
        this.right = null
    }
}

// Note: this is _not_ a BST on purpose
const buildTree = () => {
    const root = new Node(7)

    // Left
    const l1 = new Node(4)
    root.left = l1
    const l2 = new Node(5)
    l1.left = l2
    const l3 = new Node(2)
    l1.right = l3

    // Right
    const r1 = new Node(3)
    root.right = r1
    const r2 = new Node(1)
    r1.left = r2
    const r3 = new Node(9)
    r1.right = r3
    const r4 = new Node(15)
    r2.left = r4
    const r5 = new Node(6)
    r2.right = r5

    return root
}

// DFS with in-order traversal
const findCommonAncestor = (node, a, b) => {
    let foundSoFar = {}

    // Left
    if (node.left !== null) {
        foundSoFar = {
            ...foundSoFar,
            ...findCommonAncestor(node.left, a, b)
        }
    }
    if (done(foundSoFar)) {
        foundSoFar.node = foundSoFar.node ? foundSoFar.node : node
        return foundSoFar
    }

    // Current
    if (node.data === a) foundSoFar.hasA = true
    if (node.data === b) foundSoFar.hasB = true
    if (done(foundSoFar)) {
        foundSoFar.node = foundSoFar.node ? foundSoFar.node : node
        return foundSoFar
    }

    // right
    if (node.right !== null) {
        foundSoFar = {
            ...foundSoFar,
            ...findCommonAncestor(node.right, a, b)
        }
    }
    if (done(foundSoFar)) {
        foundSoFar.node = foundSoFar.node ? foundSoFar.node : node
        return foundSoFar
    }
    return foundSoFar
}

const done = (status) => {
    return status.hasA && status.hasB
}

// console.log(findCommonAncestor(buildTree(), 5, 2))
console.log(findCommonAncestor(buildTree(), 15, 6))