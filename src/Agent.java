import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Agent {
    public static Agent createAgentFromString(FileUtils file) {
        return new Agent(file.getLine(0), file.getLine(1), file.getLine(2), file.getLine(3), file.getMateriel());
    }

    private String nom;
    private String prenom;
    private String login;
    private String mission;
    private String mdp;
    private ArrayList<String> materiels = new ArrayList<>();

    public Agent(String nom, String prenom, String mission, String mdp, ArrayList<String> materiels) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = this.nom.toLowerCase().charAt(0) + this.prenom.toLowerCase();
        this.mission = mission;
        this.mdp = mdp;
        this.materiels = materiels;
    }

    public void createAgentDetails() {
        FileUtils detailFile = new FileUtils("template/detail.txt");
        String content = detailFile.toString().replaceAll("%NOM%", nom)
                        .replaceAll("%PRENOM%", prenom)
                        .replaceAll("%MISSION%", mission)
                        .replaceAll("%LOGIN%", login)
                        .replaceAll("%OBJETS%", getObjetsHTML());
        try {
            Files.createDirectories(Paths.get("html/agents/" + login));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUtils.createFile("html/agents/" + login + "/details.html", content);
    }

    private String getObjetsHTML() {
        StringBuilder res = new StringBuilder();
        for (String obj : Objets.getObjets().keySet()) {
            boolean checked = materiels.contains(obj);
            res.append(Objets.getHtmlForObject(obj, checked));
        }
        return String.valueOf(res);
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
