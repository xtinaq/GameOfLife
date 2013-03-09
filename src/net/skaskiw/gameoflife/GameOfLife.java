package net.skaskiw.gameoflife;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GameOfLife extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 1L;
	private Generation generation;
	private GenerationView generationView;
	private JTextField densityField;
	private JTextField sizeField;
	private Timer timer;

	public GameOfLife() {
		initGeneration();
		initComponents();
	}

	private void initGeneration() {
		List<Rule> gameRules = new ArrayList<Rule>();
		gameRules.add(new LivingCellDiesFromUnderPopulation());
		gameRules.add(new LivingCellDiesFromOverCrowding());
		gameRules.add(new LivingCellLivesOn());
		gameRules.add(new UnbornCellIsBorn());
		generation = new Generation(gameRules);
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Game of Life");
		this.setSize(489, 498);
		initTimer();
		initGameOfLifePanel();
		initButtonPanel();
	}
	
	private void initTimer() {
		ActionListener timerEar = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				doNextGeneration();
			}
		};
		timer = new Timer(500, timerEar);		
	}

	private void initGameOfLifePanel() {
		generationView = new GenerationView(generation);
		getContentPane().add(generationView, BorderLayout.CENTER);
		generationView.setLayout(null);
	}

	private void initButtonPanel() {
		JPanel buttonArea = new JPanel();
		getContentPane().add(buttonArea, BorderLayout.NORTH);
		
		JLabel lblSize = new JLabel("Size");
		sizeField = new JTextField();
		sizeField.setHorizontalAlignment(SwingConstants.RIGHT);
		sizeField.setToolTipText("Enter an integer");
		sizeField.setText("15");
		sizeField.setColumns(3);

		JLabel lblDensity = new JLabel("Density");
		densityField = new JTextField();
		densityField.setHorizontalAlignment(SwingConstants.RIGHT);
		densityField.setToolTipText("Enter a value between 0 and 1");
		densityField.setText("0.5");
		densityField.setColumns(3);

		JButton createCommand = new JButton("Create 1st generation");
		createCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				createFirstGeneration();
			}
		});

		JButton startCommand = new JButton("Start");
		startCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timer.start();
			}
		});

		JButton stopCommand = new JButton("Stop");
		stopCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timer.stop();
			}
		});

		buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonArea.add(lblSize);
		buttonArea.add(sizeField);
		buttonArea.add(lblDensity);
		buttonArea.add(densityField);
		buttonArea.add(createCommand);
		buttonArea.add(startCommand);
		buttonArea.add(stopCommand);		
	}
	
	private double seedGenerationDensity() {
		return Double.parseDouble(densityField.getText());
	}

	private int seedGenerationSize() {
		return Integer.parseInt(sizeField.getText());
	}

	private void createFirstGeneration() {
		generation.createSeedPopulation(seedGenerationSize(),
				seedGenerationDensity());
		generationView.render(generation);
	}

	private void doNextGeneration() {
		if (!generation.isExtinct())
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
