import java.util.Scanner;  // Import the Scanner class


public class Game {
    char[][] board;
    int rows;
    int cols;
    static char RED = 'X';
    static char YELLOW = 'O';
    char winner = ' ';
    Connect4 game;
    State currState;


    public Game(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        board = new char[rows][cols];
        for(int i = 0; i < rows; i ++){
            for(int j = 0; j < cols; j++){
                board[i][j] = ' ';
            }
        }

            game = new Connect4("Small");
            currState = game.initialState;

       start();

    }

    public boolean move(char player, int col){
        int topMost = getTopMostAtCol(col);
        if(topMost != -1){
            board[topMost][col]= player;
            return true;
        }
        return false;
    }



    public  boolean isWinner(char player){
        int m = rows;
        int n = cols;
        int k = (rows == 3 && cols == 3) ? 3: 4;
        // k in a row
        for(int i = m-1; i >=0; i--) {
            for(int j = 0; j <= n-k; j++) {
                if(this.board[i][j] != (player)){  //!this.grid[i][j].equals(color)
                    continue;
                }
                for(int w = j; w <= j+k-1; w++) {
                    if(this.board[i][w] != (player)) {
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
                if(this.board[i][j] != (player)) {
                    continue;
                }
                for(int w = i; w >= i-k+1; w--) {
                    if(this.board[w][j] != (player)) {
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
                    if(this.board[r][s] != (player)) {
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
                    if(this.board[r][s] != (player)) {
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

    public int getTopMostAtCol(int col){
        if(col > cols-1 || col < 0) return -1;
        for(int i = rows-1; i>= 0; i--){
            if(board[i][col] == ' ') return i;
        }
        return -1;
    }

    public void printBoard(){
        System.out.print(" ");
        for(int i = 0; i < cols; i++){
            System.out.print( i + " ");
        }
        System.out.println();


        for(int i = 0; i < rows; i++){
            System.out.print("|");
            for(int j = 0; j < cols; j++){
                System.out.print(board[i][j] + "|");
            }
            System.out.println();

        }

        System.out.println();

    }

    public void start(){

        Scanner scnr = new Scanner(System.in);

        System.out.println("Who starts? (1) Computer (2) Human");

        int firstPlayer = scnr.nextInt();

        System.out.println("======== NEW GAME =============");
        System.out.println();
        int turn = 0;
        printBoard();

        while(true){

            if(game.terminalTest(currState)) break;

            if(currState.turn % 2 == 0){

                System.out.println("RED's turn. Choose your move: ");
                int col;
                if(firstPlayer == 1) {
                    col = AlphaBetaMinimax.alphaBetaSearch(game, currState, true, 7);
                } else {
                    col = scnr.nextInt();
                }
                if (!move(RED, col)){
                    System.out.println("Invalid move. You are trying to move at " + col );
                }
                else{
                    System.out.println("RED @" + col);
                    printBoard();
                    if(isWinner(RED)){
                        winner = RED;
                        break;
                    }
                    turn++;
                    currState = game.result(currState, col);

                }
            } else {
                System.out.println("YELLOW's turn. Choose your move: ");
                int col;

                if(firstPlayer == 2){
                    col = AlphaBetaMinimax.alphaBetaSearch(game, currState, false, 7);
                } else{
                    col = scnr.nextInt();
                }

                if (!move(YELLOW, col)){
                    System.out.println("Invalid move.");
                }
                else{
                    System.out.println("YELLOW @" + col);
                    printBoard();
                    if(isWinner(YELLOW)){
                        winner = YELLOW;
                        break;
                    }
                    turn++;
                    currState = game.result(currState, col);
                }
            }

        }

        if(winner == 'X') {
            System.out.println("RED WINS!");
        } else if(winner == 'O'){
            System.out.println("YELLOW WINS!");
        } else{
            System.out.println("DRAW");
        }

    }

    public static void main(String[] args){
        Game g = new Game(6,7);
        /*
        State s = g.currState;
        s.printState();
        System.out.print("Actions: ");
        for(Integer a : g.game.actions(s)){
            System.out.println("ON ACTION: " + a);
            State s_prime = g.game.result(s, a);
            s_prime.printState();
        }
        System.out.println();

         */


    }


}
