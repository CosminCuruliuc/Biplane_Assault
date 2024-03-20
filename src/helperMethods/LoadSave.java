package helperMethods;

import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Clasa LoadSave oferă metode pentru încărcarea, salvarea și crearea de fișiere, precum și gestionarea imaginilor sprite atlas.
public class LoadSave {

    // Metoda pentru încărcarea sprite atlas-ului pentru hărți
    public static BufferedImage getSpriteAtlas() {

        BufferedImage image = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("Mapa.png");

        try {
            assert is != null;
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // Metoda pentru încărcarea sprite atlas-ului pentru monștri
    public static BufferedImage getSpriteAtlas_2() {

        BufferedImage image = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("Monstrii.png");

        try {
            assert is != null;
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // Metoda pentru scrierea conținutului unui array de întregi într-un fișier text
    private static void WriteToFile(File file, int[] arr, PathPoint start, PathPoint end) {

        try {
            PrintWriter pw = new PrintWriter(file);
            for(Integer i : arr) {
                pw.println(i);
            }
            pw.println(start.getX());
            pw.println(start.getY());
            pw.println(end.getX());
            pw.println(end.getY());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru citirea conținutului unui fișier text într-un ArrayList de întregi
    private static ArrayList<Integer> ReadFromFile(File file) {

        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Salvează datele nivelului într-un fișier text dacă acesta există, altfel afișează un mesaj de eroare
    public static void SaveLevel(String name, int[][] arr, PathPoint start, PathPoint end) {

        File lvlFile = new File("Resources/" + name + ".txt");

        if(lvlFile.exists()) {
            WriteToFile(lvlFile, Utilz.twoDto1DArr(arr), start, end);
        } else {
            System.out.println("Fisierul: " + name + " nu exista!");
        }
    }

    // Încarcă datele nivelului dintr-un fișier text și returnează un array 2D de întregi, altfel returnează null și afișează un mesaj de eroare
    public static int[][] GetLevelData(String name) {

        File lvlFile = new File("Resources/" + name + ".txt");

        if(lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utilz.ArrayListTo2D(list, 20, 20);
        } else {
            System.out.println("Fisierul: " + name + " nu exista!");
            return null;
        }
    }

    // Creează un nou nivel, salvând datele într-un fișier text, dacă nu există deja un fișier cu același nume
    public static void CreateLevel(String name, int[] arr) {

        File newLevel = new File("Resources/" + name + ".txt");

        if(newLevel.exists()) {
            System.out.println("File: " + name + " exista deja!");
        } else {
            try {
                newLevel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            WriteToFile(newLevel, arr, new PathPoint(0,0), new PathPoint(0,0));
        }
    }

    // Metoda pentru a prelua cele doua puncte de start si sfarsit de drum
    public static ArrayList<PathPoint> GetLevelPathPoints(String name) {
        File lvlFile = new File("Resources/" + name + ".txt");

        if(lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));
            return points;
        } else {
            System.out.println("Fisierul: " + name + " nu exista!");
            return null;
        }
    }
}