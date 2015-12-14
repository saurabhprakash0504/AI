import java.util.List;

public class Node {
	public String label = "";
	int id = 0;
	int pid = 0;
	String parentId;
	int depth = 0;
	int cost = 0;
	List<Intervals> offTime;
	int parentCost=0;

	public boolean visited = false;

	public Node(String l, String parentId, int depth, int cost, int id,
			int pid, List<Intervals> offTime) {
		this.label = l;
		this.id = id;
		this.pid = pid;
		this.parentId = parentId;
		this.depth = depth;
		this.cost = cost;
		this.offTime = offTime;
	}

	public Node(String l, String parentId, int cost, List<Intervals> offTime) {
		this.label = l;
		this.parentId = parentId;
		this.cost = cost;
		this.offTime = offTime;
	}

	public boolean isActive(int time) {
		// TODO Auto-generated method stub
		boolean active = true;
		time = time % 24;
		if (null != offTime) {
			for (Intervals i : this.offTime) {
				active = active && !intervallContains(i.start, i.end, time);
			}
		}
		return active;
	}

	private boolean intervallContains(int low, int high, int n) {
		return n >= low && n <= high;
	}

}
