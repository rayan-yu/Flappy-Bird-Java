package org.cis120.FlappyBird;

import java.awt.*;

public class Pipe {

    // instantiate the variables used in this class
    private Color myColor;
    private int myX;
    private int myXWidth;
    private int myY;
    private int myYWidth;

    // constructors for the pipe/bumper
    public Pipe() {
        myX = 0;
        myXWidth = 100;
        myY = 0;
        myYWidth = 100;
        myColor = Color.GREEN;
    }

    public Pipe(int x, int y, int xWidth, int yWidth) {
        myX = x;
        myXWidth = xWidth;
        myY = y;
        myYWidth = yWidth;
    }

    public Pipe(int x, int y, int xWidth, int yWidth, Color c) {
        myX = x;
        myXWidth = xWidth;
        myY = y;
        myYWidth = yWidth;
        myColor = c;
    }

    // accessor methods for the pipe
    public int getX() {
        return myX;
    }

    public int getXWidth() {
        return myXWidth;
    }

    public int getY() {
        return myY;
    }

    public int getYWidth() {
        return myYWidth;
    }

    public Color getColor() {
        return myColor;
    }

    // modifier methods for bumper

    public void setX(int x) {
        myX = x;
    }

    public void setY(int y) {
        myY = y;
    }

    public void setXWidth(int xWidth) {
        myXWidth = xWidth;
    }

    public void setYWidth(int yWidth) {
        myYWidth = yWidth;
    }

    public void setColor(Color c) {
        myColor = c;
    }

    // instance methods

    // draws the bumper
    public void draw(Graphics myBuffer) {
        myBuffer.setColor(getColor());
        myBuffer.fillRect((int) getX(), getY(), getXWidth(), getYWidth());
    }

    // measures a distance
    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    // calculates if any part of the bird is inside the pipe
    public boolean inPipe(Bird bd) {
        for (int x = getX(); x <= getX() + getXWidth(); x++) {
            for (int y = getY(); y <= getY() + getYWidth(); y++) {
                if (distance(x, y, bd.getX(), bd.getY()) <= bd.getRadius()) {
                    return true;
                }
            }
        }
        return false;
    }

}
