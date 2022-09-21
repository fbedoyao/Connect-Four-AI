public class AlphaBetaMinimax {

    public static int alphaBetaSearch(Connect4 game, State state, boolean computerStarts, int depth){
        char player = game.player(state);
        Tuple tuple;
        if(computerStarts) tuple = maxValue(game, state, player, -1000, 1000, depth-1);
        else tuple = minValue(game, state, player, -1000, 1000, depth-1);
        System.out.println("Returning move: " + tuple.move + " with evaluation: " + tuple.utility);
        return tuple.move;
    }

    public static Tuple maxValue(Connect4 game, State state, char player, int alpha, int beta, int depth){
        //System.out.println("uwu");
        if (game.terminalTest(state) || depth == 0){
            state.printState();
            return new Tuple(game.evaluate(state), -1);
        }

        int v = -100000;
        int move = -1;
        int newAlpha = alpha;

        for(Integer a : game.actions(state)){
            //System.out.println("gaaaaa");
            Tuple tuple = minValue(game, game.result(state, a), player, newAlpha, beta, depth -1);
            //System.out.println("ga2");
            int v2 = tuple.utility;
            if(v2 > v){
                v = v2;
                move = a;
                newAlpha = Math.max(alpha, v);
            }
            if(v >= beta) return new Tuple(v, move);
        }
        return new Tuple(v, move);
    }

    public static Tuple minValue(Connect4 game, State state, char player, int alpha, int beta, int depth){
        if (game.terminalTest(state) || depth == 0){
            return new Tuple(game.utility(state), -1);
        }

        int v = 100000;
        int move = -1;
        int newBeta = beta;

        for(Integer a : game.actions(state)){
            Tuple tuple = maxValue(game, game.result(state, a), player, alpha, newBeta, depth -1);
            int v2 = tuple.utility;
            if(v2 < v){
                v = v2;
                move = a;
                newBeta = Math.min(beta, v);
            }
            if(v <= alpha) return new Tuple(v, move);
        }
        return new Tuple(v, move);
    }


}
