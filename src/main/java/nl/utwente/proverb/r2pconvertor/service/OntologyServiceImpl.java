package nl.utwente.proverb.r2pconvertor.service;

import nl.utwente.proverb.r2pconvertor.dto.Article;
import nl.utwente.proverb.r2pconvertor.dto.Repository;
import nl.utwente.proverb.r2pconvertor.dto.ontology.PROVERB;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OntologyServiceImpl implements OntologyService{

    private final Model model;

    public OntologyServiceImpl(File modelFile){
        this.model = ModelFactory.createDefaultModel().read(modelFile.getAbsolutePath());
    }

    @Override
    public List<Resource> getAllTools() {
        var p = model.listSubjectsWithProperty(RDF.type, PROVERB.R_TOOL);
        return p.toList();
    }

    @Override
    public Resource getTool(String insName) {
        return model.getResource(PROVERB.getURI() + insName);
    }


    @Override
    public List<RDFNode> getRepositoryNodes(Resource toolResource) {

        var p = model.listObjectsOfProperty(toolResource, PROVERB.P_REPOSITORY);
        return p.toList();
    }

    @Override
    public List<Repository> getRepositories(Resource toolResource) {
        var nodes = this.getRepositoryNodes(toolResource);
        var repos = new ArrayList<Repository>(nodes.size());
        for (var node : nodes){
            var dto = new Repository();
            var name = model.listObjectsOfProperty((Resource) node, PROVERB.P_NAME)
                    .toList().stream().findFirst();
            if (name.isPresent()){
                dto.setName(name.get().asLiteral().getString());
                dto.setUrl(node.toString());
            }
            repos.add(dto);
        }
        return repos;
    }

    @Override
    public List<RDFNode> getArticleNodes(Resource toolResource) {
        var p = model.listObjectsOfProperty(toolResource, PROVERB.P_RELATED_PAPER);
        return p.toList();
    }

    @Override
    public List<Article> getArticles(Resource toolResource) {
        var nodes = this.getArticleNodes(toolResource);
        var articles = new ArrayList<Article>(nodes.size());
        for (var node : nodes){
            var dto = new Article();
            var name = getNameProperty(node);
            if (!name.isEmpty()){
                dto.setTitle(name);
                dto.setDoiURL(node.toString());
            }
            articles.add(dto);
        }
        return articles;
    }

    public String getNameProperty(RDFNode node){
        var name = model.listObjectsOfProperty((Resource) node, PROVERB.P_NAME)
                .toList().stream().findFirst();
        return name.map(rdfNode -> rdfNode.asLiteral().getString()).orElse("");
    }
}
