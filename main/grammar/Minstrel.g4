// First draft of simplest form of grammar: only allows a single file. Uses explicit end-of-statement symbol

grammar Minstrel;

// Lexer rules

fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];
SPACE: [ \t\r]+ -> skip;
NEWLINE: [\n];

NUMBER_LITERAL: DIGIT+('.'DIGIT+)?;
STRING_LITERAL: '"' ~["]* '"';

// Keywords
IS: 'is';

IDENTIFIER: LETTER (LETTER | DIGIT)* ;

// Parser rules

program: statement*;

name: IDENTIFIER;
type: IDENTIFIER;

terminator: NEWLINE | EOF;

modifier: name;

argument_list: '[' (expression (',' expression)*)? ']';

statement: expression terminator                                           # expression_statement
         | type name IS expression terminator                              # declaration_statement
         ;

expression: '(' expression ')'                                                           # parenthesised_expression
          | NUMBER_LITERAL                                                               # number_literal
          | STRING_LITERAL                                                               # string_literal
          | IDENTIFIER	                                                                 # variable
          | expression argument_list                                                     # function_call
          | expression '+' expression                                                    # plus_expression
          ;
          