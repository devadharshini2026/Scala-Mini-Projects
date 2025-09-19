# Movie Recommendation System (Spark + Scala)

## About
This is my **Movie Recommendation System** built using **Apache Spark (MLlib ALS)** in Scala.  
It recommends movies to users based on collaborative filtering.

---

## Dataset
I used the **MovieLens dataset**:
- `ratings.csv` → contains userId, movieId, rating
- `movies.csv` → contains movieId, title

Download small dataset here: [MovieLens 100k](https://grouplens.org/datasets/movielens/).

---

## Features
- Train an ALS (Alternating Least Squares) model with Spark MLlib
- Get top 5 personalized movie recommendations for a given user
- Command-line interface for interaction

---

## Concepts Covered
- Spark RDDs and transformations
- File I/O with CSVs
- Collaborative Filtering with ALS
- CLI interaction in Scala
- Recommendation systems

---
