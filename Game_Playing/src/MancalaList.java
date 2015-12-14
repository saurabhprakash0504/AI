class MancalaList {
	Pit data;
	MancalaList next;

	public MancalaList() {
		data = null;
		next = this;
	}

	public MancalaList(Pit value) {
		data = value;
		next = this;
	}

	public MancalaList InsertNext(Pit value) {
		MancalaList node = new MancalaList(value);
		if (this.next == this) // only one node in the circular list
		{
			// Easy to handle, after the two lines of executions,
			// there will be two nodes in the circular list
			node.next = this;
			this.next = node;
		} else {
			// Insert in the middle

			MancalaList temp = this.next;
			node.next = temp;
			this.next = node;
		}
		return node;

	}

	public int DeleteNext() {
		if (this.next == this) {
			System.out
					.println("\nThe node can not be deleted as there is only one node in the circular list");
			return 0;
		}

		this.next = this.next.next;
		return 1;
	}

	public void Traverse() {
		Traverse(this);
	}

	public void Traverse(MancalaList node) {
		if (node == null)
			node = this;
		System.out.println("\n\nTraversing in Forward Direction\n\n");
		MancalaList startnode = node;

		do {
			System.out.println(node.data);
			node = node.next;
		} while (node != startnode);
	}

	public int GetNumberOfNodes() {
		return GetNumberOfNodes(this);
	}

	public int GetNumberOfNodes(MancalaList node) {
		if (node == null)
			node = this;

		int count = 0;
		MancalaList startnode = node;
		do {
			count++;
			node = node.next;
		} while (node != startnode);

		System.out.println("\nCurrent Node Value: " + node.data);
		System.out.println("\nTotal nodes in Circular List: " + count);

		return count;
	}

	public static void main(String[] args) {

		/*
		 * SLinkedCircularList node1 = new SLinkedCircularList(1);
		 * node1.DeleteNext(); // Delete will fail in this case.
		 * 
		 * SLinkedCircularList node2 = node1.InsertNext(2); node1.DeleteNext();
		 * // It will delete the node2.
		 * 
		 * node2 = node1.InsertNext(2); // Insert it again
		 * 
		 * SLinkedCircularList node3 = node2.InsertNext(3); SLinkedCircularList
		 * node4 = node3.InsertNext(4); SLinkedCircularList node5 =
		 * node4.InsertNext(5);
		 * 
		 * node1.GetNumberOfNodes(); node3.GetNumberOfNodes();
		 * node5.GetNumberOfNodes();
		 * 
		 * node1.Traverse(); node3.DeleteNext(); // delete the node "4"
		 * node2.Traverse();
		 * 
		 * node1.GetNumberOfNodes(); node3.GetNumberOfNodes();
		 * node5.GetNumberOfNodes();
		 */
	}


}