package io.quarkiverse.lucene.deployment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.AbstractAnalysisFactory;
import org.apache.lucene.analysis.TokenFilterFactory;
import org.apache.lucene.analysis.TokenizerFactory;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.store.BaseDirectory;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.tartarus.snowball.SnowballProgram;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import io.quarkus.deployment.builditem.nativeimage.*;

class LuceneProcessor {

    private static final String FEATURE = "lucene";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void indexTransitiveDependencies(BuildProducer<IndexDependencyBuildItem> index) {
        index.produce(new IndexDependencyBuildItem("org.apache.lucene", "lucene-core"));
        index.produce(new IndexDependencyBuildItem("org.apache.lucene", "lucene-analysis-common"));
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

        // memory index
        classNames.add(MemoryIndex.class.getName());

        addCtorReflection(reflectiveClass, classNames);
    }

    @BuildStep
    void runtimeClasses(BuildProducer<RuntimeInitializedClassBuildItem> producer) {
        // We have to initiale the InetAddressPoint at runtime because of the internal usage of java.net.InetAddress.
        // The Graal-Compiler forces us to do this since newer versions.
        producer.produce(new RuntimeInitializedClassBuildItem("org.apache.lucene.document.InetAddressPoint"));
    }

    private void addCtorReflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass, Set<String> classNames) {
        reflectiveClass.produce(ReflectiveClassBuildItem.builder(classNames.toArray(new String[0])).constructors(true).build());
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
}
