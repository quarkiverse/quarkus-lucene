package io.quarkiverse.lucene.deployment.devui;

import org.apache.lucene.analysis.TokenizerFactory;

import io.quarkus.deployment.IsDevelopment;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.devui.spi.page.CardPageBuildItem;
import io.quarkus.devui.spi.page.Page;
import io.quarkus.devui.spi.page.PageBuilder;

/**
 * Dev UI card for displaying important details such as the Lucene library version.
 */
public class LuceneDevUIProcessor {

    @BuildStep(onlyIf = IsDevelopment.class)
    void createVersion(BuildProducer<CardPageBuildItem> cardPageBuildItemBuildProducer) {
        final CardPageBuildItem card = new CardPageBuildItem();

        final PageBuilder versionPage = Page.externalPageBuilder("Lucene Version")
                .icon("font-awesome-solid:tag")
                .url("https://lucene.apache.org/")
                .doNotEmbed()
                .staticLabel(TokenizerFactory.class.getPackage().getSpecificationVersion());

        card.addPage(versionPage);

        card.setCustomCard("qwc-lucene-card.js");

        cardPageBuildItemBuildProducer.produce(card);
    }
}