#!/usr/bin/python

from decimal import *

class RPNCalc:

    # takes a list of tokens in reverse polish notation and evaluations the expression
    def rpn(self, tokens):
        operators = "+-*/"
        stack = []

        for token in tokens:
            if token in operators:
                n1 = Decimal(stack.pop())
                n2 = Decimal(stack.pop())
                if token == "+":
                    stack.append(str(n1 + n2))
                elif token == "-":
                    stack.append(str(n2 - n1))
                elif token == "*":
                    stack.append(str(n1 * n2))
                elif token == "/":
                    stack.append(str(n2/n1))
            else:
                stack.append(token)

        if len(stack) > 0:
            return stack.pop()
        else:
            raise Exception

def main():
    c = RPNCalc()
    print c.rpn(['1', '2', '+', '5', '*'])
    print c.rpn(['3', '2', '+', '5', '*', '1', '+', '8', '/'])

if __name__ == "__main__":
    main()
