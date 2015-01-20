#!/usr/bin/python
#
# http://www.programcreek.com/2012/12/leetcode-median-of-two-sorted-arrays-java/
# There are two sorted arrays A and B of size m and n respectively.
# Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
#
# This problem can be converted to the problem of finding kth element, k is (A's length + B' Length)/2.
# If any of the two arrays is empty, then the kth element is the non-empty array's kth element.
# If k == 0, the kth element is the first element of A or B.
# For normal cases(all other cases), we need to move the pointer at the pace of half of an array length.

def findKth(a, b, k, aStart, aEnd, bStart, bEnd):

    aLen = aEnd - aStart + 1
    bLen = bEnd - bStart + 1

    if aLen == 0:
        return b[bStart + k]
    if bLen == 0:
        return a[aStart + k]
    if k == 0:
        if a[aStart] < b[bStart]:
            return a[aStart]
        else:
            return b[bStart]

    aMid = (k * aLen) / (aLen + bLen) # a's k count
    bMid = k - aMid - 1 # b's k count

    aMid = aMid + aStart
    bMid = bMid + bStart

    if (a[aMid] > b[bMid]):
        k = k - (bMid - bStart + 1)
        aEnd = aMid
        bStart = bMid + 1
    else:
        k = k - (aMid - aStart + 1)
        bEnd = bMid
        aStart = aMid + 1

    return findKth(a, b, k, aStart, aEnd, bStart, bEnd)

def findMedian(a, b):
    aLen = len(a)
    bLen = len(b)
    mid = (aLen + bLen)/2

    if (aLen + bLen) % 2 != 0:
        return findKth(a, b, mid, 0, aLen - 1, 0, bLen - 1)
    else:
        return (findKth(a, b, mid, 0, aLen - 1, 0, bLen - 1) + findKth(a, b, mid - 1, 0, aLen - 1, 0, bLen - 1))/2.0

def main():
    t1 = findMedian([1, 5, 6, 7], [2, 3, 4])
    t2 = findMedian([1, 5, 6, 7], [2, 3, 4, 9])
    t3 = findMedian([-15, -6, 0, 3], [1, 2, 8])
    t4 = findMedian([], [3, 4, 5])

    print t1
    print t2
    print t3
    print t4

if __name__ == "__main__":
    main()
