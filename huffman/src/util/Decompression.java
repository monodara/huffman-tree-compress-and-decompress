package util;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;

import impl.Node;

/**
 * Decompression Class<br>
 * Takes a compressed binary file and de-compresses it and writes the output to another file
 * Contains the following elements:<br>
 * <ul>
 *  <li><b>biString</b> - A string of 0's and 1's that are read from the binary file</li>
 *  <li><b>treeRoot</b> - A reference to the root node of the Huffman tree built from the given codemap</li>
 * </ul>
 */
public class Decompression {
	/**
	 * A string of 0's and 1's that are read from the binary file
	 */

	private String biString; // Store the 0/1 String retrieved from the bin file

	/**
	 * A reference to the root node of the Huffman tree built from the given codemap
	 */
	private Node treeRoot; // Store the tree root node built from the given codemap

	/**
	 * Default constructor
	 */
	public Decompression() {}


	/**
	 * Constructor for Decompression class.
	 * Reads the compressed binary file, builds the Huffman Tree and decodes the compressed data.
	 * @param readFileName The name of the compressed binary file to be read.
	 * @param writeFileName The name of the decompressed file to be written.
	 * @param codemap The code-symbol map used to build the Huffman Tree.
	 */
	public Decompression(String readFileName, String writeFileName, HashMap<String, Character> codemap) {
		this.biString = readBinFile(readFileName); // Retrieved the encoded String from the bin file
		this.treeRoot = buildTree(codemap);// Build the tree according to the code-symbol map
		writeFile(writeFileName, getDecodedString(this.biString, treeRoot));
	}

	/**
	 * Builds a Huffman tree using the given code-symbol map
	 * @param codemap a map that maps each symbol to its Huffman code
	 * @return the root node of the Huffman tree
	 */
	// Build a tree using the codemap
	public static Node buildTree(HashMap<String, Character> codemap) {
		Node root = new Node();
		for(String s: codemap.keySet()) {
			Node currNode = root;
			char c = codemap.get(s);
			int index = 0;
			while(index < s.length()) {
				if(s.charAt(index) == '0') {
					if(currNode.getLeftNode() == null) {
						currNode.setLeftNode(new Node());
					}
					currNode = currNode.getLeftNode();
				}else {
					if(currNode.getRightNode() == null) {
						currNode.setRightNode(new Node());
					}
					currNode = currNode.getRightNode();
				}
				if(index == s.length() - 1) {
					currNode.setText(c);
				}
				index++;
			}
		}
		return root;
	}

	/**
	 * Decodes a given Huffman encoded String using a given Huffman tree and returns the decoded String
	 * @param encodeString the encoded String to be decoded
	 * @param root the root node of the Huffman tree used for decoding
	 * @return the decoded String
	 */
	// Get decoded String
	public static String getDecodedString(String encodeString, Node root) {
		int index = 0;
		String s = "";
		Node currNode = root;
		while(index < encodeString.length()) {
			
			char c = encodeString.charAt(index);
			if(c == '0') {
				currNode = currNode.getLeftNode();
			}else {
				currNode = currNode.getRightNode();
			}
			if(currNode.getText() != '\0') {
				s += currNode.getText();
				currNode = root;
			}
				
			index++;
		}
		return s;
	}


	/**
	 * Reads the compressed binary file and converts the bits into a binary string
	 * @param fileName The name of the binary file to be read
	 * @return A string representation of the bits in the binary file
	 */
	// Read bin file and store the bits to a String
	private static String readBinFile(String fileName) {
		FileInputStream fileIs;
		try {
			fileIs = new FileInputStream(fileName);
			DataInputStream is = new DataInputStream(fileIs);
			byte[] bt = is.readAllBytes();
			is.close();
			BitSet set = BitSet.valueOf(bt);
			String binaryString = "";
			for(int i = 0; i <= set.length(); i++) {
			    if(set.get(i)) {
			        binaryString += "1";
			    } else {
			        binaryString += "0";
			    }
			}
			return binaryString;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Writes a given string to a file with the specified file name
	 * @param fileName the name of the file to write the text to
	 * @param text the text to be written to the file
	 */
	// Write a String to a txt file
	private static void writeFile(String fileName, String text) {
		try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(text);
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception ");       
        }
	}
}
