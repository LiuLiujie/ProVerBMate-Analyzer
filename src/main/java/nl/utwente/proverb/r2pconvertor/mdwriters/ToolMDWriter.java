package nl.utwente.proverb.r2pconvertor.mdwriters;


import nl.utwente.proverb.r2pconvertor.convertors.Tool2Tool;
import nl.utwente.proverb.r2pconvertor.dto.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ToolMDWriter {

    private static final String PREFIX = "#### ";

    private static final String REPO = PREFIX + "Repository";

    private final File tool;

    private final Tool2Tool config;

    private final StringBuilder output = new StringBuilder("\n");

    public ToolMDWriter(File tool, Tool2Tool tool2Tool){
        this.tool = tool;
        this.config = tool2Tool;
    }

    public void write(){
        try (var pw = new PrintWriter(new FileWriter(tool, true))){
            pw.println(output);
        }catch (IOException e){
            System.err.println(e.getStackTrace().toString());
        }
    }

    public void convertRepositories(List<Repository> repositories) {
        if (config.isLoadRepositories()){
            StringBuilder builder = new StringBuilder();
            builder.append(REPO).append("\n");
            builder.append("\n");
            for (var repo : repositories){
                builder.append(convertRepository(repo));
            }
            builder.append("\n");
            output.append(builder);
        }
    }

    private String convertRepository(Repository repository){
        StringBuilder builder = new StringBuilder();
        builder.append("- ").append(MDTemplate.urlWithName(repository.getUrl(), repository.getName())).append("\n");
        if (config.isLoadRepoLastCommitTime() && repository.getLastUpdate() != null){
            builder.append("  - ").append("List commit date: ").append(repository.getLastUpdate());
        }
        if (config.isLoadRepoContributors() && repository.getContributors().isEmpty()){
            for (var contributor : repository.getContributors()){
                builder.append(MDTemplate.urlWithName(contributor.getUrl(), contributor.getName())).append(" ");
            }
        }
        return builder.toString();
    }
}
