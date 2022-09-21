import java.util.Set;

public class State {
    char[][] array;
    int turn;

    public State(char[][] board, int turn){
        array = new char[board.length][board[0].length];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                array[i][j] = board[i][j];
            }
        }
        this.turn = turn;
    }

    public State(String mode, int turn){
        if(mode.equals("Small")){
            array = new char[6][7];
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 7; j++){
                    this.array[i][j] = ' ';
                }
            }
        }
        this.turn = turn;
    }

    public void printState(){
        System.out.println("Turn " + turn);
        System.out.print(" ");
        for(int i = 0; i < array[0].length; i++){
            System.out.print( i + " ");
        }
        System.out.println();


        for(int i = 0; i < array.length; i++){
            System.out.print("|");
            for(int j = 0; j < array[0].length; j++){
                System.out.print(array[i][j] + "|");
            }
            System.out.println();

        }

        System.out.println();
    }



}
