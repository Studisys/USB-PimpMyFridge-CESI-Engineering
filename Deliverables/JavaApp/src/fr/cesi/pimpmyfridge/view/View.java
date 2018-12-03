package fr.cesi.pimpmyfridge.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	

	public JLabel labelTargetTemp;
	public JLabel labelTempDHT;
	public JLabel labelTempOutside;
	public JLabel labelTempPeltier;
	public JLabel labelHumidity;
	public JLabel alertCondensation;
	public JLabel alertTempAnomaly;

	public JButton buttonTargetPlus;
	public JButton buttonTargetMinus;
	
	public LineChart chart;
	
	public View() {
		
		setBounds(100, 100, 1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("PimpMyFridge - CommandCenter");
		
		JLabel labelPeltierTemperature = new JLabel("°C Peltier (Internal)");
	
		labelTempPeltier = new JLabel("0 \u00B0C");
		labelTempPeltier.setFont(new Font("Sans Serif", Font.PLAIN, 22));
		labelTempPeltier.setForeground(new Color(0, 174, 189));
		labelTempPeltier.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labelInternalTemperature = new JLabel("°C Internal (DHT)");
		
		labelTempDHT = new JLabel("0 \u00B0C");
		labelTempDHT.setFont(new Font("Sans Serif", Font.PLAIN, 22));
		labelTempDHT.setForeground(new Color(0, 174, 189));
		labelTempDHT.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JLabel labelExternalTemperature = new JLabel("°C Externe");
		
		labelTempOutside = new JLabel("0\u00B0 C");
		labelTempOutside.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelTempOutside.setForeground(new Color(241, 61, 7));
		labelTempOutside.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labelHumidityPercent = new JLabel("% humidit\u00E9");
		
		labelHumidity = new JLabel("0 %");
		labelHumidity.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelHumidity.setBackground(new Color(66, 255, 66));
		labelHumidity.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JLabel labelTarget = new JLabel("Target");
		labelTarget.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelTargetTemp = new JLabel("0 \u00B0C");
		labelTargetTemp.setForeground(Color.ORANGE);
		labelTargetTemp.setFont(new Font("Tahoma", Font.PLAIN, 28));
		labelTargetTemp.setHorizontalAlignment(SwingConstants.CENTER);
		
		buttonTargetPlus = new JButton("+");
		buttonTargetPlus.setFont(new Font("Tahoma", Font.BOLD, 30));
		buttonTargetMinus = new JButton("-");
		buttonTargetMinus.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		alertCondensation = new JLabel("Risk of condensation");
		alertCondensation.setFont(new Font("Tahoma", Font.BOLD, 11));
		alertCondensation.setForeground(new Color(241, 61, 7));
		
		
		alertTempAnomaly = new JLabel("Temperature Anomaly");
		alertTempAnomaly.setFont(new Font("Tahoma", Font.BOLD, 11));
		alertTempAnomaly.setForeground(new Color(241, 61, 7));
		
		JPanel panelCenter = new JPanel();
		chart = new LineChart("Temperature Chart", "Internal and External Temperatures");
		ChartPanel component = new ChartPanel(chart.getJChart());
		
		panelCenter.add(component, "chart");
		
		
		JPanel panelLeft = new JPanel();
		
		panelLeft.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), null, TitledBorder.LEADING, TitledBorder.TOP));
		
		JPanel panelRight = new JPanel();
		panelRight.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), null, TitledBorder.LEADING, TitledBorder.TOP));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelCenter, GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelRight, GroupLayout.PREFERRED_SIZE, 423, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelCenter, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelRight, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelCenter.setLayout(new CardLayout(0, 0));
		
		GroupLayout gl_panelRight = new GroupLayout(panelRight);
		gl_panelRight.setHorizontalGroup(
			gl_panelRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRight.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING, false)
						.addComponent(labelTargetTemp, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelTarget, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelRight.createSequentialGroup()
							.addComponent(buttonTargetMinus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonTargetPlus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
								.addComponent(alertTempAnomaly, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
								.addComponent(alertCondensation, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panelRight.setVerticalGroup(
			gl_panelRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRight.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelRight.createSequentialGroup()
							.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
								.addComponent(labelTarget)
								.addComponent(alertTempAnomaly))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelTargetTemp, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelRight.createSequentialGroup()
									.addComponent(alertCondensation)
									.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE))))
						.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
							.addComponent(buttonTargetMinus, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addComponent(buttonTargetPlus, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panelRight.setLayout(gl_panelRight);
		
		
		GroupLayout gl_panelLeft = new GroupLayout(panelLeft);
		gl_panelLeft.setHorizontalGroup(
			gl_panelLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLeft.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(labelTempDHT, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(labelInternalTemperature, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING, false)
							.addComponent(labelTempPeltier, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(labelPeltierTemperature, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING, false)
						.addComponent(labelTempOutside, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(labelExternalTemperature, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING, false)
						.addComponent(labelHumidity, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(labelHumidityPercent, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
					.addContainerGap(308, Short.MAX_VALUE))
		);
		
		
		
		gl_panelLeft.setVerticalGroup(
			gl_panelLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLeft.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelPeltierTemperature)
						.addComponent(labelExternalTemperature)
						.addComponent(labelInternalTemperature)
						.addComponent(labelHumidityPercent))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING)
						.addComponent(labelHumidity, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
						.addComponent(labelTempOutside, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
						.addComponent(labelTempDHT, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
						.addComponent(labelTempPeltier, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
					.addContainerGap())
		);
		panelLeft.setLayout(gl_panelLeft);
		getContentPane().setLayout(groupLayout);
		
		getContentPane().setBackground(new Color(107, 106, 104));
		
	}
}
