package yahtzee;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.EmptyBorder;

public class PlayerSelect extends JDialog {

	private static final long serialVersionUID = 1L;

	private JLayeredPane contentPane;
	private static Image pSelectTitle = new ImageIcon(ClassLoader.getSystemResource("pSelectTitle.png")).getImage();
	private static Image pSelect1 = new ImageIcon(ClassLoader.getSystemResource("pSelect1.png")).getImage();
	private static Image pSelect2 = new ImageIcon(ClassLoader.getSystemResource("pSelect2.png")).getImage();
	private static Image pSelect3 = new ImageIcon(ClassLoader.getSystemResource("pSelect3.png")).getImage();
	private static Image pSelect4 = new ImageIcon(ClassLoader.getSystemResource("pSelect4.png")).getImage();
	private static Image[] buttonImgs = {pSelect1, pSelect2, pSelect3, pSelect4};
	private static JButton[] buttons = new JButton[4];
	public static int numPlayers;
	private static PlayerSelect frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerSelect frame = new PlayerSelect();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PlayerSelect() {
		
		frame = this;		
		this.setSize(250,180);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getWidth() /2, dim.height / 2 - this.getHeight() / 2);
		
		contentPane = new JLayeredPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel background = new JLabel();
		background.setSize(this.getWidth(), this.getHeight());
		background.setIcon(new ImageIcon(YahtzeeDynamic.getScaledImage(YahtzeeDynamic.imgFelt, this.getWidth(), this.getHeight())));
		background.setVisible(true);
		contentPane.add(background, 1);
		
		for(int i = 0; i < buttons.length; i++){
			buttons[i] = new JButton();
			setButton(buttons[i], i, i * 55, i + 1);
		}
		
		JButton lblHowManyPlayers = new JButton();
		lblHowManyPlayers.setBounds(10, 10, 215, 70);
		YahtzeeDynamic.buttonImage(lblHowManyPlayers, pSelectTitle);
		contentPane.add(lblHowManyPlayers, 1);
	}
	
	public void setButton(JButton button, int image, int YPos, int players){
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numPlayers = players;
				System.out.println("Number of players selected: " + numPlayers);
				frame.dispose();
			}
		});
		button.setBounds(15 + YPos, 95, 35, 35);
		YahtzeeDynamic.buttonImage(button, buttonImgs[image]);
		button.setVisible(true);
		contentPane.add(button, 0);
	}
}
