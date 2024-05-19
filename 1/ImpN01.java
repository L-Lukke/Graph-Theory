import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ImpN01 {
    private int V; // número de vértices
    private LinkedList<Integer>[] adj; // lista de adjacência

    // Construtor
    public ImpN01(int vertices) {
        V = vertices;
        adj = new LinkedList[V];
        for (int i = 0; i < V; ++i)
            adj[i] = new LinkedList<>();
    }

    // Adicionar uma aresta ao grafo
    public void adicionarAresta(int origem, int destino) {
        adj[origem].add(destino);
    }

    // Calcular o grau de saída de um vértice
    public int grauDeSaida(int vertice) {
        return adj[vertice].size();
    }

    // Calcular o grau de entrada de um vértice
    public int grauDeEntrada(int vertice) {
        int contador = 0;
        for (int i = 0; i < V; ++i) {
            if (adj[i].contains(vertice))
                contador++;
        }
        return contador;
    }

    // Obter o conjunto de sucessores de um vértice
    public ArrayList<Integer> sucessores(int vertice) {
        ArrayList<Integer> sucessoresList = new ArrayList<>(adj[vertice]);
        return sucessoresList;
    }


    // Obter o conjunto de predecessores de um vértice
    public ArrayList<Integer> predecessores(int vertice) {
        ArrayList<Integer> predecessores = new ArrayList<>();
        for (int i = 0; i < V; ++i) {
            if (adj[i].contains(vertice))
                predecessores.add(i);
        }
        return predecessores;
    }

    public static void main(String args[]) throws FileNotFoundException {

        Scanner keyboardRead = new Scanner(System.in);

        System.out.println("Nome do arquivo grafo: ");
        String nArquivoGrafo = keyboardRead.nextLine();
        
        File file = new File(nArquivoGrafo);
        Scanner fileRead = new Scanner(file);

        int vertices = fileRead.nextInt();

        fileRead.nextLine();

        ImpN01 grafo = new ImpN01(vertices);
        
        while (fileRead.hasNextLine()) {
            grafo.adicionarAresta(fileRead.nextInt(), fileRead.nextInt());
            fileRead.nextLine();
        }

        fileRead.close();

        System.out.println("Digite o número do vértice que deseja conhecer: ");
        String verticeString = keyboardRead.nextLine();
        int verticeInt = Integer.parseInt(verticeString);

        
        System.out.println("O grau de saída do vértice " + verticeInt + " é " + grafo.grauDeSaida(verticeInt));
        System.out.println("O grau de entrada do vértice " + verticeInt + " é " + grafo.grauDeEntrada(verticeInt));
        System.out.println("Os sucessores do vértice " + verticeInt + " são ");
        for (int i = 0; i < grafo.sucessores(verticeInt).size(); i++) System.out.print(grafo.sucessores(verticeInt).get(i) + ", "); 		  	
        System.out.println("Os predecessores do vértice " + verticeInt + " são ");
        for (int i = 0; i < grafo.predecessores(verticeInt).size(); i++) System.out.print(grafo.predecessores(verticeInt).get(i) + ", "); 

        keyboardRead.close();
    }
}