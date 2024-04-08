package BusDemo.Backend.Service;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String nome;
    private String telefone;
    private String endereco;
    private String cidade;
    private String cartaoCredito;
    private String ViagemID;


    public Reservation(String id, String nome, String telefone, String endereco, String cidade, String cartaoCredito, String ViagemID) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cartaoCredito = cartaoCredito;
        this.ViagemID = ViagemID;
    }

    public Reservation(String nome, String telefone, String endereco, String cidade, String cartaoCredito, String ViagemID) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cartaoCredito = cartaoCredito;
        this.ViagemID = ViagemID;
    }

    public Reservation() {
    }

    public String getCartaoCredito() {
        return cartaoCredito;
    }
    public String getCidade() {
        return cidade;
    }
    public String getEndereco() {
        return endereco;
    }
    public String getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getViagemID() {
        return ViagemID;
    }

    public void setCartaoCredito(String cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setViagemID(String viagemID) {
        ViagemID = viagemID;
    }
    
}
