import java.util.ArrayList;

public class Agent {
    public static Agent createAgentFromString(FileUtils file) {
            return new Agent(file.getLine(0), file.getLine(1), file.getLine(2), file.getLine(3), file.getMateriel());
    }

    private String nom;
    private String prenom;
    private String mission;
    private String mdp;
    private ArrayList<String> materiels = new ArrayList<>();

    public Agent(String nom, String prenom, String mission, String mdp, ArrayList<String> materiels) {
        this.nom = nom;
        this.prenom = prenom;
        this.mission = mission;
        this.mdp = mdp;
        this.materiels = materiels;
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
