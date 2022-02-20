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
        assertEquals(TokenType.Keyword, tokens.get(0).getType());
        assertEquals("print", tokens.get(0).getValue());

        assertEquals(TokenType.Text, tokens.get(1).getType());
        assertEquals("Hello World", tokens.get(1).getValue());
    }

    @Test
    public void testInput() {

        String source = "input a";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(2, tokens.size());
        assertEquals(TokenType.Keyword, tokens.get(0).getType());
        assertEquals("input", tokens.get(0).getValue());

        assertEquals(TokenType.Variable, tokens.get(1).getType());
        assertEquals("a", tokens.get(1).getValue());
    }

    @Test
    public void testAssignment() {

        String source = "a = 2 + 5";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(5, tokens.size());

        assertEquals(TokenType.Variable, tokens.get(0).getType());
        assertEquals("a", tokens.get(0).getValue());

        assertEquals(TokenType.Operator, tokens.get(1).getType());
        assertEquals("=", tokens.get(1).getValue());

        assertEquals(TokenType.Numeric, tokens.get(2).getType());
        assertEquals("2", tokens.get(2).getValue());

        assertEquals(TokenType.Operator, tokens.get(3).getType());
        assertEquals("+", tokens.get(3).getValue());

        assertEquals(TokenType.Numeric, tokens.get(4).getType());
        assertEquals("5", tokens.get(4).getValue());
    }

    @Test
    public void testCondition() {

        String source = "if a > 1 then\n" +
                        "    print \"a is greater than 1\"\n" +
                        "end";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(8, tokens.size());

        assertEquals(TokenType.Keyword, tokens.get(0).getType());
        assertEquals("if", tokens.get(0).getValue());

        assertEquals(TokenType.Variable, tokens.get(1).getType());
        assertEquals("a", tokens.get(1).getValue());

        assertEquals(TokenType.Operator, tokens.get(2).getType());
        assertEquals(">", tokens.get(2).getValue());

        assertEquals(TokenType.Numeric, tokens.get(3).getType());
        assertEquals("1", tokens.get(3).getValue());

        assertEquals(TokenType.Keyword, tokens.get(4).getType());
        assertEquals("then", tokens.get(4).getValue());

        assertEquals(TokenType.Keyword, tokens.get(5).getType());
        assertEquals("print", tokens.get(5).getValue());

        assertEquals(TokenType.Text, tokens.get(6).getType());
        assertEquals("a is greater than 1", tokens.get(6).getValue());

        assertEquals(TokenType.Keyword, tokens.get(7).getType());
        assertEquals("end", tokens.get(7).getValue());
    }

    @Test
    public void testObject() {

        String source = "struct Person\n" +
                        "    arg name\n" +
                        "    arg age\n" +
                        "end\n\n" +
                        "person = new Person[\"Alexander\" 25]\n" +
                        "print person :: name\n" +
                        "print person :: age";
        LexicalParser parser = new LexicalParser(source);
        List<Token> tokens = parser.parse();

        assertEquals(23, tokens.size());
        assertEquals(TokenType.Keyword, tokens.get(0).getType());
        assertEquals("struct", tokens.get(0).getValue());

        assertEquals(TokenType.Variable, tokens.get(1).getType());
        assertEquals("Person", tokens.get(1).getValue());

        assertEquals(TokenType.Keyword, tokens.get(2).getType());
        assertEquals("arg", tokens.get(2).getValue());

        assertEquals(TokenType.Variable, tokens.get(3).getType());
        assertEquals("name", tokens.get(3).getValue());

        assertEquals(TokenType.Keyword, tokens.get(4).getType());
        assertEquals("arg", tokens.get(4).getValue());

        assertEquals(TokenType.Variable, tokens.get(5).getType());
        assertEquals("age", tokens.get(5).getValue());

        assertEquals(TokenType.Keyword, tokens.get(6).getType());
        assertEquals("end", tokens.get(6).getValue());

        assertEquals(TokenType.Variable, tokens.get(7).getType());
        assertEquals("person", tokens.get(7).getValue());

        assertEquals(TokenType.Operator, tokens.get(8).getType());
        assertEquals("=", tokens.get(8).getValue());

        assertEquals(TokenType.Keyword, tokens.get(9).getType());
        assertEquals("new", tokens.get(9).getValue());

        assertEquals(TokenType.Variable, tokens.get(10).getType());
        assertEquals("Person", tokens.get(10).getValue());

        assertEquals(TokenType.GroupDivider, tokens.get(11).getType());
        assertEquals("[", tokens.get(11).getValue());

        assertEquals(TokenType.Text, tokens.get(12).getType());
        assertEquals("Alexander", tokens.get(12).getValue());

        assertEquals(TokenType.Numeric, tokens.get(13).getType());
        assertEquals("25", tokens.get(13).getValue());

        assertEquals(TokenType.GroupDivider, tokens.get(14).getType());
        assertEquals("]", tokens.get(14).getValue());

        assertEquals(TokenType.Keyword, tokens.get(15).getType());
        assertEquals("print", tokens.get(15).getValue());

        assertEquals(TokenType.Variable, tokens.get(16).getType());
        assertEquals("person", tokens.get(16).getValue());

        assertEquals(TokenType.Operator, tokens.get(17).getType());
        assertEquals("::", tokens.get(17).getValue());

        assertEquals(TokenType.Variable, tokens.get(18).getType());
        assertEquals("name", tokens.get(18).getValue());

        assertEquals(TokenType.Keyword, tokens.get(19).getType());
        assertEquals("print", tokens.get(19).getValue());

        assertEquals(TokenType.Variable, tokens.get(20).getType());
        assertEquals("person", tokens.get(20).getValue());

        assertEquals(TokenType.Operator, tokens.get(21).getType());
        assertEquals("::", tokens.get(21).getValue());

        assertEquals(TokenType.Variable, tokens.get(22).getType());
        assertEquals("age", tokens.get(22).getValue());
    }

}