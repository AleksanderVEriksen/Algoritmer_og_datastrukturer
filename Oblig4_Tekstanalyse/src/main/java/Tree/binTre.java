package Tree;

public class binTre {

    treeNode root;

    public binTre() {
        root = null;
    }
    // Tok den fra http://www.it.hiof.no/algdat/kode/binarySearchTree.java
    public void insert(String ord)  {
        treeNode newNode = new treeNode(ord);
        if (isEmpty())
        {
            root = newNode;
            //System.out.println("Root = " + root.data);
            return;
        }
        treeNode current = root;
        while (true)
        {
            // Ser om ordet du innsetter finnes fra før
            if(ord.compareTo(current.data) == 0){
                current.teller++;
               // System.out.println("Frekvens update for " + current.data);
                break;
            }
            // Ser om ordet du innsetter er av lavere verdi enn bladnoden(eller root) som blir sammenliknet.
            else if (ord.compareTo(current.data) < 0)
            {
                if (current.venstre == null)
                {
                    current.venstre = newNode;
                    //System.out.println(current.venstre.data + " er den nye venstre node for " + current.data);
                    break;
                }
                else
                    current = current.venstre;
            }
            // Om den er av høyere verdi.
            else
            {
                if (current.hoyre == null)
                {
                    current.hoyre = newNode;
                    //System.out.println(current.hoyre.data + " er den nye høyre node for " + current.data);
                    break;
                }
                else
                    current = current.hoyre;
            }
        }
    }

    // Inspirasjon fra http://www.it.hiof.no/algdat/kode/binarySearchTree.java (helt nederst)
    public void printInorder() {
        System.out.print("Inorder: ");
        printInorder(root);
        System.out.println("\n");
    }

    public void printInorder(treeNode node) {
        // Om root ikke er null
        if (root != null) {
            // om både venstre og høyre subtre er tom - print noden man er på
            if(node.venstre == null && node.hoyre == null){
                node.print();
            }
            // om høyre subtre er tom - ta et rekursiv kall på venstre node og print så ut noden man er på
            else if(node.hoyre == null) {
                printInorder(node.venstre);
                node.print();
            }
            // om venstre subtre er tom - ta et rekursiv kall på hoyre node og print så ut noden man er på
            else if(node.venstre == null) {
                node.print();
                printInorder(node.hoyre);
            }
            // Om begge er tilgjengelige, ta rekursivt på venstre, print den man er på og tilslutt rekursiv på hoyre
            else{
                printInorder(node.venstre);
                node.print();
                printInorder(node.hoyre);
            }

        }
    }

    public boolean isEmpty() {
        return (root == null);
    }
}
