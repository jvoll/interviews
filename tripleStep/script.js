const tripleStep = (n) => {
    if (n === 0) return 0
    if (n === 1) return 1
    if (n === 2) return 2
    if (n === 3) return 4

    let a = 1
    let b = 2
    let c = 4
    for (let i = 4; i < n + 1; i++) {
        let d = a + b + c
        a = b
        b = c
        c = d
    }
    return c
}

console.log(tripleStep(0))
console.log(tripleStep(1))
console.log(tripleStep(2))
console.log(tripleStep(3))
console.log(tripleStep(4))
console.log(tripleStep(5))
console.log(tripleStep(6))