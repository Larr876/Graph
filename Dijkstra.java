import java.util.*;

public class Dijkstra {

    private String input; 
    private Map<String, List<int[]>> graph = new HashMap<>();
    private List<String> nodeList = new ArrayList<>();
    private Map<String, Integer> nodeIndex = new LinkedHashMap<>();

    public Dijkstra(String input) {
        this.input = input;
    }

    public void parseInput() {
        String[] edges = input.split(",");
        for (String edge : edges) {
            String[] parts = edge.trim().split("-");
            if (parts.length != 3) continue;

            String u = parts[0].trim();
            String v = parts[1].trim();
            int w;
            try {
                w = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                continue;
            }

            if (!nodeIndex.containsKey(u)) { nodeIndex.put(u, nodeList.size()); nodeList.add(u); }
            if (!nodeIndex.containsKey(v)) { nodeIndex.put(v, nodeList.size()); nodeList.add(v); }

            graph.computeIfAbsent(u, k -> new ArrayList<>()).add(new int[]{nodeIndex.get(v), w});
            graph.computeIfAbsent(v, k -> new ArrayList<>()).add(new int[]{nodeIndex.get(u), w});
        }
    }

    public void run(String startNode) {
        parseInput();
        if (!nodeIndex.containsKey(startNode)) {
            System.out.println("ไม่พบ Node เริ่มต้น: " + startNode);
            return;
        }

        int n = nodeList.size();
        int startIdx = nodeIndex.get(startNode);
        int[] dist = new int[n];
        int[] parent = new int[n]; 
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[startIdx] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, startIdx});

        while (!pq.isEmpty()) {
            int u = pq.poll()[1];
            if (visited[u]) continue;
            visited[u] = true;

            for (int[] neighbor : graph.getOrDefault(nodeList.get(u), new ArrayList<>())) {
                int v = neighbor[0], w = neighbor[1];
                if (!visited[v] && dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u; 
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        printResults(startNode, dist, parent);
    }

    private void printResults(String startNode, int[] dist, int[] parent) {
        for (int i = 0; i < nodeList.size(); i++) {
            String targetNode = nodeList.get(i);
            
            if (targetNode.equals(startNode)) continue;

            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println("{" + startNode + ", " + targetNode + "} = Unreachable");
            } else {
                List<String> path = new ArrayList<>();
                for (int curr = i; curr != -1; curr = parent[curr]) {
                    path.add(nodeList.get(curr));
                }
                Collections.reverse(path);
                
                System.out.println("{" + String.join(", ", path) + "} = " + dist[i]);
            }
        }
    }

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
}