package venom.drawer;

import venom.PLine;
import venom.ParticleSim;
import venom.contract.ColorProvider;
import venom.contract.Drawer;

public class LineDrawer implements Drawer<PLine> {
    public void draw(PLine line, ColorProvider<PLine> colorProvider) {
        colorProvider.setStroke();
        ParticleSim.singleton.line(line.p1.x, line.p1.y, line.p2.x, line.p2.y);
    }
}
