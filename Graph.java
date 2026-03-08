public class Graph {
    private String input;
    private Prim prim;
    private Kruskal krus;

    public Graph() {
        input = "e1-e2-4,e1-e3-2,e2-e3-1,e2-e4-5,e3-e4-3";
        prim = new Prim(input);
        krus = new Kruskal(input);
        showResult();
    }
    public Prim getPrim() {
        return prim;
    }
    public Kruskal getKruskal() {
        return krus; 
    }
    public void setPrim(Prim p) {
        prim = p;
    }
    public void setKruskal(Kruskal k) {
        krus = k;
    }
    public void showResult() {
        prim.run();
        System.out.println("\n-----------------------\n");
        krus.run();
    }
}
