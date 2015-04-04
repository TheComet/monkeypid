package ch.fhnw.ht.eit.pro2.team3.monkeypid;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GraphDisplayPanel extends JPanel implements ActionListener {

	
	private JCheckBox cbPhasengangmethode = new JCheckBox("Phasengangmethode", true);
	private JCheckBox cbFaustformel1a = new JCheckBox("Faustformel 1(XX%)", true);
	private JCheckBox cbFaustformel1b = new JCheckBox("Faustformel 1(X%)", true);
	private JCheckBox cbFaustformel2a = new JCheckBox("Faustformel 2(XX%)", true);
	private JCheckBox cbFaustformel2b = new JCheckBox("Faustformel 2(X%)", true);
	private JCheckBox cbFaustformel3a = new JCheckBox("Faustformel 3(XX%)", true);
	private JCheckBox cbFaustformel3b = new JCheckBox("Faustformel 3(X%)", true);
	private JCheckBox cbFaustformel4a = new JCheckBox("Faustformel 4(XX%)", true);
	private JCheckBox cbFaustformel4b = new JCheckBox("Faustformel 4(X%)", true);
	private JCheckBox cbFaustformel5a = new JCheckBox("Faustformel 5(XX%)", true);
	private JCheckBox cbFaustformel5b = new JCheckBox("Faustformel 5(X%)", true);


	public GraphDisplayPanel(Controller controller) {
		super(new FlowLayout(FlowLayout.LEADING));

		add(cbPhasengangmethode);
		add(cbFaustformel1a);
		add(cbFaustformel1b);
		add(cbFaustformel2a);
		add(cbFaustformel2b);
		add(cbFaustformel3a);
		add(cbFaustformel3b);
		add(cbFaustformel4a);
		add(cbFaustformel4b);
		add(cbFaustformel5a);
		add(cbFaustformel5b);

		/*
		add(cbPhasengangmethode, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel1a, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel1b, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel2a, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel2b, new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel3a, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel3b, new GridBagConstraints(3, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel4a, new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel4b, new GridBagConstraints(4, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel5a, new GridBagConstraints(5, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		add(cbFaustformel5b, new GridBagConstraints(5, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));*/
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
