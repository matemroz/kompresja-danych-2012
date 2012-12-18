package pw.we.kd.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pw.we.kd.img.GrayScaleImage;
import pw.we.kd.wavelets.Daub;
import pw.we.kd.wavelets.Haar;

public class CompressionView extends JFrame {

	private static final long serialVersionUID = -890721668833012744L;

	public CompressionView() {
		initUI();
	}

	private void initUI() {
		setTitle("Wavelet Compressor");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		transformBtn = new JButton("process");
		transformBtn.setBounds(390, 300, 120, 30);
		transformBtn.addActionListener(new transformBtnListener());
		resetBtn = new JButton("reset");
		resetBtn.setBounds(390, 350, 120, 30);
		resetBtn.addActionListener(new resetBtnListener());
		String transType[] = { "transformation", "inversion" };
		procTypeCB = new JComboBox(transType);
		procTypeCB.setBounds(390, 40, 120, 30);
		String waveletType[] = { "haar", "daubechie" };
		waveletCB = new JComboBox(waveletType);
		waveletCB.setBounds(390, 80, 120, 30);
		String startType[] = { "256", "128", "64", "32", "16", "8", "4", "2",
				"1" };
		startCB = new JComboBox(startType);
		startCB.setBounds(390, 120, 120, 30);
		String endType[] = { "1", "2", "4", "8", "16", "32", "64", "128", "256" };
		endCB = new JComboBox(endType);
		endCB.setBounds(390, 160, 120, 30);
		compressionDegreeSlider = new JSlider(JSlider.HORIZONTAL, 1, 4, 1);
		compressionDegreeSlider.setMajorTickSpacing(1);
		compressionDegreeSlider.setMinorTickSpacing(1);
		compressionDegreeSlider.setPaintTicks(true);
		compressionDegreeSlider.setPaintLabels(true);
		compressionDegreeSlider.setBounds(360, 330, 180, 50);
		Font font = new Font("Serif", Font.ITALIC, 15);
		compressionDegreeSlider.setFont(font);
		add(transformBtn);
		//add(resetBtn);
		add(procTypeCB);
		add(waveletCB);
		//add(compressionDegreeSlider);
		add(startCB);
		add(endCB);
		addImage("lenagray.bmp", imgPanelLeft, 50, 100, 256, 256);
		addImage("lenagray.bmp", imgPanelRight, 590, 100, 256, 256);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException ex) {
			System.out.println("Unable to load native look and feel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		setVisible(true);
		revalidate();
		repaint();
	}

	private void addImage(String imgPath, JPanel panel, int x, int y, int w,
			int h) {
		panel = new ImageViewer((imgPath));
		setLayout(null);
		panel.setBounds(x, y, w, h);
		this.add(panel);
		panel.revalidate();
		panel.repaint();
	}

	class resetBtnListener implements ActionListener {
		resetBtnListener() {
		}

		public void actionPerformed(ActionEvent e) {
			initUI();
		}
	}

	class transformBtnListener implements ActionListener {
		transformBtnListener() {
		}

		public void actionPerformed(ActionEvent e) {
			Haar haar = new Haar();
			Daub daub = new Daub();
			if (e.getActionCommand().equals("process")) {
				String processType = procTypeCB.getSelectedItem().toString();
				String waveletTransType = waveletCB.getSelectedItem()
						.toString();
				int start = Integer.parseInt(startCB.getSelectedItem()
						.toString());
				int end = Integer.parseInt(endCB.getSelectedItem().toString());
				if (processType.equals("transformation")
						&& waveletTransType.equals("haar")) {
					GrayScaleImage img = new GrayScaleImage("lenagray.bmp");
					double[][] mat = img.getData();
					haar.transform(mat, start, end);
					String outputFilename = "lenagray_tr.bmp";
					img.save(outputFilename);
					addImage(outputFilename, imgPanelRight, 590, 100, 256, 256);
					System.out.println("transformacja: haar");
				} else if (processType.equals("transformation")
						&& waveletTransType.equals("daubechie")) {
					GrayScaleImage img = new GrayScaleImage("lenagray.bmp");
					double[][] mat = img.getData();
					daub.transform(mat, start, end);
					String outputFilename = "lenagray_daub_tr.bmp";
					img.save(outputFilename);
					addImage(outputFilename, imgPanelRight, 590, 100, 256, 256);
					System.out.println("transformacja: daubechie");
				} else if (processType.equals("inversion")
						&& waveletTransType.equals("haar")) {
					GrayScaleImage img = new GrayScaleImage("lenagray_tr.bmp");
					double[][] mat = img.getData();
					haar.invTransform(mat, start, end);
					String outputFilename = "lenagray_inv.bmp";
					img.save(outputFilename);
					addImage(outputFilename, imgPanelLeft, 50, 100, 256, 256);
					System.out.println("inwersja: haar");
				} else if (processType.equals("inversion")
						&& waveletTransType.equals("daubechie")) {
					GrayScaleImage img = new GrayScaleImage(
							"lenagray_daub_tr.bmp");
					double[][] mat = img.getData();
					daub.invTransform(mat, start, end);
					String outputFilename = "lenagray_daub_inv.bmp";
					img.save(outputFilename);
					addImage(outputFilename, imgPanelLeft, 50, 100, 256, 256);
					System.out.println("inwersja: daubechie");
				} else {
					System.err
							.println("Chosen inappropriate process configuration");
				}
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CompressionView ex = new CompressionView();
				ex.setVisible(true);
			}
		});
	}

	private ImageViewer imgPanelLeft;
	private ImageViewer imgPanelRight;
	private JButton transformBtn;
	private JButton resetBtn;
	private JComboBox waveletCB;
	private JComboBox procTypeCB;
	private JComboBox startCB;
	private JComboBox endCB;
	private JSlider compressionDegreeSlider;
}
