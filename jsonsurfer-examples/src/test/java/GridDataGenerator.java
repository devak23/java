import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GridDataGenerator {
    
    private static final Random random = new Random();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    // Sample data arrays for random generation
    private static final String[] DESCRIPTIONS = {
        "Incoming transaction from India",
        "Outgoing wire transfer to UK",
        "ACH payment received",
        "IMPS transfer to vendor",
        "Salary credit",
        "Utility bill payment",
        "Investment dividend",
        "Insurance premium payment",
        "Loan EMI deduction",
        "Credit card payment",
        "Online purchase refund",
        "International remittance",
        "Tax payment",
        "Subscription renewal",
        "Freelance payment received"
    };
    
    private static final String[] TYPES = {
        "Wires", "ACH", "IMPS", "NEFT", "RTGS", "UPI", "Card", "Check"
    };
    
    private static final String[] CURRENCIES = {
        "USD", "EUR", "GBP", "INR", "JPY", "CAD", "AUD"
    };
    
    private static final String[] CATEGORIES = {
        "High", "Medium", "Low", "Critical", "Normal"
    };
    
    public static void main(String[] args) {
        try {
            generateGridData();
            System.out.println("Successfully generated grid data with 10,000 rows!");
        } catch (IOException e) {
            System.err.println("Error generating grid data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void generateGridData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        // Read the original grid-definition.json
        File inputFile = new File("src/main/resources/data/grid-definition.json");
        JsonNode rootNode = mapper.readTree(inputFile);
        
        // Get the gridInfo section
        ObjectNode gridInfo = (ObjectNode) rootNode.get("gridInfo");
        ArrayNode rowsArray = mapper.createArrayNode();
        
        // Generate 10,000 rows
        BigDecimal runningBalance = new BigDecimal("21446000.00");
        
        for (int i = 0; i < 10000; i++) {
            ObjectNode row = mapper.createObjectNode();
            
            // Generate random date (within last 365 days)
            LocalDate randomDate = LocalDate.now().minusDays(random.nextInt(365));
            row.put("Date", randomDate.format(DATE_FORMAT));
            
            // Random description
            row.put("Description", DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)]);
            
            // Random type
            row.put("Type", TYPES[random.nextInt(TYPES.length)]);
            
            // Random amount (between -50,000 and +50,000)
            BigDecimal amount = new BigDecimal((random.nextDouble() - 0.5) * 100000)
                    .setScale(2, RoundingMode.HALF_UP);
            row.put("Amount", formatAmount(amount));
            
            // Random currency
            row.put("Currency", CURRENCIES[random.nextInt(CURRENCIES.length)]);
            
            // Update running balance
            runningBalance = runningBalance.add(amount);
            row.put("Running Balance", formatAmount(runningBalance));
            
            // Random category
            row.put("Category", CATEGORIES[random.nextInt(CATEGORIES.length)]);
            
            rowsArray.add(row);
        }
        
        // Replace the rows array in gridInfo
        gridInfo.set("rows", rowsArray);
        
        // Update footer info with total count
        ObjectNode footerInfo = (ObjectNode) rootNode.get("footerInfo");
        ObjectNode footerRows = (ObjectNode) footerInfo.get("rows");
        footerRows.put("Total Transactions", "10,000");
        footerRows.put("Generated Date", LocalDate.now().format(DATE_FORMAT));
        
        // Write the updated JSON to a new file
        File outputFile = new File("src/main/resources/data/grid-definition-with-data.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, rootNode);
        
        System.out.println("Generated file: " + outputFile.getAbsolutePath());
        System.out.println("Total rows generated: 10,000");
        System.out.println("Final running balance: " + formatAmount(runningBalance));
    }
    
    private static String formatAmount(BigDecimal amount) {
        // Format with commas for thousands separator
        return String.format("%,.2f", amount);
    }
}