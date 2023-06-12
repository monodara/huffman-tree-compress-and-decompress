package util;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import javax.management.loading.PrivateClassLoader;

/**
 * Experiment class to conduct performance analysis
 */
public class Experiment {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the name of folder containing files to be compressed: ");
		String readPath = scanner.nextLine();
		System.out.println("Please enter the name of folder to store the compressed files: ");
		String compressPath = scanner.nextLine();
		System.out.println("Please enter the name of folder to store the retrieved/decompressed files: ");
		String decompressPath = scanner.nextLine();
		runExperiments(readPath, compressPath, decompressPath);
	}
	private static void runExperiments(String readPath, String compressPath, String decompressPath) {
		File folder = new File(readPath);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	String fileName = file.getName();
		        System.out.println("File Name: " + fileName + ", File Size: " + file.length()/1024 + "Kb");
		        String writeName = fileName.substring(0, fileName.length()-4) + ".bin";
		        // Compress a file and get Huffman code
		        long startCompressTime = System.currentTimeMillis();
		        Compression compress = new Compression(readPath + fileName, compressPath + writeName);
		        long endCompressTime = System.currentTimeMillis();
		        System.out.println("Compress Time: " + (endCompressTime - startCompressTime) + "ms, Compress Ratio: " + compress.compressRatio);
		        HashMap<String, Character> codemap = compress.codeToChar;
		        
		        // Decompress a bin file and write the text into a txt file
		        // Parse the codemap to Decompression
		        String deCompressName = fileName.substring(0, fileName.length()-4) + "-Retrieved.txt";
		        long startDecompressTime = System.currentTimeMillis();
		        Decompression decompression = new Decompression(compressPath + writeName, decompressPath + deCompressName, codemap);
		        long endDecompressTime = System.currentTimeMillis();
		        System.out.println("Decompress Time: " + (endDecompressTime - startDecompressTime) + "ms");
		        System.out.println();		        
		    }
		}
	}
}
