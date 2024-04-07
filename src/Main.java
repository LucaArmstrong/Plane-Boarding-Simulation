import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int planeLength = 20;
        simulation(planeLength);
    }

    public static void simulation(int planeLength) {
        int seatNum = planeLength * 6;
        int[] indices = makeRandomSeatIndices(seatNum);
        Plane plane = new Plane(planeLength, indices);
        Rendering renderObj = new Rendering(plane, 1760, 990);  // 1920, 1080
        renderObj.renderPlane();
        double dt = 0.5;

        // loop while not all passengers are seated
        for (int update = 0; !plane.allPassengersSeated(); update++) {
            plane.update(plane.aislePassengers.head, dt);    // update plane
            if (update % 3 == 0) renderObj.renderPlane();  // render plane

            // wait 0.1s for next animation update
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
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