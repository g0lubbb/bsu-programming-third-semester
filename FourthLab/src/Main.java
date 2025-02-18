class Tree<T extends Comparable<T>> {

    class Node {
        T value;
        Node left, right;

        Node(T value) {
            this.value = value;
            left = right = null;
        }
    }
    Node root;

    Tree(T value) {
        root = new Node(value);
    }

    // Итеративная версия вставки
    public void insert(T value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;


        while (true) {
            parent = current;
            int compare = value.compareTo(current.value);
            if (compare < 0) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return;
                }
            } else if (compare > 0) {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return;
                }
            } else {
                return;
            }
        }
    }

    // Итеративная версия удаления
    public void delete(T value) {
        root = deleteIter(root, value);
    }

    private Node deleteIter(Node root, T value) {
        Node parent = null;
        Node current = root;

        // Поиск узла, который нужно удалить
        while (current != null && !current.value.equals(value)) {
            parent = current;
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        // Если узел не найден
        if (current == null) {
            return root;
        }

        // Узел без потомков
        if (current.left == null && current.right == null) {
            if (current != root) {
                if (parent.left == current) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else {
                root = null;
            }
        }

        // Узел с одним потомком
        else if (current.left == null) {
            if (current != root) {
                if (parent.left == current) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
            } else {
                root = current.right;
            }
        } else if (current.right == null) {
            if (current != root) {
                if (parent.left == current) {
                    parent.left = current.left;
                } else {
                    parent.right = current.left;
                }
            } else {
                root = current.left;
            }
        }

        // Узел с двумя потомками
        else {
            Node successor = findMinIter(current.right);
            T successorValue = successor.value;
            deleteIter(root, successorValue);
            current.value = successorValue;
        }

        return root;
    }

    // Итеративная версия поиска минимального узла
    private Node findMinIter(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public T find(T value) {
        Node current = root;
        while (current != null) {
            int compare = value.compareTo(current.value);
            if (compare == 0) {
                return current.value;
            } else if (compare < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public void preOrder() {
        preOrderRec(root);
        System.out.println();
    }

    private void preOrderRec(Node node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preOrderRec(node.left);
            preOrderRec(node.right);
        }
    }

    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(Node node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.print(node.value + " ");
            inOrderRec(node.right);
        }
    }

    public void postOrder() {
        postOrderRec(root);
        System.out.println();
    }

    private void postOrderRec(Node node) {
        if (node != null) {
            postOrderRec(node.left);
            postOrderRec(node.right);
            System.out.print(node.value + " ");
        }
    }
}
public class Main {
    public static void main(String[] args) {
        // Работа с деревом Integer
        Tree<Integer> tree = new Tree<>(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        System.out.println("In-order после вставки (Integer):");
        tree.inOrder();

        tree.delete(70);
        System.out.println("In-order после удаления 70 (Integer):");
        tree.inOrder();

        Integer searchResult = tree.find(40);
        if (searchResult != null) {
            System.out.println("Элемент 40 найден в дереве (Integer).");
        } else {
            System.out.println("Элемент 40 не найден в дереве (Integer).");
        }

        System.out.print("Pre-order (Integer): ");
        tree.preOrder();

        System.out.print("In-order (Integer): ");
        tree.inOrder();

        System.out.print("Post-order (Integer): ");
        tree.postOrder();

        // Работа с деревом Person
        Tree<Person> personTree = new Tree<>(new Person("Alex", 50));
        personTree.insert(new Person("Charlie", 30));
        personTree.insert(new Person("Sam", 70));
        personTree.insert(new Person("John", 20));
        personTree.insert(new Person("Paul", 40));
        personTree.insert(new Person("Chris", 60));
        personTree.insert(new Person("Pat", 80));

        System.out.println("\nIn-order после вставки (Person):");
        personTree.inOrder();

        personTree.delete(new Person("Sam", 70));
        System.out.println("In-order после удаления Sam (70) (Person):");
        personTree.inOrder();

        Person personSearchResult = personTree.find(new Person("Paul", 40));
        if (personSearchResult != null) {
            System.out.println("Элемент Paul (40) найден в дереве (Person).");
        } else {
            System.out.println("Элемент Paul (40) не найден в дереве (Person).");
        }

        System.out.print("Pre-order (Person): ");
        personTree.preOrder();

        System.out.print("In-order (Person): ");
        personTree.inOrder();

        System.out.print("Post-order (Person): ");
        personTree.postOrder();
    }
}

class Person implements Comparable<Person> {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
//
    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}
//