package org.cis120.FlappyBird;

import java.awt.*;

public class Projectile implements Circle {
    private Color myColor;
    private double myX;
    private double myY;

    public Projectile(double x, double y, Color c) {
        myX = x;
        myY = y;
        myColor = c;
    }

    public void move(double minSpeed) {
        if ((int) (Math.random() * 2) == 1) {
            if ((int) (Math.random() * 2) == 1) {
                setY(getY() - (Math.random() * (minSpeed * 10) + minSpeed));
            } else {
                setY(getY() + (Math.random() * (minSpeed * 10) + minSpeed));
            }
        } else {
            setX(getX() - (Math.random() * (minSpeed * 2) + minSpeed));
        }
    }

    public void draw(Graphics myBuffer) {
        myBuffer.setColor(myColor);
        myBuffer.fillOval(
                (int) (getX() - getRadius()), (int) (getY() - getRadius()), (int) getDiameter(),
                (int) getDiameter()
        );
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    // calculates if any part of the bird is inside the projectile
    public boolean inPrj(Bird bd) {
        if (distance(
                bd.getX() + bd.getRadius(), bd.getY() + bd.getRadius(),
                getX() + getRadius(), getY() + getRadius()
        ) < bd.getRadius() + getRadius()) {
            return true;
        }
        return false;
    }

    public void setX(double x) {
        myX = x;
    }

    public void setY(double y) {
        myY = y;
    }

    public void setColor(Color c) {
        myColor = c;
    }

    public Color getColor() {
        return myColor;
    }

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
}
