import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileUtils {
    /*
    static final byte[] CHUNK_SEPARATOR;
    FileUtils.CHUNK_SEPARATOR = new byte[]{13, 10};
    public static String encodeBase64String(final byte[] binaryData) {
        return StringUtils.newStringUsAscii(encodeBase64(binaryData, false));
    }
    public static byte[] encodeBase64(final byte[] binaryData, final boolean isChunked) {
        return encodeBase64(binaryData, isChunked, false);
    }
    public static byte[] encodeBase64(final byte[] binaryData, final boolean isChunked, final boolean urlSafe) {
        return encodeBase64(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
    }
    public static byte[] encodeBase64(final byte[] binaryData, final boolean isChunked,
                                      final boolean urlSafe, final int maxResultSize) {
        if (binaryData == null || binaryData.length == 0) {
            return binaryData;
        }

        // Create this so can use the super-class method
        // Also ensures that the same roundings are performed by the ctor and the code
        final Base64 b64 = isChunked ? new Base64(urlSafe) : new Base64(0, CHUNK_SEPARATOR, urlSafe);
        final long len = b64.getEncodedLength(binaryData);
        if (len > maxResultSize) {
            throw new IllegalArgumentException("Input array too big, the output array would be bigger (" +
                    len +
                    ") than the specified maximum size of " +
                    maxResultSize);
        }

        return b64.encode(binaryData);
    }

    public static String newStringUsAscii(final byte[] bytes) {
        return newString(bytes, StandardCharsets.US_ASCII);
    }



    public static MessageDigest getDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
    public static MessageDigest getSha1Digest() {
        return getDigest("SHA-1");
    }

    public static byte[] sha1(final String data) {
        return sha1(FileUtils.getBytesUtf8(data));
    }
    public static byte[] sha1(final byte[] data) {
        return getSha1Digest().digest(data);
    }


    public static byte[] getBytesUtf8(final String string) {
        return getBytes(string, StandardCharsets.UTF_8);
    }

    private static byte[] getBytes(final String string, final Charset charset) {
        if (string == null) {
            return null;
        }
        return string.getBytes(charset);
    }*/

    public static void generateIndex(ArrayList<String> agents) {
        StringBuilder res = new StringBuilder();
        StringBuilder htpasswdContent = new StringBuilder();

        for(String agent : agents) {
            res.append("<a href=\"./agents/").
                    append(agent).append("/details.html\" class=\"card\">\n<span><i class=\"far fa-user\"></i>").
                    append(agent).append("</span><span>Se connecter <i class=\"fas fa-sign-in-alt\"></i></span></a>");
            /*String passwd64 = "{SHA}" + Base64.encodeBase64String(FileUtils.sha1(Agent.getAgentFromLogin(agent).getMdp()));*/
            htpasswdContent.append(agent).append(":").append("{SHA}C5wmJdwh7wX2rU3fR8XyA4N6oyw=").append('\n');
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

