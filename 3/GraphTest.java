import java.io.*;

public class GraphTest {
    public static void generateCompleteGraph(int size, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        int edges = size * (size - 1) / 2;
        writer.write(size + " " + edges + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                writer.write(i + " " + j + " 1\n");
            }
        }
        writer.close();
    }

    public static void generateGridGraph(int rows, int cols, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        int vertices = rows * cols;
        int edges = (rows - 1) * cols + (cols - 1) * rows;
        writer.write(vertices + " " + edges + "\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int u = i * cols + j;
                if (j + 1 < cols) {
                    int v = u + 1;
                    writer.write(u + " " + v + " 1\n");
                }
                if (i + 1 < rows) {
                    int v = u + cols;
                    writer.write(u + " " + v + " 1\n");
                }
            }
        }
        writer.close();
    }

    public static void main(String[] args) {
        try {
            // Generate complete graphs
            generateCompleteGraph(4, "complete4.txt");
            generateCompleteGraph(5, "complete5.txt");
            generateCompleteGraph(6, "complete6.txt");
            generateCompleteGraph(7, "complete7.txt");

            // Generate grid graphs
            generateGridGraph(2, 2, "grid2x2.txt");
            generateGridGraph(3, 3, "grid3x3.txt");
            generateGridGraph(4, 4, "grid4x4.txt");
            generateGridGraph(5, 5, "grid5x5.txt");

            // Test the generated graphs
            testGraph("complete4.txt", 0, 3);
            testGraph("complete5.txt", 0, 4);
            testGraph("complete6.txt", 0, 5);
            testGraph("complete7.txt", 0, 6);
            testGraph("grid2x2.txt", 0, 3);
            testGraph("grid3x3.txt", 0, 8);
            testGraph("grid4x4.txt", 0, 15);
            testGraph("grid5x5.txt", 0, 24);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testGraph(String filename, int source, int sink) {
        try {
            int[][] capacity = MaxFlowDisjointPaths.readGraph(filename);
            long startTime = System.currentTimeMillis();
            MaxFlowDisjointPaths.findDisjointPaths(capacity, source, sink);
            long endTime = System.currentTimeMillis();
            System.out.println("Tempo de execução: " + (endTime - startTime) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
