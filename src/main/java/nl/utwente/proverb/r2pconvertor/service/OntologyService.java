package nl.utwente.proverb.r2pconvertor.service;

import nl.utwente.proverb.r2pconvertor.dto.Article;
import nl.utwente.proverb.r2pconvertor.dto.Repository;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.List;

public interface OntologyService {

    List<Resource> getAllTools();

    Resource getTool(String insName);

    List<RDFNode> getRepositoryNodes(Resource toolResource);

    List<Repository> getRepositories(Resource toolResource);

    List<RDFNode> getArticleNodes(Resource toolResource);

    List<Article> getArticles(Resource toolResource);
}
