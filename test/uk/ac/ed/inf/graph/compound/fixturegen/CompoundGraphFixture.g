grammar CompoundGraphFixture;

options{
	output = AST;
	language = Java;
}

@lexer::header{
package uk.ac.ed.inf.graph.compound.fixturegen.output;
}

@parser::header{
package uk.ac.ed.inf.graph.compound.fixturegen.output;
}


fixture	:	fixture_name defns EOF
		-> fixture_name defns
	;
	
fixture_name
	:	NAME ID EOL
		-> ^(NAME ID)
	;
	
defns
	:	def+ EOL
		-> def+
	;

def	:	node
	|	edge
	;


node	:	NODE '(' ID STRING? def+ ')'
		-> ^(NODE ID STRING def+ )
	|	NODE '(' ID STRING? ')'
		-> ^(NODE ID STRING)
	;

edge
	:	EDGE '(' ID STRING? ID ID def+ ')'
		-> ^(EDGE ^(ID STRING?) ID ID  def+)
	|	EDGE '(' ID STRING? ID ID ')'
		-> ^(EDGE ^(ID STRING?) ID ID)
	;

NAME	:	'name'
	;
	
NODE	:	'n'
	;

EDGE	:	'e'
	;

EOL	:	'\r'? '\n'
	;


ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

WS	:	(' '|'\t'|'\\' EOL) {$channel=HIDDEN;}
	;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
