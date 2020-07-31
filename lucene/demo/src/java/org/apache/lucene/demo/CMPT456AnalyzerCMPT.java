package org.apache.lucene.analysis.standard;
//package org.tartarus.snowball.ext.PorterStemmer;
//package org.apache.lucene.demo;
//import org.apache.lucene.demo.PorterStemmer;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;


import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;

//import lucene/analysis/common/src/java/org/apache/lucene/analysis/en

//import org.apache.lucene.analysis.en.PorterStemFilter;
//import org.tartarus.snowball.ext.PorterStemmer;
//import org.apache.lucene.analysis.snowball.SnowballFilter;
//import org.apache.lucene.analysis.en.PorterStemFilter;
//import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
//import org.tartarus.snowball.ext.PorterStemmer;
//import org.tartarus.snowball.ext.PorterStemmer;
//import org.tartarus.snowball.ext.PorterStemmer;

/**
 * Filters {@link StandardTokenizer} with {@link StandardFilter}, {@link
 * LowerCaseFilter} and {@link StopFilter}, using a list of
 * English stop words.
 */
public final class CMPT456AnalyzerCMPT extends StopwordAnalyzerBase {

  /** An unmodifiable set containing some common English words that are not usually useful
  for searching.*/
  public static final CharArraySet ENGLISH_STOP_WORDS_SET;
  
  static {
    final List<String> stopWords = Arrays.asList(
      "a", "an", "and", "are", "as", "at", "be", "but", "by",
      "for", "if", "in", "into", "is", "it",
      "no", "not", "of", "on", "or", "such",
      "that", "the", "their", "then", "there", "these",
      "they", "this", "to", "was", "will", "with"
    );
    final CharArraySet stopSet = new CharArraySet(stopWords, false);
    ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet); 
  }
  
  /** Default maximum allowed token length */
  public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;

  private int maxTokenLength = DEFAULT_MAX_TOKEN_LENGTH;

  /** An unmodifiable set containing some common English words that are usually not
  useful for searching. */
  public static final CharArraySet STOP_WORDS_SET = ENGLISH_STOP_WORDS_SET;

  /** Builds an analyzer with the given stop words.
   * @param stopWords stop words */
  public CMPT456AnalyzerCMPT(CharArraySet stopWords) {
    super(stopWords);
  }

  /** Builds an analyzer with the default stop words ({@link #STOP_WORDS_SET}).
   */
  public CMPT456AnalyzerCMPT() {
    this(STOP_WORDS_SET);
  }

  /** Builds an analyzer with the stop words from the given reader.
   * @see WordlistLoader#getWordSet(Reader)
   * @param stopwords Reader to read stop words from */
  public CMPT456AnalyzerCMPT(Reader stopwords) throws IOException {
    this(loadStopwordSet(stopwords));
    //System.out.println("WTF");
  }

  /**
   * Set the max allowed token length.  Tokens larger than this will be chopped
   * up at this token length and emitted as multiple tokens.  If you need to
   * skip such large tokens, you could increase this max length, and then
   * use {@code LengthFilter} to remove long tokens.  The default is
   * {@link StandardAnalyzer#DEFAULT_MAX_TOKEN_LENGTH}.
   */
  public void setMaxTokenLength(int length) {
    maxTokenLength = length;
  }
    
  /** Returns the current maximum token length
   * 
   *  @see #setMaxTokenLength */
  public int getMaxTokenLength() {
    System.out.println("Token Length WTF1:");
    return maxTokenLength;
  }

  @Override
  protected TokenStreamComponents createComponents(final String fieldName) {
    final StandardTokenizer src = new StandardTokenizer();
    src.setMaxTokenLength(maxTokenLength);
    TokenStream tok = new StandardFilter(src);
    tok = new LowerCaseFilter(tok);
    tok = new StopFilter(tok, stopwords);
    //porter stem
    tok = new PorterStemFilter(tok);
    return new TokenStreamComponents(src, tok) {
      @Override
      protected void setReader(final Reader reader) {
        // So that if maxTokenLength was changed, the change takes
        // effect next time tokenStream is called:
        src.setMaxTokenLength(CMPT456AnalyzerCMPT.this.maxTokenLength);
        super.setReader(reader);
      }
    };
  }

  @Override
  protected TokenStream normalize(String fieldName, TokenStream in) {
    TokenStream result = new StandardFilter(in);
    result = new LowerCaseFilter(result);
    result = new PorterStemFilter(result);
    return result;
  }
}
