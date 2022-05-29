package org.example.toylanguage;

import org.example.toylanguage.token.Token;
import org.example.toylanguage.token.TokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexicalParserTest {

    @Test
    public void testPrint() {
        String source = "print \"Hello World\"";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(2, tokens.size());

        int count = 0;
        assertEquals(TokenType.Keyword, tokens.get(count).getType());
        assertEquals("print", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Text, tokens.get(++count).getType());
        assertEquals("Hello World", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());
    }

    @Test
    public void testInput() {

        String source = "input a";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(2, tokens.size());

        int count = 0;
        assertEquals(TokenType.Keyword, tokens.get(count).getType());
        assertEquals("input", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("a", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());
    }

    @Test
    public void testAssignment() {

        String source = "a = 2 + 5";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(5, tokens.size());

        int count = 0;
        assertEquals(TokenType.Variable, tokens.get(count).getType());
        assertEquals("a", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("=", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Numeric, tokens.get(++count).getType());
        assertEquals("2", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("+", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Numeric, tokens.get(++count).getType());
        assertEquals("5", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());
    }

    @Test
    public void testCondition() {

        String source = "if a > 1 then\n" +
                "    print \"a is greater than 1\"\n" +
                "end";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(10, tokens.size());

        int count = 0;
        assertEquals(TokenType.Keyword, tokens.get(count).getType());
        assertEquals("if", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("a", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals(">", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Numeric, tokens.get(++count).getType());
        assertEquals("1", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Keyword, tokens.get(++count).getType());
        assertEquals("then", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.LineBreak, tokens.get(++count).getType());
        assertEquals("\n", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Keyword, tokens.get(++count).getType());
        assertEquals("print", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Text, tokens.get(++count).getType());
        assertEquals("a is greater than 1", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.LineBreak, tokens.get(++count).getType());
        assertEquals("\n", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Keyword, tokens.get(++count).getType());
        assertEquals("end", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());
    }

    @Test
    public void testObject() {

        String source = "struct Person [ name, age ]\n" +
                        "person = new Person[\"Robert\", 25]\n" +
                        "print person :: name + \" is \" + person :: age + \" years old\"";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(30, tokens.size());

        int count = 0;
        assertEquals(TokenType.Keyword, tokens.get(count).getType());
        assertEquals("struct", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("Person", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.GroupDivider, tokens.get(++count).getType());
        assertEquals("[", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("name", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.GroupDivider, tokens.get(++count).getType());
        assertEquals(",", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("age", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.GroupDivider, tokens.get(++count).getType());
        assertEquals("]", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.LineBreak, tokens.get(++count).getType());
        assertEquals("\n", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("person", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("=", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("new", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("Person", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.GroupDivider, tokens.get(++count).getType());
        assertEquals("[", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Text, tokens.get(++count).getType());
        assertEquals("Robert", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.GroupDivider, tokens.get(++count).getType());
        assertEquals(",", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Numeric, tokens.get(++count).getType());
        assertEquals("25", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.GroupDivider, tokens.get(++count).getType());
        assertEquals("]", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.LineBreak, tokens.get(++count).getType());
        assertEquals("\n", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Keyword, tokens.get(++count).getType());
        assertEquals("print", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("person", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("::", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("name", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("+", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Text, tokens.get(++count).getType());
        assertEquals(" is ", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("+", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("person", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("::", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("age", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("+", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());

        assertEquals(TokenType.Text, tokens.get(++count).getType());
        assertEquals(" years old", tokens.get(count).getValue());
        assertEquals(3, tokens.get(count).getRow());
    }

    @Test
    public void testComment() {
        String source = "# a = 5\n" +
                        "a = 5 # a is equal to 5";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(6, tokens.size());

        int count = 0;
        assertEquals(TokenType.Comment, tokens.get(count).getType());
        assertEquals("# a = 5", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.LineBreak, tokens.get(++count).getType());
        assertEquals("\n", tokens.get(count).getValue());
        assertEquals(1, tokens.get(count).getRow());

        assertEquals(TokenType.Variable, tokens.get(++count).getType());
        assertEquals("a", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Operator, tokens.get(++count).getType());
        assertEquals("=", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Numeric, tokens.get(++count).getType());
        assertEquals("5", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());

        assertEquals(TokenType.Comment, tokens.get(++count).getType());
        assertEquals("# a is equal to 5", tokens.get(count).getValue());
        assertEquals(2, tokens.get(count).getRow());
    }

}