import java.util.Scanner;

public class Graph {
    private String input;
    private Prim prim;
    private Kruskal krus;
    private Dijkstra dij;
    private int mode;
    Scanner sc = new Scanner(System.in);

    public Graph() {
        Menu();
    }
    public Prim getPrim() { return prim; }
    public Kruskal getKruskal() { return krus; }
    public void setPrim(Prim p) { prim = p; }
    public void setKruskal(Kruskal k) { krus = k; }
    public void setDijkstra(Dijkstra d) {dij = d; }
    public Dijkstra getDijkstra() {return  dij; }

    public void Menu() {
        while (true) { 
            System.out.println("=== Select graph type ===");
            System.out.println("1. Prim (Minimum spaning tree)");
            System.out.println("2. Kruskal (Minimum spaning tree)");
            System.out.println("3. Dijkstra (Shortest path)");
            System.out.println("4. Show all");
            System.out.println("5. Exit");
            try {
            System.out.print("Choose: ");
                String m = sc.nextLine();
                mode = Integer.parseInt(m);
            } catch (Exception e) {
                System.out.println("Wrong input");
            }
            if (mode < 0 || mode > 5) {
                System.out.println("Wrong input");
                continue;
            }
            if (mode == 5) {break;}
            System.out.println();
            Mode(mode);
        }
    }
    public void Mode(int n) {
        setInput();
        if (n == 1 || n == 4) {
            prim = new Prim(input);
            System.out.print("Enter start node(Prim): ");
            String start1 = sc.nextLine();
            prim.run(start1);
            System.out.println("\n-----------------------");
        }
        if (n == 2 || n == 4) {
            krus = new Kruskal(input);
            krus.run();
            System.out.println("\n-----------------------");
        }
        if (n == 3 || n == 4 ) {
            dij = new Dijkstra(input);
            System.out.print("Enter start node(Dijkstra): ");
            String start2 = sc.nextLine();
            dij.run(start2);
            System.out.println("\n-----------------------");
        }
        System.out.println();
    }
    public void setInput() {
        System.out.println("Enter edges (format: node1-node2-weight, separated by commas)");
        System.out.println("Example: e1-e2-4,e1-e3-2,e2-e3-1,e2-e4-5,e3-e4-3");
        System.out.print("Input: ");
        input = sc.nextLine().trim();
    }
}