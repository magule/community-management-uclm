package NeighborhoodCommunityManagement;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class NeighborhoodCommunityManagement {
    private static List<Neighbor> neighbors = new ArrayList<>();
    private static List<ImprovementRequest> requests = new ArrayList<>();
    private static CleaningCompany cleaningCompany;

    public static void main(String[] args) {
        loadNeighborsFromFile("vecinos.txt");
        cleaningCompany = new CleaningCompany("ABC Cleaning", "123 Main St", 50.0); // Create the CleaningCompany object
        showMainMenu();
    }


//----------------------------------------------------------------------------------------------------------------------------------------


    //get the neighboors from vecinos.txt


//----------------------------------------------------------------------------------------------------------------------------------------


    private static void loadNeighborsFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");

                if (parts[0].equalsIgnoreCase("p")) {
                    String name = parts[1];
                    String NIF = parts[2];
                    String flatNumber = parts[3];
                    int year = Integer.parseInt(parts[4]);
                    Neighbor neighbor = new Neighbor("p", name, NIF, flatNumber, year);
                    neighbors.add(neighbor);
                } else if (parts[0].equalsIgnoreCase("i")) {
                    String name = parts[1];
                    String NIF = parts[2];
                    String flatNumber = parts[3];
                    double rental = Double.parseDouble(parts[4]);
                    Neighbor neighbor = new Neighbor("i", name, NIF, flatNumber, rental);
                    neighbors.add(neighbor);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }


//----------------------------------------------------------------------------------------------------------------------------------------


    //main menu


//----------------------------------------------------------------------------------------------------------------------------------------

    private static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nNeighborhood Community Management");
            System.out.println("1. Show all information about neighbors");
            System.out.println("2. Make a request for improvement");
            System.out.println("3. Show neighbor who pays the most taxes");
            System.out.println("4. Show high urgency requests made by a neighbor");
            System.out.println("5. Show sum of annual taxes raised by the community");
            System.out.println("6. Show taxes to be paid by a neighbor");
            System.out.println("7. Show annual cleaning cost for the community");
            System.out.println("8. Add a new sneighnor");
            System.out.println("9. Show average taxes paid by tenants or landlords");

           

            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllNeighborInformation();
                    break;
                case 2:
                    makeImprovementRequest(scanner);
                    break;
                case 3:
                    showNeighborWithHighestTaxes();
                    break;
                case 4:
                	showHighUrgencyRequests();
                    break;
                case 5:
                    showSumOfAnnualTaxes();
                    break;
                case 6:
                    showTaxesForNeighbor(scanner);
                    break;
                case 7:
                    showAnnualCleaningCost();
                    break;
                    
                case 8:
                	addNeighbor(scanner);
                	break;
                case 9:
                    showAverageTaxesPaid(scanner);
                    break;
                	
                	
                case 0:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    


//----------------------------------------------------------------------------------------------------------------------------------------


    // shows all the neighboor information
    // menu option 1:


//----------------------------------------------------------------------------------------------------------------------------------------

    private static void showAllNeighborInformation() {
        System.out.println("\nAll Neighbor Information:");
        for (Neighbor neighbor : neighbors) {
            System.out.println("Name: " + neighbor.getName());
            System.out.println("NIF: " + neighbor.getNIF());
            System.out.println("Flat Number: " + neighbor.getFlatNumber());

            if (neighbor.getType().equalsIgnoreCase("p")) {
                System.out.println("Type: Landlord");
                System.out.println("Year Bought: " + neighbor.getYear());
            } else if (neighbor.getType().equalsIgnoreCase("i")) {
                System.out.println("Type: Tenant");
                System.out.println("Monthly Rental: " + neighbor.getRental() + " EUR");
            }

            System.out.println();
        }
    }
    private static boolean validateNIF(String nif) {
        if (nif.length() != 9) {
            return false;
        }

        String numbers = nif.substring(0, 8);
        String letter = nif.substring(8);

        if (!numbers.matches("\\d+") || !letter.matches("[A-Z]")) {
            return false;
        }

        return true;
    }
    


//----------------------------------------------------------------------------------------------------------------------------------------


    //show avarage taxes paid by landlord or tenant


//----------------------------------------------------------------------------------------------------------------------------------------

    private static void showAverageTaxesPaid(Scanner scanner) {
        System.out.print("Enter neighbor type (p: Landlords, i: Tenants): ");
        String neighborType = scanner.nextLine();

        double totalTaxesPaid = 0.0;
        int neighborCount = 0;

        for (Neighbor neighbor : neighbors) {
            if (neighbor.getType().equalsIgnoreCase(neighborType)) {
                double taxes = calculateAnnualTaxes(neighbor);
                totalTaxesPaid += taxes;
                neighborCount++;
            }
        }

        if (neighborCount > 0) {
            double averageTaxesPaid = totalTaxesPaid / neighborCount;
            System.out.printf("Average taxes paid by %s: %.2f\n", neighborType.equals("p") ? "landlords" : "tenants", averageTaxesPaid);
        } else {
            System.out.printf("No %s found.\n", neighborType.equals("p") ? "landlords" : "tenants");
        }
    }



//----------------------------------------------------------------------------------------------------------------------------------------


    //testing avarage taxes paid-- out


//----------------------------------------------------------------------------------------------------------------------------------------

/*
    private static void showAverageTaxesPaid(Scanner scanner) {
        System.out.print("Enter neighbor type (p: Landlords, i: Tenants): ");
        String neighborType = scanner.nextLine();

        double totalTaxesPaid = 0.0;
        int neighborCount = 0;

        for (Neighbor neighbor : neighbors) {
            if (neighbor.getType().equalsIgnoreCase(neighborType)) {
                totalTaxesPaid += neighbor.getTaxesPaid();
                neighborCount++;
            }
        }

        if (neighborCount > 0) {
            double averageTaxesPaid = totalTaxesPaid / neighborCount;
            System.out.printf("Average taxes paid by %s: %.2f\n", neighborType.equals("p") ? "landlords" : "tenants", averageTaxesPaid);
        } else {
            System.out.printf("No %s found.\n", neighborType.equals("p") ? "landlords" : "tenants");
        }
    }

*/



//----------------------------------------------------------------------------------------------------------------------------------------


    // creating a improvement request


//----------------------------------------------------------------------------------------------------------------------------------------
    private static void makeImprovementRequest(Scanner scanner) {
        System.out.print("\nEnter neighbor name: ");
        String name = scanner.nextLine();

        Neighbor neighbor = findNeighborByName(name);

        if (neighbor != null) {
            if (canMakeRequest(neighbor)) {
                System.out.print("Enter description: ");
                String description = scanner.nextLine();
                System.out.print("Enter urgency level (0: low, 1: moderate, 2: high): ");
                int urgencyLevel = scanner.nextInt();
                scanner.nextLine();

                ImprovementRequest request = new ImprovementRequest(description, urgencyLevel, neighbor);
                requests.add(request);

                System.out.println("Request successfully added!");

                // Prompt for additional requests to the same neighbor
                System.out.print("Add another request for " + neighbor.getName() + "? (y/n): ");
                String addMoreRequests = scanner.nextLine();
                if (addMoreRequests.equalsIgnoreCase("y")) {
                    makeImprovementRequest(scanner);
                }
            } else {
                System.out.println("Maximum number of requests reached for this neighbor.");
            }
        } else {
            System.out.println("Neighbor not found.");
        }
    }

/*
   private static void makeImprovementRequest(Scanner scanner) {
    System.out.print("\nEnter neighbor name: ");
    String name = scanner.nextLine();

    Neighbor neighbor = findNeighborByName(name);

    if (neighbor != null) {
        if (canMakeRequest(neighbor)) {
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            System.out.print("Enter urgency level (0: low, 1: moderate, 2: high): ");
            int urgencyLevel = scanner.nextInt();
            scanner.nextLine();

            ImprovementRequest request = new ImprovementRequest(description, urgencyLevel);

            if (neighbor.addImprovementRequest(request)) {
                System.out.println("Request successfully added!");
            } else {
                System.out.println("Maximum number of requests reached for this neighbor.");
            }

            // Prompt for additional requests to the same neighbor
            System.out.print("Add another request for " + neighbor.getName() + "? (y/n): ");
            String addMoreRequests = scanner.nextLine();
            if (addMoreRequests.equalsIgnoreCase("y")) {
                makeImprovementRequest(scanner);
            }
        } else {
            System.out.println("Maximum number of requests reached for this neighbor.");
        }
    } else {
        System.out.println("Neighbor not found.");
    }
}

*/
//----------------------------------------------------------------------------------------------------------------------------------------


    // show high urgency requests


//----------------------------------------------------------------------------------------------------------------------------------------


    private static void showHighUrgencyRequests() {
        System.out.println("\nHigh Urgency Requests:");
        for (ImprovementRequest request : requests) {
            if (request.getUrgencyLevel() == 2) {
                System.out.println("Neighbor: " + request.getNeighbor().getName());
                System.out.println("Description: " + request.getDescription());
                System.out.println();
            }
        }
    }


/*works
    private static void showHighUrgencyRequests(Scanner scanner) {
        System.out.println("\nHigh Urgency Requests:");
        for (ImprovementRequest request : requests) {
            if (request.getUrgencyLevel() == 2) {
                System.out.println("Description: " + request.getDescription());
                System.out.println();
            }
        }
    }

   */ 
   

    private static List<ImprovementRequest> getHighUrgencyRequests(Neighbor neighbor) {
        List<ImprovementRequest> highUrgencyRequests = new ArrayList<>();

        for (ImprovementRequest request : requests) {
            if (request.getUrgencyLevel() == 2 && request.getDescription().equalsIgnoreCase(neighbor.getName())) {
                highUrgencyRequests.add(request);
            }
        }

        return highUrgencyRequests;
    }



//----------------------------------------------------------------------------------------------------------------------------------------


    // maximum 10 requests per neighboor


//----------------------------------------------------------------------------------------------------------------------------------------


    private static boolean canMakeRequest(Neighbor neighbor) {
        long requestCount = requests.stream()
                .filter(req -> req.getDescription().equalsIgnoreCase(neighbor.getName()))
                .count();
        return requestCount < 10;
    }



//----------------------------------------------------------------------------------------------------------------------------------------


    // get the neighboor who pays highest taxes


//----------------------------------------------------------------------------------------------------------------------------------------

    private static void showNeighborWithHighestTaxes() {
        double maxTaxes = 0;
        Neighbor neighborWithMaxTaxes = null;

        for (Neighbor neighbor : neighbors) {
            double taxes = calculateAnnualTaxes(neighbor);
            if (taxes > maxTaxes) {
                maxTaxes = taxes;
                neighborWithMaxTaxes = neighbor;
            }
        }

        if (neighborWithMaxTaxes != null) {
            System.out.println("\nNeighbor with the highest taxes:");
            System.out.println("Name: " + neighborWithMaxTaxes.getName());
            System.out.println("Taxes: " + maxTaxes + " EUR");
        } else {
            System.out.println("\nNo neighbors found.");
        }
    }





//----------------------------------------------------------------------------------------------------------------------------------------


    // sum of annual taxes 


//----------------------------------------------------------------------------------------------------------------------------------------


    private static void showSumOfAnnualTaxes() {
        double sumTaxes = 0;

        for (Neighbor neighbor : neighbors) {
            double taxes = calculateAnnualTaxes(neighbor);
            sumTaxes += taxes;
        }

        System.out.println("\nSum of annual taxes raised by the community: " + sumTaxes + " EUR");
    }



//----------------------------------------------------------------------------------------------------------------------------------------


    // tax for neighboor


//----------------------------------------------------------------------------------------------------------------------------------------


    private static void showTaxesForNeighbor(Scanner scanner) {
        System.out.print("\nEnter neighbor name: ");
        String name = scanner.nextLine();

        Neighbor neighbor = findNeighborByName(name);

        if (neighbor != null) {
            double taxes = calculateAnnualTaxes(neighbor);
            System.out.println("\nTaxes for " + neighbor.getName() + ": " + taxes + " EUR");
        } else {
            System.out.println("Neighbor not found.");
        }
    }



//----------------------------------------------------------------------------------------------------------------------------------------


    // annual tax


//----------------------------------------------------------------------------------------------------------------------------------------


    private static double calculateAnnualTaxes(Neighbor neighbor) {
        double basePrice;
        double surcharge;

        if (neighbor.getType().equalsIgnoreCase("p")) {
            basePrice = 650;
            int age = 2023 - neighbor.getYear();

            if (age > 17) {
                surcharge = basePrice * 0.12;
            } else {
                surcharge = basePrice * 0.1;
            }
        } else if (neighbor.getType().equalsIgnoreCase("i")) {
            basePrice = 450;
            surcharge = basePrice * 0.05;
        } else {
            // Invalid neighbor type
            return 0.0;
        }

        return basePrice + surcharge;
    }



//----------------------------------------------------------------------------------------------------------------------------------------


    // cleaning cost


//----------------------------------------------------------------------------------------------------------------------------------------


    private static void showAnnualCleaningCost() {
        System.out.println("\nAnnual cleaning cost for the community:");
        double costPerStairs = cleaningCompany.getCostPerStairs();
        int numStairs = getNumStairs();
        double annualCost = costPerStairs * numStairs;
        System.out.println(annualCost + " EUR");
    }



//----------------------------------------------------------------------------------------------------------------------------------------


    // number of stairs


//----------------------------------------------------------------------------------------------------------------------------------------

    private static int getNumStairs() {
        // Hardcoded number of stairs for demonstration purposes
        return 10;
    }



//----------------------------------------------------------------------------------------------------------------------------------------


    // neighboor by name


//----------------------------------------------------------------------------------------------------------------------------------------

    
    private static Neighbor findNeighborByName(String name) {
        for (Neighbor neighbor : neighbors) {
            if (neighbor.getName().equalsIgnoreCase(name)) {
                return neighbor;
            }
        }
        return null;
    }
    
   

//----------------------------------------------------------------------------------------------------------------------------------------


    // add new neighboor and check the nif input is correct


//----------------------------------------------------------------------------------------------------------------------------------------

    /*
    
    private static void addNeighbor(Scanner scanner) {
        System.out.print("\nEnter neighbor type (p: Landlord, i: Tenant): ");
        String type = scanner.nextLine();
        System.out.print("Enter neighbor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter neighbor NIF: ");
        String nif = scanner.nextLine();
        
        if (!validateNIF(nif)) {
            System.out.println("Invalid NIF. The NIF must consist of 8 numbers followed by a letter (e.g., 12345678A). Please try again.");
            return;
        }
        
        System.out.print("Enter flat number: ");
        String flatNumber = scanner.nextLine();

        int year = 0; // Default value for year
        double rental = 0.0; // Default value for rental

        if (type.equalsIgnoreCase("p")) {
            System.out.print("Enter year bought: ");
            year = scanner.nextInt();
            scanner.nextLine();
        } else if (type.equalsIgnoreCase("i")) {
            System.out.print("Enter monthly rental: ");
            rental = scanner.nextDouble();
            scanner.nextLine();
        } else {
            System.out.println("Invalid neighbor type. Please try again.");
            return;
        }

        Neighbor neighbor;
        if (type.equalsIgnoreCase("p")) {
            neighbor = new Neighbor("p", name, nif, flatNumber, year);
        } else {
            neighbor = new Neighbor("i", name, nif, flatNumber, rental);
        }
        neighbors.add(neighbor);

        // Append the new neighbor to the "vecinos.txt" file
        try {
            File file = new File("vecinos.txt");
            FileWriter writer = new FileWriter(file, true);
            writer.write(System.lineSeparator()); // Start with a line break
            writer.write(type + " " + name + " " + nif + " " + flatNumber);
            if (type.equalsIgnoreCase("p")) {
                writer.write(" " + year);
            } else if (type.equalsIgnoreCase("i")) {
                writer.write(" " + rental);
            }
            writer.write(System.lineSeparator());
            writer.close();
            System.out.println("Neighbor added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        } 

      
    }
*/
    private static void addNeighbor(Scanner scanner) {
        System.out.print("\nEnter neighbor type (p: Landlord, i: Tenant): ");
        String type = scanner.nextLine();
        System.out.print("Enter neighbor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter neighbor NIF: ");
        String nif = scanner.nextLine();

        if (!validateNIF(nif)) {
            System.out.println("Invalid NIF. The NIF must consist of 8 numbers followed by a letter (e.g., 12345678A). Please try again.");
            return;
        }

        String flatNumber;
        while (true) {
            System.out.print("Enter flat number: ");
            flatNumber = scanner.nextLine();

            if (isFlatNumberUnique(flatNumber)) {
                break;
            } else {
                System.out.println("Flat number already exists. Please enter a unique flat number.");
            }
        }

        int year = 0; // Default value for year
        double rental = 0.0; // Default value for rental

        if (type.equalsIgnoreCase("p")) {
            System.out.print("Enter year bought: ");
            year = scanner.nextInt();
            scanner.nextLine();
        } else if (type.equalsIgnoreCase("i")) {
            System.out.print("Enter monthly rental: ");
            rental = scanner.nextDouble();
            scanner.nextLine();
        } else {
            System.out.println("Invalid neighbor type. Please try again.");
            return;
        }

        Neighbor neighbor;
        if (type.equalsIgnoreCase("p")) {
            neighbor = new Neighbor("p", name, nif, flatNumber, year);
        } else {
            neighbor = new Neighbor("i", name, nif, flatNumber, rental);
        }
        neighbors.add(neighbor);

        // Append the new neighbor to the "vecinos.txt" file
        try {
            File file = new File("vecinos.txt");
            FileWriter writer = new FileWriter(file, true);
            writer.write(System.lineSeparator()); // Start with a line break
            writer.write(type + " " + name + " " + nif + " " + flatNumber);
            if (type.equalsIgnoreCase("p")) {
                writer.write(" " + year);
            } else if (type.equalsIgnoreCase("i")) {
                writer.write(" " + rental);
            }
            writer.write(System.lineSeparator());
            writer.close();
            System.out.println("Neighbor added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }

    private static boolean isFlatNumberUnique(String flatNumber) {
        for (Neighbor neighbor : neighbors) {
            if (neighbor.getFlatNumber().equalsIgnoreCase(flatNumber)) {
                return false;
            }
        }
        return true;
    }


}
