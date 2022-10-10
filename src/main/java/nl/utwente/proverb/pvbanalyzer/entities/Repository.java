package nl.utwente.proverb.pvbanalyzer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Repository {

    private String name;

    private String abs;

    private List<Contributor> contributors;

    private String lastCommitDate;

    private String url;

    @Getter
    @AllArgsConstructor
    public static class Contributor {

        private String name;

        private String url;
    }
}
