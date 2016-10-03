# Russian Morphology for lucene

Russian and English morphology for java and lucene 6.1 framework based on open source dictionary from site [АОТ](http://aot.ru). It use dictionary base morphology with some heuristics for unknown words. It support homonym for example for Russian word "вина" it gives two variants "вино" and "вина".


### How to use

First download 
[morph-1.2.jar](https://bintray.com/artifact/download/akuznetsov/russianmorphology/org/apache/lucene/morphology/morph/1.2/morph-1.2.jar)  
and add it to your class path. When download [Russian](https://bintray.com/artifact/download/akuznetsov/russianmorphology/org/apache/lucene/morphology/russian/1.2/russian-1.2.jar) or 
[English](https://bintray.com/artifact/download/akuznetsov/russianmorphology/org/apache/lucene/morphology/english/1.2/english-1.2.jar) package. 

If you use maven you can add dependency 

        <dependency>
            <groupId>org.apache.lucene.morphology</groupId>
            <artifactId>russian</artifactId>
            <version>1.2</version>
        </dependency>


        <dependency>
            <groupId>org.apache.lucene.morphology</groupId>
            <artifactId>english</artifactId>
            <version>1.2</version>
        </dependency>

Don't forget add link to repository


    <repositories>
    ...............
      <repository>
        <snapshots>
          <enabled>false</enabled>
        </snapshots>
        <id>bintray-akuznetsov-russianmorphology</id>
        <name>bintray</name>
        <url>http://dl.bintray.com/akuznetsov/russianmorphology</url>
      </repository>
    </repositories>



Now you can create a Lucene Analyzer 


      RussianAnalayzer russian = new RussianAnalayzer();
      EnglishAnalayzer english = new EnglishAnalayzer();


You can write you own analyzer using filter that convert word in it's right forms. 

      LuceneMorphology luceneMorph = new EnglishLuceneMorphology();
      TokenStream tokenStream = new MorphlogyFilter(result, luceneMorph);

Because usually LuceneMorphology contains a lot data needing for it functionality, it is better didn't create this object for each MorphologyFilter.

Also if you need get a list of base forms of word, you can use following example 


     LuceneMorphology luceneMorph = new EnglishLuceneMorphology();
     List<String> wordBaseForms = luceneMorph.getMorphInfo(word);


### Restrictions
  
  * It works only with UTF-8.
  * It assume what letters е and ё are the same.
  * Word forms with prefixes like "наибольший" treated as separate word. 
