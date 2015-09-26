# Venturesity
# PROBLEM STATEMENTS
# Codes and Cyphers
Codes and cyphers have been used to protect secrets since ancient times. In 1882, Frank Miller described the use of a one-time pad: an encryption technique that cannot be cracked if used correctly. The technique involves the use of a key and plaintext that is to be coded. Each character of the plaintext and the key are paired up using modular addition.

Example:
Suppose Alice wants to send the following message to Bob:
The enemy will attack at dawn!

If they agree on the key venturesity, then you pair up the characters as follows:
The enemy will attack at dawn!
Venturesityventuresityve!

The coding will be done as follows:
 ```sh
T h e e n e m y w i l l a t t a c k a t d a w n! Message 
20 8 5 5 14 5 13 25 23 9 12 12 1 20 20 1 3 11 1 20 4 1 23 14! Message
+22 5 14 20 21 18 5 19 9 20 25 22 5 14 20 21 18 5 19 9 20 25 22 5! Key
=42 13 19 25 35 23 18 44 32 29 37 34 6 34 40 22 21 16 20 29 24 26 45 19! Message + Key
= 17 14 20 26 10 24 19 19 7 4 12 9 7 9 15 23 22 17 21 4 25 1 20 20! Message + Key (mod 26) + 1
= Qntzjxssgdligiowvqudyatt!
...
So Alice would send Qntzjxssgdligiowvqudyatt! to Bob. If Bob knows the key, he would decode it as follows:

Q n t z j x s s g d l i g i o w v q u d y a t t! ciphertext
17 14 20 26 10 24 19 19 7 4 12 9 7 9 15 23 22 17 21 4 25 1 20 20! Ciphertext
- 22 5 14 20 21 18 5 19 9 20 25 22 5 14 20 21 18 5 19 9 20 25 22 5! Key
= -5 9 6 6 -11 6 14 0 -2 -16 -23 -13 2 -5 -5 2 4 12 2 -5 5 -24 -2 15! Ciphertext  key
= 20 8 5 5 14 5 13 25 23 9 12 12 1 20 20 1 3 11 1 20 4 1 23 14! Ciphertext key (mod 26)  1
= Theenemywillattackatdawn!

Write a program that accepts as input from the user, the key, whether the message is plaintext or not, the message (either in plaintext or ciphertext), and as output, the ciphertext (if the message is indicated as plaintext) or plaintext (if the message is indicated as ciphertext)

Sample input 1:
Key: venturesity
Is message plaintext (Y/N): Y
Plaintext: The enemy will attack at dawn!
Sample output:
Ciphertext: Qntzjxssgdligiowvqudyatt!

Sample input 2:
Key: venturesity
Is message plaintext (Y/N)?: N
Ciphertext: Qntzjxssgdligiowvqudyatt!
Sample output 2:
Plaintext: The enemy will attack at dawn!
