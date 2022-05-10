import java.io.FileNotFoundException;
import java.util.*;

public class SimpleTree {
    static class Node {
        private String value;
        private Node leftChild;
        private Node rightChild;

        public void printNode() {
            System.out.println(" Выбранный узел имеет значение :" + value);
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(final String value) {
            this.value = value;
        }

        public Node getLeftChild() {
            return this.leftChild;
        }

        public void setLeftChild(final Node leftChild) {
            this.leftChild = leftChild;
        }

        public Node getRightChild() {
            return this.rightChild;
        }

        public void setRightChild(final Node rightChild) {
            this.rightChild = rightChild;
        }

        @Override
        public String toString() {
            return "Node { value = " + value + ", leftChild = " + leftChild + ", rightChild = " + rightChild + '}';
        }
    }

    private Node rootNode;

    public SimpleTree() { // Пустое дерево
        rootNode = null;
    }

    public Node findNodeByValue(String value) {
        Node currentNode = rootNode;

        while (!currentNode.getValue().equals(value)) {
            if (value.compareTo(currentNode.getValue()) < 0) {
                currentNode = currentNode.getLeftChild();
            } else {
                currentNode = currentNode.getRightChild();
            }
            if (currentNode == null) {
                return null;
            }
        }
        return currentNode;
    }

    public void insertNode(String value) {
        Node node = new Node();
        node.setValue(value);
        if (rootNode == null) {
            rootNode = node;
        } else {
            Node currentNode = rootNode;
            Node parentNode;
            while (true) {
                parentNode = currentNode;
                if (value.equals(currentNode.getValue())) {
                    return;
                } else if (value.compareTo(currentNode.getValue()) < 0) {
                    currentNode = currentNode.getLeftChild();
                    if (currentNode == null) {
                        parentNode.setLeftChild(node);
                        return;
                    }
                } else {
                    currentNode = currentNode.getRightChild();
                    if (currentNode == null) {
                        parentNode.setRightChild(node);
                        return;
                    }
                }
            }
        }
    }

    public boolean deleteNode(String value) {
        Node currentNode = rootNode;
        Node parentNode = rootNode;
        boolean isLeftChild = true;

        while (!currentNode.getValue().equals(value)) {
            parentNode = currentNode;
            if (value.compareTo(currentNode.getValue()) < 0) {
                isLeftChild = true;
                currentNode = currentNode.getLeftChild();
            } else {
                isLeftChild = false;
                currentNode = currentNode.getRightChild();
            }
            if (currentNode == null)
                return false;
        }

        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
            if (currentNode == rootNode)
                rootNode = null;
            else if (isLeftChild)
                parentNode.setLeftChild(null);
            else
                parentNode.setRightChild(null);
        } else if (currentNode.getRightChild() == null) {
            if (currentNode == rootNode)
                rootNode = currentNode.getLeftChild();
            else if (isLeftChild)
                parentNode.setLeftChild(currentNode.getLeftChild());
            else
                parentNode.setRightChild(currentNode.getLeftChild());
        } else if (currentNode.getLeftChild() == null) {
            if (currentNode == rootNode)
                rootNode = currentNode.getRightChild();
            else if (isLeftChild)
                parentNode.setLeftChild(currentNode.getRightChild());
            else
                parentNode.setRightChild(currentNode.getRightChild());
        } else {
            Node heir = receiveHeir(currentNode);
            if (currentNode == rootNode)
                rootNode = heir;
            else if (isLeftChild)
                parentNode.setLeftChild(heir);
            else
                parentNode.setRightChild(heir);
        }
        return true;
    }


    private Node receiveHeir(Node node) {
        Node parentNode = node;
        Node heirNode = node;
        Node currentNode = node.getRightChild();
        while (currentNode != null) {
            parentNode = heirNode;
            heirNode = currentNode;
            currentNode = currentNode.getLeftChild();
        }

        if (heirNode != node.getRightChild()) {
            parentNode.setLeftChild(heirNode.getRightChild());
            heirNode.setRightChild(node.getRightChild());
        }
        return heirNode;
    }

    public void printTree() {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 2-мя параметрами)
        class Inner {
            void preOrderVisit(Node node, List<String> parts) {
                if (node == null) {
                    return;
                }

                String word = node.getValue();
                printString(parts, word);

                if (parts.size() > 0) {
                    if (parts.get(parts.size() - 1).equals("└──")) {
                        parts.set(parts.size() - 1, "   ");
                    }
                    if (parts.get(parts.size() - 1).equals("├──")) {
                        parts.set(parts.size() - 1, "│  ");
                    }
                }

                int length = word.length() / "а".length();
                String spaces = createStringSpaces(length - 1);
                parts.add(spaces);

                if (node.getLeftChild() != null) {
                    if (node.getRightChild() != null) {
                        parts.add("├──");
                    } else {
                        parts.add("└──");
                    }
                } else {
                    if (node.getRightChild() != null) {
                        parts.add("│");
                        printString(parts, "");
                    }
                }
                preOrderVisit(node.getLeftChild(), new ArrayList<>(parts));

                if (node.getRightChild() != null) {
                    parts.set(parts.size() - 1, "└──");
                }
                preOrderVisit(node.getRightChild(), new ArrayList<>(parts));
            }
        }
        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().preOrderVisit(rootNode, new ArrayList<>());
    }

    private void printString(List<String> parts, String word) {
        for (String part : parts) {
            System.out.print(part);
        }
        System.out.print(word);
        System.out.println();
    }

    private String createStringSpaces(int quantity) {
        return "" + " ".repeat(Math.max(0, quantity));
    }


    public void insertWordsOfTextFromFile(String fileName) throws FileNotFoundException {
        List<String> words = ListUtils.readWordsForTreeFromFile(fileName);
        for (String word : words) {
            insertNode(word);
        }
    }
}

