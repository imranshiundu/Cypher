import java.util.HashMap;
import java.util.Scanner;

/**
 * CypherTool - An interactive command-line encryption and decryption tool.
 *
 * This tool demonstrates 6 different encryption techniques:
 *   1. ROT13       - Caesar cipher with a fixed shift of 13
 *   2. Atbash      - Alphabetical substitution where A->Z, B->Y, etc.
 *   3. Caesar      - Classic letter shift cipher (configurable shift)
 *   4. Morse Code  - Dots and dashes for each letter
 *   5. Base64      - Encoding scheme that represents binary data as ASCII text
 *   6. Pigpen      - Geometric shapes based on a tic-tac-toe grid
 *
 * Educational tool for learning how classical ciphers work.
 * Author: Cypher Tool Project
 */
public class CypherTool {

    // Scanner for reading user input from the console
    private static final Scanner scanner = new Scanner(System.in);

    // ---------------------------------------------------------------
    // MAIN METHOD - Entry point for the application
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        printWelcome();

        // Main program loop - keeps running until the user types "exit"
        while (true) {
            // Step 1: Get the operation (encrypt or decrypt)
            InputData input = getInput();

            // If user typed "exit", break out of the loop
            if (input == null) {
                System.out.println("\nGoodbye! Stay cryptic.");
                break;
            }

            // Step 2: Process the message using the selected cipher
            String result = processMessage(input);

            // Step 3: Display the result
            printResult(input, result);

            // Add a separator line for readability
            System.out.println("\n--------------------------------------------\n");
        }

        // Close the scanner to prevent resource leaks
        scanner.close();
    }

    // ---------------------------------------------------------------
    // INPUT HANDLING - Get and validate all user input
    // ---------------------------------------------------------------

    /**
     * Gathers all input from the user: operation, cipher type, and message.
     * Returns null if the user wants to exit.
     *
     * @return InputData object containing all input, or null to exit
     */
    public static InputData getInput() {
        // Get the operation (encrypt or decrypt)
        int operation = getOperation();
        if (operation == -1) return null; // User wants to exit

        // Get the cipher type
        int cipherType = getCipherType();
        if (cipherType == -1) return null; // User wants to exit

        // Get the message to process
        String message = getMessage();
        if (message == null) return null; // User wants to exit

        // For Caesar cipher, also get the shift value
        int shift = 0;
        if (cipherType == 3) {
            shift = getCaesarShift();
            if (shift == -1) return null;
        }

        // Create and return an InputData object with all collected data
        return new InputData(operation, cipherType, message, shift);
    }

    /**
     * Asks the user to choose between encrypt and decrypt.
     * Keeps prompting until a valid option or "exit" is entered.
     *
     * @return 1 for encrypt, 2 for decrypt, -1 to exit
     */
    public static int getOperation() {
        while (true) {
            System.out.println("Select operation:");
            System.out.println("  1. Encrypt");
            System.out.println("  2. Decrypt");
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            // Check for exit command (case-insensitive)
            if (input.equalsIgnoreCase("exit")) return -1;

            // Validate input
            if (input.equals("1")) return 1;
            if (input.equals("2")) return 2;

            // Invalid input - show error and loop again
            System.out.println("Invalid option. Please enter 1, 2, or 'exit'.\n");
        }
    }

    /**
     * Asks the user to choose which cipher to use.
     * Keeps prompting until a valid option or "exit" is entered.
     *
     * @return cipher type number (1-6), or -1 to exit
     */
    public static int getCipherType() {
        while (true) {
            System.out.println("\nSelect cipher:");
            System.out.println("  1. ROT13      - The classic 13-shift");
            System.out.println("  2. Atbash     - The alphabet mirror");
            System.out.println("  3. Caesar     - Julius Caesar's favorite");
            System.out.println("  4. Morse Code - Dots and dashes");
            System.out.println("  5. Base64     - The internet's workhorse");
            System.out.println("  6. Pigpen     - Spy shapes");
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            // Check for exit command
            if (input.equalsIgnoreCase("exit")) return -1;

            // Validate input (must be 1-6)
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 6) return choice;
            } catch (NumberFormatException e) {
                // Not a number - fall through to error message
            }

            System.out.println("Invalid option. Please enter 1-6, or 'exit'.");
        }
    }

    /**
     * Prompts for and reads the message to encrypt/decrypt.
     * Trims whitespace and checks that the string is not empty.
     *
     * @return trimmed message, or null to exit
     */
    public static String getMessage() {
        while (true) {
            System.out.print("\nEnter the message: ");
            String input = scanner.nextLine().trim();

            // Check for exit
            if (input.equalsIgnoreCase("exit")) return null;

            // Validate: must not be empty after trimming
            if (input.isEmpty()) {
                System.out.println("Message cannot be empty. Try again.");
                continue;
            }

            return input;
        }
    }

    /**
     * Asks for the Caesar cipher shift value (1-25).
     *
     * @return shift value, or -1 to exit
     */
    public static int getCaesarShift() {
        while (true) {
            System.out.print("\nEnter shift value (1-25): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) return -1;

            try {
                int shift = Integer.parseInt(input);
                if (shift >= 1 && shift <= 25) return shift;
            } catch (NumberFormatException e) {
                // Not a valid number
            }

            System.out.println("Invalid shift. Please enter a number between 1 and 25.");
        }
    }

    // ---------------------------------------------------------------
    // ENCRYPTION / DECRYPTION METHODS
    // ---------------------------------------------------------------

    /**
     * ROT13: Shift each letter by 13 positions in the alphabet.
     *
     * HOW IT WORKS:
     *   A B C D E F G H I J K L M   N O P Q R S T U V W X Y Z
     *   |               |           |               |
     *   N O P Q R S T U V W X Y Z   A B C D E F G H I J K L M
     *
     * Apply it twice and you get back to the original text!
     * Example: "HELLO" -> "URYYB" -> "HELLO"
     *
     * @param s the input string
     * @return ROT13 encoded string
     */
    public static String encryptRot13(String s) {
        // ROT13 is just Caesar cipher with shift 13
        return caesarCipher(s, 13);
    }

    /**
     * ROT13 decryption - same operation as encryption!
     * This is because shifting by 13 twice brings you back to the start.
     *
     * @param s the ROT13 encoded string
     * @return the original string
     */
    public static String decryptRot13(String s) {
        // Decryption is the same as encryption for ROT13
        return caesarCipher(s, 13);
    }

    /**
     * Atbash: Replace each letter with its reverse in the alphabet.
     *
     * THE MIRROR ALPHABET:
     *   Plain:    A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
     *   Cipher:   Z Y X W V U T S R Q P O N M L K J I H G F E D C B A
     *
     * Named after the first (Aleph) and last (Tav) letters of the Hebrew alphabet.
     * Fun fact: This cipher is described in the Bible (Jeremiah 25:26)!
     *
     * @param s the input string
     * @return Atbash encoded string
     */
    public static String encryptAtbash(String s) {
        StringBuilder result = new StringBuilder();

        // Process each character
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isUpperCase(c)) {
                // For uppercase: A(65) -> Z(90), B(66) -> Y(89), etc.
                // Formula: 90 - (charCode - 65) = reverse position
                result.append((char) (90 - (c - 65)));
            } else if (Character.isLowerCase(c)) {
                // For lowercase: a(97) -> z(122), b(98) -> y(121), etc.
                result.append((char) (122 - (c - 97)));
            } else {
                // Non-alphabetic characters remain unchanged
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * Atbash decryption - same operation as encryption!
     * Reversing the alphabet twice restores the original.
     *
     * @param s the Atbash encoded string
     * @return the original string
     */
    public static String decryptAtbash(String s) {
        // Atbash is its own inverse
        return encryptAtbash(s);
    }

    /**
     * Caesar cipher: Shift each letter by a fixed number of positions.
     *
     * Named after Julius Caesar, who used a shift of 3 to protect
     * his military messages. He reportedly used this to communicate
     * with his generals during the Gallic Wars (58-50 BC).
     *
     * VISUAL (shift = 3):
     *   Plain:  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
     *   Cipher: D E F G H I J K L M N O P Q R S T U V W X Y Z A B C
     *
     * @param s the input string
     * @param shift the number of positions to shift (1-25)
     * @return Caesar cipher encoded string
     */
    public static String encryptCaesar(String s, int shift) {
        return caesarCipher(s, shift);
    }

    /**
     * Caesar cipher decryption - shift in the opposite direction.
     *
     * @param s the Caesar encoded string
     * @param shift the original shift value used for encryption
     * @return the original string
     */
    public static String decryptCaesar(String s, int shift) {
        // To decrypt, shift in the opposite direction (26 - shift)
        return caesarCipher(s, 26 - shift);
    }

    /**
     * Internal helper method that performs the actual Caesar cipher logic.
     * Used by both ROT13 and Caesar cipher methods.
     *
     * @param s the input string
     * @param shift the shift value
     * @return the shifted string
     */
    private static String caesarCipher(String s, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isUpperCase(c)) {
                // Shift uppercase letters: (charCode - 65 + shift) % 26 + 65
                result.append((char) ((c - 65 + shift) % 26 + 65));
            } else if (Character.isLowerCase(c)) {
                // Shift lowercase letters: (charCode - 97 + shift) % 26 + 97
                result.append((char) ((c - 97 + shift) % 26 + 97));
            } else {
                // Non-alphabetic characters pass through unchanged
                result.append(c);
            }
        }

        return result.toString();
    }

    // ---------------------------------------------------------------
    // MORSE CODE
    // ---------------------------------------------------------------

    // Lookup table: letter -> Morse code
    // Each letter maps to a series of dots (.) and dashes (-)
    private static final HashMap<Character, String> charToMorse = new HashMap<>();
    // Reverse lookup table: Morse code -> letter
    private static final HashMap<String, Character> morseToChar = new HashMap<>();

    // Static initializer - fills in both lookup tables
    static {
        // The complete Morse code alphabet
        // A = ".-", B = "-...", C = "-.-.", etc.
        String[][] morseMap = {
            {"A", ".-"},    {"B", "-..."},  {"C", "-.-."},  {"D", "-.."},
            {"E", "."},     {"F", "..-."},  {"G", "--."},   {"H", "...."},
            {"I", ".."},    {"J", ".---"},  {"K", "-.-"},   {"L", ".-.."},
            {"M", "--"},    {"N", "-."},    {"O", "---"},   {"P", ".--."},
            {"Q", "--.-"},  {"R", ".-."},   {"S", "..."},   {"T", "-"},
            {"U", "..-"},   {"V", "...-"},  {"W", ".--"},   {"X", "-..-"},
            {"Y", "-.--"},  {"Z", "--.."},

            {"0", "-----"}, {"1", ".----"}, {"2", "..---"}, {"3", "...--"},
            {"4", "....-"}, {"5", "....."}, {"6", "-...."}, {"7", "--..."},
            {"8", "---.."}, {"9", "----."}
        };

        // Fill both hashmaps
        for (String[] pair : morseMap) {
            charToMorse.put(pair[0].charAt(0), pair[1]);
            charToMorse.put(Character.toLowerCase(pair[0].charAt(0)), pair[1]);
            morseToChar.put(pair[1], pair[0].charAt(0));
        }
    }

    /**
     * Morse Code encryption: Convert each letter to dots and dashes.
     *
     * THE MORSE CODE ALPHABET:
     *   A .-      B -...    C -.-.    D -..     E .
     *   F ..-.    G --.     H ....    I ..      J .---
     *   K -.-     L .-..    M --      N -.      O ---
     *   P .--.    Q --.-    R .-.     S ...     T -
     *   U ..-     V ...-    W .--     X -..-    Y -.--
     *   Z --..
     *
     * HOW IT WORKS:
     *   Each letter becomes a unique pattern of dots and dashes.
     *   Letters are separated by spaces, words by " / ".
     *
     *   Example: "SOS"
     *   S = ...     O = ---     S = ...
     *   Result: "... --- ..."
     *
     * FUN FACT: Morse code was invented in the 1830s for telegraph
     * communication. Samuel Morse and Alfred Vail developed it so
     * messages could be sent over electrical wires. SOS (... --- ...)
     * became the universal distress signal in 1906.
     *
     * REAL-WORLD USE: Still used in aviation and maritime today.
     * Some amateur radio operators use it daily.
     *
     * @param s the input string
     * @return Morse code representation
     */
    public static String encryptMorse(String s) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ') {
                // Use " / " to separate words (not just letters)
                result.append(" / ");
            } else {
                String morse = charToMorse.get(Character.toUpperCase(c));
                if (morse != null) {
                    result.append(morse);
                    // Add space between letters (not after the last one)
                    if (i < s.length() - 1 && s.charAt(i + 1) != ' ') {
                        result.append(" ");
                    }
                }
                // Skip characters not in the Morse table (punctuation, etc.)
            }
        }

        return result.toString();
    }

    /**
     * Morse Code decryption: Convert dots and dashes back to letters.
     *
     * @param s the Morse code string
     * @return the original text
     */
    public static String decryptMorse(String s) {
        StringBuilder result = new StringBuilder();

        // Split by " / " to get words
        String[] words = s.trim().split(" / ");

        for (int w = 0; w < words.length; w++) {
            // Split each word by spaces to get individual Morse characters
            String[] letters = words[w].split(" ");

            for (String letter : letters) {
                Character c = morseToChar.get(letter);
                if (c != null) {
                    result.append(c);
                }
            }

            // Add space between words (not after the last word)
            if (w < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    // ---------------------------------------------------------------
    // BASE64 ENCODING
    // ---------------------------------------------------------------

    /**
     * Base64 encoding: Convert binary data to ASCII text.
     *
     * HOW IT WORKS:
     *   Input:  3 bytes (24 bits)
     *   Output: 4 Base64 characters (6 bits each)
     *
     *   Example: "Man"
     *   M = 01001101  a = 01100001  n = 01101110
     *   Split into 6-bit groups: 010011 010110 000101 101110
     *   Map to Base64 alphabet:  T      W      F      u
     *   Result: "TWFu"
     *
     * Base64 ALPHABET (64 characters):
     *   A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
     *   a b c d e f g h i j k l m n o p q r s t u v w x y z
     *   0 1 2 3 4 5 6 7 8 9 + /
     *
     * FUN FACT: Base64 is used EVERYWHERE on the internet:
     *   - Email attachments (MIME standard)
     *   - Embedding images directly in HTML/CSS
     *   - Storing complex data in JSON
     *   - HTTP Basic Authentication headers
     *   - Bitcoin addresses use a modified Base58
     *
     * WARNING: Base64 is ENCODING, not encryption!
     * Anyone can decode it. It's like writing in a different alphabet,
     * not like locking something in a safe. It changes the FORMAT
     * of data, not the SECRECY.
     *
     * REAL-WORLD ANALOGY: It's like translating English to French.
     * Anyone who knows French can read it. But it's useful for
     * sending English text through a French-only postal system.
     *
     * @param s the input string
     * @return Base64 encoded string
     */
    public static String encryptBase64(String s) {
        // Java's built-in Base64 encoder does all the heavy lifting
        // getEncoder() returns an encoder, encodeToString() does the work
        return java.util.Base64.getEncoder().encodeToString(s.getBytes());
    }

    /**
     * Base64 decoding: Convert ASCII text back to original data.
     *
     * @param s the Base64 encoded string
     * @return the original string
     */
    public static String decryptBase64(String s) {
        // Decode from Base64 back to bytes, then to string
        return new String(java.util.Base64.getDecoder().decode(s));
    }

    // ---------------------------------------------------------------
    // PIGPEN CIPHER
    // ---------------------------------------------------------------

    /**
     * Pigpen Cipher encryption: Replace letters with geometric symbols.
     *
     * HOW IT WORKS:
     *   Draw two tic-tac-toe grids. Letters get the shape that
     *   surrounds them in the grid.
     *
     *   GRID 1 (plain letters):     GRID 2 (dots for J, K, etc.):
     *   +---+---+---+               +---+---+---+
     *   | A | B | C |               | J | K | L |
     *   +---+---+---+               +---+---+---+
     *   | D | E | F |               | M | N | O |
     *   +---+---+---+               +---+---+---+
     *   | G | H | I |               | P | Q | R |
     *   +---+---+---+               +---+---+---+
     *
     *   The SYMBOL for each letter is the shape of its cell:
     *
     *   A = |    (open right)      J = |.   (open right, with dot)
     *   B = |_   (open bottom)     K = |_.  (open bottom, with dot)
     *   C = |_   (open left)       L = |._  (open left, with dot)
     *   D = -|   (open right)      M = -|.  (open right, with dot)
     *   E = _|_  (open all sides)  N = _|_. (open all sides, with dot)
     *   F = -|   (open left)       O = -|.  (open left, with dot)
     *   G = -|   (open top)        P = -|.  (open top, with dot)
     *   H = -|   (open right)      Q = -|.  (open right, with dot)
     *   I = -|   (open left)       R = -|.  (open left, with dot)
     *
     *   Letters J-R are the SAME shapes but with a dot inside.
     *   This gives us 18 unique symbols for 26 letters.
     *
     *   For simplicity, we use: A-I = shapes, J-R = shapes with
     *   dots, S-Z = special markers.
     *
     * FUN FACT: The Freemasons used a version of this cipher.
     * It's been found in Freemason buildings dating back to the
     * 1700s. Kids also love it because it looks like secret
     * alien writing.
     *
     * REAL-WORLD USE: Primarily educational and recreational.
     * The simplicity makes it easy to crack but fun to learn.
     *
     * @param s the input string
     * @return Pigpen encoded string using symbol notation
     */
    public static String encryptPigpen(String s) {
        // For practical display, we use text-based representations
        // of the Pigpen shapes instead of actual geometric drawings
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = Character.toUpperCase(s.charAt(i));
            result.append(pigpenSymbol(c));
            if (i < s.length() - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    /**
     * Returns the text representation of a Pigpen symbol.
     * Uses simple ASCII art to show the grid shape.
     */
    private static String pigpenSymbol(char c) {
        // Map each letter to a simple shape description
        // Grid 1: A-I (plain), Grid 2: J-R (with dot), S-Z (special)
        switch (c) {
            // GRID 1 - Plain shapes (no dots)
            case 'A': return "|'";     // open right
            case 'B': return "|_";     // open bottom
            case 'C': return "'|";     // open left
            case 'D': return "'|.";    // open right-top
            case 'E': return "+-+";    // open all (cross)
            case 'F': return ".|'";    // open left-bottom
            case 'G': return "'|'";    // open top
            case 'H': return "'.'";    // open bottom
            case 'I': return ".|.";    // center

            // GRID 2 - Same shapes but with a DOT (represented by *)
            case 'J': return "|'*";    // open right + dot
            case 'K': return "|_*";    // open bottom + dot
            case 'L': return "'|*";    // open left + dot
            case 'M': return "'|.*";   // open right-top + dot
            case 'N': return "+-*";    // open all + dot
            case 'O': return ".|'*";   // open left-bottom + dot
            case 'P': return "'|'*";   // open top + dot
            case 'Q': return "'.*";    // open bottom + dot
            case 'R': return ".|.*";   // center + dot

            // GRID 3 - Double lines (for S-Z)
            case 'S': return "||'";    // thick open right
            case 'T': return "||_";    // thick open bottom
            case 'U': return "'||";    // thick open left
            case 'V': return "'||.";   // thick open right-top
            case 'W': return "=+=";    // thick cross
            case 'X': return ".||'";   // thick open left-bottom
            case 'Y': return "'||'";   // thick open top
            case 'Z': return "'..'";   // thick open bottom

            default: return String.valueOf(c); // non-alpha passes through
        }
    }

    /**
     * Pigpen Cipher decryption: Convert symbols back to letters.
     *
     * @param s the Pigpen encoded string
     * @return the original text
     */
    public static String decryptPigpen(String s) {
        // Build reverse lookup table
        HashMap<String, Character> symbolToChar = new HashMap<>();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // We need to re-encrypt each letter to build the reverse map
        for (char c : alphabet.toCharArray()) {
            symbolToChar.put(pigpenSymbol(c), c);
        }

        StringBuilder result = new StringBuilder();
        // Split by spaces to get individual symbols
        String[] symbols = s.trim().split(" ");

        for (String symbol : symbols) {
            Character c = symbolToChar.get(symbol);
            if (c != null) {
                result.append(c);
            } else {
                result.append(symbol); // Unknown symbol, pass through
            }
        }

        return result.toString();
    }

    // ---------------------------------------------------------------
    // PROCESSING - Route to the correct cipher based on user input
    // ---------------------------------------------------------------

    /**
     * Takes the user's input and routes it to the appropriate
     * encryption or decryption method.
     *
     * @param input the InputData object with all user selections
     * @return the processed message
     */
    public static String processMessage(InputData input) {
        // Determine if we're encrypting or decrypting
        boolean encrypt = (input.operation == 1);

        // Route to the correct cipher method
        switch (input.cipherType) {
            case 1: // ROT13
                return encrypt ? encryptRot13(input.message)
                               : decryptRot13(input.message);

            case 2: // Atbash
                return encrypt ? encryptAtbash(input.message)
                               : decryptAtbash(input.message);

            case 3: // Caesar
                return encrypt ? encryptCaesar(input.message, input.shift)
                               : decryptCaesar(input.message, input.shift);

            case 4: // Morse Code
                return encrypt ? encryptMorse(input.message)
                               : decryptMorse(input.message);

            case 5: // Base64
                return encrypt ? encryptBase64(input.message)
                               : decryptBase64(input.message);

            case 6: // Pigpen
                return encrypt ? encryptPigpen(input.message)
                               : decryptPigpen(input.message);

            default:
                return "Error: Unknown cipher type";
        }
    }

    // ---------------------------------------------------------------
    // OUTPUT - Display results to the user
    // ---------------------------------------------------------------

    /**
     * Prints the result in a nicely formatted way.
     *
     * @param input the original InputData (for labeling)
     * @param result the processed message
     */
    public static void printResult(InputData input, String result) {
        // Map cipher type numbers to names for display
        String[] cipherNames = {
            "", "ROT13", "Atbash", "Caesar",
            "Morse Code", "Base64", "Pigpen"
        };

        String operation = (input.operation == 1) ? "Encrypted" : "Decrypted";
        String cipherName = cipherNames[input.cipherType];

        System.out.println("\n============================================");
        System.out.println(operation + " message (" + cipherName + "):");
        System.out.println("============================================");
        System.out.println(result);
        System.out.println("============================================");
    }

    /**
     * Prints the welcome banner when the program starts.
     */
    public static void printWelcome() {
        System.out.println("============================================");
        System.out.println("         Welcome to the Cypher Tool!        ");
        System.out.println("============================================");
        System.out.println();
        System.out.println("  6 ciphers to explore:");
        System.out.println("    1. ROT13      - The classic 13-shift");
        System.out.println("    2. Atbash     - The alphabet mirror");
        System.out.println("    3. Caesar     - Julius Caesar's favorite");
        System.out.println("    4. Morse Code - Dots and dashes");
        System.out.println("    5. Binary     - The computer language");
        System.out.println("    6. Pigpen     - Spy shapes");
        System.out.println();
        System.out.println("  Type 'exit' at any time to quit.");
        System.out.println("============================================\n");
    }

    // ---------------------------------------------------------------
    // INNER CLASS - Holds all input data in one place
    // ---------------------------------------------------------------

    /**
     * InputData is a simple container class that holds all the
     * information the user has entered.
     *
     * Think of it like a form that gets filled out:
     *   - What operation? (encrypt/decrypt)
     *   - Which cipher? (ROT13, Atbash, etc.)
     *   - What message?
     *   - Any extra parameters? (shift value)
     */
    public static class InputData {
        public int operation;     // 1 = encrypt, 2 = decrypt
        public int cipherType;    // 1-6 for each cipher
        public String message;    // The text to process
        public int shift;         // For Caesar cipher (1-25)

        /**
         * Constructor - creates a new InputData with all values.
         */
        public InputData(int operation, int cipherType, String message,
                         int shift) {
            this.operation = operation;
            this.cipherType = cipherType;
            this.message = message;
            this.shift = shift;
        }
    }
}
