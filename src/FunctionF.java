import java.io.*;
import java.util.Random;

class MyThreadF extends Thread {

    private BufferedReader br = null;
    private BufferedWriter bw;

    public MyThreadF(BufferedWriter bw ) {
        this.bw = bw;
    }

    @Override
    public void run() {

        try {
            br = new BufferedReader(new FileReader(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeFexit")));
        } catch (FileNotFoundException e) {

        }

        String msg = null;
        while (true) {
            //Process
            try {
                msg = br.readLine();
            } catch (IOException e) {}
            if (msg == null)
                break;
            try {
                bw.write("kaput\n");
                bw.flush();
            } catch (IOException e) {}
            Runtime.getRuntime().exit(1);
        }
    }
}

public class FunctionF {
	public static void main(String[] args) throws IOException {
        System.out.println("Process F started");
		int testVariant = Integer.parseInt(args[0]);
        Random rand = new Random();

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipe")));

        MyThreadF thread = new MyThreadF(bw);
        thread.start();

        int result = 0;
		
		switch(testVariant) {
        case 1:
            result = 2;
            break;
        case 2:
            while ((result = rand.nextInt(101)) > 99) ;
            break;
        case 3:
            result = 0;
            break;
        case 4:
            while(true);
        case 5:
            result = rand.nextInt(1000);
            break;
        case 6:
            while(true);
		}
		System.out.println("paka");
		bw.write("F:" + String.valueOf(result) + "\n");
		bw.flush();
        System.out.println("Process F finished");
        Runtime.getRuntime().exit(0);
	}
}
