package charCreator;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

//check out windowbuilder

public class CreationStation extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLayeredPane contentPane;
	
	JLabel face = new JLabel();
	int faceX, faceY;
	int buffer = 10;
	double scale = 1;
	
	private ImageIcon faceImg = new ImageIcon(ClassLoader.getSystemResource("face.png"));
	private ImageIcon reinhartBuildIconImg = new ImageIcon(ClassLoader.getSystemResource("reinhartBuildIcon.png"));
	private ImageIcon dillonBuildIconImg = new ImageIcon(ClassLoader.getSystemResource("dillonBuildIcon.png"));
	
	ImageIcon[] hairImg = new ImageIcon[5], hairIcon = new ImageIcon[5];
	JButton[] hairButton = new JButton[5];
	JLabel[] hairArray = new JLabel[5];
	
	ImageIcon[] lowerImg = new ImageIcon[5], lowerIcon = new ImageIcon[5];
	JButton[] lowerButton = new JButton[5];
	JLabel[] lowerArray = new JLabel[5];
	
	ImageIcon[] glassesImg = new ImageIcon[2], glassesIcon = new ImageIcon[2];
	JButton[] glassesButton = new JButton[2];
	JLabel[] glassesArray = new JLabel[2];

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreationStation frame = new CreationStation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CreationStation() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 600);
		contentPane = new JLayeredPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnClearLower = new JButton("Clear Lower");
		btnClearLower.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClearLower.setBounds(364, 290, 155, 23);
		contentPane.add(btnClearLower);
		buttonClearArray(btnClearLower, lowerArray);
		
		JButton btnClearUpper = new JButton("Clear Upper");
		btnClearUpper.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClearUpper.setBounds(364, 11, 155, 23);
		contentPane.add(btnClearUpper);
		buttonClearArray(btnClearUpper, hairArray);
		
		JButton btnClearGlasses = new JButton("Clear Eyes");
		btnClearGlasses.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClearGlasses.setBounds(178, 450, 150, 23);
		contentPane.add(btnClearGlasses);
		buttonClearArray(btnClearGlasses, glassesArray);
		
		setArray("hair", hairImg, hairIcon, hairArray, hairButton);
		setArray("lower", lowerImg, lowerIcon, lowerArray, lowerButton);
		setArray("glasses", glassesImg, glassesIcon, glassesArray, glassesButton);
		
		int hairXAlign = 364, hairYAlign = btnClearUpper.getY() + btnClearUpper.getHeight() + buffer;
		int hairCount = 0;
		//hair label button array
		for(int column = 0; column < 2; column++){
			for(int row = 0; row < 3; row++){
				hairButton[hairCount].setIcon(hairIcon[hairCount]);
				hairButton[hairCount].setBounds(hairXAlign, hairYAlign, 70, 70);
				hairButton[hairCount].setVisible(true);
				buttonSetSingle(hairButton[hairCount], hairArray, hairArray[hairCount]);
				contentPane.add(hairButton[hairCount]);
				hairYAlign += hairIcon[hairCount].getIconWidth() + buffer;
				hairCount += 1;
				if(hairCount == 5) {break;}
			}
			if(hairCount == 5) {hairCount = 0; hairXAlign = 364; break;}
			hairYAlign = btnClearUpper.getY() + btnClearUpper.getHeight() + buffer;
			hairXAlign += hairIcon[hairCount].getIconHeight() + buffer;
		}
		
		int lowerXAlign = 364, lowerYAlign = btnClearLower.getY() + btnClearLower.getHeight() + buffer;
		int lowerCount = 0;
		//lower label button array
		for(int column = 0; column < 2; column++){
			for(int row = 0; row < 3; row++){
				lowerButton[lowerCount].setIcon(lowerIcon[lowerCount]);
				lowerButton[lowerCount].setBounds(lowerXAlign, lowerYAlign, 70, 70);
				lowerButton[lowerCount].setVisible(true);
				buttonSetSingle(lowerButton[lowerCount], lowerArray, lowerArray[lowerCount]);
				contentPane.add(lowerButton[lowerCount]);
				lowerYAlign += 80;
				lowerCount += 1;
				if(lowerCount == 5) {lowerCount = 0; lowerXAlign = 364; break;}
			}
			if(lowerCount == 5) {lowerCount = 0; lowerXAlign = 364; break;}
			lowerYAlign = btnClearLower.getY() + btnClearLower.getHeight() + buffer;
			lowerXAlign += lowerIcon[lowerCount].getIconHeight() + buffer;
		}
		
		int glassesXAlign = btnClearGlasses.getX(), glassesYAlign = btnClearGlasses.getY() + btnClearGlasses.getHeight() + buffer;
		int glassesCount = 0;
		//lower label button array
		for(int column = 0; column < 2; column++){
			for(int row = 0; row < 1; row++){
				glassesButton[glassesCount].setIcon(glassesIcon[glassesCount]);
				glassesButton[glassesCount].setBounds(glassesXAlign, glassesYAlign, 70, 70);
				glassesButton[glassesCount].setVisible(true);
				buttonSetSingle(glassesButton[glassesCount], glassesArray, glassesArray[glassesCount]);
				contentPane.add(glassesButton[glassesCount]);
				glassesCount += 1;
				if(glassesCount == 2) {glassesCount = 0; glassesYAlign = btnClearGlasses.getY(); break;}
			}
			if(glassesCount == 2) {glassesCount = 0; glassesYAlign = btnClearGlasses.getY(); break;}
			glassesXAlign += glassesIcon[glassesCount].getIconWidth() + buffer;
		}
	
		face.setIcon(faceImg);
		face.setBackground(Color.WHITE);
		face.setBounds(0, 0, (int) (faceImg.getIconWidth() * scale), (int) (faceImg.getIconHeight() * scale));
		contentPane.add(face);
		contentPane.moveToBack(face);
		
		faceX = face.getX() + (face.getHeight() / 2);
		faceY = face.getY() + (face.getWidth() / 2);
		
		JButton reinhartBuildIcon = new JButton();
		reinhartBuildIcon.setBounds(0, 492, 70, 70);
		reinhartBuildIcon.setIcon(reinhartBuildIconImg);
		reinhartBuildIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSingle(hairArray, hairArray[4]);
				setSingle(lowerArray, lowerArray[4]);
				clearArray(glassesArray);
			}
		});
		
		JButton dillonBuildIcon = new JButton();
		dillonBuildIcon.setBounds(80, 492, 70, 70);
		dillonBuildIcon.setIcon(dillonBuildIconImg);
		dillonBuildIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSingle(hairArray, hairArray[3]);
				setSingle(lowerArray, lowerArray[3]);
				setSingle(glassesArray, glassesArray[0]);
			}
		});
		
		JLabel lblNewLabel = new JLabel("REINHART");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 475, 70, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDillon = new JLabel("DILLON");
		lblDillon.setHorizontalAlignment(SwingConstants.CENTER);
		lblDillon.setForeground(Color.BLUE);
		lblDillon.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDillon.setBounds(80, 475, 70, 14);
		contentPane.add(lblDillon);

		JButton[] icons = {reinhartBuildIcon, dillonBuildIcon};
		
		contentAdd(icons, true);		
	}
		
	public void insertIcon(JLabel frame, ImageIcon icon, int x, int y){
		frame.setIcon(icon);
		Point centerScale = new Point(faceX, faceY);
		Point placementPoint = new Point((int) (centerScale.getX() - (icon.getIconHeight()/2)), (int) (centerScale.getY() - (icon.getIconWidth()/2)));
		frame.setBounds((int) (placementPoint.getX() + x), (int) (placementPoint.getY() + y), icon.getIconWidth(), icon.getIconHeight());
		//Accomplishes below:
		//normalHair.setIcon(normalHairImg);
		//normalHair.setBounds(49, 33, 270, 202);
	}
	
	public void buttonClearArray(AbstractButton button, JLabel[] set){
			button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearArray(set);
			}
		});
	}
	
	
	public void buttonSetSingle(AbstractButton button, JLabel[] set, JLabel piece){
			button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSingle(set, piece);
			}
		});
	}
	
	public void contentAdd(Object[] array, boolean visible){
		for(Object object : array){
			((Component) object).setVisible(visible);
			contentPane.add((Component) object);
			contentPane.moveToFront((Component) object);
		}
	}
	
	public void clearArray(JLabel[] current){
		for(JLabel label : current){
			label.setVisible(false);
		}
		
	}
	public void setSingle(JLabel[] current, JLabel single){
		for(JLabel label : current){
			label.setVisible(false);
			single.setVisible(true);
		}
	}
	
	public void setArray(String set, ImageIcon[] img, ImageIcon[] icon, JLabel[] array, JButton[] button){
		for(int i = 0; i < array.length; i++){
			img[i] = new ImageIcon(ClassLoader.getSystemResource(set + (i+1) + ".png"));
			icon[i] = new ImageIcon(ClassLoader.getSystemResource(set + "Icon" + (i+1) + ".png"));
			array[i] = new JLabel();
			array[i].setIcon(img[i]);
			array[i].setBounds(face.getX(),  face.getY(),  (int) (img[i].getIconWidth() * scale), (int) (img[i].getIconHeight() * scale));
			array[i].setVisible(false);
			contentPane.add(array[i]);
			button[i] = new JButton();
		}
	}
}
