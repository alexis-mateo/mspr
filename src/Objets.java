import java.util.HashMap;

public class Objets {
    private static HashMap<String, String> objets = new HashMap<>();

    public static void initListObjets() {
        FileUtils objetsFile = new FileUtils("files/liste.txt");
        for(String line: objetsFile.getLines()) {
            objets.put(line.split("\t")[0], line.split("\t")[1]);
        }
    }

    public static HashMap<String, String> getObjets() {
        return objets;
    }

    public static String getObjetsName(String alias) {
        return objets.get(alias);
    }

    public static String getHtmlForObject(String alias, boolean checked) {
        return
            "<div>" + getObjetsName(alias) + "<input type=\"checkbox\" " + (checked ? "checked" : "") + "/></div>\n";
    }
}
