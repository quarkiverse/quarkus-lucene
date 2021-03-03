package io.quarkiverse.lucene.deployment;

import javax.inject.Inject;

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
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.tartarus.snowball.ext.EnglishStemmer;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

class LuceneProcessor {

    @Inject
    BuildProducer<ReflectiveClassBuildItem> reflectiveClass;

    private static final String FEATURE = "lucene";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void commonTokenizerReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
        addCtorReflection(StandardTokenizerFactory.class);
        addCtorReflection(LowerCaseFilterFactory.class);
        addCtorReflection(EdgeNGramTokenizerFactory.class);
        addCtorReflection(PathHierarchyTokenizerFactory.class);
        addCtorReflection(WhitespaceTokenizerFactory.class);
        addCtorReflection(PatternTokenizerFactory.class);
        addCtorReflection(KeywordTokenizerFactory.class);
        addCtorReflection(NGramTokenizerFactory.class);
        // Filter Factories
        addCtorReflection(EdgeNGramFilterFactory.class);
        addCtorReflection(LowerCaseFilterFactory.class);
        addCtorReflection(ShingleFilterFactory.class);
        addCtorReflection(StopFilterFactory.class);
        addCtorReflection(SynonymGraphFilterFactory.class);
        addCtorReflection(SnowballPorterFilterFactory.class);
        addCtorReflection(UpperCaseFilterFactory.class);
        addCtorReflection(PatternReplaceFilterFactory.class);
        addCtorReflection(PorterStemFilterFactory.class);
        addCtorReflection(NGramFilterFactory.class);
        addCtorReflection(ShingleFilterFactory.class);
        addCtorReflection(WordDelimiterGraphFilterFactory.class);

        // Stemmers
        addCtorReflection(EnglishStemmer.class);
        // Char filter factories
        addCtorReflection(CharFilterFactory.class);
        addCtorReflection(MappingCharFilterFactory.class);
        addCtorReflection(PatternReplaceCharFilterFactory.class);

        // Directories
        addCtorReflection(MMapDirectory.class);
        addCtorReflection(ByteBuffersDirectory.class);
        addCtorReflection(RAMDirectory.class);
        addCtorReflection(SimpleFSDirectory.class);
        addCtorReflection(NIOFSDirectory.class);
    }

    private void addCtorReflection(Class<?> clazz) {
        reflectiveClass.produce(new ReflectiveClassBuildItem(true, false, false, clazz));
    }
}
