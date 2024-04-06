import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int planeLength = 20;
        int seatNum = planeLength * 6;
    }

    public void simulation(int planeLength) {
        int seatNum = planeLength * 6;
        int[] indices = makeRandomSeatIndices(seatNum);
        Plane plane = new Plane(planeLength, indices);
        Rendering Render = new Rendering(plane);
        double dt = 0.5;

        // loop while not all passengers are seated
        for (int update = 0; !plane.allPassengersSeated(); update++) {
            plane.update(plane.passengers.head, dt);    // update plane
            if (update % 3 == 0) Render.renderPlane();  // render plane

            // wait 0.1s for next animation update
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int[] makeRandomSeatIndices(int seatNum) {
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