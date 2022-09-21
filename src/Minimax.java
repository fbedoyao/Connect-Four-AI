public class Minimax {

    public static int minimaxSearch(Connect4 game, State state, boolean computerStarts){
        char player = game.player(state);
        Tuple tuple;
        if(computerStarts) tuple = maxValue(game, state, player);
        else tuple = minValue(game, state, player);
        System.out.println("Move: " + tuple.move + " | Utility: " + tuple.utility);
        return tuple.move;

    }

    public static Tuple maxValue(Connect4 game, State state, char player){
        if(game.terminalTest(state)){

            //char player = game.player(state) == 'O' ? 'X' : 'O';

            return new Tuple(game.utility(state), -1);


        }

        int v = -1000;
        int move = -1;

        for (Integer a : game.actions(state)){
            Tuple tuple = minValue(game, game.result(state, a), player);

            int v2 = tuple.utility;
            int a2 = tuple.move;
            if(v2 > v){
                v = v2;
                move = a;
            }
        }
        return new Tuple(v, move);
    }

    public static Tuple minValue(Connect4 game, State state, char player){
        if(game.terminalTest(state)){
            //char player = game.player(state) == 'O' ? 'X' : 'O';
            return new Tuple(game.utility(state), -1);
        }

        int v = 1000;
        int move = -1;

        for (Integer a : game.actions(state)){
            Tuple tuple = maxValue(game, game.result(state, a), player);

            int v2 = tuple.utility;
            int a2 = tuple.move;
            if(v2 < v){
                v = v2;
                move = a;
            }
        }
        return new Tuple(v, move);
    }





}
