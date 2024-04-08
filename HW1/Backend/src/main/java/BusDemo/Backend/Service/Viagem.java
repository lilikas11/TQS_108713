package BusDemo.Backend.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Viagem {
    @Id
    @JsonProperty("ViagemID")
    private String ViagemID;
    @JsonProperty("Dia")
    private String dia;
    @JsonProperty("Dia de partida Hour")
    private String diaDePartidaHour;
    @JsonProperty("Dia de chegada Hour")
    private String diaDeChegadaHour;
    @JsonProperty("Preço")
    private String preço;

    public Viagem(String ViagemID, String dia, String diaDePartidaHour, String diaDeChegadaHour, String preço) {
        this.ViagemID = ViagemID;
        this.dia = dia;
        this.diaDePartidaHour = diaDePartidaHour;
        this.diaDeChegadaHour = diaDeChegadaHour;
        this.preço = preço;
    }
    public Viagem() {
    }

    public String getDia() {
        return dia;
    }
    public String getDiaDeChegadaHour() {
        return diaDeChegadaHour;
    }
    public String getDiaDePartidaHour() {
        return diaDePartidaHour;
    }
    public String getPreço() {
        return preço;
    }
    public String getViagemID() {
        return ViagemID;
    }
    public void setDia(String dia) {
        this.dia = dia;
    }
    public void setDiaDeChegadaHour(String diaDeChegadaHour) {
        this.diaDeChegadaHour = diaDeChegadaHour;
    }
    public void setDiaDePartidaHour(String diaDePartidaHour) {
        this.diaDePartidaHour = diaDePartidaHour;
    }
    public void setPreço(String preço) {
        this.preço = preço;
    }
    public void setViagemID(String ViagemID) {
        this.ViagemID = ViagemID;
    }

}
