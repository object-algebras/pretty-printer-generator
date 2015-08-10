package pp;

import pptest.ExpAlg;

public class PPExpAlg implements ExpAlg<String> {

    public String add(String l, String r) {
	return l + " + " + r;
    }

    public String lit(int n) {
	return ((Integer) n).toString();
    }
}
