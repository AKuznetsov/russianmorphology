package org.apache.lucene.russian.morphology.dictonary;

import java.io.IOException;

/**
 * Interface allows get information from 
 * {@org.apache.lucene.russian.morphology.dictonary.DirtonaryReader}.
 */
public interface WordProccessor {

    public void proccess(WordCard wordCard) throws IOException;
}
