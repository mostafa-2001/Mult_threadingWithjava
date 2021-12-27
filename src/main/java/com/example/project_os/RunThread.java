package com.example.project_os;

import java.util.concurrent.locks.ReentrantLock;
class RunThread implements Runnable{

    public int [][] maze;
    public int threadId;
    private final ReentrantLock lock = new ReentrantLock();

    int[][] finalPath1;

    public RunThread(int [][] maze){
        this.maze = maze;
        finalPath1 =new int [maze.length][maze.length];

    }


    public boolean printMazePath(int threadId)   {


        if(!printMazePathUtil(maze, 0, 0, finalPath1  , threadId)) {
            System.out.println("Rat Can't reach to End");
            return false;
        }


        printPath(finalPath1);
        return true;
    }

    private boolean printMazePathUtil(int[][] maze, int x, int y, int[][] sol ,int threadId)  {


        if((x == maze.length - 1 && y == maze.length - 1 && maze[x][y] == 1)) {

            if(threadId == 0)
            {
                sol[x][y] = 1;
            }else {
                sol[x][y] = 5;
            }
            return true;
        }

        if(isSafe(maze, x, y)) {

            if(threadId == 0)
            {
                sol[x][y] = 1;
            }else {
                sol[x][y] = 5;
            }

            if(printMazePathUtil(maze, x + 1, y , sol  , 0) && printMazePathUtil(maze, x , y + 1, sol  , 1) && threadId==1)
            {
                return true;
            }

            if(printMazePathUtil(maze, x, y + 1, sol , threadId) ) {

                return true;
            }
            if(printMazePathUtil(maze, x + 1, y, sol , threadId) ) {

                return true;
            }
            sol[x][y] = 0;


        }


        return false;
    }

    private boolean isSafe(int[][] maze, int x, int y) {
        if((x >= 0 && x < maze.length && y >= 0 && y < maze.length && maze[x][y] == 1)) {
            return true;
        }

        return false;
    }

    void printPath(int[][] mat) {
        lock.lock();
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze.length; j++) {
                System.out.print(mat[i][j] + " ");
            }

            System.out.println();
        }
        System.out.println();
        lock.unlock();
    }

    public int[][] path(int threadId)
    {
        int[][] finalPath = new int[maze.length][maze.length];
        if(!printMazePathUtil(maze, 0, 0, finalPath, threadId)) {
            System.out.println("Rat Can't reach to End");
            return  finalPath;
        }

        return finalPath;
    }

    public int getId(){
        return threadId;
    }

    @Override
    public void run() {
        threadId = Integer.parseInt(Thread.currentThread().getName());
        printMazePath(threadId);

    }
}