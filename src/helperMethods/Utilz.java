package helperMethods;

import java.util.ArrayList;

// Clasa Utilz oferă metode utilitare pentru manipularea array-urilor, cum ar fi conversia între array-urile 1D și 2D.
public class Utilz {

    // Convertește un ArrayList de întregi într-un array 2D cu dimensiunile specificate
    public static int[][] ArrayListTo2D(ArrayList<Integer> list, int xSize, int ySize) {

        int[][] arr = new int[ySize][xSize];

        for(int j = 0; j < arr.length; j++) {
            for(int i = 0; i < arr[j].length; i++) {
                int index = j * ySize + i;
                arr[j][i] = list.get(index);
            }
        }
        return arr;
    }

    // Convertește un array 2D de întregi într-un array 1D
    public static int[] twoDto1DArr(int[][] arr) {

        int[] arr_trans = new int[arr.length * arr[0].length];

        for(int j = 0; j < arr.length; j++) {
            for(int i = 0; i < arr[j].length; i++) {
                int index = j * arr.length + i;
                arr_trans[index] = arr[j][i];
            }
        }

        return arr_trans;
    }

    // Metoda pentru calcului distantei intre 2 entitati
    public static int GetDistance(float x1, float y1, float x2, float y2) {
        float xDiff = Math.abs(x1 - x2);
        float yDiff = Math.abs(y1 - y2);
        return (int) Math.hypot(xDiff, yDiff);
    }
}
