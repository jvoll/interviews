#!/usr/bin/python

class TokenType:
    #Operator, Num = range(2)
    Operator = "op"
    Num = "num"

class OperatorType:
    #LParen, RParen, Plus, Minus, Multiply, Divide = range(6)
    LParen = "("
    RParen = ")"
    Add = "+"
    Subtract = "-"
    Multiply = "*"
    Divide = "/"

    @staticmethod
    def opHierarchy(opType):
        if opType == OperatorType.LParen or opType == OperatorType.RParen:
            return 0
        # note: leaving 2 for exponents if we ever do that
        elif opType == OperatorType.Multiply or opType == OperatorType.Divide:
            return 2
        elif opType == OperatorType.Add or opType == OperatorType.Subtract:
            return 3
        else:
            raise Exception("Invalid operator type: " + str(opType))

class Token:

    def isOperator(self):
        return self.tokenType == TokenType.Operator

    def isOperand(self):
        return self.tokenType == TokenType.Num

    # given a string expression, split into an array of tokens
    # parses out (ignores) any garbage input
    # currently we only accept (, ), +, -, *, /, 0-9
    @staticmethod
    def tokenize(exprStr):
        #splitString = exprStr.split(' ')
        tokenList = []
        numBuffer = ''

        for t in exprStr:
            if Token.__isInt(t):
                numBuffer = numBuffer + t
            else:
                # append int value of number buffer and clear the buffer
                if len(numBuffer) > 0:
                    tokenList.append(Token(TokenType.Num, int(numBuffer)))
                    numBuffer = ''

                if t == '(':
                    tokenList.append(Token(TokenType.Operator, OperatorType.LParen))
                elif t == ')':
                    tokenList.append(Token(TokenType.Operator, OperatorType.RParen))
                elif t == '+':
                    tokenList.append(Token(TokenType.Operator, OperatorType.Add))
                elif t == '-':
                    tokenList.append(Token(TokenType.Operator, OperatorType.Subtract))
                elif t == '*':
                    tokenList.append(Token(TokenType.Operator, OperatorType.Multiply))
                elif t == '/':
                    tokenList.append(Token(TokenType.Operator, OperatorType.Divide))
                elif t != ' ':
                    ## debug help
                    print "Cannot parse token: ", t

        if len(numBuffer) > 0:
            tokenList.append(Token(TokenType.Num, int(numBuffer)))

        return tokenList

    @staticmethod
    def __isInt(inString):
        try:
           val = int(inString)
        except ValueError:
            return False
        return True

    def __repr__(self):
        #if self.tokenType == TokenType.Operator:
        #    return "Token(" +  str(self.tokenType) + "," + str(self.val) + ")"
        #else:
        return str(self.val)

    # val will be an OperatorType for tokenType == Opeartor, integer for TokenType of Num
    def __init__(self, tokenType, val):
        self.tokenType = tokenType
        self.val = val

def main():
    t1 = "2 + 2"
    t2 = "(4 + 7) * 8"
    t3 = "((5 - 2) * 6)/9"
    t4 = "(((50 - 2) * 6)/9)"

    #stuff = Token.tokenize(t1)
    #for s in stuff:
    #    if s.isOperand():
    #        print "yeah"
    #    else:
    #        print "nah"

    print "in: ", t1, " out: ", Token.tokenize(t1)
    print "in: ", t2, " out: ", Token.tokenize(t2)
    print "in: ", t3, " out: ", Token.tokenize(t3)
    print "in: ", t4, " out: ", Token.tokenize(t4)

if __name__ == "__main__":
    main()
