#!/usr/bin/python
# http://www.programcreek.com/2013/01/leetcode-longest-consecutive-sequence-java/
#
# Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
# For example, given [100, 4, 200, 1, 3, 2], the longest consecutive elements sequence should be [1, 2, 3, 4]. Its length is 4.
# Your algorithm should run in O(n) complexity.

def longestConsecutiveSequence(nums):

    lookup = set(nums)
    result = 0

    for n in nums:

        curMax = 1
        below = n - 1
        while(below in lookup):
            curMax += 1
            lookup.remove(below)
            below -= 1

        above = n + 1
        while(above in lookup):
            curMax += 1
            lookup.remove(above)
            above += 1

        result = max(result, curMax)

    return result

def main():
    t1 = [100, 4, 200, 1, 3, 2]
    t2 = [100, 2, 99, 103, 88, 101, 102, 97, 3, 98, 1, 45, 46, 76, 47]
    t3 = []
    t4 = [1]

    print longestConsecutiveSequence(t1) # 4
    print longestConsecutiveSequence(t2) # 7
    print longestConsecutiveSequence(t3) # 0
    print longestConsecutiveSequence(t4) # 1

if __name__ == "__main__":
    main()
