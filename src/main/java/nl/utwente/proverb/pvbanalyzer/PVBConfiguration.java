package nl.utwente.proverb.pvbanalyzer;

import lombok.Getter;

public class PVBConfiguration {

    @Getter
    private boolean loadRelatedPapers;
    @Getter
    private boolean loadPaperAllAuthors;
    @Getter
    private boolean loadPaperMainAuthor;
    @Getter
    private boolean loadRepositories;
    @Getter
    private boolean loadRepoLastCommitTime;
    @Getter
    private boolean loadRepoContributors;
}
