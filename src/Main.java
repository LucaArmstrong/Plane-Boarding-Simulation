import java.util.Arrays;
import java.util.Random;

public class Main {
    public int planeLength = 20;
    public int seatNum = planeLength * 6;

    public static void main(String[] args) {

    }

    public int[] makeRandomSeatIndices() {
        int[] indices = new int[seatNum];
        Arrays.fill(indices, -1);
        Random random = new Random();
        int remainingIndices = seatNum;

        for (int i = 0; i < seatNum; i++, remainingIndices--) {
            int rand = random.nextInt(1, remainingIndices);

            for (int j = 0; rand > 0; j++) {
                if (indices[i] == -1) rand--;
                if (rand == 0) indices[j] = i;
            }
        }

        return indices;
    }
}