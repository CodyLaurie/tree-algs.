package Main;

import java.awt.Label;
import java.util.ArrayList;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class BinaryTreeCopy<T> {
	
	protected TreeNode<T> root;
	
	public List<T> traverse() {
		List<T> result = new ArrayList<>();
		return result;
	}

	public TreeNode<T> getRoot() {
		return root;
	}

	public void setRoot(TreeNode<T> root) {
		this.root = root;
	}

	public String toString() {

		List<T> result1 = recursiveBFSTraversal();
		return result1.toString();

	}
	

	
	public List<T> recursivePreorderTraversal() {
		List<T> result  = new ArrayList<>();
		recursivePreorderTraversal (this.root,result);
		return result;
	}

	public void recursivePreorderTraversal(TreeNode<T> current,List<T> list) {
		//1. process the current node
		list.add(current.getValue());
		//2. recursively call method on the current's left
		if (current.hasLeftChild()) {
			recursivePreorderTraversal(current.getLeftChild(),list);
		}
		//3. recursively call method on the current's right
		if(current.hasRightChild()) {
			recursivePreorderTraversal(current.getRightChild(),list);
		}
	}
	
	public List<T> recursivePostorderTraversal() {
		List<T> result  = new ArrayList<>();
		recursivePostorderTraversal(this.root,result);
		return result;
	}

	public void recursivePostorderTraversal(TreeNode<T> current,List<T> list) {
		if (current.hasLeftChild()&&list.contains(current.getLeftChild().getValue())==false) {
			current=current.getLeftChild();
			recursivePostorderTraversal(current,list);
		}
		//checks if the right child exists if it does check to see if it is the last value added if it doesnt add it to the list
		//has to check if it is the lastadded to avoid duplicates
		//needs search array list for t or f on if is added

		else if (current.hasRightChild()&& list.contains(current.getRightChild().getValue())==false){
			current = current.getRightChild();
			recursivePostorderTraversal(current,list);
		}
		else if(list.contains(current.getValue())==false){
			list.add(current.getValue());
			if(current.hasParent()==true){
				current = current.getParent();
				recursivePostorderTraversal(current,list);

			}
		}
	}
	
	public List<T> recursiveBFSTraversal() {
		List<T> result  = new ArrayList<>();
		Deque<TreeNode<T>> queue = new LinkedList<TreeNode<T>>();
		queue.push(this.root);
		recursiveBFSTraversal (this.root,queue,result);
		return result;
	}

	public void recursiveBFSTraversal(TreeNode<T> current,Deque<TreeNode<T>>queue,List<T> list) {
		current = queue.pop();
		if (current.hasLeftChild()&&list.contains(current.getLeftChild())==false) {
			queue.add(current.getLeftChild());
		}
		if(current.hasRightChild()&&list.contains(current.getRightChild())==false) {
			queue.add(current.getRightChild());
		}
		if(list.contains(current.getValue())==false){
			list.add(current.getValue());
		}
		if(queue.peek()!=null&&list.contains(queue.peek())==false){
			recursiveBFSTraversal(current,queue,list);
		}
	}
	
	public List<T> recursiveInOrderTraversal() {
		List<T> result  = new ArrayList<>();
		recursiveInOrderTraversal (this.root,result);
		return result;
	}

	public void recursiveInOrderTraversal(TreeNode<T> current,List<T> list) {
		if (current.hasLeftChild()==true){
			if(list.contains(current.getLeftChild().getValue())==false){
				current = current.getLeftChild();
				recursiveInOrderTraversal(current,list);
			}else if(list.contains(current.getValue())==false){
				list.add(current.getValue());
				recursiveInOrderTraversal(current,list);
			}
		}else if(list.contains(current.getValue())==false){
			list.add(current.getValue());
		}
		if(current.hasRightChild()==true){
			if(list.contains(current.getRightChild().getValue())==false){
				current = current.getRightChild();
				recursiveInOrderTraversal(current,list);
			}else if(current.hasParent()==true){
				current = current.getParent();
				recursiveInOrderTraversal(current,list);
			}
		}else if(current.hasParent()==true){
			current = current.getParent();
			recursiveInOrderTraversal(current,list);
		}
	}
	
	public static void main(String args[]) {
		BinaryTreeCopy<String> t = new BinaryTreeCopy<>();
		Map<String, TreeNode<String>> m = new HashMap<>();
		for (Character c = 'a'; c <= 'r'; c++) {
			m.put(c.toString(), new TreeNode<>(c.toString()));
		}
		t.root = m.get("a");
		m.get("a").setParent(null).setLeftChild(m.get("b")).setRightChild(m.get("c"));
		m.get("b").setParent(m.get("a")).setLeftChild(m.get("d")).setRightChild(m.get("e"));
		m.get("c").setParent(m.get("a")).setLeftChild(null).setRightChild(m.get("f"));
		m.get("d").setParent(m.get("b")).setLeftChild(m.get("g")).setRightChild(m.get("h"));
		m.get("e").setParent(m.get("b")).setLeftChild(null).setRightChild(m.get("i"));
		m.get("f").setParent(m.get("c")).setLeftChild(m.get("j")).setRightChild(m.get("k"));
		m.get("g").setParent(m.get("d")).setLeftChild(m.get("l")).setRightChild(m.get("m"));
		m.get("h").setParent(m.get("d")).setLeftChild(null).setRightChild(m.get("n"));
		m.get("i").setParent(m.get("e")).setLeftChild(m.get("o")).setRightChild(null);
		m.get("j").setParent(m.get("f")).setLeftChild(m.get("p")).setRightChild(m.get("q"));
		m.get("k").setParent(m.get("f")).setLeftChild(null).setRightChild(null);
		m.get("l").setParent(m.get("g")).setLeftChild(null).setRightChild(null);
		m.get("m").setParent(m.get("g")).setLeftChild(m.get("r")).setRightChild(null);
		m.get("n").setParent(m.get("h")).setLeftChild(null).setRightChild(null);
		m.get("o").setParent(m.get("i")).setLeftChild(null).setRightChild(null);
		m.get("p").setParent(m.get("j")).setLeftChild(null).setRightChild(null);
		m.get("q").setParent(m.get("j")).setLeftChild(null).setRightChild(null);
		m.get("r").setParent(m.get("m")).setLeftChild(null).setRightChild(null);
		//preorder: [a, b, d, g, l, m, r, h, n, e, i, o, c, f, j, p, q, k]
		//inorder: [l, g, r, m, d, h, n, b, e, o, i, a, c, p, j, q, f, k]
		//post order: [l, r, m, g, n, h, d, o, i, e, b, p, q, j, k, f, c, a]
		//BFS: alphabetic!
		System.out.println(t);
	}
}
