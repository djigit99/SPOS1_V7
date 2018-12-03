import java.io.*;
import javax.swing.*;
import java.util.Scanner;
import java.awt.event.*;
import java.awt.*;
class Prompt extends Thread {

    private JTextArea textArea;
    private boolean isPromt = true;
    private int ans;

    public Prompt(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public synchronized void run() {
        textArea.append("Choose the option:\n1) continue;\n2) continue without prompt\n3) cancel\n");
        while (isPromt) {
            try {
                wait(5000);
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter your choise:");
                ans = sc.nextInt();
                if (ans == 1) ;
                if (ans == 2) {
                    isPromt = false;
                    textArea.append("Prompt off\n");
                }
                if (ans == 3) {
                    try {
                        BufferedWriter brF = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeFexit")));
                        brF.write("terminated\n");
                        brF.flush();

                        BufferedWriter brG = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeGexit")));
                        brG.write("terminated\n");
                        brG.flush();
                    } catch (IOException e) {
                    }
                }
            } catch (InterruptedException exp) {
            }
        }
    }
    public void setAns(char ans) {
        this.ans = ans;
    }
}

public class Manager {

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Manager");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 200));

        JTextArea textArea = new JTextArea();
        Prompt prompt = new Prompt(textArea);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setFocusable(true);
        textArea.setEditable(false);
        textArea.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    try {

                        BufferedWriter brF = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeFexit")));
                        brF.write("terminated\n");
                        brF.flush();

                        BufferedWriter brG = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeGexit")));
                        brG.write("terminated\n");
                        brG.flush();

                    } catch (IOException exc) {}
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
            }
        });
        frame.add(textArea);
        frame.setVisible(true);


        textArea.append("Process F started\n");
        ProcessBuilder pbF = new ProcessBuilder("java", "FunctionF", "1");
        pbF.directory(new File("/home/andrii/IdeaProjects/lab1-spos/out/"));
        Process pF = pbF.start();

        textArea.append("Process G started\n");
        ProcessBuilder pbG = new ProcessBuilder("java", "FunctionG", "1");
        pbG.directory(new File("/home/andrii/IdeaProjects/lab1-spos/out/"));
        Process pG = pbG.start();

        try {
            Thread.sleep(1000);
        }  catch (InterruptedException exp) {}
        prompt.start();

        BufferedReader br = new BufferedReader(new FileReader(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipe")));

        int valF = 0, valG = 0;
        boolean f_is_finished = false;
        boolean g_is_finished = false;
        String msg;
        while (f_is_finished == false || g_is_finished == false) {
            //Process
            msg = br.readLine();
            if (msg != null && msg.equals("kaput")) {
                if (valF!=0 && valG != 0) {
                    System.out.println("Ans = " + String.valueOf(valF + valG));
                    Runtime.getRuntime().exit(0);
                } else {
                    if (valF == 0)
                        System.out.println("Function F is not computed");
                    if (valG == 0)
                        System.out.println("Function G is not computed");
                    Runtime.getRuntime().exit(1);
                }
            }
            if (msg == null)
                break;
            String func = msg.substring(0,2);
            if (func.charAt(0) == 'F') {
                valF = Integer.parseInt(msg.substring(2));
                f_is_finished = true;
            } else {
                valG = Integer.parseInt(msg.substring(2));
                g_is_finished = true;
            }
        }
        br.close();
        System.out.println("Ans = " + String.valueOf(valF + valG));
        Runtime.getRuntime().exit(0);
    }
}