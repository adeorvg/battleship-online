package Gra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;



public class MainMenuFrame extends JFrame implements ActionListener
{
	private ClientSocket client;

	private Dimension mainMenuFrameSize;

	private JLabel nameOfGame;

	private JPanel mainMenuPanel;

	private JButton newGameBtn;

	private JButton aboutGameBtn;

	private JButton highScoreBtn;

	private JButton quitBtn;

	private JoinGameWindow joiningGameWindow;

	private Instruction instruction;

	private String ipAddress="localhost";

	private JTextArea addressInput;


	public MainMenuFrame()
	{
		setTitle("Battleships");
		mainMenuFrameSize = new Dimension(800, 580);
		setPreferredSize(mainMenuFrameSize);
		pack();
		setLocationRelativeTo(null);
		createUI();
	}

	private void createUI()
	{
		mainMenuPanel = new JPanel();
		nameOfGame = new JLabel("BATTLESHIPS!", SwingConstants.CENTER);
		nameOfGame.setFont(new Font(Font.MONOSPACED,Font.BOLD, 40));
		mainMenuPanel.add(nameOfGame);
		newGameBtn = new JButton("Nowa Gra!");
		newGameBtn.addActionListener(this);
		newGameBtn.setFocusable(false);
		newGameBtn.setActionCommand("Nowa Gra");
		mainMenuPanel.add(newGameBtn);
		aboutGameBtn = new JButton("O grze");
		aboutGameBtn.addActionListener(this);
		aboutGameBtn.setFocusable(false);
		aboutGameBtn.setActionCommand("O grze");
		mainMenuPanel.add(aboutGameBtn);
		highScoreBtn = new JButton("Ranking graczy");
		highScoreBtn.addActionListener(this);
		highScoreBtn.setFocusable(false);
		highScoreBtn.setActionCommand("Ranking");
		mainMenuPanel.add(highScoreBtn);
		quitBtn = new JButton("Wyj¶cie");
		quitBtn.addActionListener(this);
		quitBtn.setFocusable(false);
		quitBtn.setActionCommand("Wyj¶›cie");
		mainMenuPanel.add(quitBtn);
		mainMenuPanel.setBackground(new Color(8,127,198));
		mainMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 50));
		addressInput = new JTextArea();
		addressInput.setPreferredSize(new Dimension(150,25));
		addressInput.setEditable(true);
		mainMenuPanel.add(addressInput);
		getContentPane().add(mainMenuPanel);
	}

	protected void makeCreatingGameWindow()
	{

		List<String> listID = new ArrayList<String>();
		client.openConnection();
		/*Message listOfGameID = new Message(false);
		client.sendMessage(listOfGameID);
		while(client.receiveMessage()!="error")
		{
			listID.add(client.receiveMessage());
		}
		String[] arrayOfID = new listID.toArray(new String[listID.size()]);*/
		System.out.println(String.valueOf(client.getId()));
		joiningGameWindow = new JoinGameWindow(client);
		joiningGameWindow.setVisible(true);
	}


    protected void makeHighScoresWindow() throws IOException
    {
    	//highWindow = new HighScoresWindow();
    	//highWindow.setVisible(true);
    }


    protected void  makeInstruction() throws IOException
    {
    	instruction = new Instruction(client);
    	instruction.setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String komenda = actionEvent.getActionCommand();
        switch (komenda) {
        case "Nowa Gra":
            this.setVisible(false);


            ipAddress = addressInput.getText();

		       this.client = new ClientSocket(ipAddress);

            makeCreatingGameWindow();

            break;

        case "Ranking":

        	this.setVisible(false);
        	 try {
				makeHighScoresWindow();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "BÂ³Â¹d odczytu listy");
				e.printStackTrace();
			}

        	break;

        case "O grze":

        	this.setVisible(false);
        	 try {
				makeInstruction();
			} catch (IOException e) {
				e.printStackTrace();
			}

        	break;

        case "Wyj¶›cie":
                Object[] options = {"Tak", "Nie"};
                int reply = JOptionPane.showOptionDialog(this, "Czy chcesz opuÅ›ciÄ‡ grÄ™?", "WyjÅ›cie z gry",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (reply == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;

            default:
                break;
        }
    }

}
