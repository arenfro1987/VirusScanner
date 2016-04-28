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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class ScannerFile implements FileVisitor<Path> {

    private static final String TAB = "    ";
    private static final int TAB_SIZE = TAB.length();

    private StringBuffer prefix;
    private int fileCount;
    private int directoryCount;
    

    public ScannerFile() {
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
        StringBuffer hexString = new StringBuffer();
        try {
            //md = MessageDigest.getInstance("MD5");
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Scanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        String path = file.getParent().toString() + file.getFileName().toString();
        path = path.substring(0, 2) + "\\" + path.substring(2, path.length());


        
        
        if (new File(path).exists() && new File(path).canRead() && !(path).contains(".sys")) {
            FileInputStream fis = new FileInputStream(path);  
            byte[] dataBytes = new byte[1024];

            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            };

            byte[] mdbytes = md.digest();

            
            for (int i = 0; i < mdbytes.length; i++) {
                hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
            }
            
        }
        fileCount++;
        compareToDatabase(hexString.toString(),path);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        //System.out.printf("%s%s (failed)%n", prefix, file.getFileName());
        return FileVisitResult.CONTINUE;
    }

    private void compareToDatabase(String digest,String theFile) {
       
    }

    

}
