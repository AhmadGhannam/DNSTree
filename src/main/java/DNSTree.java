import java.util.Scanner;

class DNSTree {
    private DomainNode root;

    public DNSTree() {
        root = new DomainNode(""); // Root node
    }

    public void addDomain(String domain) {
        String[] parts = domain.split("\\.");
        DomainNode current = root;

        for (int i = parts.length - 1; i >= 0; i--) {
            current = current.addChild(parts[i]);
        }
    }

    public boolean searchDomain(String domain) {
        String[] parts = domain.split("\\.");
        DomainNode current = root;

        for (int i = parts.length - 1; i >= 0; i--) {
            current = current.getChild(parts[i]);
            if (current == null) {
                return false;
            }
        }
        return true;
    }

    public boolean deleteDomain(String domain) {
        String[] parts = domain.split("\\.");
        return deleteHelper(root, parts, parts.length - 1);
    }

    private boolean deleteHelper(DomainNode current, String[] parts, int index) {
        if (index < 0) {
            return false;
        }

        DomainNode child = current.getChild(parts[index]);
        if (child == null) {
            return false;
        }

        if (index == 0) {
            if (!child.hasChildren()) {
                return current.removeChild(parts[index]);
            }
            return false;
        }

        boolean canDelete = deleteHelper(child, parts, index - 1);
        if (canDelete && !child.hasChildren()) {
            current.removeChild(parts[index]);
        }
        return canDelete;
    }

    public void printSubtree(String domain) {
        String[] parts = domain.split("\\.");
        DomainNode current = root;

        for (int i = parts.length - 1; i >= 0; i--) {
            current = current.getChild(parts[i]);
            if (current == null) {
                System.out.println("Domain not found: " + domain);
                return;
            }
        }

        printSubtreeHelper(current, 0);
    }

    private void printSubtreeHelper(DomainNode node, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(node.getName());

        for (DomainNode child : node.getChildren().values()) {
            printSubtreeHelper(child, level + 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DNSTree tree = new DNSTree();

        while (true) {
            System.out.println("Choose an option: ");
            System.out.println("1. Add Domain");
            System.out.println("2. Search Domain");
            System.out.println("3. Delete Domain");
            System.out.println("4. Print DNS Tree for Domain");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter domain to add:");
                    String domainToAdd = scanner.nextLine();
                    tree.addDomain(domainToAdd);
                    System.out.println("Domain added: " + domainToAdd);
                    break;
                case 2:
                    System.out.println("Enter domain to search:");
                    String domainToSearch = scanner.nextLine();
                    boolean found = tree.searchDomain(domainToSearch);
                    System.out.println("Domain " + (found ? "found" : "not found") + ": " + domainToSearch);
                    break;
                case 3:
                    System.out.println("Enter domain to delete:");
                    String domainToDelete = scanner.nextLine();
                    boolean deleted = tree.deleteDomain(domainToDelete);
                    System.out.println("Domain " + (deleted ? "deleted" : "not found") + ": " + domainToDelete);
                    break;
                case 4:
                    System.out.println("Enter domain to print DNS tree:");
                    String domainToPrint = scanner.nextLine();
                    System.out.println("DNS Tree for domain: " + domainToPrint);
                    tree.printSubtree(domainToPrint);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
}