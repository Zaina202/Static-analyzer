package edu.najah.cap;


import edu.najah.cap.ex.EditorException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;

import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, DocumentListener {

	private static final Logger LOGGER = Logger.getLogger(Editor.class.getName());

	public static  void main(String[] args) {
		new Editor();
	}

	static JEditorPane textPanel=new JEditorPane();
	static JMenuBar defaultMenu = new JMenuBar();
	private static final JMenuItem COPY = new JMenuItem("Copy");
	private static final JMenuItem PASTE= new JMenuItem("Paste");
	private static final JMenuItem CUT= new JMenuItem("Cut");
	private boolean changed = false;
	protected File file;
	private static final String APP_NAME = "Editor";
	private static final String MID_OF_CONTAINER = "Center";
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HIGH = 500;
	private static final String SAVE = "Save file";
	private static final String CANNOT_WRITE="Cannot write file!";
	private static final String SAVE_CHANGE="The file has changed. You want to save it?";
	private final String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};
	
	protected JMenu jmFile;

	public Editor() {
		super(APP_NAME);
		add(new JScrollPane(textPanel), MID_OF_CONTAINER);
		textPanel.getDocument().addDocumentListener(this);
		setJMenuBar(defaultMenu);
		buildMenu();
		setSize(WINDOW_WIDTH, WINDOW_HIGH);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void buildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {
		jmFile = new JMenu("File");
		jmFile.setMnemonic('F');
		defaultMenu.add(jmFile);
		JMenuItem n = new JMenuItem(actions[2]);
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		jmFile.add(n);
		JMenuItem open = new JMenuItem(actions[0]);
		jmFile.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem(actions[1]);
		jmFile.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saves = new JMenuItem(actions[5]);
		saves.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
		jmFile.add(saves);
		saves.addActionListener(this);
		JMenuItem quit = new JMenuItem(actions[4]);
		jmFile.add(quit);
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}

	private void buildEditMenu() {
		JMenu edit = new JMenu(actions[3]);
		defaultMenu.add(edit);
		edit.setMnemonic('E');
		// cut
		CUT.addActionListener(this);
		CUT.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		CUT.setMnemonic('T');
		edit.add(CUT);

		// copy
		COPY.addActionListener(this);
		COPY.setMnemonic('C');
		COPY.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		edit.add(COPY);

		// paste
		PASTE.setMnemonic('P');
		PASTE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(PASTE);
		PASTE.addActionListener(this);

		// find
		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic('F');
		find.addActionListener(this);
		edit.add(find);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		// select all
		JMenuItem sale = new JMenuItem("Select All");
		sale.setMnemonic('A');
		sale.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		sale.addActionListener(this);
		edit.add(sale);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
			case "Quit" -> System.exit(0);
			case "Open" -> loadFile();
			case "Save" -> saveFile();
			case "New" -> {
				newFile();
				file = null;
				textPanel.setText("");
				changed = false;
				setTitle(APP_NAME);
			}
			case "Save as..." -> saveAs(actions[5]);
			case "Select All" -> textPanel.selectAll();
			case "Copy" -> textPanel.copy();
			case "Cut" -> textPanel.cut();
			case "Paste" -> textPanel.paste();
			case "Find" -> {
				FindDialog find = new FindDialog(this, true);
				find.showDialog();
			}
			default -> LOGGER.warning("unknown action!!");
		}
	}
	public void saveFile(){
		int ans = 0;
		if (changed) {
			ans = JOptionPane.showConfirmDialog(null, SAVE_CHANGE, SAVE, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		if (ans != JOptionPane.INFORMATION_MESSAGE) {
			if (file == null) {
				saveAs(actions[1]);
			} else {
				String text = textPanel.getText();
				LOGGER.info(text);
				try (PrintWriter writer = new PrintWriter(file)){
					if (!file.canWrite())
						throw new EditorException(CANNOT_WRITE);
					writer.write(text);
					changed = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	public void newFile(){
		if (changed) {
			int ans = JOptionPane.showConfirmDialog(null, SAVE_CHANGE, SAVE,
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (ans == JOptionPane.INFORMATION_MESSAGE)
				return;
			if (file == null) {
				saveAs(actions[1]);
				return;
			}
			String text = textPanel.getText();
			LOGGER.info(text);
			try (PrintWriter writer = new PrintWriter(file)){
				if (!file.canWrite())
					throw new EditorException(CANNOT_WRITE);
				writer.write(text);
				changed = false;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	private void loadFile() {
		JFileChooser dialog = new JFileChooser();
		dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = dialog.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = dialog.getSelectedFile();
			String content = readFile(selectedFile);
			textPanel.setText(content);
			file = selectedFile;
			changed = false;
		}
	}

	private String readFile(File file) {
		StringBuilder rs = new StringBuilder();
		try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr)) {
			String line;
			while ((line = reader.readLine()) != null) {
				rs.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, CANNOT_WRITE, "Error!", JOptionPane.ERROR_MESSAGE);//0 means show Error Dialog
		}
		return rs.toString();
	}



	private void saveAs(String dialogTitle) {
		dialogTitle = dialogTitle.toUpperCase();
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);
		if (result != 0)//0 value if approve (yes, ok) is chosen.
			return;
		file = dialog.getSelectedFile();
		PrintWriter writer = getWriter(file);
		if(writer != null){
		writer.write(textPanel.getText());
		changed = false;
		setTitle("Editor - " + file.getName());}
	}

	private static PrintWriter getWriter(File file) {
		if (file == null) {
			throw new IllegalArgumentException("File cannot be null");
		}
		try {
			return new PrintWriter(file);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changed = true;
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		changed = true;
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		changed = true;
	}
}