import java.io.*;
import java.util.Random;

class MyThread extends Thread {

    private BufferedReader br = null;
    private BufferedWriter bw;

    public MyThread(BufferedWriter bw ) {
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
                bw.write("kaput");
                bw.flush();
            } catch (IOException e) {}
            Runtime.getRuntime().exit(0);
        }
    }
}

public class FunctionF {
	public static void main(String[] args) throws IOException {
        System.out.println("Process F started");
		int testVariant = Integer.parseInt(args[0]);
        Random rand = new Random();

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipe")));

        MyThread thread = new MyThread(bw);
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
		
		bw.write(String.valueOf(result));
		bw.flush();
        System.out.println("Process F finished");
        Runtime.getRuntime().exit(0);
	}
}
