package org.apache.lucene.russian.morphology.dictonary;

import java.io.IOException;


public interface WordProccessor {

    public void proccess(WordCard wordCard) throws IOException;
}
