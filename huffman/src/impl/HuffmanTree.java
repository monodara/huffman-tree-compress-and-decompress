package impl;

import java.util.*;
import java.util.NoSuchElementException;

/**
 * <p>This class implements a Huffman Tree using a priority queue to sort the characters
 * according to their frequency of occurrence. The Huffman Tree is used in Huffman coding
 * to compress data by encoding characters with variable-length codes.</p><br>
 * The HuffmanTree class contains the following elements:
 * <ul>
 * <li><b>frequencyMap</b> - a HashMap that stores the frequency of occurrence of each character</li>
 * <li><b>minHeap</b> - a priority queue that sorts the characters based on their frequency of occurrence</li>
 * <li><b>root</b> - a reference to the root node of the Huffman Tree</li>
 * <li><b>codeMap</b> - a HashMap that stores the Huffman code for each character</li>
 * <li><b>decodeMap</b> - a HashMap that maps Huffman codes to characters for decoding</li>
 * </ul>
 */

public class HuffmanTree {

	/**
	 * a HashMap that stores the frequency of occurrence of each character
	 */
	private HashMap<Character, Integer> frequencyMap; // Count the frequency of each character;

	/**
	 * a priority queue that sorts the characters based on their frequency of occurrence
	 */
	private PriorityQueue<Node> minHeap; // Sort the characters according to their frequency;

	/**
	 * a reference to the root node of the Huffman Tree
	 */
	private Node root; // Store the root node of the tree

	/**
	 * a HashMap that stores the Huffman code for each character
	 */
	private HashMap<Character, String> codeMap; // Store code of each character;

	/**
	 * a HashMap that maps Huffman codes to characters for decoding
	 */
	private HashMap<String, Character> decodeMap; // Map code to character for decoding

	// Getter and Setter
	/**
	 * Getter for the codeMap HashMap that maps each character to its corresponding Huffman code.
	 * @return The codeMap HashMap.
	 */
	public HashMap<Character, String> getCodeMap() {
		return codeMap;
	}

	/**
	 * Setter for the codeMap HashMap that maps each character to its corresponding Huffman code.
	 * @param codeMap The codeMap HashMap to set.
	 */
	public void setCodeMap(HashMap<Character, String> codeMap) {
		this.codeMap = codeMap;
	}

	/**
	 * Getter for the decodeMap HashMap that maps each Huffman code to its corresponding character.
	 * @return The decodeMap HashMap.
	 */
	public HashMap<String, Character> getDecodeMap() {
		return decodeMap;
	}

	/**
	 * Setter for the decodeMap HashMap that maps each Huffman code to its corresponding character.
	 * @param decodeMap The decodeMap HashMap to set.
	 */
	public void setDecodeMap(HashMap<String, Character> decodeMap) {
		this.decodeMap = decodeMap;
	}

	/**
	 * Getter for the frequencyMap HashMap that maps each character to its frequency of occurrence in the input data.
	 * @return The frequencyMap HashMap.
	 */
	public HashMap<Character, Integer> getFrequencyMap() {
		return frequencyMap;
	}

	/**
	 * Setter for the frequencyMap HashMap that maps each character to its frequency of occurrence in the input data.
	 * @param frequencyMap The frequencyMap HashMap to set.
	 */
	public void setFrequencyMap(HashMap<Character, Integer> frequencyMap) {
		this.frequencyMap = frequencyMap;
	}

	/**
	 * Getter for the minHeap priority queue that sorts the characters based on their frequency of occurrence.
	 * @return The minHeap priority queue.
	 */
	public PriorityQueue<Node> getMinHeap() {
		return minHeap;
	}

	/**
	 * Setter for the minHeap priority queue that sorts the characters based on their frequency of occurrence.
	 * @param minHeap The minHeap priority queue to set.
	 */
	public void setMinHeap(PriorityQueue<Node> minHeap) {
		this.minHeap = minHeap;
	}

	/**
	 * Getter for the root Node of the Huffman Tree.
	 * @return The root Node of the Huffman Tree.
	 */
	public Node getRoot() {
		return root;
	}

	/**
	 * Setter for the root Node of the Huffman Tree.
	 * @param root The root Node of the Huffman Tree to set.
	 */
	public void setRoot(Node root) {
		this.root = root;
	}

	// Constructors
	/**
	 * Default constructor
	 */
	public HuffmanTree() {}

	/**
	 * Custom constructor that takes a string as input, initiates a minimum heap, and builds the
	 * Huffman Tree for the input string.
	 * @param s The string to be compressed using Huffman coding
	 */
	public HuffmanTree(String s) {
		frequencyMap = new HashMap<>();
		minHeap = new PriorityQueue<>();
		this.codeMap = new HashMap<>();
		this.decodeMap = new HashMap<>();
		InitiateMinHeap(s);
		buildHuffmanTree();
		enCode();
	}

	/**
	 * This method builds the Huffman Tree for the current HuffmanTree object. The method
	 * uses a priority queue of nodes to construct the tree in a bottom-up manner.
	 * @throws NoSuchElementException If the priority queue is empty
	 */
	// Build a Huffman Tree
	private void buildHuffmanTree() throws NoSuchElementException {
		if(minHeap.size() == 0) return;
		// Each time get two nodes with least frequency and add them as left/right node of a dummy root
		// Until there is only one node left in the priority queue
		while(minHeap.size() > 1) {
			Node leftNode = minHeap.poll();
			Node rightNode = minHeap.poll();
			Node internalNode = new Node(leftNode, rightNode, leftNode.getFreq()+rightNode.getFreq(), '\u0000');
			minHeap.offer(internalNode);
			
		}
		root = minHeap.peek(); // Store the last node to root
		if (root == null) {
			throw new NoSuchElementException("Priority queue is empty");
		}
	}

	/**
	 * This method counts the frequency of characters in a string
	 * and stores it in the {@link impl.HuffmanTree#frequencyMap frequencyMap} of the Huffman tree object.
	 *
	 * @param s String of variable length to count frequency of each character.
	 */
	// Count the frequency of the characters
	private void countFreq(String s){
		for(char c: s.toCharArray()) {
			frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
		}
	}


	// Build the minHeap
	/**
	 * Initiates the min heap {@link impl.HuffmanTree#minHeap}.
	 * Counts the frequency of the characters in the given string using the {@link #countFreq(String)} method,
	 * stores the frequency count in the {@link impl.HuffmanTree#frequencyMap} map, and adds each character node to the
	 * priority queue {@link impl.HuffmanTree#minHeap} based on their frequency count.
	 *
	 * @param s String of variable length
	 */
	private void InitiateMinHeap(String s) {
		countFreq(s); // Call the method to count frequencies and store them to the frequency map
		// Store all nodes into the priority queue according to their occurrence frequency
		for(Character c: frequencyMap.keySet()) {
			Node node = new Node(null, null, frequencyMap.get(c), c);
			minHeap.offer(node);
		}
	}
	
	// Assign 0/1 to each edge and set code for each node
	/**
	 * This method generates the Huffman code for each character in the Huffman Tree by traversing the tree
	 * in level order and assigning a code to each leaf node. The codes are then stored
	 * in the {@link impl.HuffmanTree#codeMap codeMap} and
	 * {@link impl.HuffmanTree#decodeMap decodeMap} HashMaps for encoding and decoding respectively.
	 */
	private void enCode() {
		Queue<Node> queue = new LinkedList<>();
		root.setCode("");
		queue.add(root);
		// Traverse the tree in level order and get the code of each node
		while(!queue.isEmpty()) {
			Node currentNode = queue.poll();
			// if the node is a leaf (without left or right node), store its symbol and code to the map
			if(currentNode.getLeftNode() == null && currentNode.getRightNode() == null) {
				codeMap.put(currentNode.getText(), currentNode.getCode());
				decodeMap.put(currentNode.getCode(), currentNode.getText());
			}
			// '0' for left edge
			if(currentNode.getLeftNode() != null) {
				currentNode.getLeftNode().setCode(currentNode.getCode()+'0');
				queue.add(currentNode.getLeftNode());
			}
			// '1' for right edge
			if(currentNode.getRightNode() != null) {
				currentNode.getRightNode().setCode(currentNode.getCode()+'1');
				queue.add(currentNode.getRightNode());
			}
		}
	}

}
