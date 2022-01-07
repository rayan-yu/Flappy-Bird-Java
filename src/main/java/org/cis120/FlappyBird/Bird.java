package org.cis120.FlappyBird;

import java.awt.*;
// bird class

public class Bird implements Circle {
    // variables used
    private double myX;
    private double myY;
    private double yMove;

    // constructors

    public Bird(double x, double y) {
        myX = x;
        myY = y;
        yMove = 1.2;
    }

    // accessor methods
    public double getX() {
        return myX;
    }

    public double getY() {
        return myY;
    }

    public double getDiameter() {
        return MY_DIAMETER;
    }

    public double getRadius() {
        return MY_RADIUS;
    }

    // modifier methods
    public void setX(double x) {
        myX = x;
    }

    public void setY(double y) {
        myY = y;
    }

    public void setyMove(double y) {
        yMove = y;
    }
    // instance methods

    // allows the bird to have a down motion
    public void move(double speed) {
        yMove = speed;
        setY(getY() + yMove);
    }

    /*
     * public void moveUp(double speed) {
     * yMove = speed;
     * setY(getY()- yMove);
     * }
     */

    // allows the bird to have an up motion
    public void jump(int howFar) {
        yMove = .1;
        for (int a = 0; a < howFar * 10; a++) {
            setY(getY() - yMove);
        }
    }
}