package org.apache.lucene.russian.morphology.analayzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import java.io.Reader;
import java.io.IOException;

public class RussianMorphlogyAnalayzer  extends Analyzer {
    private ArrayEvristics arrayEvristics;

    public RussianMorphlogyAnalayzer() throws IOException {
        arrayEvristics = new ArrayEvristics();
    }

    public TokenStream tokenStream(String fieldName, Reader reader) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
