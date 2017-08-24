import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JavaCompiler extends JFrame{
	JTextField titleField;
	JTextArea txtArea;
	JButton compileButton;
	JButton runButton;

	public JavaCompiler() {
		super("GUI for Java compilation and testing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1250, 800);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		titleField = new JTextField("Insert Document Name Here", 20);
		txtArea = new JTextArea("Insert Code Here");
		compileButton = new JButton("Compile code");
		runButton = new JButton("Run code");
		this.add(runButton, BorderLayout.EAST);
		this.add(compileButton, BorderLayout.WEST);
		this.add(titleField, BorderLayout.NORTH);
		this.add(new JScrollPane(txtArea), BorderLayout.CENTER);
		
	    compileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                compile(txtArea.getText(), titleField.getText());
            }
        });
	    runButton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent ae) {
	    		run(titleField.getText());
	    	}
	    	
	    });

	}
	 private static void printLines(String name, InputStream ins, boolean err) throws Exception {
		    String line = null;
		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(ins));
		    while ((line = in.readLine()) != null) {
		    	if(err)
		    		System.err.println(name + " " + line);
		    	else
		    		System.out.println(line);
		    }
	}

	private static void runCommand(String command) {
		try{
			String[] ss = {"bash", "-c", command};
		    Process pr = Runtime.getRuntime().exec(ss);
		    printLines(command + "stdout:", pr.getInputStream(), false);
		    printLines(command + " stderr:", pr.getErrorStream(), true);
		    pr.waitFor();
		    //System.out.println(command + " exitValue() " + pr.exitValue());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void compile(String code, String title){
			runCommand("rm -f " + title + ".java");
			//code = code.replace("\"", "\"");
			code = code.replace("'", "\'"); //format string properly for echo
			runCommand("echo \'" + code + "\' >> " + title + ".java");
			runCommand("javac " + title + ".java");
	}
	public void run(String title){
		runCommand("java " + title);
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JavaCompiler().setVisible(true);
	}

}
