const findAnagrams = (small, big) => {
    const need = createInitialMap(small)
    const found = {}

    let loosing = ''
    let curChar
    for (let i = 0; i < big.length; i++) {
        curChar = big[i]

        // Add curChar to found
        if (found[curChar]) {
            found[curChar]++
        } else {
            found[curChar] = 1
        }

        // Remove loosing
        if (i >= small.length) {
            loosing = big[i - small.length]

            if (found[loosing] > 1) {
                found[loosing]--
            } else {
                delete(found[loosing])
            }
        }

        // Check if we've got a match
        if (compare(need, found)) {
            console.log(`Found at index: ${i - small.length + 1}`)
        }
    }
}

const createInitialMap = (small) => {
    const map = {}
    for (let ch of small) {
        if (map[ch]) {
            map[ch]++
        }
        else {
            map[ch] = 1
        }
    }
    return map
}

const compare = (need, found) => {
    if (Object.keys(need).length !== Object.keys(found).length) {
        return false
    }
    for (let key of Object.keys(need)) {
        if (need[key] !== found[key]) {
            return false
        }
    }
    return true
}

// Test cases borrowed from Cracking the Coding Interview pg. 70
// and https://www.geeksforgeeks.org/anagram-substring-search-search-permutations/
const small = 'abbc'
const big = 'cbabadcbbabbcbabaabccbabc'
findAnagrams(small, big)

const small1 = 'DBCA'
const big1 = 'BACDGABCDA'
findAnagrams(small1, big1)

const small2 = 'AABA'
const big2 = 'AAABABAA'
findAnagrams(small2, big2)