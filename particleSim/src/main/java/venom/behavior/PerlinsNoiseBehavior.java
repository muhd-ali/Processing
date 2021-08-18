package venom.behavior;

import org.apache.commons.lang3.tuple.Pair;
import processing.core.PApplet;
import processing.core.PVector;
import venom.ParticleSim;
import venom.contract.Forced;

public class PerlinsNoiseBehavior extends Behavior<Forced> {
    float[][] noise;
    float xOff, yOff, zOff = 0;
    float intensity = 0.0025f;
    int width, height, step = 50;

    public PerlinsNoiseBehavior(int screenWidth, int screenHeight) {
        width = screenWidth;
        height = screenHeight;
        noise = new float[width / step][height / step];
        ParticleSim.singleton.noiseDetail(1, 0.1f);
    }

    private Pair<Integer, Integer> calculateIndexFromPosition(PVector centerOfMassPosition) {
        int i, j;
        if (centerOfMassPosition.x < 0) {
            i = 0;
        } else if (centerOfMassPosition.x >= width) {
            i = width / step - 1;
        } else {
            i = (int) PApplet.map(centerOfMassPosition.x, 0, width, 0, width / step);
        }

        if (centerOfMassPosition.y < 0) {
            j = 0;
        } else if (centerOfMassPosition.y >= height) {
            j = height / step - 1;
        } else {
            j = (int) PApplet.map(centerOfMassPosition.y, 0, height, 0, height / step);
        }
        return Pair.of(i, j);
    }

    public void applyTo(Forced system) {
        Pair<Integer, Integer> index = calculateIndexFromPosition(system.getCenterOfMassPosition());
        float selectedNoise = noise[index.getLeft()][index.getRight()];
        float angle = PApplet.map(selectedNoise, 0, 1, 0, 2 * ParticleSim.singleton.PI);
        PVector force = PVector.fromAngle(angle).setMag(5);
        system.addForce(force);
        xOff = PApplet.map(index.getLeft(), 0, height / step, 0, height * intensity);
        yOff = PApplet.map(index.getRight(), 0, width / step, 0, width * intensity);
        noise[index.getLeft()][index.getRight()] = ParticleSim.singleton.noise(xOff, yOff, zOff);

        if (ParticleSim.singleton.frameCount % 10 == 0) {
            zOff += 0.0001;
        }
    }

}
