package Tree;

public class treeNode {
    String data;
    treeNode venstre, hoyre;
    int teller;
    public treeNode(String s){
        data = s;
        venstre = hoyre = null;
        teller = 1;
    }
    public void print(){
        System.out.println("\n" + data + " " + teller);
    }
}
