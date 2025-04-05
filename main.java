package lab1;
import java.util.*;

class BigVigenere {
    private int[] key;
    private char[][] alphabet;
    private final int ALPHABET_SIZE = 64;
    private final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ñÑ";

    public BigVigenere() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la clave numérica: ");
        String numericKey = scanner.nextLine();
        this.key = convertToKeyArray(numericKey);
        this.alphabet = generateAlphabetMatrix();
    }

    public BigVigenere(String numericKey) {
        this.key = convertToKeyArray(numericKey);
        this.alphabet = generateAlphabetMatrix();
    }

    private int[] convertToKeyArray(String numericKey) {
        int[] result = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            result[i] = Character.getNumericValue(numericKey.charAt(i)) % ALPHABET_SIZE;
        }
        return result;
    }

    private char[][] generateAlphabetMatrix() {
        char[][] matrix = new char[ALPHABET_SIZE][ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                matrix[i][j] = CHAR_SET.charAt((i + j) % ALPHABET_SIZE);
            }
        }
        return matrix;
    }

    public String encrypt(String message) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            int row = CHAR_SET.indexOf(message.charAt(i));
            int col = key[i % key.length];
            encrypted.append(alphabet[row][col]);
        }
        return encrypted.toString();
    }

    public String decrypt(String encryptedMessage) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < encryptedMessage.length(); i++) {
            int col = key[i % key.length];
            int row = -1;
            for (int r = 0; r < ALPHABET_SIZE; r++) {
                if (alphabet[r][col] == encryptedMessage.charAt(i)) {
                    row = r;
                    break;
                }
            }
            decrypted.append(CHAR_SET.charAt(row));
        }
        return decrypted.toString();
    }

    public void reEncrypt() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el mensaje encriptado: ");
        String encrypted = scanner.nextLine();
        String decrypted = decrypt(encrypted);

        System.out.print("Ingrese la nueva clave numérica: ");
        String newKey = scanner.nextLine();

        this.key = convertToKeyArray(newKey);
        String reEncrypted = encrypt(decrypted);
        System.out.println("Nuevo mensaje encriptado: " + reEncrypted);
    }

    public char search(int position) {
        if (position < 0 || position >= ALPHABET_SIZE * ALPHABET_SIZE) return '?';
        int row = position / ALPHABET_SIZE;
        int col = position % ALPHABET_SIZE;
        return alphabet[row][col];
    }

    public char optimalSearch(int position) {
        return CHAR_SET.charAt(position % ALPHABET_SIZE);
    }

    // Para pruebas
    public static void main(String[] args) {
        BigVigenere cipher = new BigVigenere("123456");
        String message = "HolaMundo123ñÑ";
        String encrypted = cipher.encrypt(message);
        String decrypted = cipher.decrypt(encrypted);

        System.out.println("Mensaje original: " + message);
        System.out.println("Mensaje encriptado: " + encrypted);
        System.out.println("Mensaje desencriptado: " + decrypted);
    }
}
