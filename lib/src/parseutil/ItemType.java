package lib.src.parseutil;

public enum ItemType
{
    //NON TERMINALS//
PROGRAM, // -> .SLIST
SLIST, // -> .STMNT SLIST | -> .e
STMNT, // -> .VAR | .ASS | .IF | .WHILE | .REPEAT | .FUNCDEC | .FUNCCALL | .RETURN | .SHOWSTMT | .GETSTMT
VAR, // -> ."let" TYPE ID "be" EXP ";"
ASS, // -> .ID "be" EXP ";"
IF, // -> ."check""("EXP")""{"SLIST"}" ELSE
ELSE, // -> ."otherwise""{"SLIST"}" | -> .ε
WHILE, // -> ."while""("EXP")""{"SLIST"}"
REPEAT, // -> ."repeat"(""let" TYPE ID "be" EXP;EXP;ID "be" EXP)"{"SLIST"}"
FUNCDEC, // -> ."task" TYPE ID"("PARAM")""{"SLIST"}"
PARAM, // -> .TYPE ID PARAM'
PARAM_PRIME, // -> .","PARAM | -> .ε
FUNCCALL, // -> .ID"("ARG")"
ARG, // -> .EXP ARG'
ARG_PRIME, // -> .","ARG | -> .ε
RETURN, // -> ."give"EXP";"
SHOWSTMT, // -> ."show""("EXP")"";"
GETSTMT, // -> ."get"EXP";"
EXP, // -> .LOG EXP'
EXP_PRIME, // -> ."or" LOG EXP' | -> .ε
LOG, // -> .REL LOG'
LOG_PRIME, // -> ."both" REL LOG' | -> .ε
REL, // -> .ADD REL'
REL_PRIME, // -> .REL ADD REL' | -> .ε
ADD, // -> .MULTI ADD'
ADD_PRIME, // -> .ADDOP MULTI ADD' | -> .ε
ADDOP, // -> ."plus" | ."minus"
MULTI, // -> .UNARY MULTI'
MULTI_PRIME, // -> .MULTIOP UNARY MULTI' | -> .ε
MULTIOP, // -> ."times" | ."over" | ."mod"
UNARY, // -> ."not" PRIMARY | .PRIMARY
PRIMARY, // -> .LITERAL | .ID | .FUNCCALL | ."("EXP")"
TYPE, // -> ."INT" | ."FLOAT" | ."STRING" | ."BOOL"
LITERAL, // -> .INT | .FLOAT | .STRING | .BOOL
INT, // -> .[0-9]+
FLOAT, // -> .INT "." INT
STRING, // -> .".*"
BOOL, // -> ."TRUE" | ."FALSE"

    //TERMINALS//
ID, // -> .[a-zA-Z][a-zA-Z0-9]*
}
