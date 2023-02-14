import java.util.ArrayList;		// the traversal method will create/return an arrayList that holds all the nodes in reverse order 
								// based on their comparator
public class BinTree<G extends Comparable<G>> {

// attributes
    private Node<G> root;


// methods
	public Node<G> getRoot() {		// return current root of tree, not including a set root since that can be accomplished with insertion
        return root;
    }

    public void insert(G data)			// this function calls a recursive helper function to search 
    {									// for a location to insert data (in the form of a node) into the tree
		root = insert(root, data);
	}

	private Node<G> insert(Node<G> root, G data)
	{
		// if the tree is empty, then create a new tree
		if (root == null)
		{
			return new Node<G>(data);
		}

		// if the current value is less than the value to be inserted, then traverse to left child of the current node (denoted as root or root of subtree)
		else if (data.compareTo(root.getData()) < 0)
		{
			root.setLeft(insert(root.getLeft(), data));
		}

		// if the current value is greater than the value to be inserted, then traverse to the right child of the current root of the subtree
		else if (data.compareTo(root.getData()) > 0)
		{
			root.setRight(insert(root.getRight(), data));
		}
		else
		{
			// there shouldn't be any duplicate values being inserted in this particular tree data structure
			return null;
		}

		return root;
	}




	public void delete(G data)
	{
		root = delete(root, data);
	}

	private Node<G> delete(Node<G> root, G data)
	{
		// nothing to delete/no more recursion is required to find it (it's been found)
		if (root == null)
		{
			return new Node<G>(data);
		}

		// if the current value is less than the value to be deleted, then traverse to left child of the current node (denoted as root or root of subtree)
		else if (data.compareTo(root.getData()) < 0)
		{
			root.setLeft(delete(root.getLeft(), data)); 
		}

		// if the current value is greater than the value to be deleted, then traverse to the right child of the current root of the subtree
		else if (data.compareTo(root.getData()) > 0)
		{
			root.setRight(delete(root.getRight(), data));
		}
		// the node to delete has been located in the tree
		else
		{
			// if the node has no children, then return a value of null

			// Case: 0 children 
			if (root.getLeft() == null && root.getRight() == null)
			{
				return null;
			}
	
			// Case: 1 child, right
			else if (root.getLeft() == null)
			{
				return root.getRight();
			}

			// Case: 1 child, left 
			else if (root.getRight() == null)
			{
				return root.getLeft();
			}

			// Case: 2 children
			else
			{
				root.setData(maxValue(root.getLeft()));		// set node to be deleted's data to largest value in left subtree
				root.setLeft(delete(root.getLeft(), root.getData()));		// set the left child's data as the data to be deleted, then delete
			}
		}

		return root;
	}

	public G maxValue(Node<G> node)		// finds the max data value in the BST and returns it
	{
		if (node.getRight() == null)
			return node.getData();
		return maxValue(node.getRight());
	}

	
    public ArrayList<Node<G>> reverseInOrder()		// this function calls a helper function to create an arrayList holding all nodes in the tree
	{
		ArrayList<Node<G>> list = new ArrayList<>();
		
		reverseInOrder(root, list);
		
		return list;
	}
		
	public void reverseInOrder(Node<G> root, ArrayList<Node<G>> list)		// this function adds each node to the arrayList using reverse in-order traversal
	{
		if (root == null)
		{
			return;
		}
			
		reverseInOrder(root.getRight(), list);
		list.add(root);
		reverseInOrder(root.getLeft(), list);
	}




}
