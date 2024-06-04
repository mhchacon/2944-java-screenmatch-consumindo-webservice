package br.com.alura.screenmatch.modelos;

public class ErroConversaoAnoException extends RuntimeException{
    private String mensagem;

    public ErroConversaoAnoException(String mensagem){
        this.mensagem = mensagem;
    }
    
    @Override
    public String getMessage() {
        return this.mensagem;
    }
}
