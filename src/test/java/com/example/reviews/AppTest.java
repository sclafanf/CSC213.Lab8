package com.example.reviews;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testFilterByPriceRange() {
        Review r1 = new Review(); r1.setPrice(10);
        Review r2 = new Review(); r2.setPrice(30);
        Review r3 = new Review(); r3.setPrice(150);

        List<Review> input = Arrays.asList(r1, r2, r3);
        List<Review> filtered = App.filterByPriceRange(input, 20, 100);
        assertEquals(1, filtered.size());
        assertEquals(30, filtered.get(0).getPrice());
    }

    @Test
    void testCountByProductId() {
        Review r1 = new Review(); r1.setProductId("P001");
        Review r2 = new Review(); r2.setProductId("P001");
        Review r3 = new Review(); r3.setProductId("P002");

        Map<String, Long> counts = App.countByProductId(Arrays.asList(r1, r2, r3));
        assertEquals(2, counts.get("P001"));
        assertEquals(1, counts.get("P002"));
    }

    @Test
    void testFindByKeywordInTitle() {
        Review r1 = new Review(); r1.setTitle("This is great");
        Review r2 = new Review(); r2.setTitle("Nothing special");
        Review r3 = new Review(); r3.setTitle("Great again");

        List<Review> result = App.findByKeywordInTitle(Arrays.asList(r1, r2, r3), "great");
        assertEquals(2, result.size());
    }

    @Test
    void testGetTechTitlesOver50Dollars() {
        // Create test data: a mix of Tech and non-Tech reviews, with various prices
        Review r1 = new Review();
        r1.setCategory("Tech");
        r1.setPrice(59.99);
        r1.setTitle("Great headphones");
    
        Review r2 = new Review();
        r2.setCategory("Home");
        r2.setPrice(75.00);
        r2.setTitle("Decent blender");
    
        Review r3 = new Review();
        r3.setCategory("Tech");
        r3.setPrice(49.99); // Should be excluded (price <= 50)
        r3.setTitle("Cheap speakers");
    
        Review r4 = new Review();
        r4.setCategory("Tech");
        r4.setPrice(120.00);
        r4.setTitle("Smartwatch deluxe");
    
        Review r5 = new Review();
        r5.setCategory("Tech");
        r5.setPrice(200.00);
        r5.setTitle(null); // Should be excluded (null title)
    
        // Run the method
        List<String> result = App.getTechTitlesOver50Dollars(Arrays.asList(r1, r2, r3, r4, r5));
    
        // Check the expected output (uppercase titles of r1 and r4)
        List<String> expected = Arrays.asList("GREAT HEADPHONES", "SMARTWATCH DELUXE");
        assertEquals(expected, result);
    }
    

    @Test
    void testHomeProductIdsUnder100Sorted() {
        Review r1 = new Review(); r1.setCategory("Home"); r1.setPrice(80); r1.setProductId("P001");
        Review r2 = new Review(); r2.setCategory("Home"); r2.setPrice(45); r2.setProductId("P002");
        Review r3 = new Review(); r3.setCategory("Home"); r3.setPrice(120); r3.setProductId("P003");
        Review r4 = new Review(); r4.setCategory("Tech"); r4.setPrice(60); r4.setProductId("P004");

        List<String> result = App.getHomeProductIdsUnder100(Arrays.asList(r1, r2, r3, r4));
        assertEquals(Arrays.asList("P002", "P001"), result); // Sorted by price: 45, 80
    }

    @Test
    void testHomeProductIdsUnder100Sorted_noMatches() {
        Review r1 = new Review(); r1.setCategory("Tech"); r1.setPrice(90); r1.setProductId("P001");
        Review r2 = new Review(); r2.setCategory("Fitness"); r2.setPrice(60); r2.setProductId("P002");

        List<String> result = App.getHomeProductIdsUnder100(Arrays.asList(r1, r2));
        assertTrue(result.size() == 0);
    }

}
