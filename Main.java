/**
 * Main - Entry point for the Cypher Tool application.
 *
 * This class simply calls CypherTool.main() to start the program.
 * This separation allows CypherTool to be tested independently
 * and keeps the entry point clean and simple.
 *
 * To run: javac Main.java CypherTool.java && java Main
 */
public class Main {
    public static void main(String[] args) {
        CypherTool.main(args);
    }
}
