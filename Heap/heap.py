#!/usr/bin/python
#
# author: jvoll
#
# A simple max heap
#
class Heap:

    def __init__(self, array=[]):
        self.heap = [0]
        for i in array:
            self.insert(i)

    def peekMax(self):
        if len(self.heap) > 1:
            return self.heap[1]
        else:
            raise Exception

    def insert(self, elem):
        self.heap.append(elem)
        self.bubbleUp(len(self.heap) - 1)

    def bubbleUp(self, index):
        if index < 2:
            return

        parentIndex = self.parentIndex(index)
        if self.heap[index] > self.heap[parentIndex]:
            self.swap(index, parentIndex)
            self.bubbleUp(parentIndex)

    def bubbleDown(self, index):

        if index < len(self.heap):
            leftIndex = self.leftChildIndex(index)
            rightIndex = self.rightChildIndex(index)
            cur = self.heap[index]
            leftChild = None
            rightChild = None
            if self.validNode(leftIndex):
                leftChild = self.heap[leftIndex]
            if self.validNode(rightIndex):
                rightChild = self.heap[rightIndex]

            # check if we should do this bubble (current node must be smaller than at least one child)
            if (leftChild and cur < leftChild) or (rightChild and cur < rightChild):
                swapIndex = leftIndex
                if (leftChild and rightChild):
                    # we need to swap with bigger child
                    if (rightChild > leftChild):
                        swapIndex = rightIndex

                self.swap(index, swapIndex)
                self.bubbleDown(swapIndex)

    def deleteMax(self):
        self.heap[1] = self.heap[-1]
        del(self.heap[-1])
        self.bubbleDown(1)

    # determine parent index for a given child index
    def parentIndex(self, child):
        return child/2

    def leftChildIndex(self, parent):
        return parent * 2

    def rightChildIndex(self, parent):
        return (parent * 2) + 1

    def swap(self, i, j):
        t = self.heap[i]
        self.heap[i] = self.heap[j]
        self.heap[j] = t

    # quick check to determine if a given index is in the heap
    def validNode(self, index):
        return index < len(self.heap)

    def prettyPrint(self):
        row = ""
        for i in range(1, len(self.heap)):
            if self.isPowerOfTwo(i):
                print row
                row = str(self.heap[i])
            else:
                row = row + ' ' + str(self.heap[i])
        print row

    def isPowerOfTwo(self, num):
        return num != 0 and ((num & (num - 1)) == 0)

def main():
    test = [2, 3, 1, 8, 4, 9, 10, -5, 6]
    print "input: ", test
    h = Heap(test)
    print "max: ", h.peekMax()
    h.prettyPrint()

    print "deleting max"
    h.deleteMax()
    h.prettyPrint()

    print "deleting max"
    h.deleteMax()
    h.prettyPrint()

    print "deleting max"
    h.deleteMax()
    h.prettyPrint()

    print "deleting max"
    h.deleteMax()
    h.prettyPrint()

if __name__ == "__main__":
    main()
