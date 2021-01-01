# Image Particle Effects

## Steps to run
1. Download [Processing](https://processing.org/download/).
2. Install [Controlp5](http://www.sojamo.de/libraries/controlP5).
    * Easiest way I know is to go to Processing -> Sketch -> Import Library -> Add Library.
    * Search and Install Controlp5.
3. Download and open this project in Processing.
4. Hit Play.

## What does it do?
* The program takes an image as an input and generates particles using the color data from that image.
* It then applies some physics behaviour on those particles and the results are visually pleasing.
* There is a primary behaviour which forces particles to move to their physical location in the image. Right now I have two of these:
    * **Steering Behaviour**
        * Particles smoothly steer their way to the right location to form the image.
    * **Spring Behaviour**
        * Particles behave as if they are attached to their original location from the image through a spring.
* There is a secondary behaviour which activates when the user clicks somewhere on the window. I have two of these as well.
    * **Gravity behaviour**
        * Particles simply fall freely to the bottom of the screen.
    * **Rendezvous Behaviour**
        * Particles gather at the mouse location.

## Controls
Once you launch the program there are some controls on the left.
* Select Image Button
    * The program launches with a default image but you can change it using this button.
* Resolution Slider
    * Use it to tweak the resolution but remember there are trade-offs.
        * Higher the resolution more defined would be the image.
        * Higher the resolution more computationally costly it is so frame rate may drop.
* Radio Buttons
    * Use them to change primary and secondary behaviours on particles.


## [Video Demo](https://youtu.be/b4RmhPh51Ks)