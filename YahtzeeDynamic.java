// FREEZES OUT IF YOU START NEW GAME WITH SAME NUMBER OF PLAYERS - WHY

package yahtzee;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class YahtzeeDynamic extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static Image imgNgBtn = new ImageIcon(ClassLoader.getSystemResource("newGame.png")).getImage();
	private static Image imgRollBtn = new ImageIcon(ClassLoader.getSystemResource("roll.png")).getImage();
	private static Image imgTitle = new ImageIcon(ClassLoader.getSystemResource("title.png")).getImage();
	
	protected static Image imgFelt = new ImageIcon(ClassLoader.getSystemResource("greenFelt.png")).getImage();

	private Color[] playerColor = {Color.BLUE, Color.RED, Color.MAGENTA, Color.ORANGE.darker()};
	private Die[] DICEPACK = new Die[5];
	private int activePlayer;
	private int numPlayers, rollNum, ppHeight, ppWidth;
	private int minDisplayWidth, displayWidth, displayHeight, screenPadding, dicePadding;
	private JButton btnRoll;
	private JLayeredPane contentPane;
	private PlayerPanel[] playerPanels;
	private String[] UPPER_SCORE_NAMES = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes"};
	private String[] UPPER_LABEL_NAMES = {"UPPER SCORE", "BONUS (If>= 63)", "UPPER TOTAL"};
	private String[] LOWER_SCORE_NAMES = {"Three of a Kind", "Four of a Kind", "Full House", "Sm Straight", "Lg Straight", "Yahtzee", "Chance"};
	private String[] LOWER_LABEL_NAMES = {"LOWER TOTAL"};
	private String[] TOTAL_NAMES = {"TOTAL"};
		
 // Launch app
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YahtzeeDynamic frame = new YahtzeeDynamic();
					frame.setVisible(true);
					frame.setResizable(false);
					//frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

 // Create frame
	public YahtzeeDynamic() {
		
		newGame();
	
	}
	
	public void newGame(){
		
		screenPadding = 0;
		dicePadding = 0;
		numPlayers = 0;
		activePlayer = 0;
		rollNum = 0;
		
 // Launch number of player selection
		PlayerSelect ps = new PlayerSelect();
		ps.setAlwaysOnTop(true);
		ps.setModal(true);
		ps.setVisible(true);
		numPlayers = PlayerSelect.numPlayers;
		playerPanels = new PlayerPanel[numPlayers];
		
 // Grab screen size for scaling
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		minDisplayWidth = (int) (Die.getSize() * 5);
		displayWidth =  (int) (dim.getWidth() / 10);
		if (displayWidth < minDisplayWidth) {
			displayWidth = minDisplayWidth;
		}
		displayHeight = (int) (dim.height * .88);
		screenPadding = (int) (displayWidth * .05);
		dicePadding = (int) (screenPadding * 1.5);
		
 // Size out player panel		
		ppWidth = ((int) (displayWidth *.5));
		ppHeight = ((int) (displayHeight * .67));

 // Scale size of display to number of players, set to center screen at launch
		this.setSize(displayWidth + (numPlayers * ppWidth), displayHeight);
		this.setLocation(dim.width / 2 - this.getWidth() /2, dim.height / 2 - this.getHeight() / 2);
		
		JLabel background = new JLabel();
		background.setSize(this.getWidth(), this.getHeight());
		background.setIcon(new ImageIcon(getScaledImage(imgFelt, background.getWidth(), background.getHeight())));
		background.setVisible(true);
		
		
 // Initailize JPanel		
		contentPane = new JLayeredPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.RED);
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.add(background, 2);
		
 // Add dice to display
		int dicePos = (int) (this.getWidth() / 2 - (Die.getSize() * 2.5) - (2 * dicePadding));
		for(Die die : DICEPACK) {
			die = new Die(dicePos, (int) (this.getHeight() * .078));
			dicePos += Die.getSize() + dicePadding;
			contentPane.add(die.btn_die, 0);
		};
		
 // New game button		
		JButton btnNewGame= new JButton();
		btnNewGame.setLocation((int)(this.getWidth() * .02), (int) (this.getWidth() * .02));
		btnNewGame.setSize((int) (displayWidth * .275), (int) (displayWidth * .3 * .55));
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});
		buttonImage(btnNewGame, imgNgBtn);
		contentPane.add(btnNewGame, 1);

 // Add rollNumberCounter		
		JLabel lblRollNum = new JLabel();
		lblRollNum.setText("Roll number: " + rollNum);
		lblRollNum.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRollNum.setForeground(Color.WHITE);
		lblRollNum.setSize(this.getWidth(), lblRollNum.getPreferredSize().height);
		lblRollNum.setLocation(0, (int) (this.getHeight() * .15));
		lblRollNum.setHorizontalAlignment(SwingConstants.CENTER);
		lblRollNum.setVisible(true);
		contentPane.add(lblRollNum, 1);

 // Add Yahtzee title graphic		
		JLabel lblTitle = new JLabel();
		lblTitle.setSize((int) (displayWidth * .8), (int) (displayWidth * .3 * .7));
		lblTitle.setLocation((int)((this.getWidth()  / 2) - (lblTitle.getWidth() / 2)), (int) (this.getHeight() * .01));
		imgTitle = getScaledImage(imgTitle, lblTitle.getWidth(), lblTitle.getHeight());
		lblTitle.setIcon(new ImageIcon(imgTitle));
		contentPane.add(lblTitle, 1);
		
 // Add roll button	
		btnRoll = new JButton();
		btnRoll.setSize((int) (displayWidth * .4), (int) (displayWidth * .3 * .66));
		btnRoll.setLocation((int)((this.getWidth()  / 2) - (btnRoll.getWidth() / 2)), (int) (this.getHeight() * .18));
		buttonImage(btnRoll, imgRollBtn);
		contentPane.add(btnRoll, 1);
		
 // Create the playerPanels and add to contentPane
		for(int i = 1; i <= numPlayers; i++){
			PlayerPanel pp = new PlayerPanel(playerColor[i-1], ppWidth, ppHeight, i);
			pp.setLocation((this.getWidth() - screenPadding) - (((numPlayers + 1) - i) * (screenPadding + ppWidth)), (int) (displayHeight / 3.8));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			pp.setVisible(true);
			contentPane.add(pp, 1);
			playerPanels[i-1] = pp;
		}
		
		int currentYPos = 0;
		
 // Create the upper scorecard
		JCheckBox[] upperScoreCheckboxes = new JCheckBox[UPPER_SCORE_NAMES.length];
		for(int i = 0; i < upperScoreCheckboxes.length; i++){
			setScoreLabels(upperScoreCheckboxes, UPPER_SCORE_NAMES, i, currentYPos);
			final int index = i;
			upperScoreCheckboxes[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					playerPanels[activePlayer].upperScoresArray[index].setText(Integer.toString(index));
					endTurn();
				}
			});
			currentYPos++;
		}
		
		JLabel[] upperScoreLabels = new JLabel[UPPER_LABEL_NAMES.length];
		for(int i = 0; i < UPPER_LABEL_NAMES.length; i++){
			setScoreLabels(upperScoreLabels, UPPER_LABEL_NAMES, i, currentYPos);
			upperScoreLabels[i].setLocation(playerPanels[0].getLocation().x - upperScoreLabels[i].getWidth() - screenPadding, upperScoreLabels[i].getY());
			currentYPos++;
		}
		
 // Create the lower scorecard
		JCheckBox[] lowerScoreCheckboxes = new JCheckBox[LOWER_SCORE_NAMES.length];
		for(int i = 0; i < lowerScoreCheckboxes.length; i++){
			setScoreLabels(lowerScoreCheckboxes, LOWER_SCORE_NAMES, i, currentYPos);
			final int index = i;
			lowerScoreCheckboxes[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					playerPanels[activePlayer].lowerScoresArray[index].setText(Integer.toString(index));
					endTurn();
				}
			});
			currentYPos++;
		}
		
		JLabel[] lowerScoreLabels = new JLabel[LOWER_LABEL_NAMES.length];
		for(int i = 0; i < LOWER_LABEL_NAMES.length; i++){
			setScoreLabels(lowerScoreLabels, LOWER_LABEL_NAMES ,i, currentYPos);
			lowerScoreLabels[i].setLocation(playerPanels[0].getLocation().x - lowerScoreLabels[i].getWidth() - screenPadding, lowerScoreLabels[i].getY());
			currentYPos++;
		}
		
		JLabel[] totalLabel = new JLabel[TOTAL_NAMES.length];
		for(int i = 0; i < TOTAL_NAMES.length; i++){
			setScoreLabels(totalLabel, TOTAL_NAMES ,i, currentYPos);
			totalLabel[i].setLocation(playerPanels[0].getLocation().x - totalLabel[i].getWidth() - screenPadding, totalLabel[i].getY());
			totalLabel[i].setForeground(Color.YELLOW);
			currentYPos++;
		}
	}
	
	private void setScoreLabels(JCheckBox[] array, String[] nameArray, int i, int yPos){
		array[i] = new JCheckBox(nameArray[i]);
		array[i].setSize((int) (displayWidth / 1.5), ppHeight / 19);
		array[i].setLocation(screenPadding, playerPanels[0].getY() + playerPanels[activePlayer].yPos[yPos]);
		array[i].setFont(new Font("Tahoma", Font.BOLD, 12));
		array[i].setForeground(Color.WHITE);
		array[i].setBorder(null);
		array[i].setContentAreaFilled(false);
		array[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(array[i], 0);
	}
	
	private void setScoreLabels(JLabel[] array, String[] nameArray, int i, int yPos){
		array[i] = new JLabel(nameArray[i]);
		array[i].setSize((int) (displayWidth / 1.5), ppHeight / 19);
		array[i].setHorizontalAlignment(SwingConstants.RIGHT);
		array[i].setLocation(screenPadding, playerPanels[0].getY() + playerPanels[activePlayer].yPos[yPos]);
		array[i].setFont(new Font("Tahoma", Font.BOLD, 12));
		array[i].setForeground(Color.WHITE);
		array[i].setBorder(null);
		contentPane.add(array[i], 0);
	}
	
	private void endTurn(){
		calcScore();
		setActivePlayer();
	}
	
	private void setActivePlayer(){
		activePlayer++;
		if(activePlayer == numPlayers){
			activePlayer = 0;
		}
		for(int i = 0; i < playerPanels.length; i++){
			if(i == activePlayer){
				playerPanels[i].setBorder(new TitledBorder(new LineBorder(playerColor[i], 2), "Player " + playerPanels[i].playerNum));
				playerPanels[i].setBackground(Color.WHITE);
			}
			else {
				playerPanels[i].setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY, 2), "Player " + playerPanels[i].playerNum));
				playerPanels[i].setBackground(new Color(150, 150, 150));
			}
		}
	}
	private void calcScore(){

		for(int i  = 0; i < playerPanels.length; i++){
			int upperSub = 0;
			int bonusScore = 0;
			int upperTotal = 0;
			int lowerScore = 0;
			int total = 0;
			for(int j = 0; j < playerPanels[i].upperScoresArray.length; j++){
				int add;
				if(playerPanels[i].upperScoresArray[j].getText() != ""){
					add = Integer.valueOf(playerPanels[i].upperScoresArray[j].getText()).intValue();
				}
				else add = 0;
				upperSub += add;
				if(upperSub > 10){
					bonusScore = 35;
				}
				
			}
			for(int j = 0; j < playerPanels[i].lowerScoresArray.length; j++){
				int add;
				if(playerPanels[i].lowerScoresArray[j].getText() != ""){
					add = Integer.valueOf(playerPanels[i].lowerScoresArray[j].getText()).intValue();
				}
				else add = 0;
				lowerScore += add;
				
			}
			
			upperTotal = upperSub + bonusScore;
			total = upperTotal + lowerScore;
			
			playerPanels[i].upperLabelsArray[0].setText(Integer.toString(upperSub));
			playerPanels[i].upperLabelsArray[1].setText(Integer.toString(bonusScore));
			playerPanels[i].upperLabelsArray[2].setText(Integer.toString(upperTotal));
			playerPanels[i].lowerLabelsArray[0].setText(Integer.toString(lowerScore));
			playerPanels[i].total[0].setText(Integer.toString(total));
		}
	}
	
	public static Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	public static void buttonImage(JButton button, Image image){
		button.setBorder(null);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		image = getScaledImage(image, button.getWidth(), button.getHeight());
		button.setIcon(new ImageIcon(image));
		button.setPressedIcon(new ImageIcon(getScaledImage(image, (int) (button.getWidth() * .8), (int) (button.getHeight() * .8))));
	}
}
