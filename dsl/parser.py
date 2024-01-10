from pyparsing import Word, nums, alphas, Suppress, Literal, Forward, Group, ZeroOrMore

integer = Word(nums).setParseAction(lambda t: int(t[0]))
variable = Word(alphas)

operand = integer | variable
expression = Forward()

factor = operand + ZeroOrMore((Literal('*') | Literal('/')) + operand)
term = factor + ZeroOrMore((Literal('+') | Literal('-')) + factor)
expression <<= term


def parse_expression(input_string):
    return expression.parseString(input_string, parseAll=True)

example1 = "3 + 5 * 2"
example2 = "a + 2 * b / 4"

parsed1 = parse_expression(example1)
parsed2 = parse_expression(example2)

print(f"Parsed expression 1: {parsed1}")
print(f"Parsed expression 2: {parsed2}")
