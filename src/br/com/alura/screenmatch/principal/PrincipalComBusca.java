package br.com.alura.screenmatch.principal;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.alura.screenmatch.modelos.ErroConversaoAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        String busca = "";
        
        List<Titulo> titulos = new ArrayList<>();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting().create();

        while(true){
        
        System.out.println("Digite um filme abaixo: ");
        var buscar = leitura.nextLine();
        if(buscar.equalsIgnoreCase("sair")){
            break;
        }
        
        String endereco = "https://www.omdbapi.com/?t=" + buscar.replace(" " , "+") + "&apikey=6608c387";


        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
            HttpResponse<String> response = client
                .send(request, BodyHandlers.ofString());
        
            String json = response.body();
            System.out.println(json);
            
            
            
            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(meuTituloOmdb);
            
            
            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println("Titulo convertido: ");
            System.out.println(meuTitulo);

            titulos.add(meuTitulo);

            
        }catch(NumberFormatException e){
            System.out.println("Aconteceu um erro: ");
            System.out.println(e.getMessage());
        }catch(IllegalArgumentException e){
            System.out.println("Algum erro de argumento na busca, reveja o endereço");
        }catch(ErroConversaoAnoException e){
            System.out.println(e.getMessage());
        }
    }
            System.out.println(titulos); 
            try(FileWriter escrita = new FileWriter("filmes.json")){
                escrita.write(gson.toJson(titulos));
            }catch (IOException e){
                System.out.println("erro ao escrever no arquivo: ");
                System.out.println(e.getMessage());
            }
            
            System.out.println("O programa finalizou corretamente!");
            
    }
    
}
