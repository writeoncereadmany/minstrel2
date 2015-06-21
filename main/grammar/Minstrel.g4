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
ARROW: '->';

IDENTIFIER: LETTER (LETTER | DIGIT)* ;

// Parser rules

program: statement*;

name: IDENTIFIER;
type: IDENTIFIER;

parameter: type name;

argument_list: '[' (expression (',' expression)*)? ']';
parameter_list: '[' (parameter (',' parameter)*)? ']';



statement: expression_statement                                 # standalone_expression_statement
         | declaration                                          # declaration_statement
         ;

expression_statement: expression TERMINATOR;

declaration: type name IS expression TERMINATOR                   # variable_declaration
           | FUNCTION name parameter_list body                    # function_declaration
           ;

body: '{' statement* '}'                                        # block
    | ARROW expression_statement                                # expression_body
    ;

expression: '(' expression ')'                                                           # parenthesised_expression
          | NUMBER_LITERAL                                                               # number_literal
          | STRING_LITERAL                                                               # string_literal
          | IDENTIFIER	                                                                 # variable
          | expression argument_list                                                     # function_call
          | expression '+' expression                                                    # plus_expression
          ;
          