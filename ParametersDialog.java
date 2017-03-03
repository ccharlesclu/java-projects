package fileTranscriber;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ParametersDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	JTextField maxRowsInput;
	public boolean hasHeader = true;
	public int maxRows;
	public String outputExt = ".csv";
	private int inputVal;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParametersDialog frame = new ParametersDialog(15000);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ParametersDialog(int fileRows) {
		
		if(fileRows > 1000000){
			maxRows = 1000000;
		} else maxRows = fileRows - 1;
		
		ParametersDialog frame = this;
		this.setModal(true);
		
		this.setSize(340, 280);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getWidth() /2, dim.height / 2 - this.getHeight() / 2);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblHeader = new JLabel("Does this file have a header row?");
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHeader.setBounds(5, 5, 244, 24);
		lblHeader.setVisible(true);
		contentPane.add(lblHeader);
		
		JRadioButton radioYes = new JRadioButton("Yes");
		radioYes.setSelected(true);
		radioYes.setBounds(5, 25, 78, 23);
		contentPane.add(radioYes);
		
		JRadioButton radioNo = new JRadioButton("No");
		radioNo.setSelected(false);
		radioNo.setBounds(85, 25, 74, 23);
		contentPane.add(radioNo);
		
		ButtonGroup yesNo = new ButtonGroup();
		yesNo.add(radioNo);
		yesNo.add(radioYes);
		
		JLabel lblMaxRows = new JLabel("How many rows should be in each output file?");
		lblMaxRows.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMaxRows.setBounds(5, 70, 596, 14);
		contentPane.add(lblMaxRows);
		
		maxRowsInput = new JTextField();
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		
		inputVal = maxRows;
		
		String starter = decimalFormat.format(inputVal);
		maxRowsInput.setText(starter);
	    
		maxRowsInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if(!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)){
					arg0.consume();
				}
			}
		});
		
		maxRowsInput.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e){
				maxRowsInput.setText(Integer.toString(inputVal));
				maxRowsInput.selectAll();
				maxRowsInput.requestFocus();
			}
		    public void focusLost(FocusEvent e) {
		    	inputVal = Integer.parseInt(maxRowsInput.getText());
		    	String formattedNumber = decimalFormat.format(Integer.parseInt(maxRowsInput.getText()));
		    	maxRowsInput.setText(formattedNumber);
		    	System.out.println("Max rows per file set to: " + inputVal);
		    }
		});
		
		maxRowsInput.setFont(new Font("Tahoma", Font.BOLD, 11));
		maxRowsInput.setBounds(5, 105, 86, 20);
		contentPane.add(maxRowsInput);
		maxRowsInput.setColumns(10);
		
		JLabel lblMax = new JLabel();
		String maxLabel = decimalFormat.format(maxRows);
		lblMax.setText("MAX " + maxLabel);
		lblMax.setForeground(Color.GRAY);
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMax.setBounds(5, 85, 109, 14);
		contentPane.add(lblMax);
		
		radioYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputVal == fileRows){
					inputVal -= 1;
					String noHeaderMax = decimalFormat.format(inputVal);
					maxRowsInput.setText(noHeaderMax);
				}
				String maxLabel = decimalFormat.format(fileRows - 1);
				lblMax.setText("MAX " + maxLabel);
				maxRows = fileRows - 1;
				contentPane.revalidate();
				hasHeader = true;
				System.out.println("File set to contains header: true");
			}
		});
		
		radioNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputVal == (fileRows - 1)){
					inputVal += 1;
					String noHeaderMax = decimalFormat.format(inputVal);
					maxRowsInput.setText(noHeaderMax);
				}
				String maxLabel = decimalFormat.format(fileRows);
				lblMax.setText("MAX " + maxLabel);
				maxRows = fileRows;
				contentPane.revalidate();
				hasHeader = false;
				System.out.println("File set to contains header: false");
			}
		});
		
		JLabel lblWhatFileTuype = new JLabel("What file type for the output?");
		lblWhatFileTuype.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblWhatFileTuype.setBounds(5, 150, 596, 14);
		contentPane.add(lblWhatFileTuype);
		
		JRadioButton radioCSV = new JRadioButton("CSV File (.csv)");
		radioCSV.setSelected(true);
		radioCSV.setBounds(5, 170, 109, 23);
		contentPane.add(radioCSV);
		radioCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputExt = ".csv";
				System.out.println("File output type set to .csv");
			}
		});
		
		JRadioButton radioTXT = new JRadioButton("Text File (.txt)");
		radioTXT.setBounds(126, 170, 109, 23);
		contentPane.add(radioTXT);
		radioTXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputExt = ".txt";
				System.out.println("File output type set to .txt");
			}
		});
		
		ButtonGroup outputGroup = new ButtonGroup();
		outputGroup.add(radioCSV);
		outputGroup.add(radioTXT);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((inputVal > maxRows) | inputVal < 0){
					JOptionPane.showMessageDialog(null, "Max rows must be between 0 and " + maxLabel);
					inputVal = maxRows;
					maxRowsInput.setText(Integer.toString(maxRows));
					maxRowsInput.requestFocus();
					maxRowsInput.selectAll();
					lblMaxRows.setForeground(Color.RED);
				}
				else {
					maxRows = inputVal;
					System.out.println("Paramaters accepted {\nContains header: " + hasHeader
							+ "\nMax rows per file: " + maxRows
									+ "\nOutput file type: " + outputExt
									+ "\n}");
					frame.dispose();
				}
			}
		});
		btnOk.setBounds(85, 208, 85, 23);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Operation cancelled by user");
				System.out.println("Program terminated from ParametersDialog");
				System.exit(0);
			}
		});
		btnCancel.setBounds(181, 208, 85, 23);
		contentPane.add(btnCancel);
	}
}
