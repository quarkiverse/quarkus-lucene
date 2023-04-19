package io.quarkiverse.lucene.deployment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.util.AbstractAnalysisFactory;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.store.BaseDirectory;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.tartarus.snowball.SnowballProgram;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

class LuceneProcessor {

    private static final String FEATURE = "lucene";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void indexTransitiveDependencies(BuildProducer<IndexDependencyBuildItem> index) {
        index.produce(new IndexDependencyBuildItem("org.apache.lucene", "lucene-core"));
        index.produce(new IndexDependencyBuildItem("org.apache.lucene", "lucene-analyzers-common"));
        index.produce(new IndexDependencyBuildItem("org.apache.lucene", "lucene-queryparser"));
    }

    @BuildStep
    void commonTokenizerReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass,
            CombinedIndexBuildItem combinedIndex) {
        Set<String> classNames = new HashSet<>();

        // Token Factories
        classNames.addAll(collectSubclasses(combinedIndex, TokenizerFactory.class.getName()));

        // Filter Factories
        classNames.addAll(collectSubclasses(combinedIndex, TokenFilterFactory.class.getName()));

        // Stemmers
        classNames.addAll(collectSubclasses(combinedIndex, SnowballProgram.class.getName()));

        // Char filter factories
        classNames.addAll(collectSubclasses(combinedIndex, AbstractAnalysisFactory.class.getName()));

        // Directories
        classNames.addAll(collectSubclasses(combinedIndex, BaseDirectory.class.getName()));

        addCtorReflection(reflectiveClass, classNames);
    }

    private void addCtorReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass, Set<String> classNames) {
        reflectiveClass.produce(new ReflectiveClassBuildItem(true, false, false, classNames.toArray(new String[0])));
    }

    private List<String> collectSubclasses(CombinedIndexBuildItem combinedIndex, String className) {
        List<String> classes = combinedIndex.getIndex()
                .getAllKnownSubclasses(DotName.createSimple(className))
                .stream()
                .map(ClassInfo::toString)
                .collect(Collectors.toList());
        classes.add(className);
        return classes;
    }

    private List<String> collectImplementors(CombinedIndexBuildItem combinedIndex, String className) {
        List<String> classes = combinedIndex.getIndex()
                .getAllKnownImplementors(DotName.createSimple(className))
                .stream()
                .map(ClassInfo::toString)
                .collect(Collectors.toList());
        classes.add(className);
        return classes;
    }
}