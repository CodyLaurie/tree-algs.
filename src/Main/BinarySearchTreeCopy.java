package Main;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import Algorithms.TreeNode;


public class BinarySearchTreeCopy<T> extends BinaryTreeCopy<T> {
	
	private final Comparator<T> cmp;
	
	public BinarySearchTreeCopy (Comparator<T> cmp) {
		this.cmp = cmp;
	}
	public boolean contains (T needle) {
		
		TreeNode<T> current = this.root;
		while(current != null) {
			if(this.cmp.compare(needle, current.getValue())== 0) {
				return true;
			} else if(this.cmp.compare(needle, current.getValue()) < 0) {
				//go to the left child
				current = current.getLeftChild();
			} else if(this.cmp.compare(needle, current.getValue()) > 0) {
				//go to the right child
				current = current.getRightChild();
			}
			
		}	
		return false;

}

	
	public TreeNode<T> getParentOf (T needle) {
		TreeNode<T> pre = null;
		TreeNode<T> current = this.root;
		while(current != null) {
			if(this.cmp.compare(needle, current.getValue())== 0) {
				return null;
			} else if(this.cmp.compare(needle, current.getValue()) < 0) {
				//go to the left child
				pre = current;
				current = current.getLeftChild();
			} else if(this.cmp.compare(needle, current.getValue()) > 0) {
				//go to the right child
				pre = current;
				current = current.getRightChild();
			}
			
		}

		return pre;

}

	public boolean insert (T element) {
		//First we need a new node to put element in
		TreeNode<T> newNode = new TreeNode<T>(element);
		//Corner case: if the tree is null, which means that you are adding to an empty tree
		if (this.root == null) {
			this.root = newNode;
			return true;
		}
		//If not in the tree, I have to search for the parent of the new node
		TreeNode<T> parent = getParentOf(element);
		if (element == null) {
			//If already there, no need to insert
			return false;
		}
		newNode.setParent(parent);
		if (this.cmp.compare(parent.getValue(),newNode.getValue()) < 0) {
			parent.setRightChild(newNode);			
		}
		else {
			parent.setLeftChild(newNode);
		}
		//we return true. Inserting a node had an effect on the tree
		return true;
		
	}
	
	public void delete (T element) {
		//First we need to find the node
		if(contains(element)){
			TreeNode<T> node = findNodeOf(element);
			//If the node is leaf
			if (!node.hasLeftChild() && !node.hasRightChild()) {
				if (node.getParent().getLeftChild() == node) { 
					//if the node is left child
					node.getParent().setLeftChild(null);
				} else {
					//if the node is right child
					node.getParent().setRightChild(null);				
				}			
			} else if ((node.hasLeftChild() && !node.hasRightChild())){
				//t only has a left child, promote it:
				//set t's parent's child (left or right) to t's left child			
				TreeNode<T> parent = node.getParent();
				TreeNode<T> left = node.getLeftChild();
				left.setParent(parent);
				//if t is the left child of the parent
				if(node == parent.getLeftChild()) {
					//set the parent's left child to left:
					parent.setLeftChild(left);
				} else { 
					parent.setRightChild(left);
				}
			} else if ((!node.hasLeftChild() && node.hasRightChild())){
				//t only has a right child, promote it:
				//set t's parent's child (left or right) to t's right child
				TreeNode<T> parent = node.getParent();
				TreeNode<T> right = node.getRightChild();
				right.setParent(parent);
				//if t is the left child of the parent
				if(node == parent.getLeftChild()) {
					//set the parent's left child to right:
					parent.setLeftChild(right);
				} else {
					parent.setRightChild(right);
				}
			} else if(node.hasLeftChild() && node.hasRightChild()) {
				//two children
				//1. find the max of left tree(or the min of the right tree)
				if(node.hasParent()){
					if (node.getParent().getLeftChild() == node) { 
						//if the node is left child
						TreeNode<T> replace = node.getLeftChild();
						while (replace.hasRightChild()){
							replace = replace.getRightChild();
						}
						if(replace!=replace.getParent().getLeftChild()){
							replace.getParent().setRightChild(replace.getLeftChild());
						}
						replace.setParent(node.getParent());
						replace.setRightChild(node.getRightChild());
						if(replace!=node.getLeftChild()){
							replace.setLeftChild(node.getLeftChild());
							node.getLeftChild().setParent(replace);
						}
						node.getRightChild().setParent(replace);
						node.getParent().setLeftChild(replace);
						node = replace;
					} else {
						//if the node is right child
						TreeNode<T> replace = node.getRightChild();
						while (replace.hasLeftChild()){
							replace = replace.getLeftChild();
						}
						if(replace!=replace.getParent().getRightChild()){
							replace.getParent().setLeftChild(replace.getRightChild());
						}
						replace.setParent(node.getParent());
						replace.setLeftChild(node.getLeftChild());
						node.getLeftChild().setParent(replace);
						if(replace!=node.getRightChild()){
							replace.setRightChild(node.getRightChild());
							node.getRightChild().setParent(replace);
						}
						node.getParent().setRightChild(replace);
						node = replace;
					}
				}else{
					TreeNode<T> replace = node.getLeftChild();
					while (replace.hasRightChild()){
						replace = replace.getRightChild();
					}
					if(replace!=this.root.getLeftChild()){
						replace.getParent().setRightChild(replace.getLeftChild());
					}
					replace.setParent(node.getParent());
					replace.setRightChild(node.getRightChild());
					if(replace!=node.getLeftChild()){
						replace.setLeftChild(node.getLeftChild());
						node.getLeftChild().setParent(replace);
					}
					node.getRightChild().setParent(replace);
					this.root=replace;
					node = replace;
					
				}
			}
			//TODO: finish by using one of the previous cases
		//	TreeNode<T> maxOfLeft = findMax(node.getLeftChild())
		}else{
			System.out.println(element+" is not contained in tree");
		}
		
	}
	
	public TreeNode<T> findNodeOf(T element) {
		TreeNode<T> check = this.root;
		while(check.hasLeftChild()||check.hasRightChild()){
			if((Integer)check.getValue()<(Integer)element){
				if(check.hasRightChild()){
					check = check.getRightChild();
					continue;
				}else{
					return null;
				}
			}else if((Integer)check.getValue()>(Integer)element){
				if(check.hasLeftChild()){
					check = check.getLeftChild();
					continue;
				}else{
					return null;
				}
			}else if((Integer)check.getValue()==(Integer)element){
				return check;
			}
		}
		if((Integer)check.getValue()==(Integer)element){
			return check;
		}
		return null;
	}
	
	public int size(){
		return recursiveInOrderTraversal().size();
	}
	
	public int height(){
		return this.root.getHeight();
	}
	
	public static int balancefactor(TreeNode<Integer> node){
	if(node==null){
		return 0;
	} else if(node.getLeftChild()==null&&node.getRightChild()==null){
		return 0;
	}else if (node.getLeftChild()==null){
		return -node.getRightChild().getHeight();	
	}else if(node.getRightChild()==null){
		return node.getLeftChild().getHeight();
	}	
	return node.getLeftChild().getHeight() - node.getRightChild().getHeight();
	}

	public static void rotateL(BinarySearchTreeCopy<Integer> t,TreeNode<Integer> x){
		TreeNode<Integer> y = x.getRightChild();
		x.setRightChild(y.getLeftChild());
		if(y.getLeftChild() != null){
			y.getLeftChild().setParent(x);
		}
		y.setParent(x.getParent());
		if(x.getParent()==null){
			t.setRoot(y);
		}else if(x==x.getParent().getLeftChild()){
			x.getParent().setLeftChild(y);
		}else{
			x.getParent().setRightChild(y);
		}
		y.setLeftChild(x);
		x.setParent(y);
	}
	
	public static void rotateR(BinarySearchTreeCopy<Integer> t,TreeNode<Integer> x){
		TreeNode<Integer> y = x.getLeftChild();
		x.setLeftChild(y.getRightChild());
		if(y.getRightChild() != null){
			y.getRightChild().setParent(x);
		}
		y.setParent(x.getParent());
		if(x.getParent()==null){
			t.setRoot(y);
		}else if(x==x.getParent().getRightChild()){
			x.getParent().setRightChild(y);
		}else{
			x.getParent().setLeftChild(y);
		}
		y.setRightChild(x);
		x.setParent(y);
	}
	
	public static void rotateLR(BinarySearchTreeCopy<Integer> t,TreeNode<Integer> x){
		rotateL(t,x);
		rotateR(t,x);
	}
	
	public static void rotateRL(BinarySearchTreeCopy<Integer> t,TreeNode<Integer> x){
		rotateR(t,x);
		rotateL(t,x);
	}
	
	public static void main(String args[]) {
		
		BinarySearchTreeCopy<Integer> t;

		t = new BinarySearchTreeCopy<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});

	
		TreeNode<Integer> n50 = new TreeNode<>(50);
		TreeNode<Integer> n40 = new TreeNode<>(40);
		TreeNode<Integer> n60 = new TreeNode<>(60);
		TreeNode<Integer> n20 = new TreeNode<>(20);
		TreeNode<Integer> n45 = new TreeNode<>(45);
		TreeNode<Integer> n90 = new TreeNode<>(90);
		TreeNode<Integer> n10 = new TreeNode<>(10);
		TreeNode<Integer> n24 = new TreeNode<>(24);
		TreeNode<Integer> n49 = new TreeNode<>(49);
		TreeNode<Integer> n80 = new TreeNode<>(80);
		TreeNode<Integer> n95 = new TreeNode<>(95);
		TreeNode<Integer> n02 = new TreeNode<>(02);
		TreeNode<Integer> n15 = new TreeNode<>(15);
		TreeNode<Integer> n28 = new TreeNode<>(28);
		TreeNode<Integer> n48 = new TreeNode<>(48);
		TreeNode<Integer> n75 = new TreeNode<>(75);
		TreeNode<Integer> n85 = new TreeNode<>(85);
		TreeNode<Integer> n12 = new TreeNode<>(12);
		
		
		n50.setParent(null).setLeftChild(n40).setRightChild(n60);
		n40.setParent(n50).setLeftChild(n20).setRightChild(n45);
		n60.setParent(n50).setLeftChild(null).setRightChild(n90);
		n20.setParent(n40).setLeftChild(n10).setRightChild(n24);
		n45.setParent(n40).setLeftChild(null).setRightChild(n49);
		n90.setParent(n60).setLeftChild(n80).setRightChild(n95);
		n10.setParent(n20).setLeftChild(n02).setRightChild(n15);
		n24.setParent(n20).setLeftChild(null).setRightChild(n28);
		n49.setParent(n45).setLeftChild(n48).setRightChild(null);
		n80.setParent(n90).setLeftChild(n75).setRightChild(n85);
		n95.setParent(n90).setLeftChild(null).setRightChild(null);
		n02.setParent(n10).setLeftChild(null).setRightChild(null);
		n15.setParent(n10).setLeftChild(n12).setRightChild(null);
		n28.setParent(n24).setLeftChild(null).setRightChild(null);
		n48.setParent(n49).setLeftChild(null).setRightChild(null);
		n75.setParent(n80).setLeftChild(null).setRightChild(null);
		n85.setParent(n80).setLeftChild(null).setRightChild(null);
		n12.setParent(n15).setLeftChild(null).setRightChild(null);
		
		t.setRoot(n50);
		System.out.println(t);
		System.out.println(t.size());
		System.out.println(t.height());
		System.out.println(balancefactor(t.root));
		System.out.println(t.contains(50));
		t.delete(2);
		t.delete(10);
		t.delete(12);
		t.delete(15);
		t.delete(20);
		System.out.println(t);
		System.out.println(t.size());
		System.out.println(t.height());
		System.out.println(balancefactor(t.root.getLeftChild()));
		rotateL(t,n40);
		System.out.println(t);
		System.out.println(balancefactor(t.root.getLeftChild()));

	}
}