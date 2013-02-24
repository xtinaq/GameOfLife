import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameOfLife extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private javax.swing.JButton runCommand;
	private GenerationView generationView;
	private Generation generation;

	public GameOfLife() {
		initGeneration();
		initComponents();
	}

	private void initGeneration() {
		generation = new Generation();
		generation.addRule(new LivingCellDiesFromUnderPopulation());
		generation.addRule(new LivingCellDiesFromOverCrowding());
		generation.addRule(new LivingCellLivesOn());
		generation.addRule(new UnbornCellIsBorn());
		generation.createSeedPopulation(15, 0.5);
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Game of Life");
		this.setSize(300, 400);

		generationView = new GenerationView(generation);
		getContentPane().add(generationView, BorderLayout.CENTER);

		JPanel buttonArea = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonArea.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonArea, BorderLayout.SOUTH);


		ActionListener timerEar = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runAction(evt);
			}
		};
		final Timer timer = new Timer(500, timerEar);
		
		runCommand = new JButton("Run");
		buttonArea.add(runCommand);
		runCommand.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				timer.start();
			}
		});
	}

	private void runAction(java.awt.event.ActionEvent evt) {
		if (!generation.isExtinct() && generation.sequenceNumber() < 100) 
			generationView.render(generation.next());
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GameOfLife().setVisible(true);
			}
		});
	}
}
