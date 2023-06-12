package util;

import java.util.HashMap;
import java.util.Scanner;

/**
   Main class that handles user input and runs the Compression and Decompression process
 */
public class Main {

	/**
	 * Main method that handles user input and runs the Compression and Decompression process
	 * @param args arguments passed through the command line
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the name of the file to be compressed: ");
		String readFileNameString = scanner.nextLine();
		System.out.println("Please enter the name of the file to store the compressed file: ");
		String writeFileNameString = scanner.nextLine();
		
		// Compress a file and get Huffman code
		long startCompressTime = System.currentTimeMillis();
		Compression compress = new Compression(readFileNameString, writeFileNameString);
		long endCompressTime = System.currentTimeMillis();
		System.out.println("Compress Time: " + (endCompressTime - startCompressTime) + "ms, Compress Ratio: " + compress.compressRatio);
		System.out.println();
		HashMap<String, Character> codemap = compress.codeToChar;
		
		// Decompress a bin file and write the text into a txt file
		System.out.println("Please enter the name of the file to be decompressed: ");
		String binFileName = scanner.nextLine();
		System.out.println("Please enter the name of the file to store the decompressed file: ");
		String txtFileNameString = scanner.nextLine();
		// Parse the codemap to Decompression
		long startDecompressTime = System.currentTimeMillis();
		Decompression decompression = new Decompression(binFileName, txtFileNameString, codemap);
		long endDecompressTime = System.currentTimeMillis();
		System.out.println("Decompress Time: " + (endDecompressTime - startDecompressTime));
		
	}
}
