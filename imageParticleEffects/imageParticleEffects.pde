int mousePressCount = 0;
ImageParticlesGenerator gen;
UIManager uiManager;
PVector mousePointVector;
String imageFile = "images/img.jpg";
int simResolutionPercent = 1;

int navBehaviourId = 0;
int alternateBehaviourId = 0;
BehaviourGenerator[] navBehaviours = new BehaviourGenerator[] {
    new BehaviourGenerator() {
        public ImageParticleBehaviour get(ImageParticle particle) {
            return new SteeringBehaviour(particle);
        }
    },
    new BehaviourGenerator() {
        public ImageParticleBehaviour get(ImageParticle particle) {
            return new SpringBehaviour(particle);
        }
    },
};
BehaviourGenerator[] alternateBehaviours = new BehaviourGenerator[] {
    new BehaviourGenerator() {
        public ImageParticleBehaviour get(ImageParticle particle) {
            return new GravityBehaviour(particle);
        }
    },
    new BehaviourGenerator() {
        public ImageParticleBehaviour get(ImageParticle particle) {
            return new RendezvousBehaviour(particle);
        }
    },
};

interface BehaviourGenerator {
   ImageParticleBehaviour get(ImageParticle particle);
}

void settings() {
    size(ImageParticlesGenerator.windowWidth, ImageParticlesGenerator.windowHeight);
}

void setup() {
    surface.setLocation(0, 0);
    uiManager = new UIManager(this);
    uiManager.setup();
    background(0);
    dataSetup();
}

void dataSetup() {
    gen = new ImageParticlesGenerator(
        imageFile,
        simResolutionPercent,
        navBehaviours[navBehaviourId],
        alternateBehaviours[alternateBehaviourId]
    );
}

void mousePressed() {
    mousePressCount++;
    mousePointVector = new PVector(mouseX, mouseY);
    gen.mousePressed();
}

void draw() {
    background(0);
    gen.update();
    gen.draw();
    textSize(25);
    fill(0, 255, 0);
    text(String.valueOf((int)(frameRate)), 10, 25);
}

public void controlEvent(ControlEvent event) {
    switch(event.getName()) {
        case "Select Image":
            selectInput("Select Image", "selectFile");
            break;
        case "Resolution":
            simResolutionPercent = (int)event.getValue();
            break;
        case "Change Behaviour 1":
            navBehaviourId = (int) event.getValue();
            break;
        case "Change Behaviour 2":
            alternateBehaviourId = (int) event.getValue();
            break;
        default:
            break;
    }
    dataSetup();
}

void selectFile(File file) {
    if (file != null) {
        imageFile = file.getAbsolutePath();
        dataSetup();
    }
}
