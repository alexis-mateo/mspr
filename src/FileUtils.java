import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {

    public static void generateIndex(ArrayList<String> agents) {
        StringBuilder res = new StringBuilder();
        StringBuilder htpasswdContent = new StringBuilder();
        for(String agent : agents) {
            res.append("<a href=\"./agents/").
                    append(agent).append("/details.html\" class=\"card\">\n<span><i class=\"far fa-user\"></i>").
                    append(agent).append("</span><span>Se connecter <i class=\"fas fa-sign-in-alt\"></i></span></a>");
            htpasswdContent.append(agent).append(":").append(Agent.getAgentFromLogin(agent).getMdp()).append('\n');
        }
        FileUtils.createFile("html/.htpasswd", String.valueOf(htpasswdContent));
        FileUtils indexFile = new FileUtils("template/index.txt");
        FileUtils.createFile("html/index.html", indexFile.toString().replaceAll("%AGENTS%", String.valueOf(res)));
//        try {
//            Files.copy(Paths.get("files/ressources"), Paths.get("html/ressources"), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void createFile(String destination, String content) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(destination));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileName;
    private ArrayList<String> lines = new ArrayList<>();

    public FileUtils(String fileName) {
        this.fileName = fileName;
        this.readFile();
    }

    public void readFile() {
        File file = new File(this.fileName);
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public String getLine(int index) {
        return lines.get(index);
    }
    
    public ArrayList<String> getMateriel() {
        ArrayList<String> materiel = new ArrayList<>();
        for (int i = 5; i < lines.size(); i++) {
            materiel.add(lines.get(i));
        }
        return materiel;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for(String s : lines){
            str.append(s).append("\n");
        }
        return str.toString();
    }
}
