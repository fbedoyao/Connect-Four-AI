import java.util.HashSet;
import java.util.Set;

//Formal Model: A game can be formally defined as a kind of search problem
public class Connect4{

    State initialState;



    public Connect4(String mode){
        initialState = new State(mode, 0);
    }

    public char player(State s){
        if (s.turn % 2  == 0){
            return 'X';
        }
        else {
            return 'O';
        }
    }

    public Set<Integer> actions(State s){
        Set<Integer> possibleMoves = new HashSet<>();
        for(int i = 0; i < s.array[0].length; i++){
            if(s.array[0][i] == ' ') possibleMoves.add(i);
        }
        return possibleMoves;
    }

    public State result(State s, int a){

        State s_prime = new State(s.array, s.turn+1);



        int temp = getTopMostAtCol(s.array, a);

        s_prime.array[temp][a] = player(s);

        return s_prime;
    }

    public boolean terminalTest(State s){

        if(isWinner('X', s) || isWinner('O', s) || checkIfDraw(s)){
            return true;
        }

        return false;
    }

    /*
    public int utility(State s, char player){
        if(player == 'X' && isWinner('O', s)){
            return -1;
        }
        else if(player == 'O' && isWinner('X', s)){
            return -1;
        }
        else if(checkIfDraw(s)){
            return 0;
        }
        else if(player == 'X' && isWinner('X', s)){
            return 1;
        }
        else if(player == 'O' && isWinner('O', s)){
            return 1;
        }

        return 0;
    }

     */

    public int utility(State s){
        if(isWinner('X', s)) return 1;
        else if(isWinner('O', s)) return -1;
        else return 0;
    }

    public int evaluate(State s) {
        int player1Score = 0;
        int player2Score = 0;

        if (isWinner('X', s) || isWinner('O', s)) {
            if (isWinner('X', s)) {
                player1Score = (int) Math.pow(10, (2));
            } else if (isWinner('O', s)) {
                player2Score = (int) Math.pow(10, (2));
            }
        }

        for (int i = 0; i < 2 ; i++) {
            player1Score += countNInARow(s, i + 2, 'X') * Math.pow(10, i);
            player2Score += countNInARow(s, i + 2, 'O') * Math.pow(10, i);
        }

        // If the result is 0, then it's a draw.
        return player1Score - player2Score;
    }

    public boolean canMove(int row, int col) {
        return (row > -1) && (col > -1) && (row < 6) && (col < 7);
    }

    public int countNInARow(State s, int N, int player) {
        int times = 0;

        // Check for "checkersInARow" consecutive checkers of the same player or empty tiles in a row, horizontally.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i, j + 4 - 1)) {
                    // Check for "N" consecutive checkers of the same player in a row, horizontally.
                    int k = 0;
                    while (k < N && s.array[i][j + k] == player) {
                        k++;
                    }
                    // Check for "checkersInARow - N" consecutive checkers of the same player or empty tiles in a row, horizontally.
                    if (k == N) {
                        while (k < 4 && (s.array[i][j + k] == player || s.array[i][j + k] == ' ')) {
                            k++;
                        }
                        if (k == 4) times++;
                    }
                }
            }
        }

        // Check for "checkersInARow" consecutive checkers of the same player or empty tiles in a row, vertically.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 6 + 1, j)) {
                    // Check for "N" consecutive checkers of the same player in a row, vertically.
                    int k = 0;
                    while (k < N && s.array[i - k][j] == player) {
                        k++;
                    }
                    // Check for "checkersInARow - N" consecutive checkers of the same player or empty tiles in a row, vertically.
                    if (k == N) {
                        while (k < 4 && (s.array[i - k][j] == player || s.array[i - k][j] == ' ')) {
                            k++;
                        }
                        if (k == 4) times++;
                    }
                }
            }
        }

        // Check for "checkersInARow" consecutive checkers of the same player or empty tiles in a row, in descending diagonal.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i + 4 - 1, j + 4 - 1)) {
                    // Check for "N" consecutive checkers of the same player in a row, in descending diagonal.
                    int k = 0;
                    while (k < N && s.array[i + k][j + k] == player) {
                        k++;
                    }
                    // Check for "checkersInARow - N" consecutive checkers of the same player or empty tiles in a row, in descending diagonal.
                    if (k == N) {
                        while (k < 4 && (s.array[i + k][j + k] == player || s.array[i + k][j + k] == ' ')) {
                            k++;
                        }
                        if (k == 4) times++;
                    }
                }
            }
        }

        // Check for "checkersInARow" consecutive checkers of the same player or empty tiles in a row, in ascending diagonal.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 6 + 1, j + 4 - 1)) {
                    // Check for "N" consecutive checkers of the same player in a row, in ascending diagonal.
                    int k = 0;
                    while (k < N && s.array[i - k][j + k] == player) {
                        k++;
                    }
                    // Check for "checkersInARow - N" consecutive checkers of the same player or empty tiles in a row, in ascending diagonal.
                    if (k == N) {
                        while (k < 4 && (s.array[i - k][j + k] == player || s.array[i - k][j + k] == ' ')) {
                            k++;
                        }
                        if (k == 4) times++;
                    }
                }
            }
        }

        return times;
    }



    //auxiliary functions

    public int getTopMostAtCol(char[][] array, int col){
        if(col > array[0].length-1 || col < 0) return -1;
        for(int i = array.length-1; i>= 0; i--){
            if(array[i][col] == ' ') return i;
        }
        return -1;
    }

    public  boolean isWinner(char player, State s){
        int m = s.array.length;
        int n = s.array[0].length;
        int k = (m == 3 && n == 3) ? 3: 4;
        // k in a row
        for(int i = m-1; i >=0; i--) {
            for(int j = 0; j <= n-k; j++) {
                if(s.array[i][j] != (player)){  //!this.grid[i][j].equals(color)
                    continue;
                }
                for(int w = j; w <= j+k-1; w++) {
                    if(s.array[i][w] != (player)) {
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
                if(s.array[i][j] != (player)) {
                    continue;
                }
                for(int w = i; w >= i-k+1; w--) {
                    if(s.array[w][j] != (player)) {
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
                int z = j;
                for(int w = 0; w < k; w++) {
                    if(r > m-1 || z > n-1) {
                        break;
                    }
                    if(s.array[r][z] != (player)) {
                        break;
                    }
                    if(w == k-1) {
                        return true;
                    }
                    r++;
                    z++;
                }
            }
        }

        for(int i = m-1 ; i >= 0; i--) {
            int r = i;
            for (int j = 0; j <= n-1; j++) {
                int z = j;
                for(int w = 0; w < k; w++) {
                    if(r < 0 || z > n-1) {
                        break;
                    }
                    if(s.array[r][z] != (player)) {
                        break;
                    }
                    if(w == k-1) {
                        return true;
                    }
                    r--;
                    z++;
                }
            }
        }
        return false;
    }

    public boolean checkIfDraw(State s){
        for(int i = 0; i < s.array.length; i++){
            for(int j = 0; j < s.array[0].length; j++){
                if(s.array[i][j] == ' ') return false;
            }
        }

        return true;
    }

    public static void main(String[] args){
        Connect4 game = new Connect4("Small");
        char arr[][]
                = { { ' ', ' ', 'X' }, { 'O', ' ', 'X' }, { 'O', ' ', 'X'} };
        State s = new State(arr, 1);
        s.printState();

        System.out.println(game.terminalTest(s));

        char player = game.player(s);
        System.out.println(game.utility(s));


    }

    public void printActions(State s){
        Set<Integer> actions = actions(s);
        System.out.print("Actions: ");
        for(Integer i : actions){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void printNextStates(State s){
        System.out.println("Next states: ");
        if (!terminalTest(s)){
            Set<Integer> actions = actions(s);
            for(Integer i : actions){
                result(s, i).printState();
            }
        }
    }




}
