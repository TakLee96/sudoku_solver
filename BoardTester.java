import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class BoardTester {

    public static void main(String[] args) {
        try {
            if (args.length == 0 || (
                !args[0].equals("simple") && !args[0].equals("easy") &&
                !args[0].equals("intermediate") && !args[0].equals("expert") &&
                !args[0].equals("all") && !args[0].equals("toughest")
            )) {
                System.out.println("Usage: java BoardTester [simple/easy/intermediate/expert/all]");
                return;
            }
            String[] diffs = null;
            if (args[0].equals("all")) {
                diffs = new String[]{ "simple", "easy", "intermediate", "expert", "toughest" };
            } else {
                diffs = new String[]{ args[0] };
            }
            for (String difficulty : diffs) {
                BufferedReader br = new BufferedReader(new FileReader("./boards/" + difficulty + ".txt"));
                ArrayList<String> boards = new ArrayList<String>();
                for (String line = br.readLine(); line != null; line = br.readLine())
                    boards.add(line);
                Sudoku instance = null, copy = null;
                long total = 0; int length = boards.size();
                for (int i = 0; i < length; i++) {
                    instance = new Sudoku(boards.get(i));
                    copy = instance.deepcopy();
                    AmazingSolver s = new AmazingSolver(instance);
                    total += s.start();
                    if (!instance.complete() || !instance.checkAllConstraints() || !instance.equals(copy)) {
                        System.out.println(copy);
                        System.out.println(instance);
                        throw new RuntimeException("Solving Error");
                    }
                    System.out.print(".");
                }
                System.out.print("Solved " + length + " puzzles in " + total + "ms ");
                System.out.println("(About " + 1.0 * total / length + "ms each)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
