import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Agent {
    public static Agent createAgentFromString(FileUtils file) {
        return new Agent(file.getLine(1), file.getLine(0), file.getLine(2), file.getLine(3), file.getMateriel());
    }

    public static Agent getAgentFromLogin(String login) {
        FileUtils file = new FileUtils("files/agents/" + login + ".txt");
        return createAgentFromString(file);
    }

    private String nom;
    private String prenom;
    private String login;
    private String mission;
    private String mdp;
    private String imageUrl;
    private ArrayList<String> materiels;

    public Agent(String nom, String prenom, String mission, String mdp, ArrayList<String> materiels) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = this.prenom.toLowerCase().charAt(0) + this.nom.toLowerCase();
        this.mission = mission;
        this.mdp = mdp;
        this.materiels = materiels;
        this.imageUrl = "./" + this.login + ".jpg";
    }

    public void createAgentDetails() {
        FileUtils detailFile = new FileUtils("/template/detail.txt");
        String content = detailFile.toString().replaceAll("%NOM%", nom)
                        .replaceAll("%PRENOM%", prenom)
                        .replaceAll("%MISSION%", mission)
                        .replaceAll("%LOGIN%", login)
                        .replaceAll("%OBJETS%", getObjetsHTML())
                        .replaceAll("%IMAGE%", "<img class=\"detail-image\" src=\"" + this.imageUrl + "\" />");
        try {
            Files.createDirectories(Paths.get("./html/agents/" + login));
            Files.copy(Paths.get("files/agents/" + login + ".jpg"),
                    Paths.get("./html/agents/" + login + "/" + login + ".jpg"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUtils.createFile("./html/agents/" + login + "/details.html", content);
        FileUtils fileHtaccess = new FileUtils("/template/htaccess.txt");
        FileUtils.createFile("./html/agents/" + login + "/.htaccess",
                fileHtaccess.toString().replaceAll("%LOGIN%", login));
    }

    private String getObjetsHTML() {
        StringBuilder res = new StringBuilder();
        boolean checked;
        for (String obj : Objets.getObjets().keySet()) {
            checked = materiels.contains(obj);
            res.append(Objets.getHtmlForObject(obj, checked));
        }
        return String.valueOf(res);
    }

    public String getMdp() {
        return mdp;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mission='" + mission + '\'' +
                ", mdp='" + mdp + '\'' +
                ", materiels=" + materiels +
                '}';
    }
}
