package nl.utwente.proverb.r2pconvertor.mdwriters;

public class MDTemplate {

    private MDTemplate() { }

    public static String urlWithName(String url, String name){
        return "["+name+"]("+url+")";
    }
}
