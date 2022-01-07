package org.cis120.FlappyBird;

public interface Circle {
    public double MY_DIAMETER = 25;
    public double MY_RADIUS = 25 / 2;

    public void setX(double x);

    public void setY(double y);

    public void move(double speed);

    public double getX();

    public double getY();

    public double getDiameter();

    public double getRadius();
}
