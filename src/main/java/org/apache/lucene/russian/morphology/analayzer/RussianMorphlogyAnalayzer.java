package org.apache.lucene.russian.morphology.analayzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;

import java.io.Reader;
import java.io.IOException;

public class RussianMorphlogyAnalayzer  extends Analyzer {
    private SuffixEvristics suffixEvristics;

    public RussianMorphlogyAnalayzer() throws IOException {
        suffixEvristics = new SuffixEvristics();
    }

    public TokenStream tokenStream(String fieldName, Reader reader) {
        TokenStream result = new StandardTokenizer(reader);
        result = new StandardFilter(result);
        result = new LowerCaseFilter(result);
        return new RussianMorphlogyFilter(result,suffixEvristics);
    }
}
