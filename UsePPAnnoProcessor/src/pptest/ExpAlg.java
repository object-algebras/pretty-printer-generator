package pptest;

import noa.annos.Syntax;
import anno.PP;

@PP
public interface ExpAlg<E> {
    @Syntax("exp = exp '+' exp")
	E add(E l, E r);

	// Refer to tokens (defined in Tokens.java)
    @Syntax("exp = NUM")
	E lit(int n);
}

class PPExpAlg implements ExpAlg<String> {

    public String add(String l, String r) {
	return "add";
    }

    public String lit(int n) {
	return "lit";
    }
}