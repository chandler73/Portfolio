/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import java.util.*;
import Operations.DVD;

/**
 *
 * @author apprentice
 */
public class DVDLibrary {

    ArrayList<DVD> dvdLibrary = new ArrayList<>();

    public void addDVD(DVD a) {
        dvdLibrary.add(a);
    }

    public void removeDVD(DVD r) {
        dvdLibrary.remove(r);

    }

    public DVD findDVD(String title) {
        DVD reqDVD = null;

        for (int i = 0; i < dvdLibrary.size(); i++) {
            DVD temp = dvdLibrary.get(i);

            if (temp.getTitle().equals(title)) {
                reqDVD = temp;
            }
        }
        return reqDVD;

    }
    
    public ArrayList allDVD (){
        ArrayList tempArrL = (ArrayList) dvdLibrary.clone();
        return tempArrL;
    }
    public void addLibraryViaReader(ArrayList<DVD> d){
        dvdLibrary.addAll(d);
    }
    public int howMany (String title){
        int num = 0;
        for (int i = 0; i < dvdLibrary.size(); i++) {
            DVD temp = dvdLibrary.get(i);
            
            if (temp.getTitle().equals(title)){
                num+=1;
              
            }
          
        }
        return num;
       
    }
}
