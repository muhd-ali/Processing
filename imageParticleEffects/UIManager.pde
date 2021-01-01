import controlP5.*;

class UIManager {
  ControlP5 cp5;
  
  UIManager(PApplet applet) {
    cp5 = new ControlP5(applet);
  }
 
  void setup() {
    int width = 180;
    Group g1 = cp5.addGroup("Controls").setPosition(10, 50).setWidth(width);
    cp5.addButton("Select Image").setPosition(0,1 * 10).setWidth(width).moveTo(g1);
    cp5.addSlider("Resolution").setRange(1,10).setPosition(0,5 * 10).setWidth(width).moveTo(g1);
    cp5.addRadioButton("Change Behaviour 1").setPosition(0,10 * 10)
    .addItem("Steer", 0)
    .addItem("Spring", 1)
    .activate(0)
    .moveTo(g1);
    cp5.addRadioButton("Change Behaviour 2").setPosition(0,15 * 10)
    .addItem("Gravity", 0)
    .addItem("Rendezvous", 1)
    .activate(0)
    .moveTo(g1);
  }
}
