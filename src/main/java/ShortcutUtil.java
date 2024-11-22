import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
public class ShortcutUtil {
        // Class to represent an edge in the graph
    class Edge {
        int dest;
        int weight;

        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    // Class to store the result (distance and route)
    class Result {
        int distance;
        List<Integer> route;

        public Result(int distance, List<Integer> route) {
            this.distance = distance;
            this.route = route;
        }

        @Override
        public String toString() {
            return "Distance: " + distance + ", Route: " + route;
        }
    }

    // Graph class to handle the adjacency list and Dijkstra's algorithm
    class Graph {
        private final Map<Integer, List<Edge>> adjList;

        public Graph() {
            adjList = new HashMap<>();
        }

        // Add edges for a bidirectional graph
        public void addEdge(int src, int dest, int weight) {
            adjList.putIfAbsent(src, new ArrayList<>());
            adjList.putIfAbsent(dest, new ArrayList<>());
            adjList.get(src).add(new Edge(dest, weight));
            adjList.get(dest).add(new Edge(src, weight)); // Add reverse edge
        }

        // Dijkstra's algorithm to find the shortest path and distance
        public String dijkstra(int start, int end) {
            // Priority queue for {node, distance}, sorted by distance
            PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
            Map<Integer, Integer> dist = new HashMap<>();
            Map<Integer, Integer> parent = new HashMap<>();

            // Initialize distances
            for (int node : adjList.keySet()) {
                dist.put(node, Integer.MAX_VALUE);
            }
            dist.put(start, 0);
            pq.offer(new AbstractMap.SimpleEntry<>(start, 0));
            parent.put(start, null); // Start node has no parent

            while (!pq.isEmpty()) {
                Map.Entry<Integer, Integer> current = pq.poll();
                int currentNode = current.getKey();
                int currentDist = current.getValue();

                if (currentDist > dist.get(currentNode)) continue;

                for (Edge neighbor : adjList.get(currentNode)) {
                    int newDist = currentDist + neighbor.weight;
                    if (newDist < dist.get(neighbor.dest)) {
                        dist.put(neighbor.dest, newDist);
                        pq.offer(new AbstractMap.SimpleEntry<>(neighbor.dest, newDist));
                        parent.put(neighbor.dest, currentNode); // Update parent
                    }
                }
            }

            // If the end node is unreachable
            if (!dist.containsKey(end) || dist.get(end) == Integer.MAX_VALUE) {
                return new Result(-1, Collections.emptyList()).toString();
            }

            // Reconstruct the route
            List<Integer> route = new ArrayList<>();
            Integer currentNode = end;
            while (currentNode != null) {
                route.add(0, currentNode); // Add to the front of the list
                currentNode = parent.get(currentNode);
            }

            return new Result(dist.get(end), route).toString();
        }
    }

    public String getShortcutValue(int StationOne, int StationTwo, Util db) {

        // Create a graph and add edges
        Graph graph = new Graph();
        try {
            PreparedStatement query = db.con.prepareStatement("Select * from StationsDistance");
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                graph.addEdge(resultSet.getInt("StationOneId"), resultSet.getInt("StationTwoId"), resultSet.getInt("InBetweenDistance"));
            }
            db.con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return graph.dijkstra(StationOne,StationTwo);
    }
}