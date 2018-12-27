import java.io.*;
import java.util.Random;


class MyThreadG extends Thread {

    private BufferedReader br = null;
    private BufferedWriter bw;

    public MyThreadG(BufferedWriter bw ) {
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
                continue;

            if (msg.equals("cancel")) {
                try {
                    bw.write("cancel\n");
                    bw.flush();
                } catch (IOException e) {}
                System.out.println("Process finished by cancel");
                Runtime.getRuntime().exit(0);
            }

            if (msg.equals("terminated")) {
                try {
                    bw.write("terminated\n");
                    bw.flush();
                } catch (IOException e) {}
                System.out.println("Process finished by ESC");
                Runtime.getRuntime().exit(0);
            }
        }
    }
}

public class FunctionG {
	public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Process G started");        
        int testVariant = Integer.parseInt(args[0]);
        Random rand = new Random();
        int result = 0;

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipe")));

        MyThreadG thread = new MyThreadG(bw);
        thread.start();

        switch (testVariant) {
        case 1:
            Thread.sleep(9000l);
            result =  1;
            break;
        case 2:
            result = 2;
            break;
        case 3:
            while (true) ;
        case 4:
            result = 0;
            break;
        case 5:
            while (true) ;
        case 6:
            result = 5;
        }
        bw.write("G:" + String.valueOf(result) + "\n");
        bw.flush();
        System.out.println("Process G finished");
        Runtime.getRuntime().exit(0);
	}
}
