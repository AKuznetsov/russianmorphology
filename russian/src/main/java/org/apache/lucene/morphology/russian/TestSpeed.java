/**
 * Copyright 2009 Alexander Kuznetsov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.morphology.russian;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Token;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: akuznetsov
 * Date: 31.10.2009
 * Time: 14:01:11
 * To change this template use File | Settings | File Templates.
 */
public class TestSpeed {

    public static void main(String[] args) throws IOException {
        RussianAnalayzer russianAnalayzer = new RussianAnalayzer();
        bookProccess(russianAnalayzer, "C:/tmp/_Aleksandr_Suhov_Tanets_na_raskalennyih_uglyah1.fb2");
        Long stat = System.currentTimeMillis();
        bookProccess(russianAnalayzer, "C:/tmp/_Aleksandr_Suhov_Tanets_na_raskalennyih_uglyah1.fb2");
        System.out.println("Done in " + (System.currentTimeMillis() - stat));
    }

    private static void bookProccess(RussianAnalayzer russianAnalayzer, String bookName) throws IOException {
        FileInputStream inputStream = new FileInputStream(bookName);
        TokenStream tokenStream = russianAnalayzer.tokenStream(null,new InputStreamReader(inputStream,"UTF-8"));
        final Token reusableToken = new Token();
        long count = 0;
        Token nextToken;
        for (; ;) {
            nextToken = tokenStream.next(reusableToken);
           // System.out.println(" " + nextToken.term());
            count++;
            if (nextToken == null) {
                break;
            }

        }
        //System.out.println("Words " + count);
    }
}
