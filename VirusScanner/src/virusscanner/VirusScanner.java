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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class VirusScanner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Path path = Paths.get("C:");
        Scanner scanner = new Scanner();
        Files.walkFileTree(path, scanner);
        
    }
    
}
