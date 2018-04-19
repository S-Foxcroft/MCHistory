package ml.javadev.app.mchistory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ResultUI extends JDialog {
	private JTextPane textPane;

	/**
	 * Create the dialog.
	 */
	public ResultUI() {
		setTitle("Username Results");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			textPane = new JTextPane();
			getContentPane().add(textPane, BorderLayout.CENTER);
			textPane.setEditable(false);
			textPane.setContentType("text/html");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						UIForm.result.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	public void updateContent(String results){
		textPane.setText(results);
	}
}
