#!/usr/bin/python

# Calculator that converts infix notation input to RPN
# and gives the user back their result.

from token import TokenType
from token import OperatorType
from token import Token

class Calc:



    def infixToPostfix(self, expr):
        tokens = Token.tokenize(expr)
        operatorStack = []
        operandStack = []

        for token in tokens:
            if token.isOperand():
                operandStack.append(token)
            elif (token.val == OperatorType.LParen or len(operatorStack) < 1 or
                    OperatorType.opHierarchy(token.val) > OperatorType.opHierarchy(operatorStack[-1].val)):
                operatorStack.append(token)
            elif token.val == OperatorType.RParen:
                # pop off both stacks building postfix expressions until LParen is found
                # each postfix expression gets pushed back into the operand stack
                while (operatorStack[-1].val != OperatorType.LParen):
                    self.__buildSinglePostfixExpr(operatorStack, operandStack)

                # pop the LParen from the stack
                operatorStack.pop()
            elif OperatorType.opHierarchy(token.val) <= OperatorType.opHierarchy(operatorStack[-1].val):
                # Continue to pop operator and operand stack, building postfix
                # expressions until the stack is empty or until an operator at
                # the top of the operator stack has a lower hierarchy than that
                # of the token.
                while (len(operatorStack) > 0 and
                        OperatorType.opHierarchy(token.val) <= OperatorType.opHierarchy(operatorStack[-1].val)):
                    print 'operators', operatorStack
                    print 'operands', operandStack
                    self.__buildSinglePostfixExpr(operatorStack, operandStack)

                # push the lower prcendence operator onto stack
                operatorStack.append(token)

        # read in all tokens, pop stuff off the operator stack until it is empty (if it's not already)
        while (len(operatorStack) > 0):
            self.__buildSinglePostfixExpr(operatorStack, operandStack)

        return operandStack.pop()


    def __buildSinglePostfixExpr(self, operatorStack, operandStack):
        right = operandStack.pop()
        left = operandStack.pop()
        operator = operatorStack.pop()
        operand = str(left) + ' ' + str(right) + ' ' + str(operator)
        operandStack.append(operand)

    def __init__(self):
        print "Welcome to my calculator program"


def main():
    t1 = "2 + 2"
    t2 = "(4 + 7) * 8"
    t3 = "((5 - 2) * 6)/9"

    c = Calc()
    print "in: ", t1, " out: ", c.infixToPostfix(t1)
    print "in: ", t2, " out: ", c.infixToPostfix(t2)
    print "in: ", t3, " out: ", c.infixToPostfix(t3)

if __name__ == "__main__":
    main()
