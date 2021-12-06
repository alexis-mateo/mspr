import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileUtils staff = new FileUtils("files/staff.txt");
        System.out.println(staff.getLines());

        FileUtils.generateIndex(staff.getLines());

        for(String s : staff.getLines()) {
            FileUtils fileAgent = new FileUtils("files/agents/" + s + ".txt");
            Agent agent = Agent.createAgentFromString(fileAgent);
            System.out.println(agent);
            agent.createAgentDetails();
        }
    }
}
