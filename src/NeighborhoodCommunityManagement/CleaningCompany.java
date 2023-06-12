package NeighborhoodCommunityManagement;

class CleaningCompany {
    private String name;
    private String address;
    private double costPerStairs;

    public CleaningCompany(String name, String address, double costPerStairs) {
        this.name = name;
        this.address = address;
        this.costPerStairs = costPerStairs;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getCostPerStairs() {
        return costPerStairs;
    }
}