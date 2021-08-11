package venom.drawer;

import processing.core.PVector;
import venom.ParticleSim;
import venom.contract.ColorProvider;
import venom.contract.Drawer;

public class PointDrawer implements Drawer<PVector> {
    public void draw(PVector vector, ColorProvider<PVector> colorProvider) {
        colorProvider.setStroke();
        ParticleSim.singleton.strokeWeight(10);
        ParticleSim.singleton.point(vector.x, vector.y);
    }
}
