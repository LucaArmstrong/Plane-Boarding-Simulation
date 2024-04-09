import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int planeLength = 20;
        simulation(planeLength);
    }

    public static double simulation(int planeLength) {
        int seatNum = planeLength * 6;
        int[] indices = makeRandomSeatIndices(seatNum);
        Plane plane = new Plane(planeLength, indices);
        DrawingPanel panel = new DrawingPanel(plane, 1500, 750);  // 1920, 1080

        double dt = 0.025 * 2;
        double totalTime = 0;

        // loop while not all passengers are seated
        for (int update = 0; !plane.allPassengersSeated(); update++) {
            plane.update(dt);    // update plane
            panel.repaint();    // repaint the plane
            totalTime += dt;

            // wait some duration for the next simulation update
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return totalTime;
    }

    public static int[] makeRandomSeatIndices(int seatNum) {
        int[] indices = new int[seatNum];
        Arrays.fill(indices, -1);
        Random random = new Random();

        for (int i = 0; i < seatNum; i++) {
            int rand = random.nextInt(1, seatNum - i + 1);

            for (int j = 0; rand > 0; j++) {
                if (indices[j] == -1) rand--;
                if (rand == 0) indices[j] = i;
            }
        }

        return indices;
    }
}