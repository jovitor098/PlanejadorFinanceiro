package planejadorfinanceiro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import planejadorfinanceiro.model.Cliente;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por salvar, carregar, buscar e validar clientes
 * usando um arquivo JSON para obter persistência local. 
 */
public class ClienteService {
    /** Caminho para o arquivo dentro da pasta service */
    private static final String ARQUIVO_NOME = "clientes_cadastrados.json";
    private static String CAMINHO_ARQUIVO;
    private static final ObjectMapper objectMapper = createObjectMapper();

    /**
    * Método que carrega o caminho do arquivo na pasta de service
    */
    public static void carregarCaminhoArquivo(){
        CAMINHO_ARQUIVO = obterCaminhoArquivo(ClienteService.class);
    }

    /**
     * Carrega o caminho do arquivo com base em uma classe de referência.
     * 
     * @param classeReferencia
     */
    public static void carregarCaminhoArquivo(Class<?> classeReferencia){
        CAMINHO_ARQUIVO = obterCaminhoArquivo(classeReferencia);
    }
    
    /**
     * Método para obter o caminho do arquivo em uma pasta especifica.
     * 
     * @param classeReferencia
     * @return
     */
    public static String obterCaminhoArquivo(Class<?> classeReferencia) {
        try {
            // Obtém o caminho da classe ClienteService
            URL resourceUrl = classeReferencia.getResource("");
            if (resourceUrl != null) {
                // Constrói o caminho completo para o arquivo
                Path path = Paths.get(resourceUrl.toURI()).resolve(ARQUIVO_NOME);
                System.out.println("Caminho do arquivo: " + path.toString());
                return path.toString();
            } else {
                // Fallback: usar o caminho relativo
                String caminho = "app/src/main/java/planejadorfinanceiro/service/" + ARQUIVO_NOME;
                System.out.println("Usando caminho relativo: " + caminho);
                return caminho;
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter caminho do arquivo: " + e.getMessage());
            // Fallback: usar o caminho relativo
            return "app/src/main/java/planejadorfinanceiro/service/" + ARQUIVO_NOME;
        }
    }
    
    /**
     * Cria e configura o {@link ObjectMapper} com suporte para LocalDate e LocalDateTime
     * 
     * @return
     */
    // Cria e configura o ObjectMapper com suporte para LocalDate e LocalDateTime
    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * Salva a lista de clientes no arquivo JSON.
     * 
     * @param clientes Lista de clientes a ser persistida
     */
    public static void salvarClientes(List<Cliente> clientes) {
        try {
            System.out.println("Salvando clientes em: " + CAMINHO_ARQUIVO);
            objectMapper.writeValue(new File(CAMINHO_ARQUIVO), clientes);
            System.out.println("Clientes salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de clientes do arquivo JSON
     * 
     * @return Lista de clientes lidos do arquivo. Se não houver arquivo, retorna lista vazia.
     */
    public static List<Cliente> carregarClientes() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        System.out.println("Tentando carregar clientes de: " + arquivo.getAbsolutePath());
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo de clientes não encontrado. Retornando lista vazia.");
            return new ArrayList<>();
        }

        try {
            System.out.println("Conteúdo do arquivo JSON:");
            try (Scanner scanner = new Scanner(arquivo)) {
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            }
            
            CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Cliente.class);
            List<Cliente> clientes = objectMapper.readValue(arquivo, listType);
            System.out.println("Clientes carregados com sucesso! Total: " + clientes.size());
            
            // Debug: imprimir dados dos clientes
            for (Cliente c : clientes) {
                System.out.println("Cliente: " + c.getNome() + ", Email: " + c.getEmail());
            }
            
            return clientes;
        } catch (IOException e) {
            System.err.println("Erro ao carregar clientes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Adiciona um novo cliente e salva no arquivo.
     * 
     * @param novoCliente Cliente a ser adicionado
     */
    public static void adicionarCliente(Cliente novoCliente) {
        System.out.println("Adicionando novo cliente: " + novoCliente.getNome() + ", Email: " + novoCliente.getEmail());
        List<Cliente> clientes = carregarClientes();
        clientes.add(novoCliente);
        salvarClientes(clientes);
    }

    /**
     * Verifica se já existe um cliente com o email fornecido.
     * 
     * @param email Email a ser verificado
     * @return true se já existe cliente com o email fornecido, false caso contrário
     */
    public static boolean clienteExistente(String email) {
        List<Cliente> clientes = carregarClientes();
        boolean existe = clientes.stream().anyMatch(cliente -> cliente.getEmail().equals(email));
        System.out.println("Verificando se email existe: " + email + " - Resultado: " + existe);
        return existe;
    }

    /**
     * Busca um cliente pelo email e senha (para login).
     * 
     * @param email Email do cliente
     * @param senha Senha do cliente
     * @return Cliente correspondete às credenciais, null caso não encontre
     */
    public static Cliente buscarCliente(String email, String senha) {
        System.out.println("Buscando cliente com email: " + email);
        List<Cliente> clientes = carregarClientes();
        
        Cliente cliente = clientes.stream()
                .filter(c -> c.getEmail().equals(email) && c.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
                
        System.out.println("Cliente encontrado: " + (cliente != null ? "SIM" : "NÃO"));
        return cliente;
    }
} 