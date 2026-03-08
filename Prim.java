import java.util.*;

public class Prim {

    private String input;  // เช่น "e1-e2-4,e1-e3-2,e2-e3-1,e3-e4-3"

    private Map<String, List<int[]>> graph = new HashMap<>();
    private List<String> nodeList = new ArrayList<>();
    private Map<String, Integer> nodeIndex = new LinkedHashMap<>();

    public Prim(String input) {
        this.input = input;
    }

    public void parseInput() {
        String[] edges = input.split(",");
        for (String edge : edges) {
            String[] parts = edge.trim().split("-");
            if (parts.length != 3) {
                System.out.println("รูปแบบผิด: " + edge + " (ต้องเป็น node1-node2-weight)");
                continue;
            }
            String u = parts[0].trim();
            String v = parts[1].trim();
            int w;
            try {
                w = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.out.println("weight ต้องเป็นตัวเลข: " + edge);
                continue;
            }

            if (!nodeIndex.containsKey(u)) { nodeIndex.put(u, nodeList.size()); nodeList.add(u); }
            if (!nodeIndex.containsKey(v)) { nodeIndex.put(v, nodeList.size()); nodeList.add(v); }

            // เดินไปกลับได้ (undirected)
            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(new int[]{nodeIndex.get(v), w});
            graph.computeIfAbsent(v, k -> new ArrayList<>()).add(new int[]{nodeIndex.get(u), w});
        }
    }

    public void run() {
        parseInput();
        if (nodeList.isEmpty()) { System.out.println("ไม่มีข้อมูล"); return; }

        int n = nodeList.size();
        int[] key     = new int[n];
        int[] parent  = new int[n];
        boolean[] inMST = new boolean[n];

        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        key[0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, 0});

        int totalWeight = 0;
        List<String> mstEdges = new ArrayList<>();

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[1];
            if (inMST[u]) continue;
            inMST[u] = true;

            if (parent[u] != -1) {
                mstEdges.add(nodeList.get(parent[u]) + " -- " + nodeList.get(u) + "  (weight: " + key[u] + ")");
                totalWeight += key[u];
            }

            for (int[] neighbor : graph.getOrDefault(nodeList.get(u), new ArrayList<>())) {
                int v = neighbor[0], w = neighbor[1];
                if (!inMST[v] && w < key[v]) {
                    key[v] = w;
                    parent[v] = u;
                    pq.offer(new int[]{w, v});
                }
            }
        }

        System.out.println("=== Prim: Minimum Spanning Tree ===");
        mstEdges.forEach(e -> System.out.println("  " + e));
        System.out.println("\nTotal Weight: " + totalWeight);

        long visited = 0;
        for (boolean b : inMST) if (b) visited++;
        if (visited < n) System.out.println("⚠ กราฟไม่ connected (" + (n - visited) + " node ที่เข้าไม่ถึง)");
    }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
}