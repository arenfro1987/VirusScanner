/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusscanner;

/**
 *
 * @author Derek
 */
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scanner implements FileVisitor<Path> {

    private static final String TAB = "    ";
    private static final int TAB_SIZE = TAB.length();

    private StringBuffer prefix;
    private int fileCount;
    private int directoryCount;

    public Scanner() {
        prefix = new StringBuffer();
        fileCount = 0;
        directoryCount = 0;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        //System.out.printf("%s%s:%n", prefix, dir.getFileName());
        prefix.append(TAB);
        directoryCount++;
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        //prefix.setLength(prefix.length() - TAB_SIZE);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        //System.out.printf("%s%s%n", prefix, file.getFileName());
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Scanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (InputStream is = Files.newInputStream(Paths.get("file.txt"));
                DigestInputStream dis = new DigestInputStream(is, md)) {
            /* Read decorated stream (dis) to EOF as normal... */
        }
        byte[] digest = md.digest();
        fileCount++;
        compareToDatabase(digest);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        //System.out.printf("%s%s (failed)%n", prefix, file.getFileName());
        return FileVisitResult.CONTINUE;
    }

    private void compareToDatabase(byte[] digest) {
       
    }

}
