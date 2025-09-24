package com.tor.algoritms;

import java.util.*;

/**
 * User: tor
 * Date: 24.09.2025
 * Time: 22:51
 * MinHash is a probabilistic data structure used for estimating the similarity between sets. It is particularly useful in applications like near-duplicate detection, document similarity, and recommendation systems. MinHash computes a concise signature (sketch) for each set and estimates their similarity efficiently without requiring the sets themselves to be stored or compared directly.
 *
 * Key Features:
 * 1. Jaccard Similarity: MinHash estimates the Jaccard Similarity between two sets.
 *
 * J(A, B) = \frac{|A \cap B|}{|A \cup B|}
 *
 * 2. Space-Efficient: Uses a small, fixed-size signature to represent large sets.
 *
 * 3. Union-Friendly: Allows computation of set similarity using only the MinHash signatures.
 *
 * 4. Fast Approximation: Efficiently handles very large sets where exact computation is impractical.
 */
public class MinHash {
    private final int numHashFunctions;
    private final List<HashFunction> hashFunctions;

    // Constructor
    public MinHash(int numHashFunctions) {
        this.numHashFunctions = numHashFunctions;
        this.hashFunctions = new ArrayList();
        Random random = new Random();

        // Generate random hash functions
        for (int i = 0; i < numHashFunctions; i++) {
            int a = random.nextInt(1000) + 1; // Random coefficient
            int b = random.nextInt(1000) + 1; // Random offset
            int p = 10007; // A large prime number
            hashFunctions.add(new HashFunction(a, b, p));
        }
    }

    // Compute MinHash signature for a set
    public int[] computeSignature(Set<Integer> set) {
        int[] signature = new int[numHashFunctions];
        Arrays.fill(signature, Integer.MAX_VALUE);

        for (int element : set) {
            for (int i = 0; i < numHashFunctions; i++) {
                int hashValue = hashFunctions.get(i).hash(element);
                signature[i] = Math.min(signature[i], hashValue);
            }
        }
        return signature;
    }

    // Compute Jaccard Similarity from signatures
    public double estimateJaccardSimilarity(int[] signatureA, int[] signatureB) {
        int matches = 0;

        for (int i = 0; i < numHashFunctions; i++) {
            if (signatureA[i] == signatureB[i]) {
                matches++;
            }
        }
        return (double) matches / numHashFunctions;
    }

    // Hash function class
    static class HashFunction {
        private final int a, b, p;

        public HashFunction(int a, int b, int p) {
            this.a = a;
            this.b = b;
            this.p = p;
        }

        public int hash(int x) {
            return (a * x + b) % p;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        MinHash minHash = new MinHash(100); // 100 hash functions

        // Sets
        Set<Integer> setA = new HashSet(Arrays.asList(1, 2, 3));
        Set<Integer> setB = new HashSet(Arrays.asList(2, 3, 4));

        // Compute signatures
        int[] signatureA = minHash.computeSignature(setA);
        int[] signatureB = minHash.computeSignature(setB);

        // Estimate Jaccard Similarity
        double similarity = minHash.estimateJaccardSimilarity(signatureA, signatureB);
        System.out.println("Estimated Jaccard Similarity: " + similarity);
    }
}