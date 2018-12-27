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
    public void run() {
        textArea.append("Choose the option:\n1) continue;\n2) continue without prompt\n3) cancel\n");
        while (isPromt) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter your choise:");
                ans = sc.nextInt();
                if (ans == 1) ;
                if (ans == 2) {
                    isPromt = false;
                    textArea.append("Prompt off\n");
                    break;
                }
                if (ans == 3) {
                    try {
                        BufferedWriter brF = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeFexit")));
                        brF.write("cancel\n");
                        brF.flush();

                        BufferedWriter brG = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeGexit")));
                        brG.write("cancel\n");
                        brG.flush();
                    } catch (IOException e) {
                    }
                }
                Thread.sleep(5000);
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

        try {
            Thread.sleep(1000);
        }  catch (InterruptedException exp) {}

        textArea.append("Process F started\n");
        ProcessBuilder pbF = new ProcessBuilder("java", "FunctionF", "3");
        pbF.directory(new File("/home/andrii/eclipse-workspace/SPOS1_V7/bin/"));
        Process pF = pbF.start();

        textArea.append("Process G started\n");
        ProcessBuilder pbG = new ProcessBuilder("java", "FunctionG", "3");
        pbG.directory(new File("/home/andrii/eclipse-workspace/SPOS1_V7/bin/"));
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
        while (true) {
            //Process
            msg = br.readLine();

            if (msg == null)
                continue;

            if (msg.equals("terminated")) {
                System.out.println("Program terminated");
                br.close();
                Runtime.getRuntime().exit(1);
            }

            if (msg.equals("cancel")) {
                    if (f_is_finished == false) {
                        System.out.println("FunctionF is not computed. Ans = unknown.");
                        br.close();
                        Runtime.getRuntime().exit(0);
                    } else if (g_is_finished == false) {
                        System.out.println("FunctionG is not computed. Ans = unknown.");
                        br.close();
                        Runtime.getRuntime().exit(0);
                    }

            }

            String func = msg.substring(0,2);
            if (func.charAt(0) == 'F') {
                valF = Integer.parseInt(msg.substring(2));
                f_is_finished = true;
            } else if (func.charAt(0) == 'G')  {
                valG = Integer.parseInt(msg.substring(2));
                g_is_finished = true;
            }
            if (f_is_finished == true && valF == 0) {
                System.out.println("F return 0. Ans = 0");
                br.close();
                Runtime.getRuntime().exit(0);
            }
            if (g_is_finished == true && valG == 0) {
                System.out.println("G return 0. Ans = 0");
                br.close();
                Runtime.getRuntime().exit(0);
            }
            if (f_is_finished == true && g_is_finished == true) {
                System.out.println("Ans = " + String.valueOf(valF + valG));
                br.close();
                Runtime.getRuntime().exit(0);
            }
        }
    }
}