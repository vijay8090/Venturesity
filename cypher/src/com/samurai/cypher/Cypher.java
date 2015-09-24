package com.samurai.cypher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * 
 * 
 * PROBLEM STATEMENTS
Codes and Cyphers
Codes and cyphers have been used to protect secrets since ancient times. In 1882, Frank Miller described the use of a one-time pad: an encryption technique that cannot be cracked if used correctly. The technique involves the use of a key and plaintext that is to be coded. Each character of the plaintext and the key are paired up using modular addition.

Example:
Suppose Alice wants to send the following message to Bob:
The enemy will attack at dawn!

If they agree on the key venturesity, then you pair up the characters as follows:
The enemy will attack at dawn!
Venturesityventuresityve!

The coding will be done as follows:

T h e e n e m y w i l l a t t a c k a t d a w n! Message
20 8 5 5 14 5 13 25 23 9 12 12 1 20 20 1 3 11 1 20 4 1 23 14! Message
+22 5 14 20 21 18 5 19 9 20 25 22 5 14 20 21 18 5 19 8 20 25 22 5! Key
=42 13 19 25 35 23 18 44 32 29 27 34 6 34 40 22 21 16 20 28 24 26 45 19! Message + Key
= 17 14 20 26 10 24 19 19 7 4 2 9 7 9 15 23 22 17 21 3 25 1 20 20! Message + Key (mod 26) + 1
= q n t z j x s s g d b I g I o v u q u c y a t t!
= Qntzjxssgdblglovuqucyatt!

So Alice would send “Qntzjxssgdblglovuqucyatt!” to Bob. If Bob knows the key, he would decode it as follows:

Q n t z j x s s g d b l g l o v u q u c y a t t! ciphertext
17 14 20 26 10 25 19 19 7 4 2 9 7 9 15 23 22 17 21 3 25 1 20 20! Ciphertext
-	22 5 14 20 21 18 5 19 9 20 25 22 5 14 20 21 18 5 19 8 20 25 22 5! Key
= -5 9 6 6 -11 7 14 0 -2 -16 -23 -13 2 -5 -5 2 4 12 2 -5 5 -24 -2 15! Ciphertext – key
20 8 5 5 14 5 13 25 23 9 12 12 1 20 20 1 3 11 1 20 4 1 23 14! Ciphertext – key (mod 26) – 1
= The enemy will attack at dawn!

Write a program that accepts as input from the user, the key, whether the message is plaintext or not, the message (either in plaintext or ciphertext), and as output, the ciphertext (if the message is indicated as plaintext) or plaintext (if the message is indicated as ciphertext)

Sample input 1:
Key: venturesity
Is message plaintext (Y/N): Y
Plaintext: The enemy will attack at dawn!
Sample output:
Ciphertext: Qntzjxssgdblglovuqucyatt!

Sample input 2:
Key: venturesity
Is message plaintext (Y/N)?: N
Ciphertext: Qntzjxssgdblglovuqucyatt!
Sample output 2:
Plaintext: The enemy will attack at dawn!
 */

/**
 * 
 * @author Vijayakumar Anbu
 * @project : Hackathon
 * @Date :Sep 23, 2015
 * @java Version : Java 8
 */
public class Cypher {

	private List<String> alphabets = new ArrayList<String>();
	private String key = "venturesity";
	boolean debug = false;

	public Cypher() {

		loadAlphabets();

	}

	/**
	 * 
	 * This Method is to 
	 * @param input
	 * @return
	 */
	public String encrypt(String input) {
		String cypherText = "";

		input = input.toLowerCase().trim().replaceAll(" ", "");
		if (debug)
			System.out.println(input);

		int size = input.length();

		int[] messageArray = new int[size];

		int[] keyArray = new int[size];

		int[] messageKeyArray = new int[size];

		int[] modArray = new int[size];

		if (debug)
			System.out.println("input lengtgh:" + size);
		if (debug)
			System.out.println("key length:" + key.length());

		for (int i = 0; i < size; i++) {

			char ch = input.toCharArray()[i];

			messageArray[i] = getPosition("" + ch);
		}

		String keyStr = getKeyArr(size);

		if (debug)
			System.out.println("\n new key : " + keyStr);

		for (int i = 0; i < size; i++) {

			char ch = keyStr.toCharArray()[i];

			keyArray[i] = getPosition("" + ch);
		}

		for (int i = 0; i < size; i++) {

			messageKeyArray[i] = keyArray[i] + messageArray[i];
		}

		for (int i = 0; i < size; i++) {

			modArray[i] = (messageKeyArray[i] % 26) + 1;
		}

		if (debug) {
			print(messageArray);
			print(keyArray);
			print(messageKeyArray);
			print(modArray);
		}

		for (int ch : modArray) {
			if (debug)
				System.out.print(alphabets.get(ch - 1) + " \t");
			cypherText += alphabets.get(ch - 1);
		}
		cypherText = capitalize(cypherText);
		if (debug)
			System.out.println();
		if (debug)
			System.out.println(cypherText);

		return cypherText;
	}

	public String decrypt(String cypherText) {
		String input = "";
		cypherText = cypherText.toLowerCase();
		int size = cypherText.length();

		int[] keyArray = new int[size];

		int[] messageKeyArray = new int[size];

		int[] modArray = new int[size];

		int[] cyperArray = new int[size];

		for (int i = 0; i < size; i++) {

			char ch = cypherText.toCharArray()[i];

			cyperArray[i] = getPosition("" + ch);

		}

		String keyStr = getKeyArr(size);

		if (debug)
			System.out.println("\n new key : " + keyStr);

		for (int i = 0; i < size; i++) {

			char ch = keyStr.toCharArray()[i];

			keyArray[i] = getPosition("" + ch);
		}

		for (int i = 0; i < size; i++) {

			messageKeyArray[i] = cyperArray[i] - keyArray[i];
		}

		for (int i = 0; i < size; i++) {

			if (messageKeyArray[i] == 0 || messageKeyArray[i] == 1) {
				modArray[i] = 25;
			} else {

				modArray[i] = (messageKeyArray[i] < 0 ? 26 + messageKeyArray[i] : messageKeyArray[i] % 26) - 1;
			}
		}

		if (debug) {
			print(cyperArray);
			print(keyArray);
			print(messageKeyArray);
			print(modArray);
		}

		for (int ch : modArray) {
			if (debug)
				System.out.print(alphabets.get(ch - 1) + " \t");
			input += alphabets.get(ch - 1);
		}
		input = capitalize(input);
		if (debug)
			System.out.println();
		if (debug)
			System.out.println(input);

		return input;

	}

	/**
	 * 
	 * This Method is to 
	 * @param arr
	 */
	private void print(int[] arr) {

		for (int ch : arr) {
			System.out.print(ch + " \t");
		}

		System.out.println();

	}

	/**
	 * 
	 * This Method is to 
	 * @param line
	 * @return
	 */
	private String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	/**
	 * 
	 * This Method is to
	 */
	private void loadAlphabets() {

		for (char a = 'a'; a <= 'z'; a++) {
			// System.out.println(a);
			alphabets.add("" + a);
		}

		// System.out.println(alphabets);
	}

	/**
	 * 
	 * This Method is to 
	 * @param ch
	 * @return
	 */
	private int getPosition(String ch) {

		return alphabets.indexOf(ch) + 1;
	}

	/**
	 * This Method is to
	 * 
	 * @param size
	 * @return
	 */
	private String getKeyArr(int size) {
		int keylength = key.length();

		int qoutient = size / keylength;
		int remainder = size % keylength;

		String keyStr = "";

		for (int i = 0; i < qoutient; i++) {
			keyStr += key;
		}

		for (int i = 0; i < remainder; i++) {
			keyStr += key.toCharArray()[i];
		}
		return keyStr;
	}

	/**
	 * 
	 * This Method is to 
	 * @param myString
	 * @return
	 */
	public static boolean checkValidInput(String myString) {
		
		myString = myString.trim().replaceAll(" ", "");
		
		if(myString.substring(myString.length()-1).equals("!")){
			myString =myString.substring(0,myString.length()-1);
		}
		
		if (Pattern.matches("[a-zA-Z]+", myString)) {
			// System.out.println("string has only alphabets");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * This Method is to 
	 * @param displayMsg
	 * @param sc
	 * @return
	 */
	public static boolean getOption(String displayMsg, Scanner sc) {

		System.out.print(displayMsg);
		String type = sc.nextLine();
		if (type.toLowerCase().equals("y") || type.toLowerCase().equals("n")) {
			return type.toLowerCase().equals("y") ? true : false;
		} else {
			System.out.println("Please enter (Y/N)");

			return getOption(displayMsg, sc);
		}

	}

	/**
	 * 
	 * This Method is to 
	 * @param args
	 */
	public static void main(String[] args) {

		Cypher cyp = new Cypher();
		boolean process = true;

		while (process) {

			System.out.println("================================");
			System.out.println("Please enter the details :");
			Scanner sc = new Scanner(System.in);
			System.out.print("Key:");
			String key = sc.nextLine();
			cyp.key = key;
			if (!checkValidInput(key)) {
				System.out.println("INPUT ERROR :: key should contains only alphabets !");
				continue;
			}

			boolean plainText = getOption("Is message plaintext (Y/N)", sc);

			String message = "";
			if (plainText) {
				System.out.print("Plaintext:");
				message = sc.nextLine();
				if (checkValidInput(message)) {
					System.out.print("Ciphertext:" + cyp.encrypt(message));
				} else {
					System.out.println("INPUT ERROR :: input should contains only alphabets !");
					continue;
				}

			} else {
				System.out.print("Ciphertext:");
				message = sc.nextLine();
				if (checkValidInput(message)) {
					System.out.print("Plaintext:" + cyp.decrypt(message));
				} else {
					System.out.println("INPUT ERROR :: input should contains only alphabets !");
					continue;
				}
			}
			System.out.println();
			System.out.println("==============================================");
			process = getOption("Do you wanna Continue (Y/N)", sc);
			if (!process) {
				System.out.println("Thankyou, Closing !");
			}
		}

	}

}
