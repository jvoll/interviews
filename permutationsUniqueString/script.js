const memo = {}
let callCount = 0

const permutationsOf = (str) => {
    if (str.length === 1) return str
    if (memo[str]) return memo[str]
    callCount ++
    const perms = []
    for (let i = 0; i < str.length; i++) {
        for (p of permutationsOf(str.replace(str[i], ''))) {
            perms.push(str[i] + p)
        }
    }
    memo[str] = perms
    return perms
}

console.log(permutationsOf('ab'), callCount)
console.log(permutationsOf('abc'), callCount)
console.log(permutationsOf('abcd'), callCount)