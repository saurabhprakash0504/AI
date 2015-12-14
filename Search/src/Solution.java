import java.util.*;

public class Solution {

	public static List<Intervals> merge(List<Intervals> x) {
		// Start typing your Java solution below
		// DO NOT write main() function
		if (x.size() == 0)
			return x;
		if (x.size() == 1)
			return x;

		Collections.sort(x, new IntervalComparator());

		Intervals first = x.get(0);
		int start = first.start;
		int end = first.end;

		List<Intervals> result = new ArrayList<Intervals>();

		for (int i = 1; i < x.size(); i++) {
			Intervals current = x.get(i);
			if (current.start <= end) {
				end = Math.max(current.end, end);
			} else {
				result.add(new Intervals(start, end));
				start = current.start;
				end = current.end;
			}

		}

		result.add(new Intervals(start, end));

		return result;

	}

	public static void main(String[] args) {

		List<Intervals> x = new ArrayList<Intervals>();
		Intervals a = new Intervals(0, 5);
		Intervals b = new Intervals(2, 7);
		x.add(b);
		x.add(a);
		List<Intervals> y = merge(x);
		System.out.println(y.get(0).start + "--" + y.get(0).end);
	}
}

class IntervalComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		Intervals i1 = (Intervals) o1;
		Intervals i2 = (Intervals) o2;
		return i1.start - i2.start;
	}
}