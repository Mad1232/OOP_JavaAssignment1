package edu.iastate.cs228.hw4;

import java.util.Stack;


// author :Prakarsha Poudel
public class MsgTree {

	public char payloadChar;
	public MsgTree left;
	public MsgTree right;

	// Static variable to keep track of the current position in the encoding string
	private static int staticCharIdx = 0;

	// Constructor to build the tree from an encoding string

	public MsgTree(String encodingString) {
		// Check for null or insufficient length
		if (encodingString == null || encodingString.length() < 2) {
			return;
		}

		Stack<MsgTree> stack = new Stack<>();
		int index = 0;

		// Set the payload character for the root node

		this.payloadChar = encodingString.charAt(index++);

		stack.push(this);
		MsgTree current = this;
		boolean inLeftSubtree = true;

		// Iterate through the encoding string to build the tree
		while (index < encodingString.length()) {
			MsgTree node = new MsgTree(encodingString.charAt(index++));

			// Assign the new node to the left or right child of the current node
			if (inLeftSubtree) {
				current.left = node;
			}

			else {
				current.right = node;
			}

			// Update the current node and stack based on the encountered character
			if (node.payloadChar == '^') {
				current = stack.push(node);
				inLeftSubtree = true;
			}

			else {

				if (!stack.empty()) {
					current = stack.pop();
				}

				inLeftSubtree = false;
			}
		}
	}

	// Constructor for a single node with null children
	public MsgTree(char payloadChar) {

		this.payloadChar = payloadChar;

		this.left = null;

		this.right = null;
	}

	// Method to print characters and their binary codes
	public static void printCodes(MsgTree root, String code) {

		System.out.println("character code\n-------------------------");

		for (char ch : code.toCharArray()) {
			getCode(root, ch, binCode = ""); // Get the binary code for each character

			String characterString;
			if (ch == '\n') {
				characterString = "\\n";
			} else {
				characterString = String.valueOf(ch);
			}

			System.out.println("    " + characterString + "    " + binCode);
		}
	}

	// Variable to store the binary code during recursive calls
	private static String binCode;

	// Recursive method to get the binary code for a specific character
	private static boolean getCode(MsgTree root, char ch, String path) {
		if (root != null) {
			if (root.payloadChar == ch) {
				binCode = path;
				return true;
			}
			return getCode(root.left, ch, path + "0") || getCode(root.right, ch, path + "1");
		}
		return false;
	}

	// Method to decode the message

	public void decode(MsgTree codes, String msg) {
		System.out.println("MESSAGE:");

		MsgTree current = codes;
		StringBuilder decodedMessage = new StringBuilder();

		// Iterate through the encoded message

		for (int i = 0; i < msg.length(); i++) {
			char bit = msg.charAt(i);

			// Traverse the tree based on the current bit (0 or 1)
			if (bit == '0') {
				current = current.left;
			}

			else {
				current = current.right;
			}

			// Check if the current node is a leaf node
			if (current.payloadChar != '^') {

				getCode(codes, current.payloadChar, binCode = "");
				decodedMessage.append(current.payloadChar);

				// Reset to the root of the tree for the next character
				current = codes;
			}
		}

		System.out.println(decodedMessage.toString());

		statistic(msg, decodedMessage.toString());
	}

	// Method to calculate and print extra credit statistics
	private void statistic(String encodeStr, String decodeStr) {
		System.out.println("STATISTICS:");
		System.out.println(String.format("Avg bits/char:\t\t%.1f", encodeStr.length() / (double) decodeStr.length()));
		System.out.println("Total characters:\t" + decodeStr.length());
		System.out.println(String.format("Space savings:\t\t%.1f%%",
				(1 - decodeStr.length() / (double) encodeStr.length()) * 100));
	}
}
