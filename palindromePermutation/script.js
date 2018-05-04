// Note: should ask about upper/lower case (assume case doesn't matter)
// Assume whitespace does not matter
const isPalindromePermutation = (str) => {
    // Iterate through and check if each character is in the string an even
    // number of times, except for one character max
    const charArray = []
    const spaceCharAscii = ' '.charCodeAt()
    let asciiChar
    for (let char of str) {
        asciiChar = char.toLowerCase().charCodeAt(0)

        // ignore spaces
        if (asciiChar === spaceCharAscii) continue

        if (charArray[asciiChar] !== undefined) {
            charArray[asciiChar]++
        } else {
            charArray[asciiChar] = 1
        }
    }

    let hasOddNumChar = false
    for (let charCnt of charArray) {
        if (charCnt === undefined) {
            continue
        }

        if (charCnt % 2 !== 0) {
            if (hasOddNumChar) {
                return false
            } else {
                hasOddNumChar = true
            }
        }
    }
    return true
}

console.log(isPalindromePermutation('tact cao'))
console.log(isPalindromePermutation('tact co'))
console.log(isPalindromePermutation('leevl le vel'))
console.log(isPalindromePermutation('Was It A Rat I Saw'))
console.log(isPalindromePermutation('Was It A t I Saaplw'))