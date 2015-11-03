/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.*;
import Operations.DVD;
import Operations.DVDLibrary;
import DAO.ReadAndWrite1;

/**
 *
 * @author apprentice
 */
public class DVDLibraryController {

    UI.ConsoleIO11 IO = new UI.ConsoleIO11();

    DVDLibrary DL = new DVDLibrary();
    ReadAndWrite1 RW = new ReadAndWrite1();

    public void mainMenuMethod() {
        ArrayList reader = RW.readFile("dvdLibrary.txt");
        DL.addLibraryViaReader(reader);
        int answer = 0;

        do {

            answer = IO.readInteger("Please Select the Corrsponding Number of Your Choice: \n"
                    + "1. Add DVD\n"
                    + "2. Remove DVD\n"
                    + "3. Edit DVD\n"
                    + "4. List DVDs\n"
                    + "5. Find DVD\n"
                    + "6. How many of a given title are in the collection\n"
                    + "7. Exit", 1, 7);
            System.out.println();

            switch (answer) {

                case 1:
                    addDvd();

                    break;

                case 2:
                    removeDvd();

                    break;

                case 3:
                    editDvd();

                    break;

                case 4:
                    listDvd();

                    break;

                case 5:
                    findDvd();

                    break;

                case 6:
                    howMany();

                    break;

                default:
            }

        } while (answer != 7);
        if (answer == 7) {
            RW.writeFile("dvdLibrary.txt", DL.allDVD());
        }
    }

    public void addDvd() {
        String title, releaseDate, mpaa, directorsName, studio, userRating;

        title = IO.readString("Please Enter the Title to Add: ");
        releaseDate = IO.readString("Please Enter the Release Date: ");
        mpaa = IO.readString("Please Enter the MPAA: ");
        directorsName = IO.readString("Please Enter the Directors Name: ");
        studio = IO.readString("Please Enter the Studio: ");
        userRating = IO.readString("Please Enter the User Rating: ");
        System.out.println();
        DVD temp = new DVD(title, releaseDate, mpaa, directorsName, studio, userRating);
        DL.addDVD(temp);

    }

    public void removeDvd() {
        String title;
        int choice = 0;

        title = IO.readString("Please Enter Title of DVD to Remove: ");
        DVD remove;
        remove = DL.findDVD(title);

        if (remove == null) {
            IO.write("There is no DVD with that Title.");
            System.out.println();
        } else {
            IO.write(remove.getTitle() + " " + remove.getReleaseDate());
            System.out.println();

            choice = IO.readInteger("Are you sure you want to Delete??\n"
                    + "Press 0 to Delete");

            if (choice == 0) {
                DL.removeDVD(remove);
            }
        }

    }

    public void editDvd() {
        String dvdTitle, title, releaseDate, mpaa, directorsName, studio, userRating;
        int choice = 0;

        dvdTitle = IO.readString("Please Enter Title of DVD to Edit: ");
        DVD edit;
        edit = DL.findDVD(dvdTitle);

        do {

            choice = IO.readInteger("Please Select which Field:\n"
                    + "1. Title\n"
                    + "2. Release Date\n"
                    + "3. MPAA\n"
                    + "4. Director's Name\n"
                    + "5. Studio\n"
                    + "6. User Rating\n"
                    + "7. Exit", 1, 7);
            System.out.println();

            switch (choice) {
                case 1:
                    title = IO.readString("Please Enter the Title to Add: ");
                    edit.setTitle(title);
                    System.out.println();
                    break;

                case 2:
                    releaseDate = IO.readString("Please Enter the Release Date: ");
                    edit.setReleaseDate(releaseDate);
                    System.out.println();
                    break;

                case 3:
                    mpaa = IO.readString("Please Enter the MPAA: ");
                    edit.setMpaa(mpaa);
                    System.out.println();
                    break;

                case 4:
                    directorsName = IO.readString("Please Enter the Directors Name: ");
                    edit.setDirectorsName(directorsName);
                    System.out.println();
                    break;

                case 5:
                    studio = IO.readString("Please Enter the Studio: ");
                    edit.setStudio(studio);
                    System.out.println();
                    break;

                case 6:
                    userRating = IO.readString("Please Enter the User Rating: ");
                    edit.setUserRating(userRating);
                    System.out.println();
                    break;
                default:
            }
        } while (choice != 7);

    }

    public void listDvd() {
        ArrayList<DVD> all = new ArrayList<>();
        all = DL.allDVD();

        for (int i = 0; i < all.size(); i++) {
            DVD temp = all.get(i);

            IO.write(temp.getTitle() + ", " + temp.getReleaseDate());
            System.out.println();
        }
        System.out.println();
    }

    public void findDvd() {
        String find;
        find = IO.readString("Which Movie Would you like to Find:");
        System.out.println();
        DVD movie = DL.findDVD(find);

        IO.write(movie.getTitle() + ", " + movie.getReleaseDate() + "\n"
                + "MPAA: " + movie.getMpaa() + "\n"
                + "Director: " + movie.getDirectorsName() + "\n"
                + "Studio: " + movie.getStudio() + "\n"
                + "User Rating: " + movie.getUserRating());
        System.out.println();
        System.out.println();

    }

    public void howMany() {
        String find;
        int num;

        find = IO.readString("Which Title do you want to Search: ");
        num = DL.howMany(find);

        IO.write("There are " + num + " movies of " + find);
        System.out.println();

    }

    public static void main(String[] args) {
        DVDLibraryController DLC = new DVDLibraryController();
        DLC.mainMenuMethod();

    }

}
