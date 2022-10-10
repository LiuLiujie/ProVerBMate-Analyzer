package nl.utwente.proverb.pvbanalyzer;

import nl.utwente.proverb.pvbanalyzer.mdparser.MDTool;
import nl.utwente.proverb.pvbanalyzer.model.PVBModel;
import nl.utwente.proverb.pvbanalyzer.subanalyzers.PaperAnalyzer;
import nl.utwente.proverb.BaseAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class PVBAnalyzer implements BaseAnalyzer {

    private final PVBModel model;

    private final List<BaseAnalyzer> subAnalyzers = new ArrayList<>();

    public PVBAnalyzer(PVBModel model, MDTool mdTool){
        this.model = model;
        subAnalyzers.add(new PaperAnalyzer(model, mdTool));
    }

    @Override
    public void autoEnrichment() {
        subAnalyzers.forEach(BaseAnalyzer::autoEnrichment);
    }

    @Override
    public void reGeneration() {
        subAnalyzers.forEach(BaseAnalyzer::reGeneration);
    }
}
