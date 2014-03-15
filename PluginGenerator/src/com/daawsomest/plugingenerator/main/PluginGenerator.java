package com.daawsomest.plugingenerator.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.daawsomest.plugingenerator.plugin.CreateFolders;
import com.daawsomest.plugingenerator.plugin.PluginWriter;

@SuppressWarnings("serial")
public class PluginGenerator extends JPanel {

	static JFrame frame;
	boolean enableCommand1 = false;
	PluginWriter pw;

	String name, dir;
	int version;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PluginGenerator() {

		JTabbedPane tabPane = new JTabbedPane();

		tabPane.setPreferredSize(new Dimension(640, 600));

		/*
		 * Panel 1 components
		 */
		JPanel panel1 = new JPanel();

		final JTextArea textArea1 = new JTextArea();
		panel1.setLayout(null);
		textArea1.setEditable(false);
		textArea1.setWrapStyleWord(true);
		textArea1.setLineWrap(true);
		textArea1.setBounds(20, 20, 600, 180);
		textArea1.setText("Daawsomest's Bukkit Plugin Generator requires the Java Development Kit (JDK) to be installed on your computer and a system path variable pointing to it. "
				+ "NOTE: The JDK is not included in the standard Java download (JRE) \n"
				+ "\n"
				+ "To install the JDK simply click the \"Instal JDK\" button. Simply follow in instuctions in the install wizard that opens.\n"
				+ "\n"
				+ "NOTE: Ensure the JDK installs in the following directory \"C:\\Program Files\\Java\\jdk1.7.0_51\\bin\" \n"
				+ "\n"
				+ "After you have installed the JDK click the \"Setup JDK Variable\" button and a window will open. When it is complete it will say \"Successfully Setup JDK Path\". "
				+ "Simply press any key to close the window. \n"
				+ "\n"
				+ "Now you can click on the \"Generator\" tab and begin creating your plugin!");

		JButton btnInstallJDK = new JButton("Install JDK");
		btnInstallJDK.setBounds(20, 300, 600, 50);
		
		final JButton btnSetupVariable = new JButton("Setup JDK Variable");
		btnSetupVariable.setBounds(20, 400, 600, 50);
		btnSetupVariable.setEnabled(false);
		
		panel1.add(textArea1);
		panel1.add(btnInstallJDK);
		panel1.add(btnSetupVariable);

		/*
		 * Panel 2 components
		 */
		JPanel panel2 = new JPanel();

		final JLabel lblName = new JLabel("Plugin Name:");
		lblName.setBounds(20, 20, 100, 20);

		final JTextField txtName = new JTextField("MyPlugin");
		txtName.setBounds(100, 20, 120, 20);

		final JLabel lblVersion = new JLabel("Plugin Version:");
		lblVersion.setBounds(250, 20, 120, 20);

		final JTextField txtVersion = new JTextField("1");
		txtVersion.setBounds(340, 20, 100, 20);

		final JButton btnStart = new JButton("OK");
		btnStart.setBounds(460, 20, 160, 20);

		JSeparator split = new JSeparator();
		split.setBounds(0, 65, 640, 3);

		final JLabel lblEventListener = new JLabel("Event Listener");
		lblEventListener.setBounds(20, 90, 200, 20);
		lblEventListener.setEnabled(false);

		final JComboBox listListeners = new JComboBox(new String[] { "On Commmand", "On Server Join" });
		listListeners.setBounds(20, 120, 120, 20);
		listListeners.setEnabled(false);

		final JTextField txtEventArg1 = new JTextField("Command");
		txtEventArg1.setBounds(20, 150, 120, 20);
		txtEventArg1.setEnabled(false);

		final JComboBox listEventAction = new JComboBox(new String[] { "Message", "Broadcast", "Teleport", "Give item" });
		listEventAction.setBounds(20, 180, 120, 20);
		listEventAction.setEnabled(false);
		listEventAction.setEnabled(false);

		final JTextField txtActionArg1 = new JTextField("Message");
		txtActionArg1.setBounds(20, 210, 120, 20);
		txtActionArg1.setEnabled(false);

		final JTextField txtActionArg2 = new JTextField("Message");
		txtActionArg2.setBounds(20, 240, 120, 20);
		txtActionArg2.setVisible(false);

		final JTextField txtActionArg3 = new JTextField("Message");
		txtActionArg3.setBounds(20, 270, 120, 20);
		txtActionArg3.setVisible(false);

		final JButton btnAddEvent = new JButton("Add Event >");
		btnAddEvent.setBounds(225, 150, 100, 50);
		btnAddEvent.setEnabled(false);

		final JTextArea addedEventsList = new JTextArea();
		final JScrollPane scrollPane = new JScrollPane(addedEventsList);
		scrollPane.setBounds(380, 120, 200, 140);
		addedEventsList.setEditable(false);

		final JButton btnGenerate = new JButton("Generate Plugin!");
		btnGenerate.setBounds(20, 520, 600, 40);
		btnGenerate.setEnabled(false);

		/*
		 * Add components to panel 2
		 */
		panel2.setLayout(null);

		panel2.add(split);

		panel2.add(lblName);
		panel2.add(lblVersion);
		panel2.add(lblEventListener);

		panel2.add(txtName);
		panel2.add(txtVersion);
		panel2.add(txtEventArg1);
		panel2.add(txtActionArg1);
		panel2.add(txtActionArg2);
		panel2.add(txtActionArg3);

		panel2.add(btnStart);
		panel2.add(btnAddEvent);
		panel2.add(btnGenerate);

		panel2.add(listListeners);
		panel2.add(listEventAction);

		panel2.add(scrollPane);

		/*
		 * Set tabs
		 */
		tabPane.addTab("Install", panel1);
		tabPane.addTab("Generator", panel2);
		add(tabPane);

		/*
		 * List/Combobox Listeners
		 */
		listListeners.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if onCommand
				if (listListeners.getSelectedIndex() == 0) {
					txtEventArg1.setVisible(true);
					listEventAction.setVisible(true);
					// if onJoin
				} else if (listListeners.getSelectedIndex() == 1) {
					txtEventArg1.setVisible(false);
					listEventAction.setVisible(true);
				}
			}
		});

		listEventAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// if message player
				if (listEventAction.getSelectedIndex() == 0) {
					txtActionArg1.setText("message");
					txtActionArg1.setVisible(true);
					txtActionArg2.setVisible(false);
					txtActionArg3.setVisible(false);
					// if message server
				} else if (listEventAction.getSelectedIndex() == 1) {
					txtActionArg1.setText("message");
					txtActionArg1.setVisible(true);
					txtActionArg2.setVisible(false);
					txtActionArg3.setVisible(false);
					// if teleport
				} else if (listEventAction.getSelectedIndex() == 2) {
					txtActionArg1.setText("x");
					txtActionArg2.setText("y");
					txtActionArg3.setText("z");
					txtActionArg1.setVisible(true);
					txtActionArg2.setVisible(true);
					txtActionArg3.setVisible(true);
					// if give item
				} else if (listEventAction.getSelectedIndex() == 3) {
					txtActionArg1.setText("itemID");
					txtActionArg2.setText("amount");
					txtActionArg3.setText("");
					txtActionArg1.setVisible(true);
					txtActionArg2.setVisible(true);
					txtActionArg3.setVisible(false);
				}
			}
		});

		/*
		 * Button Listeners
		 */
		
		btnInstallJDK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				URL url = PluginGenerator.class.getProtectionDomain().getCodeSource().getLocation();
				String urlDecode = null;

				try {
					urlDecode = URLDecoder.decode(url.toString(), "UTF-8");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				String jdkDir = urlDecode.substring(6, urlDecode.length() - 20);
				
				ProcessBuilder pb = new ProcessBuilder(new String[] { "cmd.exe", "/C", jdkDir + "JDKInstaller.exe" });
				try {
					Process p = pb.start();
					p.waitFor();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				btnSetupVariable.setEnabled(true);

			}
		});
		
		btnSetupVariable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				URL url = PluginGenerator.class.getProtectionDomain().getCodeSource().getLocation();
				String urlDecode = null;
				
				try {
					urlDecode = URLDecoder.decode(url.toString(), "UTF-8");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				String jdkDir = urlDecode.substring(6, urlDecode.length() - 20);
				
				ProcessBuilder pb = new ProcessBuilder(new String[] { "cmd.exe", "/C", "set PATH=C:\\Program Files\\Java\\jdk1.7.0_51\bin" });
				try {
					Process p = pb.start();
					p.waitFor();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				name = txtName.getText();
				version = Integer.parseInt(txtVersion.getText());

				URL url = PluginGenerator.class.getProtectionDomain().getCodeSource().getLocation();
				String urlDecode = null;

				try {
					urlDecode = URLDecoder.decode(url.toString(), "UTF-8");
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				dir = urlDecode.substring(6, urlDecode.length() - 20);

				CreateFolders cf = new CreateFolders(dir, name);
				pw = new PluginWriter(name, version, dir);

				try {
					pw.beginPlugin();
					pw.beginYml();
				} catch (IOException e) {
					e.printStackTrace();
				}

				lblName.setEnabled(false);
				lblVersion.setEnabled(false);
				txtName.setEnabled(false);
				txtVersion.setEnabled(false);
				btnStart.setEnabled(false);

				lblEventListener.setEnabled(true);
				txtEventArg1.setEnabled(true);
				txtActionArg1.setEnabled(true);
				txtActionArg2.setEnabled(true);
				txtActionArg3.setEnabled(true);
				listListeners.setEnabled(true);
				listEventAction.setEnabled(true);
				btnAddEvent.setEnabled(true);
				btnGenerate.setEnabled(true);
			}
		});

		btnGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					pw.generateCommandMethod();
					pw.endPlugin();
					pw.endYml();
					pw.writeCompilerBatch();

					Process p = Runtime.getRuntime().exec(dir + "compiler.bat");
					p.waitFor();
					
					
			        
					File compFile = new File(dir + "compiler.bat");
					compFile.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(frame, "Plugin Generated!");
				System.exit(0);

			}
		});

		btnAddEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String eventType = "error", action = "error";

				try {

					// Command Event
					if (listListeners.getSelectedIndex() == 0) {
						eventType = "Command: " + txtEventArg1.getText();
						// Message action
						if (listEventAction.getSelectedIndex() == 0) {
							pw.addCommand(txtEventArg1.getText(), "message", txtActionArg1.getText(), null, null);
							action = "Message: " + txtActionArg1.getText();
						}
						// Broadcast
						if (listEventAction.getSelectedIndex() == 1) {
							pw.addCommand(txtEventArg1.getText(), "broadcast", txtActionArg1.getText(), null, null);
							action = "Broadcast: " + txtActionArg1.getText();
						}
						// Teleport
						if (listEventAction.getSelectedIndex() == 2) {
							pw.addCommand(txtEventArg1.getText(), "teleport", txtActionArg1.getText(), txtActionArg2.getText(),
									txtActionArg3.getText());
							action = "Teleport: " + txtActionArg1.getText() + ", " + txtActionArg2.getText() + ", " + txtActionArg2.getText();
						}
						// Give Item
						if (listEventAction.getSelectedIndex() == 3) {
							pw.addCommand(txtEventArg1.getText(), "giveitem", txtActionArg1.getText(), txtActionArg2.getText(), null);
							action = "Give: " + txtActionArg1.getText() + " " + txtActionArg2.getText();
						}
					}
					// Join Event
					if (listListeners.getSelectedIndex() == 1) {
						eventType = " OnPlayerJoin";
						pw.beginPlayerJoinEvent();

						// Message action
						if (listEventAction.getSelectedIndex() == 0) {
							pw.message(txtActionArg1.getText());
							action = "Message: " + txtActionArg1.getText();
						}
						// Broadcast
						if (listEventAction.getSelectedIndex() == 1) {
							pw.broadcast(txtActionArg1.getText());
							action = "Broadcast: " + txtActionArg1.getText();
						}
						// Teleport
						if (listEventAction.getSelectedIndex() == 2) {
							pw.teleportPlayer(txtActionArg1.getText(), txtActionArg2.getText(), txtActionArg3.getText());
							action = "Teleport: " + txtActionArg1.getText() + ", " + txtActionArg2.getText() + ", " + txtActionArg2.getText();
						}
						// Give Item
						if (listEventAction.getSelectedIndex() == 3) {
							pw.giveItem(txtActionArg1.getText(), txtActionArg2.getText());
							action = "Give: " + txtActionArg1.getText() + " " + txtActionArg2.getText();
						}
						pw.endPlayerJoinEvent();
					}
					addedEventsList.append(eventType + " > " + action + "\n");

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	public static void main(String[] args) throws HeadlessException, URISyntaxException, UnsupportedEncodingException {

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		frame = new JFrame("Daawsomest's Bukkit Plugin Generator");
		frame.getContentPane().add(new PluginGenerator());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 640);
		frame.setVisible(true);
	}

}