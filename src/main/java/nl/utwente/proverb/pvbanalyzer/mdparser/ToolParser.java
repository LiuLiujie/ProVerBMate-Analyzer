package nl.utwente.proverb.pvbanalyzer.mdparser;


import nl.utwente.proverb.pvbanalyzer.PVBConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolParser extends BaseParser {

    private final File tool;

    private final PVBConfiguration config;

    public ToolParser(File tool, PVBConfiguration configuration){
        this.tool = tool;
        this.config = configuration;
    }

    public MDTool read(File input){
        MDTool tool = new MDTool();
        tool.setName(input.getName());
        try (var reader = new InputStreamReader(new FileInputStream(input));
             var br = new BufferedReader(reader)){
            String line;
            line = br.readLine();
            while (line != null) {
                switch (line){
                    case MDToolTemplate.FULL_NAME:
                        var name = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.FULL_NAME, name);
                        break;
                    case MDToolTemplate.DOMAIN:
                        var domains = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.DOMAIN, domains);
                        break;
                    case MDToolTemplate.TYPE:
                        var types = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.TYPE, types);
                        break;
                    case MDToolTemplate.INPUT_THING:
                        List<String> thing = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.INPUT_THING, thing);
                        break;
                    case MDToolTemplate.INPUT_FORMAT:
                        List<String> inputs = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.INPUT_FORMAT, inputs);
                        break;
                    case MDToolTemplate.OUTPUT:
                        List<String> outputs = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.OUTPUT, outputs);
                        break;
                    case MDToolTemplate.INTERNAL:
                        List<String> internals = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.INTERNAL, internals);
                        break;
                    case MDToolTemplate.COMMENT:
                        List<String> comments = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.COMMENT, comments);
                        break;
                    case MDToolTemplate.URIS:
                        List<String> uris = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.URIS, uris);
                        break;
                    case MDToolTemplate.LAST_COMMIT_DATE:
                        List<String> cmtDate = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.LAST_PUB_DATE, cmtDate);
                        break;
                    case MDToolTemplate.LAST_PUB_DATE:
                        List<String> pubDate = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.LAST_COMMIT_DATE, pubDate);
                        break;
                    case MDToolTemplate.PAPERS:
                        List<String> papers = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.PAPERS, papers);
                        break;
                    case MDToolTemplate.RELA_TOOLS:
                        List<String> relaTools = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.RELA_TOOLS, relaTools);
                        break;
                    case MDToolTemplate.META:
                        List<String> metas = readTilEmpty(br);
                        tool.addProperty(MDToolTemplate.META, metas);
                        break;
                    default:
                        List<String> defaults = readTilEmpty(br);
                        tool.addProperty(line, defaults);
                        System.err.println("Undefined title in MD file:" + line);
                        break;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Reading file error:" +e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        return tool;
    }

    public List<String> readTilEmpty(BufferedReader br) throws IOException{
        String line = br.readLine();
        List<String> content = new ArrayList<>();
        while (line != null && !line.isEmpty()){
            content.add(line);
            line = br.readLine();
        }
        return content;
    }

    public void write(MDTool mdTool){
        try (var pw = new PrintWriter(new FileWriter(tool))){
            StringBuilder builder = new StringBuilder();
            for (var title : mdTool.getTitles()){
                builder.append(combineSeg(title, mdTool.getProperty(title)));
            }
            pw.println(builder);
        }catch (IOException e){
            System.err.println(e.getStackTrace().toString());
        }
    }

    public String combineSeg(String title, List<String> contents){
        StringBuilder builder = new StringBuilder();
        builder.append(title).append("\n");
        contents.forEach(c -> builder.append(c).append("\n"));
        builder.append("\n");
        return builder.toString();
    }
}
