import java.io.*;
import java.net.URISyntaxException;
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

        try {
            Files.createDirectories(Paths.get("./html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUtils.createFile("./html/.htpasswd", String.valueOf(htpasswdContent));
        FileUtils indexFile = new FileUtils("/template/index.txt");
        FileUtils.createFile("./html/index.html", indexFile.toString().replaceAll("%AGENTS%", String.valueOf(res)));
        //ajouter ressources to output folder
        try {
            copy(new File("files/ressources"), new File("./html/ressources"));
            copy(new File("files/styles"), new File("./html/styles"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private final String fileName;
    private ArrayList<String> lines = new ArrayList<>();

    public FileUtils(String fileName) {
        this.fileName = fileName;
        this.readFile();
    }

    public void readFile() {
        try {
            BufferedReader br;
            if (fileName.startsWith("/template")) {
                br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
            } else br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
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

    public static void copyDirectory(final File from, final File to) throws IOException {
        if (! to.exists()) {
            to.mkdir();
        }
        final File[] inDir = from.listFiles();
        for (int i = 0; i < inDir.length; i++) {
            final File file = inDir[i];
            copy(file, new File(to, file.getName()));
        }
    }
    public static void copy(final InputStream inStream, final OutputStream outStream, final int bufferSize) throws IOException {
        final byte[] buffer = new byte[bufferSize];
        int nbRead;
        while ((nbRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, nbRead);
        }
    }
    public static void copyFile(final File from, final File to) throws IOException {
        final InputStream inStream = new FileInputStream(from);
        final OutputStream outStream = new FileOutputStream(to);
        copy(inStream, outStream, (int) Math.min(from.length(), 4*1024));
        inStream.close();
        outStream.close();
    }
    public static void copy(final File from, final File to) throws IOException {
        if (from.isFile()) {
            copyFile(from, to);
        } else if (from.isDirectory()){
            copyDirectory(from, to);
        } else {
            throw new FileNotFoundException(from.toString() + " does not exist" );
        }
    }
}

