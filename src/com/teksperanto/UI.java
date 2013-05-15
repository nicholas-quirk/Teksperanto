package com.teksperanto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Nicholas Quirk
 *
 */
public class UI {

	// GUI Members.
	JTextArea textArea;
	JMenuBar menuBar;
	JMenu fileMenu;
	JPanel panel;
	JPanel searchPanel;
	JTextField searchField;
	JButton searchButton;
	JTextArea searchResults;

	// Helper Members.
	HashMap<String, String> eoToEn = new HashMap<String, String>();
	ArrayList<String> results = new ArrayList<String>();
	Long lastKeyPressTime = 0l;

	public UI() {

		JFrame frame = new JFrame();

		createFileMenu();

		createFileMenuChoices();

		createTextArea();

		createDictionarySearchButton();

		createSearchField();

		createSearchResults();

		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		searchPanel = new JPanel(new BorderLayout());
		searchPanel.add(searchField, BorderLayout.LINE_START);
		searchPanel.add(searchButton, BorderLayout.CENTER);

		panel = new JPanel(new BorderLayout());
		panel.add(areaScrollPane, BorderLayout.CENTER);
		panel.add(searchPanel, BorderLayout.PAGE_START);

		JScrollPane searchResultsPane = new JScrollPane(searchResults);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(searchResultsPane, BorderLayout.PAGE_END);

		frame.setJMenuBar(menuBar);
		frame.add(panel);
		frame.setSize(400, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		EsperantoSubstitutor es = new EsperantoSubstitutor(textArea,
				lastKeyPressTime);
		es.start();

		eoToEn = (new EspdicLoader()).createDictionary();
	}

	private void createSearchResults() {
		searchResults = new JTextArea();
		searchResults.setRows(5);
	}

	private void createSearchField() {
		searchField = new JTextField(25);
	}

	private void createDictionarySearchButton() {
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String searchText = searchField.getText();
				boolean exactMatch = (searchText.startsWith("'") && searchText
						.endsWith("'"));
				for (String key : eoToEn.keySet()) {
					if (exactMatch) {
						if (key.trim().equalsIgnoreCase(
								searchText.replace("'", ""))) {
							results.add(key + " : " + eoToEn.get(key));
						}
						if (eoToEn.get(key).trim()
								.equalsIgnoreCase(searchText.replace("'", ""))) {
							results.add(key + " : " + eoToEn.get(key));
						}
					} else {
						if (key.contains(searchText)) {
							results.add(key + " : " + eoToEn.get(key));
						}
						if (eoToEn.get(key).contains(searchText)) {
							results.add(key + " : " + eoToEn.get(key));
						}
					}
				}
				String sr = "";
				for (String s : results) {
					sr += s + "\n";
				}
				searchResults.setText(sr);
				results = new ArrayList<String>();
			}

		});
	}

	private void createFileMenu() {
		fileMenu = new JMenu("File");

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
	}

	private void createFileMenuChoices() {
		JMenuItem menuItemExit = new JMenuItem();
		menuItemExit.setText("Exit");
		menuItemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		fileMenu.add(menuItemExit);
	}

	private void createTextArea() {
		textArea = new JTextArea("Saluton!");
		textArea.setWrapStyleWord(true);
		textArea.setSize(400, 600);
		textArea.setLineWrap(true);
		textArea.setAutoscrolls(true);
		textArea.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				lastKeyPressTime = System.currentTimeMillis();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}
		});
	}
}
