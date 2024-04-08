package BusDemo.Backend.Service;

import java.util.List;

public class Connection {
    private String origin;
    private String destination;
    private List<Viagem> viagens;

    public Connection(String origin, String destination, List<Viagem> viagens) {
        this.origin = origin;
        this.destination = destination;
        this.viagens = viagens;
    }

    public Connection() {
    }

    public String getDestination() {
        return destination;
    }
    public String getOrigin() {
        return origin;
    }
    public List<Viagem> getViagens() {
        return viagens;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }
    
}