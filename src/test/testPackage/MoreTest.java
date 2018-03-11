package test.testPackage;
import java.util.*;

public class MoreTest {
		
		public static int add(int a, int b){
			int c = a+b;
			String wtf = "hey";
			System.out.println(wtf);
			Integer w = 4;
			return c;
		}
		
		public boolean equals(int i, int j) {
			return i == j;
		}
		
		private class Test extends MoreTest{
			@Override
			public boolean equals(int newI, int newJ) {
				return false;
			}
			
			public int fck(int x, int y){
				int t = x+y;
				return t;
			}
		}
		public static void main (String args[]) {
			int fin = add(1,2);
		}
		public static int d;
		public List<String> hello;
		public Map<String, Integer> sup;
}

