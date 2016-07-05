#!/usr/bin/python
#
# Python implementation of a simple LRU cache
# http://www.programcreek.com/2013/03/leetcode-lru-cache-java/
#

class Node:

    def __init__(self, key, data, nextNode, prevNode):
        self.key = key
        self.data = data
        self.nextNode = nextNode
        self.prevNode = prevNode

class LRUCache:

    def __init__(self, capacity):
        self.capacity = capacity
        self.length = 0
        self.head = None
        self.tail = None
        self.lookup = {}

    def insert(self, key, value):
        if key in self.lookup:
            node = self.lookup[key]
            node.data = value
            removeNode(node)
            setHead(node)
        else:
            node = Node(key, value, None, None)

            if self.length < self.capacity:
                self.length += 1
            else:
                # TODO: should we remove the node from the map here or just have removeNode do it?
                removeNode(self.end)
                self.end = self.end.prevNode
                if self.end != None:
                    self.end.nextNode = None

            setHead(node)
            lookup[key] = node





