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
FUNCTION: 'function';

IDENTIFIER: LETTER (LETTER | DIGIT)* ;

// Parser rules

program: statement*;

name: IDENTIFIER;
type: IDENTIFIER;

parameter: type name;

argument_list: '[' (expression (',' expression)*)? ']';
parameter_list: '[' (parameter (',' parameter)*)? ']';

block: '{' statement* '}';

statement: expression TERMINATOR                                # expression_statement
         | declaration                                          # declaration_statement
         ;

declaration: type name IS expression TERMINATOR                   # variable_declaration
           | FUNCTION name parameter_list block                   # function_declaration
           ;

expression: '(' expression ')'                                                           # parenthesised_expression
          | NUMBER_LITERAL                                                               # number_literal
          | STRING_LITERAL                                                               # string_literal
          | IDENTIFIER	                                                                 # variable
          | expression argument_list                                                     # function_call
          | expression '+' expression                                                    # plus_expression
          ;
          