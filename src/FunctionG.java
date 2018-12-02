import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FunctionG {
	public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Process G started");        
        int testVariant = Integer.parseInt(args[0]);
        Random rand = new Random();
        int result = 0;

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipe")));
        
        
        switch (testVariant) {
        case 1:
            Thread.sleep(6000l);
            result =  1;
            break;
        case 2:
            result = rand.nextInt(1000);
            break;
        case 3:
            while (true) ;
        case 4:
            result = 0;
            break;
        case 5:
            while (true) ;
        case 6:
            result = rand.nextInt(1000);
        }
        bw.write(String.valueOf(result));
        bw.flush();
        System.out.println("Process G finished");
	}
}
