import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;

public class BoardTester {

    public static void main(String[] args) {
        try {
            String difficulty = args[0];
            BufferedReader br = new BufferedReader(new FileReader("./boards/" + difficulty + ".txt"));
            ArrayList<String> boards = new ArrayList<String>();
            for (String line = br.readLine(); line != null; line = br.readLine())
                boards.add(line);
            Sudoku instance = null, copy = null;
            Random random = new Random(System.nanoTime());
            for (int i = 0; i < Constant.NUM_TRIALS; i++) {
                instance = new Sudoku(boards.get(random.nextInt(boards.size())));
                copy = instance.deepcopy();
                Solver s = new Solver(instance);
                long time = s.start();
                System.out.println(copy);
                System.out.println("Solved in " + time + "ms");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
