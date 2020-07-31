package org.apache.lucene.demo;
import org.apache.lucene.search.similarities.ClassicSimilarity;

public class CMPT456Similarity extends ClassicSimilarity{
    //1+log(docCount+2/docFreq+2)
    @Override
    public float idf(long docFreq, long docCount) {
      //System.out.println("CMPT456Similarity idf");
      //System.out.println(docCount);
      //System.out.println(docFreq);
      return (float)(Math.log((docCount+2)/(double)(docFreq+2)) + 1.0);
    }
    //(1+freq)^1/2
    @Override
    public float tf(float freq) {
      //System.out.println("CMPT456Similarity tf");
      //System.out.println(freq);
      return (float)Math.sqrt(freq+1);
    }  
    
}