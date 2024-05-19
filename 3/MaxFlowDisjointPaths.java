import java.io.*;
import java.util.*;

public class MaxFlowDisjointPaths {
    private static final int INF = Integer.MAX_VALUE;

    // Função para ler o grafo a partir de um arquivo
    public static int[][] readGraph(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String[] firstLine = br.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int m = Integer.parseInt(firstLine[1]);
        int[][] capacity = new int[n][n];

        for (int i = 0; i < m; i++) {
            String[] edge = br.readLine().split(" ");
            int u = Integer.parseInt(edge[0]);
            int v = Integer.parseInt(edge[1]);
            int c = Integer.parseInt(edge[2]);
            capacity[u][v] = c;
        }

        br.close();
        return capacity;
    }

    // Implementação do algoritmo de Edmonds-Karp
    public static int edmondsKarp(int[][] capacity, int source, int sink, List<List<Integer>> paths) {
        int n = capacity.length;
        int[][] residualCapacity = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(capacity[i], 0, residualCapacity[i], 0, n);
        }

        int[] parent = new int[n];
        int maxFlow = 0;

        while (bfs(residualCapacity, source, sink, parent)) {
            int pathFlow = INF;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualCapacity[u][v]);
            }

            List<Integer> path = new ArrayList<>();
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualCapacity[u][v] -= pathFlow;
                residualCapacity[v][u] += pathFlow;
                path.add(v);
            }
            path.add(source);
            Collections.reverse(path);
            paths.add(path);

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    // Busca em largura (BFS) para encontrar um caminho de aumento
    private static boolean bfs(int[][] residualCapacity, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[residualCapacity.length];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < residualCapacity.length; v++) {
                if (!visited[v] && residualCapacity[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                    if (v == sink) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Função principal para encontrar e imprimir os caminhos disjuntos em arestas
    public static void findDisjointPaths(int[][] capacity, int source, int sink) {
        List<List<Integer>> paths = new ArrayList<>();
        int maxFlow = edmondsKarp(capacity, source, sink, paths);
        System.out.println("Número de caminhos disjuntos em arestas: " + maxFlow);
        
        System.out.println("Caminhos encontrados:");
        for (List<Integer> path : paths) {
            System.out.println(path);
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Uso: java MaxFlowDisjointPaths <arquivo do grafo>");
                return;
            }
            int[][] capacity = readGraph(args[0]);
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o vértice de origem: ");
            int source = scanner.nextInt();
            System.out.print("Digite o vértice de destino: ");
            int sink = scanner.nextInt();
            findDisjointPaths(capacity, source, sink);
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
