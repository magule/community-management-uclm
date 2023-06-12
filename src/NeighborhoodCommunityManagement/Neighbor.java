package NeighborhoodCommunityManagement;
import java.util.ArrayList;
import java.util.List;

class Neighbor {
    private String type;
    private String name;
    private String nif;
    private String flatNumber;
    private int year;
    private double rental;
    private List<ImprovementRequest> improvementRequests;


    public Neighbor(String type, String name, String nif, String flatNumber, int year) {
        this.type = type;
        this.name = name;
        this.nif = nif;
        this.flatNumber = flatNumber;
        this.year = year;
        improvementRequests = new ArrayList<>();

    }


    public Neighbor(String type, String name, String nif, String flatNumber, double rental) {
        this.type = type;
        this.name = name;
        this.nif = nif;
        this.flatNumber = flatNumber;
        this.rental = rental;
    }

    // Getters and setters

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getNIF() {
        return nif;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public int getYear() {
        return year;
    }
    
    public double getRental() { // Added getter for rental property
        return rental;
    }

  
    
}
