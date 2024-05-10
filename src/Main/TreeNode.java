package Main;

public class TreeNode<T> {

    private final T value;
    private TreeNode<T> leftChild;
    private TreeNode<T> rightChild;
    private TreeNode<T> parent;
    private int Height;
    
    public TreeNode(T value) {
        this(value, null,0);
    }

    public TreeNode(T value, TreeNode<T> parent,int Height) {
        this.value = value;
        this.parent = parent;
        this.rightChild = null;
        this.leftChild = null;
        this.Height = Height;
    }

    public TreeNode<T> setParent(TreeNode<T> parent) {
        this.parent = parent;
        return this;
    }

    public TreeNode<T> getParent() {
        return this.parent;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public TreeNode<T> setLeftChild(TreeNode<T> leftChild) {
        this.leftChild = leftChild;
        return this;
    }

    public boolean hasLeftChild() {
        return this.leftChild != null;
    }
 
    public TreeNode<T> getLeftChild() {
        return this.leftChild;
    }

    public TreeNode<T> setRightChild(TreeNode<T> rightChild) {
        this.rightChild = rightChild;
        return this;
    }
    
    public boolean hasRightChild() {
        return this.rightChild != null;
    }

    public TreeNode<T> getRightChild() {
        return this.rightChild;
    }
    
    public boolean isLeaf() {
    	return (!this.hasLeftChild() && !this.hasRightChild());
    }

    public T getValue() {
        return this.value;
    }
    
    public String toString() {
    	return "(" + this.value + ")";
    }
    
    public int getHeight() {
    	if(this.leftChild==null&&this.rightChild==null){
    		this.setHeight(1);
    		return this.Height;
    	}
    	if(this.leftChild==null){
    		this.Height = this.rightChild.Height+1;
    		return this.Height;
    	}
    	if(this.rightChild==null){
    		this.Height = this.leftChild.Height + 1;
    		return this.Height;
    	}
        if(this.getLeftChild().getHeight()>this.getRightChild().getHeight()){
        	this.Height=this.leftChild.getHeight() + 1;
        }else{
        	this.Height=this.rightChild.getHeight() + 1;
        }
        return this.Height;
    }
    public void setHeight(int Height){
    	this.Height = Height;
    }
    
}
