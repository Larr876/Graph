import java.util.*;

public class Kruskal {

    private String input;  
    private List<int[]> edges = new ArrayList<>();
    private List<String>         nodeList  = new ArrayList<>();
    private Map<String, Integer> nodeIndex = new LinkedHashMap<>();
    private int[] parent;
    private int[] rank;

    public Kruskal(String input) {
        this.input = input;
    }
    
    public void parseInput() {
        String[] rawEdges = input.split(",");
        for (String edge : rawEdges) {
            String[] parts = edge.trim().split("-");
            if (parts.length != 3) {
                System.out.println("Invalid format: " + edge + " (must be node1-node2-weight)");
                continue;
            }
            String u = parts[0].trim();
            String v = parts[1].trim();
            int w;
            try {
                w = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.out.println("Weight must be a number: " + edge);
                continue;
            }

            if (!nodeIndex.containsKey(u)) { nodeIndex.put(u, nodeList.size()); nodeList.add(u); }
            if (!nodeIndex.containsKey(v)) { nodeIndex.put(v, nodeList.size()); nodeList.add(v); }

            edges.add(new int[]{ nodeIndex.get(u), nodeIndex.get(v), w });
        }
    }

    private int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }
    
    private boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false;  

        if (rank[px] < rank[py]) { int t = px; px = py; py = t; }
        parent[py] = px;
        if (rank[px] == rank[py]) rank[px]++;
        return true;
    }
    
    public void run() {
        parseInput();
        if (nodeList.isEmpty()) { System.out.println("No data found."); return; }

        int n = nodeList.size();
        
        parent = new int[n];
        rank   = new int[n];
        for (int i = 0; i < n; i++) { parent[i] = i; rank[i] = 0; }
        
        edges.sort(Comparator.comparingInt(e -> e[2]));

        int totalWeight  = 0;
        int mstEdgeCount = 0;
        List<String> mstEdges = new ArrayList<>();

        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            if (union(u, v)) {
                mstEdges.add(nodeList.get(u) + " -- " + nodeList.get(v) + "  (weight: " + w + ")");
                totalWeight  += w;
                mstEdgeCount++;
            } 
            if (mstEdgeCount == n - 1) break;  
        }

        System.out.println("=== Kruskal: Minimum Spanning Tree ===");
        mstEdges.forEach(e -> System.out.println("  " + e));
        System.out.println("\nTotal Weight: " + totalWeight);

        
        Set<Integer> roots = new HashSet<>();
        for (int i = 0; i < n; i++) roots.add(find(i));
        if (roots.size() > 1)
            System.out.println("⚠ Graph is not connected (" + (roots.size() - 1) + " disconnected component(s))");
    }

    public String getInput()             { return input;       }
    public void   setInput(String input) { this.input = input; }
}