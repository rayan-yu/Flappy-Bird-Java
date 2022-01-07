package org.cis120.FlappyBird;

import java.awt.*;

// subclass of a pipe to allow it to move

public class MovingPipe extends Pipe {
    private int xMove;
    private int yMove;

    // constructors
    public MovingPipe() {
        super(0, 0, 100, 100, Color.GREEN);
        xMove = 2;
        yMove = 2;
    }

    public MovingPipe(int x, int y, int xWidth, int yWidth) {
        setX(x);
        setY(y);
        setXWidth(xWidth);
        setYWidth(yWidth);
        xMove = 2;
        yMove = 2;
    }

    // modifier methods

    public void setxMove(int x) {
        xMove = x;
    }

    public void setyMove(int y) {
        yMove = y;
    }

    public void setMovingPipe(int x, int y, int xWidth, int yWidth) {
        setX(x);
        setY(y);
        setXWidth(xWidth);
        setYWidth(yWidth);
        xMove = 2;
        yMove = 2;
    }

    // accessor methods
    public int getxMove() {
        return xMove;
    }

    public int getyMove() {
        return yMove;
    }

    // instance methods
    // the method that allows the bumper to move horizontally
    public void move() {
        setX(getX() - xMove);
    }
}
