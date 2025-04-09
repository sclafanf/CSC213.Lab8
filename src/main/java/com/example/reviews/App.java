package com.example.reviews;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        Reader reader = new InputStreamReader(App.class.getResourceAsStream("/reviews.csv"));
        List<Review> reviews = new CsvToBeanBuilder<Review>(reader)
                .withType(Review.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();

        System.out.println("Loaded Reviews:");
        reviews.forEach(System.out::println);

        System.out.println("\n--- Reviews with price between 20 and 100 ---");
        filterByPriceRange(reviews, 20, 100).forEach(System.out::println);

        System.out.println("\n--- Count by Product ID ---");
        countByProductId(reviews).forEach((productId, count) ->
            System.out.println(productId + ": " + count + " reviews")
        );

        System.out.println("\n--- Reviews containing 'great' ---");
        findByKeywordInTitle(reviews, "great").forEach(System.out::println);
    }


    /**
     * An imperative (non-stream) version of {@link #findByKeywordInTitle(List, String)}.
     * <p>
     * This method does the same thing: it filters a list of reviews by checking
     * whether each title contains a specific keyword (case-insensitive).
     * <p>
     * However, instead of using a stream pipeline, it uses a traditional `for` loop
     * and manually builds the resulting list.
     * <p>
     * This version is helpful to show the more verbose, mutation-based approach that
     * Java developers used before Streams were introduced.
     *
     * @param reviews the list of Review objects to search through.
     * @param keyword the word or phrase to search for in each review's title.
     * @return a list of reviews whose titles contain the keyword, ignoring case.
     */
    public static List<Review> findByKeywordInTitleImperative(List<Review> reviews, String keyword) {
        List<Review> result = new ArrayList<>(); // This will store all matching reviews

        // Loop through each review in the original list
        for (Review review : reviews) {
            String title = review.getTitle();

            // Check if the title is not null and contains the keyword (case-insensitive)
            if (title != null && title.toLowerCase().contains(keyword.toLowerCase())) {
                result.add(review); // Add the matching review to the result list
            }
        }

        // After the loop finishes, return the list of matches
        return result;
    }


    /**
     * Filters a list of {@link Review} objects, returning only those where the
     * review's title contains a given keyword, case-insensitively.
     * <p>
     * This method uses the Java Streams API to process the list in a clean,
     * declarative style. It performs the following steps:
     * <ol>
     *   <li>Turns the input list into a stream for functional processing.</li>
     *   <li>Filters out any reviews that are missing a title (null).</li>
     *   <li>Converts both the title and the keyword to lowercase so that matching
     *       is case-insensitive (e.g., "Great" matches "great").</li>
     *   <li>Checks if the title contains the keyword using {@code String.contains()}.</li>
     *   <li>Gathers all the matching results into a new list using {@code Collectors.toList()}.</li>
     * </ol>
     *
     * <h3>What's going on with Collectors.toList()?</h3>
     * When you use a stream, it's a pipeline of operations—but it doesn't actually run
     * until you tell it how to "terminate." That final step is called a <em>terminal operation</em>,
     * and {@code collect()} is one of the most common ones.
     * <p>
     * {@code Collectors.toList()} is a built-in Java utility that tells the stream:
     * <br>➡ “Take all the items that made it through the filter and gather them into a new List.”
     * <br>So instead of mutating an existing list manually (like with a loop), this collector builds
     * a brand new list for you from whatever is currently flowing through the stream.
     * <p>
     * Think of it like:
     * <br>“Hey stream, once you're done filtering, pour everything you’ve got into this new list bucket.”
     *
     * @param reviews the list of Review objects to search through. This should not be null.
     * @param keyword the word or phrase to search for in each review's title.
     *                The search is case-insensitive.
     * @return a list of reviews whose titles contain the keyword.
     *         If no reviews match, the result is an empty list.
     */
    public static List<Review> findByKeywordInTitle(List<Review> reviews, String keyword) {
        return reviews.stream() // Begin the stream pipeline
                .filter(r ->
                    r.getTitle() != null &&
                    r.getTitle().toLowerCase().contains(keyword.toLowerCase())
                )
                // Terminal operation: collect the filtered results into a new List
                .collect(Collectors.toList()); // <- This is where the stream materializes into a concrete list
    }


    public static List<Review> filterByPriceRange(List<Review> reviews, double min, double max) {
        return reviews.stream()  // Start a stream from the list of reviews
                .filter(r -> r.getPrice() >= min && r.getPrice() <= max)  // Filter reviews based on price
                .collect(Collectors.toList());  // Collect the filtered reviews into a new list
    }
    

    public static Map<String, Long> countByProductId(List<Review> reviews) {
        return reviews.stream()
                .collect(Collectors.groupingBy(Review::getProductId, Collectors.counting()));
    }

    public static List<String> getTechTitlesOver50DollarsImperative(List<Review> reviews) {
        List<String> result = new ArrayList<>();
        for (Review r : reviews) {
            if ("Tech".equalsIgnoreCase(r.getCategory()) && r.getPrice() > 50) {
                String title = r.getTitle();
                if (title != null) {
                    result.add(title.toUpperCase());
                }
            }
        }
        return result;
    }

    public static List<String> getTechTitlesOver50Dollars(List<Review> reviews) {
        return reviews.stream()
                .filter(r -> "Tech".equalsIgnoreCase(r.getCategory()))       // Keep only Tech reviews
                .filter(r -> r.getPrice() > 50)                              // ...with price > 50
                .map(Review::getTitle)                                       // Extract the title
                .filter(Objects::nonNull)                                    // Avoid nulls just in case
                .map(String::toUpperCase)                                    // Convert titles to uppercase
                .collect(Collectors.toList());                               // Final result: List<String>  
    }


    public static List<String> getHomeProductIdsUnder100(List<Review> reviews) {
    return reviews.stream()  
            .filter(r -> "Home".equalsIgnoreCase(r.getCategory()))  
            .filter(r -> r.getPrice() < 100)  
            .sorted(Comparator.comparingDouble(Review::getPrice))  
            .map(Review::getProductId)  
            .collect(Collectors.toList());  
}

    

}
