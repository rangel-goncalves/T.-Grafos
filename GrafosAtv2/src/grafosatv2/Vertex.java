package grafosatv2;

import java.util.ArrayList;

/**
 *
 * @author Rangel
 *
 */
public class Vertex {
    private String city; 
    private String growth_from_2000_to_2013; 
    private Double latitude; 
    private Double longitude; 
    private Integer population;
    private int rank; 
    private String state;
    private ArrayList<Edge> inputEdges;
    private ArrayList<Edge> exitEdges;

    public Vertex(String city, String growth_from_2000_to_2013, Double latitude, Double longitude, Integer population, Integer rank, String state) {
        this.city = city;
        this.growth_from_2000_to_2013 = growth_from_2000_to_2013;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.rank = rank;
        this.state = state;
        this.inputEdges = new ArrayList<>();
        this.exitEdges = new ArrayList<>();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGrowth_from_2000_to_2013() {
        return growth_from_2000_to_2013;
    }

    public void setGrowth_from_2000_to_2013(String growth_from_2000_to_2013) {
        this.growth_from_2000_to_2013 = growth_from_2000_to_2013;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public int getRank() {
        return this.rank;
    }

    public void setData(Integer Data) {
        this.rank = Data;
    }

    public ArrayList<Edge> getInputEdges() {
        return inputEdges;
    }

    public void addInputEdges(Edge inputEdge) {
        this.inputEdges.add(inputEdge);
    }

    public ArrayList<Edge> getExitEdges() {
        return exitEdges;
    }

    public void addExitEdges(Edge exitEdges) {
        this.exitEdges.add(exitEdges);
    }
    /**
     * 
     * @param data
     * @return o custo de uma determinada aresta se ela não existir retorna 0 
     */
    public Double getCustExpecificEdge(int data){
        Double cost = 0.00;
        
        for (Edge exitEdge : exitEdges) {
            if(exitEdge.getEnd().getRank()== data){
                cost = (Double)exitEdge.getCost();
                return cost;
            }
        }
        return cost;
    }
    
}
