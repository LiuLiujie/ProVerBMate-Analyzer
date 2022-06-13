package nl.utwente.proverb.r2pconvertor;

import nl.utwente.proverb.r2pconvertor.convertors.Convertor;
import nl.utwente.proverb.r2pconvertor.convertors.Tool2Tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RDF2ProVerB {

    private static String modelPath = "enriched_ProVerB.owl";

    private static String toolsPath = "Verification-Tool-Overview/Tools";

    private static String demoTool = "Verification-Tool-Overview/Tools/Checkers/AGREE.md";

    public static void main(String[] args) {
        if (args.length == 2){
            modelPath = args[0];
            toolsPath = args[1];
        }

        var model = loadModelFile();
        Convertor convertor;

        var tools = loadTools();
        //var tools = loadDemo();
        for (File tool : tools){
            convertor = new Tool2Tool.Builder(model, tool)
                    //.loadRelatedPapers(true)
                    .loadRepositories(true)
                    .build();
            convertor.convert();
        }
    }

    private static File loadModelFile(){
        return new File(modelPath);
    }

    private static List<File> loadDemo(){
        var list = new ArrayList<File>(1);
        list.add(new File(demoTool));
        return list;
    }

    private static List<File> loadTools(){
        var path = new File(toolsPath);
        if (!path.isDirectory()){
            throw new IllegalArgumentException("Invalid tools path");
        }
        var rootFiles = path.listFiles();
        if (rootFiles == null || rootFiles.length == 0){
            throw new IllegalArgumentException("Invalid tools path");
        }
        return getFiles(new ArrayList<>(List.of(rootFiles)));
    }

    private static List<File> getFiles(List<File> rootFiles){

        var newFiles = new ArrayList<File>();
        for (File file : rootFiles){
            if (file.isDirectory()){
                var fs = file.listFiles();
                if (fs != null){
                    newFiles.addAll(getFiles(new ArrayList<>(List.of(fs))));
                }
            }
            if (file.isFile()){
                newFiles.add(file);
            }
        }
        return newFiles;
    }
}
