import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Manager {
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Manager");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 200));

        JTextArea textArea = new JTextArea();
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
                        System.out.println("kek");
                        textArea.append("kek");
                        BufferedWriter brF = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeFexit")));
                        brF.write("fuck off");
                        brF.flush();

                        BufferedWriter brG = new BufferedWriter(new FileWriter(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipeGexit")));
                        brG.write("fuck off");
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

        BufferedReader br = new BufferedReader(new FileReader(new File("/home/andrii/IdeaProjects/lab1-spos/src/pipe")));

        int count = 0;
        String msg;
        while (count < 2) {
            //Process
            msg = br.readLine();
            System.out.println(msg);
            if (msg != null)
                count++;
        }

    }
}