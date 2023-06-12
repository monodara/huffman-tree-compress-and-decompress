package impl;

import java.util.ArrayList;
import java.util.List;


/**
 * Node of the Huffman tree<br>
 * Contains the following elements:<br>
 * <ul>
 * <li><b>freq</b> - represents the frequency of occurrence of the symbol</li>
 * <li><b>rightNode</b> - reference to the right child node</li>
 * <li><b>leftNode</b> - reference to the left child node</li>
 * <li><b>text</b> - holds the character symbol represented by the node</li>
 * <li><b>code</b> - represents the binary code assigned to the symbol</li>
 * </ul>
 * This class implements the Comparable interface to allow sorting of nodes based on their frequency of occurrence.
 */
public class Node implements Comparable<Node> {
	private int freq; //represent the frequency of occurrence
	private Node rightNode;
	private Node leftNode;
	private char text; //represent the symbol
	private String code; //represent 0/1 code
	
	// Getter and Setter

	/**
	 *This method returns the frequency of the character associated with this Node.
	 * @return the frequency of the character associated with this Node
	 */
    public int getFreq() {
		return freq;
	}

	/**
	 * This method sets the frequency of the character associated with this Node to the given frequency.
	 * @param freq the frequency to set
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}

	/**
	 * This method returns the right child node of this Node.
	 * @return the right child node of this Node
	 */
	public Node getRightNode() {
		return rightNode;
	}

	/**
	 * This method sets the right child node of this Node to the given node.
	 * @param rightNode the node to set as the right child of this Node
	 */
	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

	/**
	 * This method returns the left child node of this Node.
	 * @return the left child node of this Node
	 */
	public Node getLeftNode() {
		return leftNode;
	}

	/**
	 * This method sets the left child node of this Node to the given node.
	 * @param leftNode the node to set as the left child of this Node
	 */
	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	/**
	 * This method returns the character associated with this Node.
	 * @return the character associated with this Node
	 */
	public char getText() {
		return text;
	}

	/**
	 * This method sets the character associated with this Node to the given character.
	 * @param text the character to set as the text of this Node
	 */
	public void setText(char text) {
		this.text = text;
	}

	/**
	 * This method returns the Huffman code associated with this Node.
	 * @return the Huffman code associated with this Node
	 */
	public String getCode() {
		return code;
	}

	/**
	 * This method sets the Huffman code associated with this Node to the given code.
	 * @param code the Huffman code to set for this Node
	 */
    public void setCode(String code) {
    	this.code = code;
    }

    // Constructors

	/**
	 * Default constructor of Node
	 */
	public Node() {}


	/**
	 * Custom constructor for initializing the Node of the Huffman Tree.
	 * This constructor initializes a Node object with the provided left child node,
	 * right child node, frequency count, and character.
	 * @param leftNode a reference to the left child node of this node in the Huffman Tree.
	 * @param rightNode a reference to the right child node of this node in the Huffman Tree.
	 * @param freq the frequency count of the character represented by this node.
	 * @param text the character represented by this node.
	 *
	 */
    public Node(Node leftNode, Node rightNode, int freq, char text){
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.freq = freq;
        this.text = text;
    }


	@Override
	// Override the compareTo method for the utilization when build the minHeap according to the frequency of each symbol
	public int compareTo(Node node) {
		if(this.freq-node.freq>0){ 
			return 1;
		}else if(this.freq-node.freq<0){
			return -1; 
		}else{
			return 0; //return 0 if they're the same
		}
	}

	/**
	 * The preOrder method performs a pre-order traversal of the Huffman Tree, starting at the
	 * specified root node, and returns a list of the frequency counts of each node in the tree
	 * in pre-order format. The method takes in a Node object as the root node parameter and returns
	 * a list of integers representing the frequency counts of each node in the tree.

	 * @param root the root node of the Huffman Tree to traverse.
	 * @return a list of the frequency counts of each node in the Huffman Tree in pre-order format.
	 */
	// Traversing method for testing if the tree is built as expected
	public static List<Integer> preOrder(Node root) {
		List<Integer> list = new ArrayList<>();
		if(root == null) return list;
		list.add(root.freq);
		list.addAll(preOrder(root.leftNode));
		list.addAll(preOrder(root.rightNode));
		return list;
	}


	/**
	 * Performs a pre-order traversal of the Huffman Tree, starting at the specified root node,
	 * and returns a list of the characters represented by each leaf node in the tree in pre-order format.
	 * @param root the root node of the Huffman Tree to traverse.
	 * @return a list of the characters represented by each leaf node in the Huffman Tree in pre-order format.
	 */
	public static List<Character> preOrderGetText(Node root) {
		List<Character> list = new ArrayList<>();
		if(root == null) return list;
		if(root.text != '\0') list.add(root.text);
		list.addAll(preOrderGetText(root.leftNode));
		list.addAll(preOrderGetText(root.rightNode));
		return list;
	}
}