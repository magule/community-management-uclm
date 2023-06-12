package NeighborhoodCommunityManagement;


public class ImprovementRequest {
    private String description;
    private int urgencyLevel;
    private Neighbor neighbor;

    public ImprovementRequest(String description, int urgencyLevel, Neighbor neighbor) {
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.neighbor = neighbor;
    }

    public String getDescription() {
        return description;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public Neighbor getNeighbor() {
        return neighbor;
    }
}


/*package NeighborhoodCommunityManagement;


class ImprovementRequest {
    private String description;
    private int urgencyLevel;

    public ImprovementRequest(String description, int urgencyLevel) {
        this.description = description;
        this.urgencyLevel = urgencyLevel;
    }

    public String getDescription() {
        return description;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }
   // public String getSubmittedBy() {
     //   return submittedBy;
   // }
}

*/