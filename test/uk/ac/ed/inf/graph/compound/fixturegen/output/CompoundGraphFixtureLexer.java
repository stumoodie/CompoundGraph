// $ANTLR 3.4 /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g 2012-06-15 23:32:13

package uk.ac.ed.inf.graph.compound.fixturegen.output;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class CompoundGraphFixtureLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int COMMENT=4;
    public static final int EDGE=5;
    public static final int EOL=6;
    public static final int ESC_SEQ=7;
    public static final int HEX_DIGIT=8;
    public static final int ID=9;
    public static final int NAME=10;
    public static final int NODE=11;
    public static final int OCTAL_ESC=12;
    public static final int STRING=13;
    public static final int UNICODE_ESC=14;
    public static final int WS=15;

    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public CompoundGraphFixtureLexer() {} 
    public CompoundGraphFixtureLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CompoundGraphFixtureLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g"; }

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:11:7: ( '(' )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:11:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:12:7: ( ')' )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:12:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "NAME"
    public final void mNAME() throws RecognitionException {
        try {
            int _type = NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:49:6: ( 'name' )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:49:8: 'name'
            {
            match("name"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NAME"

    // $ANTLR start "NODE"
    public final void mNODE() throws RecognitionException {
        try {
            int _type = NODE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:52:6: ( 'n' )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:52:8: 'n'
            {
            match('n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NODE"

    // $ANTLR start "EDGE"
    public final void mEDGE() throws RecognitionException {
        try {
            int _type = EDGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:55:6: ( 'e' )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:55:8: 'e'
            {
            match('e'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EDGE"

    // $ANTLR start "EOL"
    public final void mEOL() throws RecognitionException {
        try {
            int _type = EOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:58:5: ( ( '\\r' )? '\\n' )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:58:7: ( '\\r' )? '\\n'
            {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:58:7: ( '\\r' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='\r') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:58:7: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EOL"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:62:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:62:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:62:31: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:66:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:66:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 



            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:66:14: (~ ( '\\n' | '\\r' ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '\u0000' && LA3_0 <= '\t')||(LA3_0 >= '\u000B' && LA3_0 <= '\f')||(LA3_0 >= '\u000E' && LA3_0 <= '\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:66:28: ( '\\r' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:66:28: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:69:4: ( ( ' ' | '\\t' | '\\\\' EOL ) )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:69:6: ( ' ' | '\\t' | '\\\\' EOL )
            {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:69:6: ( ' ' | '\\t' | '\\\\' EOL )
            int alt5=3;
            switch ( input.LA(1) ) {
            case ' ':
                {
                alt5=1;
                }
                break;
            case '\t':
                {
                alt5=2;
                }
                break;
            case '\\':
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }

            switch (alt5) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:69:7: ' '
                    {
                    match(' '); 

                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:69:11: '\\t'
                    {
                    match('\t'); 

                    }
                    break;
                case 3 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:69:16: '\\\\' EOL
                    {
                    match('\\'); 

                    mEOL(); 


                    }
                    break;

            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:73:5: ( '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:73:8: '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 

            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:73:12: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
            loop6:
            do {
                int alt6=3;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\\') ) {
                    alt6=1;
                }
                else if ( ((LA6_0 >= '\u0000' && LA6_0 <= '!')||(LA6_0 >= '#' && LA6_0 <= '[')||(LA6_0 >= ']' && LA6_0 <= '\uFFFF')) ) {
                    alt6=2;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:73:14: ESC_SEQ
            	    {
            	    mESC_SEQ(); 


            	    }
            	    break;
            	case 2 :
            	    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:73:24: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:78:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "ESC_SEQ"
    public final void mESC_SEQ() throws RecognitionException {
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:82:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
            int alt7=3;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt7=1;
                    }
                    break;
                case 'u':
                    {
                    alt7=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt7=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;

                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }
            switch (alt7) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:82:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 

                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:83:9: UNICODE_ESC
                    {
                    mUNICODE_ESC(); 


                    }
                    break;
                case 3 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:84:9: OCTAL_ESC
                    {
                    mOCTAL_ESC(); 


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ESC_SEQ"

    // $ANTLR start "OCTAL_ESC"
    public final void mOCTAL_ESC() throws RecognitionException {
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:89:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt8=3;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='\\') ) {
                int LA8_1 = input.LA(2);

                if ( ((LA8_1 >= '0' && LA8_1 <= '3')) ) {
                    int LA8_2 = input.LA(3);

                    if ( ((LA8_2 >= '0' && LA8_2 <= '7')) ) {
                        int LA8_4 = input.LA(4);

                        if ( ((LA8_4 >= '0' && LA8_4 <= '7')) ) {
                            alt8=1;
                        }
                        else {
                            alt8=2;
                        }
                    }
                    else {
                        alt8=3;
                    }
                }
                else if ( ((LA8_1 >= '4' && LA8_1 <= '7')) ) {
                    int LA8_3 = input.LA(3);

                    if ( ((LA8_3 >= '0' && LA8_3 <= '7')) ) {
                        alt8=2;
                    }
                    else {
                        alt8=3;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }
            switch (alt8) {
                case 1 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:89:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:90:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 3 :
                    // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:91:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OCTAL_ESC"

    // $ANTLR start "UNICODE_ESC"
    public final void mUNICODE_ESC() throws RecognitionException {
        try {
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:96:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:96:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {
            match('\\'); 

            match('u'); 

            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNICODE_ESC"

    public void mTokens() throws RecognitionException {
        // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:8: ( T__16 | T__17 | NAME | NODE | EDGE | EOL | ID | COMMENT | WS | STRING )
        int alt9=10;
        switch ( input.LA(1) ) {
        case '(':
            {
            alt9=1;
            }
            break;
        case ')':
            {
            alt9=2;
            }
            break;
        case 'n':
            {
            switch ( input.LA(2) ) {
            case 'a':
                {
                int LA9_10 = input.LA(3);

                if ( (LA9_10=='m') ) {
                    int LA9_13 = input.LA(4);

                    if ( (LA9_13=='e') ) {
                        int LA9_14 = input.LA(5);

                        if ( ((LA9_14 >= '0' && LA9_14 <= '9')||(LA9_14 >= 'A' && LA9_14 <= 'Z')||LA9_14=='_'||(LA9_14 >= 'a' && LA9_14 <= 'z')) ) {
                            alt9=7;
                        }
                        else {
                            alt9=3;
                        }
                    }
                    else {
                        alt9=7;
                    }
                }
                else {
                    alt9=7;
                }
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '_':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
                {
                alt9=7;
                }
                break;
            default:
                alt9=4;
            }

            }
            break;
        case 'e':
            {
            int LA9_4 = input.LA(2);

            if ( ((LA9_4 >= '0' && LA9_4 <= '9')||(LA9_4 >= 'A' && LA9_4 <= 'Z')||LA9_4=='_'||(LA9_4 >= 'a' && LA9_4 <= 'z')) ) {
                alt9=7;
            }
            else {
                alt9=5;
            }
            }
            break;
        case '\n':
        case '\r':
            {
            alt9=6;
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt9=7;
            }
            break;
        case '/':
            {
            alt9=8;
            }
            break;
        case '\t':
        case ' ':
        case '\\':
            {
            alt9=9;
            }
            break;
        case '\"':
            {
            alt9=10;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 9, 0, input);

            throw nvae;

        }

        switch (alt9) {
            case 1 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:10: T__16
                {
                mT__16(); 


                }
                break;
            case 2 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:16: T__17
                {
                mT__17(); 


                }
                break;
            case 3 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:22: NAME
                {
                mNAME(); 


                }
                break;
            case 4 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:27: NODE
                {
                mNODE(); 


                }
                break;
            case 5 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:32: EDGE
                {
                mEDGE(); 


                }
                break;
            case 6 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:37: EOL
                {
                mEOL(); 


                }
                break;
            case 7 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:41: ID
                {
                mID(); 


                }
                break;
            case 8 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:44: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 9 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:52: WS
                {
                mWS(); 


                }
                break;
            case 10 :
                // /Users/smoodie/git/CompoundGraph/test/uk/ac/ed/inf/graph/compound/fixturegen/CompoundGraphFixture.g:1:55: STRING
                {
                mSTRING(); 


                }
                break;

        }

    }


 

}