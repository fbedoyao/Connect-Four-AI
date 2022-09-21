import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class GUI {
    private JFrame frame;


    public GUI() {
        frame = new JFrame("AI Connect-Four");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new MultiDraw(frame.getSize()));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new GUI();
    }

    public static class MultiDraw extends JPanel  implements MouseListener {

        int PLAYER = 0;
        int AI = 1;

        int startX = 100;
        int startY = 150;
        int cellWidth = 50;
        int turn = 2;
        int rows = 6;
        int cols = 7;
        int padding = 5;
        boolean redWins = false;
        boolean yellowWins = false;
        boolean gameOver = false;

        Color[][] grid = new Color[rows][cols];

        Connect4 game = new Connect4("Small");
        State currState = game.initialState;


        public MultiDraw(Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            //1. initialize array here
            int x = 0;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    grid[row][col] = new Color(255, 255, 255);
                }
            }
        }

        private void newGame(){
            int response = JOptionPane.showConfirmDialog(null, "New Game?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION){
                System.out.println("New Game");
                game = new Connect4("Small");
                currState = game.initialState;
                gameOver = false;
                redWins = false;
                yellowWins = false;
                turn = 0;

            }
            if (response == JOptionPane.NO_OPTION){
                System.exit(0);
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Dimension d = getSize();
            g2.setColor(new Color(0, 0, 150));
            g2.fillRect(0,0,d.width,d.height);
            startX = 100;
            startY = 150;

            //2) draw grid here
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    g2.setColor(grid[row][col]);
                    g2.fillOval(startX, startY, cellWidth, cellWidth);
                    startX = startX + cellWidth + padding;
                }
                startX = 100;
                startY = startY+cellWidth + padding;

            }

            g2.setColor(new Color(255, 255, 255));

            if(!gameOver) {
                if (turn % 2 == 0) {
                    if (!yellowWins) {
                        g2.drawString("Red's Turn", 265, 50);
                    } else {
                        gameOver = true;
                        g2.drawString("Yellow Wins!", 260, 50);
                    }

                } else {
                    if (!redWins) {
                        g2.drawString("Yellow's Turn", 255, 50);
                    } else {
                        gameOver = true;
                        g2.drawString("Red Wins!", 270, 50);

                    }
                }
            }

        }

        public void mousePressed(MouseEvent e) {

            if(!redWins && !yellowWins){

                int x = e.getX();
                int y = e.getY();
                if(y >= 150 && y <= (150 + (6*(cellWidth + padding))) && x >= 100 && x<= (100 + (7*(cellWidth+padding)))){
                    int xSpot = (x-startX)/(cellWidth+padding);
                    int ySpot = getTopMostAvailable(xSpot);
                    if(ySpot >= 0){
                        grid[ySpot][xSpot] = new Color(255, 0, 0);
                        currState = game.result(currState, xSpot);
                        repaint();
                        if(isWinner(new Color(255,0,0))){
                            System.out.println("Red Wins!");
                            redWins = true;
                        }
                    }
                    if(!redWins){
                        int col = AlphaBetaMinimax.alphaBetaSearch(game, currState, false, 7);
                        System.out.println("Move: " + col);
                        int row = getTopMostAvailable(col);
                        grid[row][col] = new Color(255, 255, 0);
                        currState = game.result(currState, col);
                        repaint();
                        if(isWinner(new Color(255,255,0))){
                            System.out.println("Yellow Wins!");
                            yellowWins = true;
                        }
                    }
                }
            }

            if(yellowWins || redWins) {
                newGame();
                for (int row = 0; row < grid.length; row++) {
                    for (int col = 0; col < grid[0].length; col++) {
                        grid[row][col] = new Color(255, 255, 255);
                    }
                }
                repaint();
            }



            /*
            if(!redWins && !yellowWins){
                int x = e.getX();
                int y = e.getY();

                System.out.println(x + " "  + y);


                if(y >= 150 && y <= (150 + (6*(cellWidth + padding))) && x >= 100 && x<= (100 + (7*(cellWidth+padding)))){




                    int xSpot = (x-startX)/(cellWidth+padding);
                    //int ySpot = ((y-startY)/(cellWidth+padding))+5;
                    int ySpot = getTopMostAvailable(xSpot);
                    if(ySpot >= 0){
                        if(turn % 2 == 0){
                            grid[ySpot][xSpot] = new Color(255, 0, 0);
                            currState = game.result(currState, xSpot);
                            if(isWinner(new Color(255,0,0))){
                                System.out.println("Red Wins!");
                                redWins = true;
                            }

                        }


                        else {
                            grid[ySpot][xSpot] = new Color(255, 255, 0);
                            if(isWinner(new Color(255,255,0))){
                                System.out.println("Yellow Wins!");
                                yellowWins = true;
                            }
                        }

                        turn++;
                        repaint();
                        if(yellowWins || redWins) {
                            newGame();
                            for (int row = 0; row < grid.length; row++) {
                                for (int col = 0; col < grid[0].length; col++) {
                                    grid[row][col] = new Color(255, 255, 255);
                                }
                            }
                            repaint();
                        }


                    }

                }

            }
            */
        }

        public int getTopMostAvailable(int xSpot){
            int ySpot = rows-1;
            while(!(grid[ySpot][xSpot].equals(new Color(255,255,255))) || ySpot < 0){
                ySpot--;
            }
            return ySpot;
        }

        public boolean isWinner(Color color) {

            int m = 6;
            int n = 7;
            int k = 4;
            // k in a row
            for(int i = m-1; i >=0; i--) {
                for(int j = 0; j <= n-k; j++) {
                    if(!this.grid[i][j].equals(color)){  //!this.grid[i][j].equals(color)
                        continue;
                    }
                    for(int w = j; w <= j+k-1; w++) {
                        if(!this.grid[i][w].equals(color)) {
                            break;
                        }
                        if(w == j+k-1) {
                            return true;
                        }

                    }
                }

            }


            // k in a column

            for(int j = 0; j <= n-1; j++) {
                for(int i  = m-1; i >= k-1; i--) {
                    if(!this.grid[i][j].equals(color)) {
                        continue;
                    }
                    for(int w = i; w >= i-k+1; w--) {
                        if(!this.grid[w][j].equals(color)) {
                            break;
                        }
                        if(w==i-k+1) {
                            return true;
                        }
                    }
                }
            }

            // k in a diagonal

            for(int i = 0 ; i <= m-1; i++) {
                int r = i;
                for (int j = 0; j <= n-1; j++) {
                    int s = j;
                    for(int w = 0; w < k; w++) {
                        if(r > m-1 || s > n-1) {
                            break;
                        }
                        if(!this.grid[r][s].equals(color)) {
                            break;
                        }
                        if(w == k-1) {
                            return true;
                        }
                        r++;
                        s++;
                    }
                }
            }

            for(int i = m-1 ; i >= 0; i--) {
                int r = i;
                for (int j = 0; j <= n-1; j++) {
                    int s = j;
                    for(int w = 0; w < k; w++) {
                        if(r < 0 || s > n-1) {
                            break;
                        }
                        if(!this.grid[r][s].equals(color)) {
                            break;
                        }
                        if(w == k-1) {
                            return true;
                        }
                        r--;
                        s++;
                    }
                }
            }
            return false;
        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseClicked(MouseEvent e) {

        }
    }
}