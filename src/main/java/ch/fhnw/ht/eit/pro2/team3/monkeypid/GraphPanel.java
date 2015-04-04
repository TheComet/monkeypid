package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import javax.swing.*;


public class GraphPanel extends JPanel implements ActionListener {

	// Eingabefeld Ks Tu Tg
	private JLabel lbEingabeTitel = new JLabel(
			"Darstellung des Graphen");
	

	public GraphPanel(Controller controller) {
		super(new GridBagLayout());


	}

	/*public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
							.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("TopView");
				frame.getContentPane().add(new GraphDisplayPanel(null));
				frame.pack();
				frame.setVisible(true);
			}
		});
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
