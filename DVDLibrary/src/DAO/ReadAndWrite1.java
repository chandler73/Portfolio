/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.*;
import java.io.*;
import Operations.DVD;
/**
 *
 * @author apprentice
 */
public class ReadAndWrite1 {

    Operations.DVDLibrary AB = new Operations.DVDLibrary();

    public void writeFile(String filename, ArrayList<DVD> adds) {
        try {
            PrintWriter output = new PrintWriter(new FileWriter(filename));
            String outString;
            DVD element;
            Iterator<DVD> iter = adds.iterator();
            while (iter.hasNext()) {
                element = iter.next();
                outString = element.getTitle()+ "::"
                        + element.getReleaseDate()+ "::"
                        + element.getMpaa()+ "::"
                        + element.getDirectorsName()+ "::"
                        + element.getStudio()+ "::"
                        + element.getUserRating();
                output.println(outString);
                outString = "";
            }
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.println("File Write failed: " + e.getMessage());
        }
    }

    public ArrayList<DVD> readFile(String filename) {
        ArrayList<DVD> adds = new ArrayList<>();
        try {
            Scanner file = new Scanner(new BufferedReader(new FileReader(filename)));
            String line;
            String element;
            String[] splitLine;
            while (file.hasNextLine()) {
                line = file.nextLine();
                splitLine = line.split("::");
                DVD temp = new DVD(splitLine[0],
                        splitLine[1],
                        splitLine[2],
                        splitLine[3],
                        splitLine[4],
                        splitLine[5]);
                adds.add(temp);
            }
            file.close();
        } catch (FileNotFoundException e) {
            //either makes a populated or empty address book
        }
        return adds;
    }
}
