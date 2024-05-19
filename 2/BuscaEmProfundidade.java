import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BuscaEmProfundidade {
    private Map<Integer, List<Integer>> grafo = new HashMap<>();
    private Set<String> arestasArvore = new TreeSet<>();
    private Set<String> arestasDivergentes = new TreeSet<>();
    private boolean[] visitado;

    public void lerGrafo(String nomeArquivo) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        String linha;
        while ((linha = leitor.readLine()) != null) {
            String[] partes = linha.split("\\s+");
            int vertice = Integer.parseInt(partes[0]);
            int verticeAdjacente = Integer.parseInt(partes[1]);
            grafo.computeIfAbsent(vertice, k -> new ArrayList<>()).add(verticeAdjacente);
        }
        leitor.close();
    }

    public void dfs(int vertice) {
        visitado = new boolean[grafo.size() + 1];
        dfsUtil(vertice);
    }

    private void dfsUtil(int vertice) {
        visitado[vertice] = true;
        List<Integer> vizinhos = grafo.getOrDefault(vertice, Collections.emptyList());
        for (int vizinho : vizinhos) {
            if (!visitado[vizinho]) {
                arestasArvore.add(vertice + " -> " + vizinho);
                dfsUtil(vizinho);
            } else {
                arestasDivergentes.add(vertice + " -> " + vizinho);
            }
        }
    }

    public void imprimirResultados() {
        System.out.println("Arestas da Árvore:");
        for (String aresta : arestasArvore) {
            System.out.println(aresta);
        }
        System.out.println("\nArestas Divergentes:");
        for (String aresta : arestasDivergentes) {
            System.out.println(aresta);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BuscaEmProfundidade bfs = new BuscaEmProfundidade();

        System.out.print("Digite o nome do arquivo contendo as informações do grafo: ");
        String nomeArquivo = scanner.nextLine();

        try {
            bfs.lerGrafo(nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        System.out.print("Digite o número do vértice para iniciar a busca em profundidade: ");
        int verticeInicial = scanner.nextInt();
        scanner.close();

        bfs.dfs(verticeInicial);
        bfs.imprimirResultados();
    }
}
