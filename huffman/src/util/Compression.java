package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;

import impl.HuffmanTree;

/**
 * Compression Class<br>
 * This class takes a text file and compresses it using Huffman Encoding. The output is written to another binary file.
 * Contains the following elements:<br>
 * <ul>
 *    <li><b>codeToChar</b> - a HashMap to store the code-character map</li>
 * 	  <li><b>compressRatio</b> - the compression ratio of Huffman encoding, which is a measure of how much the data has been compressed</li>
 * </ul>
 */

public class Compression {

	/**
	 * a HashMap to store the code-character map
	 */
	public HashMap<String, Character> codeToChar; //Store code-character map

	/**
	 * the compression ratio of Huffman encoding, which is a measure of how much the data has been compressed
	 */
	public double compressRatio;

	/**
	 * Default Compression constructor
	 */
	public Compression() {}


	/**
	 * Compression Class constructor that takes a name of a text file to be compressed and name of output bin file
	 * <p>Reads the content of the text file, builds the Huffman Tree and encodes the content of the file using the tree.
	 * Stores the code-to-character map and compression ratio in {@link util.Compression#codeToChar codeToChar} and
	 * {@link util.Compression#compressRatio compressRatio} respectively</p>
	 * @param readFileName Name of the text file to be compressed
	 * @param writeFileName Name of the output bin file where the encoded content will be written
	 */
	public Compression(String readFileName, String writeFileName) {
		// Read file 
		String tobeEncode = readFile(readFileName);
		// Build Huffman Tree
		HuffmanTree tree = new HuffmanTree(tobeEncode);
		this.codeToChar = tree.getDecodeMap(); //Store code-character map
		// Get encoded String
		String encodingString = getEncodeString(tobeEncode, tree);
		// Calculate compress ratio
		this.compressRatio = calCompressRatio(tobeEncode, tree);
		// Write encoded String to a bin file
		writeFile(encodingString, writeFileName);
	}

	// Get the encoded String using a Huffman tree
	// The encoded String will contain 0s and 1s
	/**
	 * This method takes a string and a {@link impl.HuffmanTree HuffmanTree} object
	 * and returns the Huffman encoded string for the input string
	 * @param s The string to be Huffman encoded
	 * @param encodeTree The HuffmanEncoding object containing the Huffman code map
	 * @return The Huffman encoded string for the input string
	 */
	private static String getEncodeString(String s, HuffmanTree encodeTree) {
		String encodeString = "";
		// For each character, get its Huffman code and append it to the entire encoded String
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			encodeString += encodeTree.getCodeMap().get(c);
		}
		return encodeString;
	}


	/**
	 * Calculates the compression ratio of Huffman encoding for a given input string and encoding tree
	 * @param s Input string
	 * @param encodeTree Encoding tree to be used for encoding the string
	 * @return Compression ratio as a double value
	 */
	private static double calCompressRatio(String s, HuffmanTree encodeTree) {
		// Calculate the number of bits if using ASCII 
		long rawBits = s.length() * 8;
		// Calculate the number of bits when using Huffman Tree
		long bitsAfterEncode = 0;
		for(char c: s.toCharArray()) {
			bitsAfterEncode += encodeTree.getCodeMap().get(c).length();
		}
		return (double)bitsAfterEncode/rawBits;
	}


	// Method for reading a text file
	/**
	 * Reads a text file and returns its content as a single string
	 * @param fn The name of the file to be read
	 * @return The content of the file as a single string
	 */
	private static String readFile(String fn) {
		StringBuilder sb = new StringBuilder();
		File filename = new File(fn);
		try (BufferedReader in = new BufferedReader(new FileReader(filename))){
			String line = in.readLine();
			while (line != null){
				sb.append(line).append("\n");
				line = in.readLine();
			}
		}
		catch (IOException e){
			System.out.println(e);
		}
		return sb.toString();
	}


	/**
	 * Writes a compressed bit stream to a file
	 * @param encodeString the bit stream to be compressed and written
	 * @param fileName the name of the file to be written to
	 */
	// Method for writing the encoded string to a bin file
	private static void writeFile(String encodeString, String fileName) {
		BitSet bitSet = new BitSet(encodeString.length());
		int bitcounter = 0;
		for(Character c : encodeString.toCharArray()) {
		    if(c.equals('1')) {
		        bitSet.set(bitcounter);
		    }
		    bitcounter++;
		}
		byte[] bt = bitSet.toByteArray(); 
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			DataOutputStream os = new DataOutputStream(fileOutputStream);
			os.write(bt);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
