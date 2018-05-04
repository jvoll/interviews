const oneEdit = (a, b) => {
    const lenA = a.length
    const lenB = b.length
    const equalLengths = lenA === lenB
    if (Math.abs(lenA - lenB) > 1) {
        return false
    }

    let string1
    let string2
    if (lenA > lenB) {
        string1 = a
        string2 = b
    } else {
        string1 = b
        string2 = a
    }

    let editted = false;
    let isOneEditAway = true;
    [...string1].map((char1, idx) => {
        // If we've 'edited' (i.e. skipping a character in the longer string)
        // and string lengths differ, we need to
        // reach back one char in the shorter string
        const index = (editted && !equalLengths) ? idx - 1 : idx

        if (string2[index] !== char1) {
            if (editted) {
                isOneEditAway = false
                return
            }
            editted = true
        }
    })

    return isOneEditAway
}

console.assert(true === oneEdit('pale', 'ple'))
console.assert(true === oneEdit('ple', 'pale'))
console.assert(true === oneEdit('pale', 'pales'))
console.assert(false === oneEdit('paled', 'ple'))
console.assert(false === oneEdit('ape', 'dade'))
console.assert(true === oneEdit('pale', 'pade'))
console.assert(true === oneEdit('pale', 'bale'))
console.assert(false === oneEdit('pale', 'bake'))