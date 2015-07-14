package org.apache.lucene.morphology.context;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardFilter;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by akuznetsov on 6/24/15.
 */
public class StatAnalyzer extends Analyzer {



    @Override
    protected TokenStreamComponents createComponents(String s) {

        SimpleTokenizer src = new SimpleTokenizer();
        TokenFilter filter = new StandardFilter(src);
        filter = new LowerCaseFilter(filter);

        return new TokenStreamComponents(src, filter) {
            @Override
            protected void setReader(final Reader reader) throws IOException {
                super.setReader(reader);
            }
        };
    }


}