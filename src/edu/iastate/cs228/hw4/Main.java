package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;



// two cities: /Users/prakarsha/eclipse-workspace/HW4_228/src/edu/iastate/cs228/hw4/twocities.arch
// constitution: /Users/prakarsha/eclipse-workspace/HW4_228/src/edu/iastate/cs228/hw4/constitution.arch
// cadbard: /Users/prakarsha/eclipse-workspace/HW4_228/src/edu/iastate/cs228/hw4/cadbard.arch
//monalisa: /Users/prakarsha/eclipse-workspace/HW4_228/src/edu/iastate/cs228/hw4/monalisa.arch



//author :Prakarsha Poudel
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Please enter filename to decode:");

        Scanner scnr = new Scanner(System.in);
        String fileName = scnr.nextLine();

        // Read all chars to variable content
        String content = Files.readString(new File(fileName).toPath()).trim();

        // Check for empty content
        if (content.isEmpty()) {
            System.out.println("File content is empty. Exiting...");
            return;
        }

        int pos = content.lastIndexOf('\n');
        String pattern = content.substring(0, pos); // pattern
        String Encoded = content.substring(pos).trim(); // encoded message


        Set<Character> chars = new HashSet<>();
        for (char c : pattern.toCharArray()) {
            if (c != '^') {
                chars.add(c);
            }
        }

        StringBuilder charBuilder = new StringBuilder();
        
        for (Character character : chars) {
            charBuilder.append(character);
        }
        String ch = charBuilder.toString();

        MsgTree root = new MsgTree(pattern);
        MsgTree.printCodes(root, ch);
        root.decode(root, Encoded);
    }
}