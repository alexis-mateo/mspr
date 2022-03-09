import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        FileUtils staff = new FileUtils("files/staff.txt");

        FileUtils.generateIndex(staff.getLines());
        Objets.initListObjets();

        FileUtils fileAgent;
        for(String s : staff.getLines()) {
            fileAgent = new FileUtils("files/agents/" + s + ".txt");
            Agent agent = Agent.createAgentFromString(fileAgent);
            agent.createAgentDetails();
        }
    }
}
