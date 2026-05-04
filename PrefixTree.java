import java.util.Scanner;

public class PrefixTree {

    private Node root;
    private int preOrderIndex;

    private class Node {
        char character;
        Node left, right;
        public Node(char character) {
            this.character = character;
        }
    }

    public PrefixTree(String preorder) {
        this.preOrderIndex = 0;
        this.root = build(preorder);
    }

    private Node build(String previous) {
        if (preOrderIndex >= previous.length()) {
            return null;
        }

        char curchar = previous.charAt(preOrderIndex++);
        Node node = new Node(curchar);

        if (curchar == '*') {
            node.left = build(previous);
            node.right = build(previous);
        }

        return node;
    }



    public void getEncodings() {
        System.out.print("character     bits      encoding\n");
        System.out.print("----------------------------------\n");
        trav(root, "");
    }

    private void trav(Node n, String p) {
        if (n == null) {
            return;
        }

        if (n.character != '*') {
            System.out.printf("%-10c%-10d%s%n", n.character, p.length(), p);
        }

        trav(n.left, p + "0");
        trav(n.right, p + "1");
    }


    public void decode(String compressedMessage) {
        int numbits = compressedMessage.length();
        int numchars = 0;
        Node cur = root;

        System.out.println();

        for (int i = 0; i < numbits; i++) {
            char bit = compressedMessage.charAt(i);

            if (bit == '0') {
                cur = cur.left;
            } else if (bit == '1') {
                cur = cur.right;
            }

            if (cur.character != '*') {
                System.out.print(cur.character);
                numchars++;
                cur = root;
            }
        }

        System.out.println();

        double originalbits = numchars * 8;
        double compressionratio = (numbits / originalbits) * 100;

        System.out.print("number of bits: " + numbits +'\n');
        System.out.print("number of characters: " + numchars+'\n');
        System.out.print("compression ratio: " + compressionratio + " %\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextLine()) {
            String preorder = scanner.nextLine();
            PrefixTree tree = new PrefixTree(preorder);

            tree.getEncodings();

            if (scanner.hasNextLine()) {
                String compressed = scanner.nextLine();

            ////
                tree.decode(compressed);
            }
        }
        scanner.close();
    }
}
