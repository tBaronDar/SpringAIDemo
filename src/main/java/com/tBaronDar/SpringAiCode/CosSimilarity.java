package com.tBaronDar.SpringAiCode;

public class CosSimilarity {

  public static double calc2(float[] embedding1, float[] embedding2) {

    double dotProduct = 0;
    double norm1 = 0;
    double norm2 = 0;

    for (int i = 0; i < embedding1.length; i++) {
      dotProduct += embedding1[i] * embedding2[i];
      norm1 += Math.pow(embedding1[i], 2);
      norm2 += Math.pow(embedding2[i], 2);
    }

    return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
  }
}
