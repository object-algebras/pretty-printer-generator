package pptest;

import ppgen.PPExpAlg;
import anno.*;

/*
 class PPExpAlg implements ExpAlg<String> {

 public String add(String l, String r) {
 return l + "+" + r;
 }

 public String mul(String l, String r) {
 return l + "*" + r;
 }

 public String lit(int n) {
 return "" + n;
 }
 }
 */

public class Main {
    static <E> E make(ExpAlg<E> alg) {
	// return alg.add(alg.lit(3), alg.lit(4));
	return alg.add(alg.lit(3), alg.mul(alg.lit(4), alg.lit(5)));
    }

    public static void main(String[] args) {
	String e1pp = make(new PPExpAlg());
	System.out.println(e1pp);
    }
}