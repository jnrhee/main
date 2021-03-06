package com.calgo.pathfinder;

import java.util.ArrayList;
import java.util.HashSet;

class PointGroup {
    private int mX; // next point to add
    private int mY;
    private int maxX;
    private int maxY;
    private ArrayList<Point> mAl = new ArrayList<Point>();
    private int idx;
    private int mDir;
    Point targetPoint;
    HashSet<Integer> hs;

    PointGroup (int x1, int y1, int mx, int my) {
        mX = x1;
        mY = y1;
        maxX = mx;
        maxY = my;
        idx=0;
    }

    void addRandomPoints() {
        int dir;
        hs = new HashSet<Integer>();

        /* set initial direction */
        addCurrentPoint();

        boolean found = false;
        do {
            dir = randomDir();
            found = move(dir);
        } while (!found);

        addCurrentPoint();

        for (int i=0;i<(maxX*maxY)/4;i++) {
            if (move(mDir))
                addCurrentPoint();

            found = false;
            int favorCurrentDirCnt = 2;
            do {
                dir = randomDir();
                if (favorCurrentDirCnt > 0 && dir != mDir) {
                    favorCurrentDirCnt--;
                } else {
                    if (!isOppositeDir(mDir, dir)) {
                        found = move(dir);
                    }
                }
            } while (!found);

            addCurrentPoint();
        }

        Point[] pts = mAl.toArray(new Point[mAl.size()]);

        double ranV = Math.random();
        int dstX = (int) (ranV*maxX);

        if (ranV < 0.5)
            ranV = Math.random()/2+0.5;

        int dstY = (int) (ranV*maxY);
        Point dstPt = new Point(dstX, dstY);
        Point endPt = null;

        for (int i=0;i<mAl.size()-1;i++) {
            Point p = pts[i];
            for (int j = i+1; j < mAl.size(); j++) {
                p.tryToLinkTo(pts[j]);
            }

            if (endPt == null)
                endPt = p;
            else if (p.distanceFrom(dstPt) < endPt.distanceFrom(dstPt))
                endPt = p;
        }

        endPt.mTarget = true;
        targetPoint = endPt;
    }

    static boolean isOppositeDir(int d1, int d2) {
        switch (d1) {
            case Point.UP: if (d2 == Point.DOWN) return true; break;
            case Point.DOWN: if (d2 == Point.UP) return true; break;
            case Point.LEFT: if (d2 == Point.RIGHT) return true; break;
            case Point.RIGHT: if (d2 == Point.LEFT) return true; break;
        }
        return false;
    }

    static int getOppositeDir(int d1) {
        switch (d1) {
            case Point.UP: return Point.DOWN;
            case Point.DOWN: return Point.UP;
            case Point.LEFT: return Point.RIGHT;
            case Point.RIGHT: return Point.LEFT;
        }

        return -1;
    }

    private boolean move(int dir) {
        int x = mX;
        int y = mY;
        switch (dir) {
            case Point.UP: y--; break;
            case Point.DOWN: y++; break;
            case Point.LEFT: x--; break;
            case Point.RIGHT: x++; break;
        }

        if (x < 0 || x > maxX || y < 0 || y > maxY)
            return false;
        else {
            mX = x;
            mY = y;
            mDir = dir;
            return true;
        }
    }

    static int randomDir() {
        return (int)(Math.random()*4)%4;
    }

    private void addCurrentPoint() {
        Point p = new Point(mX, mY);
        int val = mX<<16 | mY;
        if (hs.add(val))
         mAl.add(p);
    }

    Point[] getAllPoints() {
        return mAl.toArray(new Point[mAl.size()]);
    }

    Point getStartingPoint() {
        return mAl.get(0);
    }

    Point getEndingPoint() {
        return mAl.get(mAl.size() - 1);
    }
}