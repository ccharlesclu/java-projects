package yahtzee;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class PlayerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static int padding;	
	
	public Color playerColor;
	public int width, height, playerNum;
	public int[] yPos = new int[18];
	public JLabel[] upperScoresArray= new JLabel[6];
	public JLabel[] upperLabelsArray= new JLabel[3];
	public JLabel[] lowerScoresArray= new JLabel[7];
	public JLabel[] lowerLabelsArray= new JLabel[1];
	public JLabel[] total = new JLabel[1];

	public PlayerPanel(Color playerColorInput, int widthInput, int heightInput, int playerNumInput){
		
		width = widthInput;
		height = heightInput;
		playerNum = playerNumInput;
		playerColor = playerColorInput;
		this.setSize(width, height);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		setBorder(new TitledBorder(new LineBorder(playerColor, 2), "Player " + playerNum));
		setAlignmentY(Component.CENTER_ALIGNMENT);
		setLayout(null);
		
		padding = (int) (height * .017);
		
		int currentYPos = 0;
		int fontSize = 14;
		
		for(int i = 1; i <= yPos.length; i++){
			yPos[i-1] = i * (height / 19) + padding;
		}
		
		// Upper score set
		for(int i = 0; i < upperScoresArray.length; i++){
			upperScoresArray[i] = new JLabel("");
			setLabel(upperScoresArray[i], yPos[currentYPos], Font.PLAIN, Color.DARK_GRAY, fontSize);
			currentYPos++;			
		}
		for(int i = 0; i < upperLabelsArray.length; i++){
			upperLabelsArray[i] = new JLabel("0");
			if(i == 2) setLabel(upperLabelsArray[i], yPos[currentYPos], Font.ITALIC, playerColor, fontSize + 2);
			else setLabel(upperLabelsArray[i], yPos[currentYPos], Font.BOLD, Color.GRAY, fontSize);
			currentYPos++;
		}
		
		// Lower score set
		for(int i = 0; i < lowerScoresArray.length; i++){
			lowerScoresArray[i] = new JLabel("");
			setLabel(lowerScoresArray[i], yPos[currentYPos], Font.PLAIN, Color.DARK_GRAY, fontSize);
			currentYPos++;			
		}
		for(int i = 0; i < lowerLabelsArray.length; i++){
			lowerLabelsArray[i] = new JLabel("0");
			setLabel(lowerLabelsArray[i], yPos[currentYPos], Font.ITALIC, playerColor, fontSize + 2);
			currentYPos++;
		}
		
		// Player total
		total[0] = new JLabel("0");
		setLabel(total[0], yPos[currentYPos], Font.BOLD, playerColor, fontSize + 4);		
	}
	
	public void setLabel(JLabel label, int yPos, int weight, Color color, int size){
		label.setBorder(null);
		label.setSize((int) (width * .8), (int) (height / (19 + padding)));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", weight, size));
		label.setLocation(this.getWidth() / 2 - label.getWidth() / 2, yPos);
		label.setForeground(color);
		label.setVisible(true);
		this.add(label);
	}
}
