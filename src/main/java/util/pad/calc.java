package util.pad;




import util.Console;

import java.util.ArrayList;
import java.util.List;

public class calc {

    private static Integer x = 10;
    private static Integer y = 26;


    public static void main(String[] args) {
        BallMap ballMap = new BallMap();
        List<List<Ball>> ballmap = ballMap.getBallmap();
        System.out.println("*********************");
        List<List<Ball>> calcBallMap = calc(ballmap);

        for (int i = 0; i < x; i++) {
            List<Ball> rowball = calcBallMap.get(i);
            for (int j = 0; j < y; j++) {
                if (!rowball.get(j).isExist()) {
                    Console.print("N  ", Console.RED);

                } else {
                    Console.print("Y  ", Console.BLUE);
                }

            }
            System.out.println();
        }

    }


    public static List<List<Ball>> calc(List<List<Ball>> ballmap) {
        List<List<Ball>> newBallMap = new ArrayList<>();
        for (int i = 0; i < x-1; i++) {
            List<Ball> newRowBall = new ArrayList<>();
            for (int j = 0; j < y-1; j++) {
                Ball b = ballmap.get(i).get(j);
                if (ballmap.get(i).get(j).getX() == 0) {
                    b.setLeft(null);
                } else {
                    ballmap.get(i).get(j).setLeft(ballmap.get(i).get(j - 1).getType());
                }
                if (ballmap.get(i).get(j).getX() == x) {
                    ballmap.get(i).get(j).setRight(null);
                } else {
                    ballmap.get(i).get(j).setRight(ballmap.get(i).get(j + 1).getType());
                }


                if (ballmap.get(i).get(j).getY() == 0) {
                    ballmap.get(i).get(j).setUp(null);
                } else {
                    ballmap.get(i).get(j).setUp(ballmap.get(i - 1).get(j).getType());
                }
                if (ballmap.get(i).get(j).getY() == x - 1) {
                    ballmap.get(i).get(j).setDown(null);
                } else {
                    ballmap.get(i).get(j).setDown(ballmap.get(i + 1).get(j).getType());
                }


                if ((b.getType().equals(b.getLeft())) && (b.getType().equals(b.getRight()))) {
                    ballmap.get(i).get(j - 1).setExist(false);
                    ballmap.get(i).get(j).setExist(false);
                    ballmap.get(i).get(j + 1).setExist(false);
                }
                if ((b.getType().equals(b.getUp())) && (b.getType().equals(b.getDown()))) {
                    ballmap.get(i - 1).get(j).setExist(false);
                    ballmap.get(i).get(j).setExist(false);
                    ballmap.get(i + 1).get(j).setExist(false);
                }

                //  newRowBall.add(b);
            }
            // newBallMap.add(newRowBall);
        }
        return ballmap;
    }


}
