package ml.javadev.app.mchistory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class UIForm extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	static ResultUI result;
	private static UIForm frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new UIForm();
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					frame.result = new ResultUI();
					result.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
	public UIForm() {
		setAlwaysOnTop(false);
		setResizable(false);
		setTitle("MCHistory version 1.0 by JavaDev");
		setBounds(100, 100, 571, 71);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblUsername = new JLabel("Username: ");
		panel.add(lblUsername);
		
		username = new JTextField();
		panel.add(username);
		username.setColumns(30);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Domain adjustment: files from javadev.ml now hosted at jd.ytfox.co.uk
				String uri = "http://jd.ytfox.co.uk/apps/dataStore/minecraftHistory/getData.php?arg0=" + username.getText();
				URLConnection con;
				try {
					con = new URL(uri).openConnection();
					con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
					con.connect();
					BufferedReader r  = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

					StringBuilder sb = new StringBuilder();
					String returned;
					while ((returned = r.readLine()) != null) {
					    sb.append(returned);
					}
					result.updateContent(sb.toString().replace("<br />","").replace("<br/>",""));
					username.setText("");
				}
				catch(Exception e){
					e.printStackTrace();
					result.updateContent("An error occured, please try again.");
				}
				result.setVisible(true);
			}
		});
		panel.add(btnSubmit);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane}));
	}

}
