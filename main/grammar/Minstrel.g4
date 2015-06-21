// First draft of simplest form of grammar: only allows a single file. Uses explicit end-of-statement symbol

grammar Minstrel;

// Lexer rules

fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];
SPACE: [ \t\r\n]+ -> skip;

NUMBER_LITERAL: DIGIT+('.'DIGIT+)?;
STRING_LITERAL: '"' ~["]* '"';
TERMINATOR: ';';

// Keywords
IS: 'is';

IDENTIFIER: LETTER (LETTER | DIGIT)* ;

// Parser rules

program: (statement TERMINATOR)*;

name: IDENTIFIER;
type: IDENTIFIER;

argument_list: '[' (expression (',' expression)*)? ']';

statement: expression                                           # expression_statement
         | type name IS expression                              # declaration_statement
         ;

expression: '(' expression ')'                                                           # parenthesised_expression
          | NUMBER_LITERAL                                                               # number_literal
          | STRING_LITERAL                                                               # string_literal
          | IDENTIFIER	                                                                 # variable
          | expression argument_list                                                     # function_call
          | expression '+' expression                                                    # plus_expression
          ;
          