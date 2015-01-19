#!/usr/bin/python
# http://www.programcreek.com/2013/02/leetcode-longest-substring-without-repeating-characters-java/
# Given a string, find the length of the longest substring without repeating characters.
# For example, the longest substring without repeating letters for "abcabcbb" is "abc", which
# the length is 3. For "bbbbb" the longest substring is "b", with the length of 1.

def longestSubstringNoRepeat(inStr):
    flags = [False for i in range(256)]
    result = 0
    curStart = 0

    for i in range(len(inStr)):
        cur = inStr[i]
        if flags[ord(cur)]:
            result = max(result, i - curStart)
            for k in range(curStart, i):
                if cur == inStr[k]:
                    curStart = k + 1
                    break
                flags[ord(inStr[k])] = False

        else:
            flags[ord(cur)] = True

    return max(result, len(inStr) - curStart)


def main():
    t1 = "abcabcbb"
    t2 = "bbbbb"
    t3 = "abccccdefghklizzyqr"
    t4 = ""

    print longestSubstringNoRepeat(t1)
    print longestSubstringNoRepeat(t2)
    print longestSubstringNoRepeat(t3)
    print longestSubstringNoRepeat(t4)

if __name__ == "__main__":
    main()
