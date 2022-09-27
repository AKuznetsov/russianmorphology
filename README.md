# Russian Morphology for Apache Lucene

Russian and English morphology for Java and [Apache Lucene](http://lucene.apache.org) 9.3 framework based on open source dictionary from site [АОТ](http://aot.ru). It uses dictionary base morphology with some heuristics for unknown words. It supports a homonym for example for a Russian word "вина" it gives two variants "вино" and "вина".


### How to use

Build project, by running `mvn clean package`, this will provide you the latest versions of the artifacts - 1.5, add it to your classpath. You could select which version to use - Russian or English.

Now you can create a Lucene Analyzer:

      RussianAnalayzer russian = new RussianAnalayzer();
      EnglishAnalayzer english = new EnglishAnalayzer();

You can write you own analyzer using filter that convert word in it's right forms. 

      LuceneMorphology luceneMorph = new EnglishLuceneMorphology();
      TokenStream tokenStream = new MorphlogyFilter(result, luceneMorph);

Because usually LuceneMorphology contains a lot data needing for it functionality, it is better didn't create this object for each MorphologyFilter.

Also if you need get a list of base forms of word, you can use following example 


     LuceneMorphology luceneMorph = new EnglishLuceneMorphology();
     List<String> wordBaseForms = luceneMorph.getMorphInfo(word);

### Solr

You can use the LuceneMorphology as morphology filter in a Solr _schema.xml_ using a **MorphologyFilterFactory:**

```xml
<fieldType name="content" class="solr.TextField" positionIncrementGap="100">
      <analyzer>
        <tokenizer class="solr.StandardTokenizerFactory"/>
		<filter class="org.apache.lucene.analysis.morphology.MorphologyFilterFactory" language="Russian"/>
		<filter class="org.apache.lucene.analysis.morphology.MorphologyFilterFactory" language="English"/>
      </analyzer>
</fieldType>
```

Just add _morphology-1.5.jar_ in your Solr lib-directories

### Restrictions
  
  * It works only with UTF-8.
  * It assume what letters е and ё are the same.
  * Word forms with prefixes like "наибольший" treated as separate word. 

### License

Apache License, Version 2.0
