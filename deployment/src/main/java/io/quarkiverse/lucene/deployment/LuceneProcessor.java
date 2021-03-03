package io.quarkiverse.lucene.deployment;

import org.apache.lucene.analysis.charfilter.MappingCharFilterFactory;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.UpperCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenizerFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramTokenizerFactory;
import org.apache.lucene.analysis.path.PathHierarchyTokenizerFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceCharFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.apache.lucene.analysis.pattern.PatternTokenizerFactory;
import org.apache.lucene.analysis.shingle.ShingleFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.synonym.SynonymGraphFilterFactory;
import org.apache.lucene.analysis.util.CharFilterFactory;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.tartarus.snowball.ext.EnglishStemmer;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

class LuceneProcessor {

    private static final String FEATURE = "lucene";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @SuppressWarnings("deprecation")
    void commonTokenizerReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
        addCtorReflection(reflectiveClass, StandardTokenizerFactory.class);
        addCtorReflection(reflectiveClass, LowerCaseFilterFactory.class);
        addCtorReflection(reflectiveClass, EdgeNGramTokenizerFactory.class);
        addCtorReflection(reflectiveClass, PathHierarchyTokenizerFactory.class);
        addCtorReflection(reflectiveClass, WhitespaceTokenizerFactory.class);
        addCtorReflection(reflectiveClass, PatternTokenizerFactory.class);
        addCtorReflection(reflectiveClass, KeywordTokenizerFactory.class);
        addCtorReflection(reflectiveClass, NGramTokenizerFactory.class);
        // Filter Factories
        addCtorReflection(reflectiveClass, EdgeNGramFilterFactory.class);
        addCtorReflection(reflectiveClass, LowerCaseFilterFactory.class);
        addCtorReflection(reflectiveClass, ShingleFilterFactory.class);
        addCtorReflection(reflectiveClass, StopFilterFactory.class);
        addCtorReflection(reflectiveClass, SynonymGraphFilterFactory.class);
        addCtorReflection(reflectiveClass, SnowballPorterFilterFactory.class);
        addCtorReflection(reflectiveClass, UpperCaseFilterFactory.class);
        addCtorReflection(reflectiveClass, PatternReplaceFilterFactory.class);
        addCtorReflection(reflectiveClass, PorterStemFilterFactory.class);
        addCtorReflection(reflectiveClass, NGramFilterFactory.class);
        addCtorReflection(reflectiveClass, ShingleFilterFactory.class);
        addCtorReflection(reflectiveClass, WordDelimiterGraphFilterFactory.class);

        // Stemmers
        addCtorReflection(reflectiveClass, EnglishStemmer.class);
        // Char filter factories
        addCtorReflection(reflectiveClass, CharFilterFactory.class);
        addCtorReflection(reflectiveClass, MappingCharFilterFactory.class);
        addCtorReflection(reflectiveClass, PatternReplaceCharFilterFactory.class);

        // Directories
        addCtorReflection(reflectiveClass, MMapDirectory.class);
        addCtorReflection(reflectiveClass, ByteBuffersDirectory.class);
        addCtorReflection(reflectiveClass, NIOFSDirectory.class);
        // Include deprecated directories for now
        addCtorReflection(reflectiveClass, org.apache.lucene.store.RAMDirectory.class);
        addCtorReflection(reflectiveClass, org.apache.lucene.store.SimpleFSDirectory.class);
    }

    private void addCtorReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass, Class<?> clazz) {
        reflectiveClass.produce(new ReflectiveClassBuildItem(true, false, false, clazz));
    }
}
