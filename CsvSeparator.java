package fileTranscriber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CsvSeparator {
	
	static int fileNum = 1;
	static int maxRecords = 2;
	static boolean hasHeader = true;
	static String outputExt;
	static final JFileChooser fc = new JFileChooser();

	public static void main(String[] args) throws IOException {
		
		if (System.getProperty("os.name").toLowerCase().indexOf("win")<0) {
            System.err.println("Sorry, Windows only!");
            System.exit(1);
        }
		
        File desktopDir = new File(System.getProperty("user.home"), "Desktop");
		

		fc.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
		
		File inputFile = null;
		BufferedReader reader = null;
		FileWriter filenameFW;
		PrintWriter outputFilePW = null;
		
		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			inputFile = fc.getSelectedFile();
			System.out.println("Processing " + inputFile.getName());
			reader = new BufferedReader(new FileReader(inputFile));
		} else{
			JOptionPane.showMessageDialog(null, "Operation cancelled by user");
			System.out.println("Program terminated from CsvSeparator");
			System.exit(0);
		}
		
		String inputFileName = inputFile.getName();
		inputFileName = inputFileName.substring(0, inputFileName.length() - 4);
		
		List<String> lines = new ArrayList<>();
		String line = null;
		while ((line = reader.readLine()) != null){
			lines.add(line);
		}
		
		ParametersDialog options = new ParametersDialog(lines.size());
		options.setVisible(true);		
		maxRecords = options.maxRows;
		hasHeader = options.hasHeader;
		outputExt = options.outputExt;
		
		filenameFW = new FileWriter(desktopDir.toString() + "\\" + inputFileName + "_OUTPUT_" + fileNum + outputExt);
		outputFilePW= new PrintWriter(filenameFW);
		
		if(JOptionPane.showConfirmDialog(null, "Breaking into file(s) of " + maxRecords + " records per file\n"
				+ "         File(s) will output to Desktop\n"
				+ "                       Continue?", null, JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE) == JOptionPane.NO_OPTION){
					JOptionPane.showMessageDialog(null, "Operation cancelled by user");
					System.out.println("Program terminated from CsvSeparator");
					System.exit(0);
		};
		
		try{			
			String header = lines.get(0);		
			int lineCount = 0;
			if(hasHeader){
				lines.remove(0);
				outputFilePW.write(header + System.getProperty("line.separator"));
			}
			
			StatusDialog status = new StatusDialog();
			status.total = lines.size();
			status.lblCompleted.setText((Integer.toString(status.completed) + " of processed out of " + Integer.toString(status.total)));
			status.setVisible(true);
			
			for(int lineNum = 0; lineNum < lines.size(); lineNum++){
				outputFilePW.write(lines.get(lineNum) + System.getProperty("line.separator"));
				status.lblCompleted.setText((Integer.toString(lineNum) + " of processed out of " + Integer.toString(status.total)));
				status.revalidate();
				lineCount++;
				if(lineCount == maxRecords && lineNum != (lines.size() - 1)){
					fileNum++;
					lineCount = 0;
					filenameFW.close();
					filenameFW = new FileWriter(desktopDir.toString() + "\\" + inputFileName + "_OUTPUT_" + fileNum + outputExt);
					outputFilePW = new PrintWriter(filenameFW);
					if(hasHeader){
						outputFilePW.write(header + System.getProperty("line.separator"));
						lineCount = 1;
					}
				}
				System.out.println("Line " + (lineNum + 1) + " printed to " + inputFileName + "_OUTPUT_" + fileNum + outputExt);
			}
			
			status.dispose();
			
			JOptionPane.showMessageDialog(null, "                             File has been processed\n"
					+ lines.size() + " records have been divided among "
					+ fileNum + " files on your Desktop\n"
					+ "     Output file name(s) is '" + inputFileName + "_OUTPUT" + outputExt + "'");
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, "Sorry...something went wrong...\n"
					+ "Program is closing - please try again.");
			outputFilePW.close();
			System.exit(1);
		}
		
		System.out.println("Process successful!");
		System.out.println("Program terminated from CsvSeparator");
		outputFilePW.close();
		System.exit(0);
	}
}
