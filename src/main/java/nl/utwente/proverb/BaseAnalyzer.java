package nl.utwente.proverb;

public interface BaseAnalyzer {

    /**
     * Enrich the original dataset without changing existed data
     */
    void autoEnrichment();

    /**
     * Generate the data based on pre-defined template, some 'illegal' data will be lost.
     */
    void reGeneration();
}
