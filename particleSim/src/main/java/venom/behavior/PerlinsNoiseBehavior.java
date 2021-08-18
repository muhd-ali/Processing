package venom.behavior;

import org.apache.commons.lang3.tuple.Pair;
import processing.core.PApplet;
import processing.core.PVector;
import venom.ParticleSim;
import venom.contract.Forced;

public class PerlinsNoiseBehavior extends Behavior<Forced> {
    float noise[][], xOff, yOff, inc = 0.05f;
    int width, height, step = 50;

    public PerlinsNoiseBehavior(int screenWidth, int screenHeight) {
        width = screenWidth;
        height = screenHeight;
        noise = new float[screenWidth/step][screenHeight/step];
    }

    private void updateNoise() {
        yOff = 0;
        for (int i = 0; i < noise.length; i++) {
            xOff = 0;
            for (int j = 0; j < noise[i].length; j++) {
                noise[i][j] = ParticleSim.singleton.noise(xOff, yOff);
                xOff += inc;
            }
            yOff += inc;
        }
    }

    private Pair<Integer, Integer> calculateIndexFromPosition(PVector centerOfMassPosition) {
        int i, j;
        if (centerOfMassPosition.x < 0) {
            i = 0;
        } else if (centerOfMassPosition.x >= width) {
            i = width/step - 1;
        } else {
                i = (int) PApplet.map(centerOfMassPosition.x, 0, width, 0, width / step);
        }

        if (centerOfMassPosition.y < 0) {
            j = 0;
        } else if (centerOfMassPosition.y >= height) {
            j = height/step - 1;
        } else {
            j = (int) PApplet.map(centerOfMassPosition.y, 0, height, 0, height / step);
        }
        return Pair.of(i, j);
    }

    public void applyTo(Forced system) {
        updateNoise();
        Pair<Integer, Integer> index = calculateIndexFromPosition(system.getCenterOfMassPosition());
        float selectedNoise = noise[index.getLeft()][index.getRight()];
        float angle = PApplet.map(selectedNoise, 0, 1, 0, 2 * ParticleSim.singleton.PI);
        PVector force = PVector.fromAngle(angle);
        system.addForce(force);
    }

}
