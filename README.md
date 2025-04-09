# 🧪 Java Lab: Functional Thinking with Streams + CSVs

Welcome to this hands-on lab focused on **functional programming in Java** using **Streams**, **OpenCSV**, and a clean, testable data model. You’ll explore modern techniques for processing data in a more declarative style—less telling the computer how to do something, and more just telling it *what* you want.

---

## 🔄 Submitting Your Lab

To submit your work, please follow these steps:

### ✅ Step 1: Fork the Repository

1. Go to the GitHub repo for this lab.
2. Click the **"Fork"** button in the top-right corner.
3. This creates your **own personal copy** of the project under your GitHub account.

### ✅ Step 2: Clone Your Fork

Use the command line or GitHub Desktop to clone your fork locally:

```bash
git clone https://github.com/YOUR_USERNAME/review-project.git
cd review-project
```

Make your changes, commit your work, and push back to your own repo:

```bash
git add .
git commit -m "Complete TODOs and tests"
git push origin main
```

---

### ✅ Step 3: Invite Me to Your Repo

1. Go to your **forked repo** on GitHub.
2. Click **"Settings"** (top-right).
3. In the left sidebar, click **"Collaborators"**.
4. Click **"Add people"** and type **my GitHub username** ```delveccj```.
5. Click **"Invite"**.

---

## 📁 Project Structure

Here's how your project is organized:

```
review-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/reviews/
│   │   │       ├── App.java           <- The main application logic
│   │   │       └── Review.java        <- The data model bound to the CSV
│   │   └── resources/
│   │       └── reviews.csv            <- Your sample dataset
│   └── test/
│       └── java/com/example/reviews/
│           └── AppTest.java          <- Unit tests for your stream logic
└── pom.xml                            <- Maven project configuration
```

---

## ✨ What You'll Learn

- How to bind CSV data directly to Java objects using **OpenCSV** (yes, it's kind of magic ✨)
- The difference between **imperative** and **functional** (stream-based) programming styles
- How to process data efficiently using the **Java Streams API**
- Writing **clean**, **readable**, and **testable** data transformations

---

## 📈 CSV-to-Object Binding: It Just Works!

Thanks to **OpenCSV**, your `Review` class is auto-populated directly from the CSV file using field annotations. Here's the magic line in `App.java`:

```java
List<Review> reviews = new CsvToBeanBuilder<Review>(reader)
    .withType(Review.class)
    .withIgnoreLeadingWhiteSpace(true)
    .build()
    .parse();
```

As long as the field names in your `Review.java` match the column headers in `reviews.csv`, the data is bound automatically. No manual parsing needed!

---

## ⚡ Streams vs. Loops

This lab includes **both** stream-based and for-loop-based versions of several data operations so you can compare the two styles.

### Imperative Style (for-loop):
```java
for (Review r : reviews) {
    if (r.getPrice() > 50) {
        result.add(r.getTitle());
    }
}
```

### Functional Style (stream):
```java
reviews.stream()
    .filter(r -> r.getPrice() > 50)
    .map(Review::getTitle)
    .collect(Collectors.toList());
```

You'll explore both styles and come to appreciate the expressive, concise nature of functional code.

---

## 📝 What You’ll Implement

Your job in this lab is to **complete the two TODO methods** in `App.java` using a **functional approach**:

---

### ✅ TODO #1 — `filterByPriceRange(...)`

```java
public static List<Review> filterByPriceRange(List<Review> reviews, double min, double max) {
    // TODO - you need to implement this using a functional approach!
    return null;
}
```

> 👉 Return all reviews with a price between `min` and `max` (inclusive or exclusive—your choice, just be consistent).

---

### ✅ TODO #2 — `getHomeProductIdsUnder100(...)`

```java
public static List<String> getHomeProductIdsUnder100(List<Review> reviews) {
    // TODO - you need to implement this using a functional approach!
    return new ArrayList<>();
}
```

> 👉 Return a list of product IDs for all reviews in the `"Home"` category with a price under 100.

---

## 🧪 Unit Tests

There are test cases already provided to help verify your solutions. Run them with:

```bash
mvn test
```

They cover:
- Price filtering
- Product counting
- Keyword searching
- Functional transformation pipelines

---

## 🧠 Key Concepts You'll Use

- `filter()` – selectively keep elements
- `map()` – transform data from one form to another
- `collect(Collectors.toList())` – gather your results into a new list
- `groupingBy()` – count or organize data by a specific key

---

You may run the main method as well.  It will have an exception until the Unit test pass.  
The project is configured to use the exec-maven-plugin, which allows you to run the main() method in App.java directly from the command line:

```
mvn compile exec:java
```

This will:
1. Compile your project
2. Run the main() method inside App.java
3. Print out the filtered and transformed results to the console

---

## 🚀 Your Mission

Complete the TODOs using **functional streams**, compare them to the **imperative versions**, and get a feel for modern, clean Java.

