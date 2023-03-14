package edu.najah.cap;


import edu.najah.cap.ex.EditorSaveException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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

	public JEditorPane TP;//Text Panel
	public JMenuBar menu;//Menu
	public JMenuItem copy, paste, cut, move;
	public boolean changed = false;
	protected File file;
	private static final String SAVE = "Save file";
	private static final String CANNOT_WRITE="Cannot write file!";
	
	private String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};
	
	protected JMenu jmfile;

	public Editor() {
		//Editor the name of our application
		super("Editor");
		TP = new JEditorPane();
		// center means middle of container.
		add(new JScrollPane(TP), "Center");
		TP.getDocument().addDocumentListener(this);

		menu = new JMenuBar();
		setJMenuBar(menu);
		BuildMenu();
		//The size of window
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void BuildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {
		jmfile = new JMenu("File");
		jmfile.setMnemonic('F');
		menu.add(jmfile);
		JMenuItem n = new JMenuItem(actions[2]);
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		jmfile.add(n);
		JMenuItem open = new JMenuItem(actions[0]);
		jmfile.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem(actions[1]);
		jmfile.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveas = new JMenuItem(actions[5]);
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		jmfile.add(saveas);
		saveas.addActionListener(this);
		JMenuItem quit = new JMenuItem(actions[4]);
		jmfile.add(quit);
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}

	private void buildEditMenu() {
		JMenu edit = new JMenu(actions[3]);
		menu.add(edit);
		edit.setMnemonic('E');
		// cut
		cut = new JMenuItem("Cut");
		cut.addActionListener(this);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cut.setMnemonic('T');
		edit.add(cut);
		// copy
		copy = new JMenuItem("Copy");
		copy.addActionListener(this);
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		edit.add(copy);
		// paste
		paste = new JMenuItem("Paste");
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(paste);
		paste.addActionListener(this);


		// find
		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic('F');
		find.addActionListener(this);
		edit.add(find);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		// select all
		JMenuItem sall = new JMenuItem("Select All");
		sall.setMnemonic('A');
		sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		sall.addActionListener(this);
		edit.add(sall);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(actions[4])) {
			System.exit(0);
		} else if (action.equals(actions[0])) {
			loadFile();
		} else if (action.equals(actions[1])) {
			//Save file
			int ans = 0;
			if (changed) {
				// 0 means yes and no option, 2 Used for warning messages.
				ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", SAVE, 0, 2);
			}
			//1 value from class method if NO is chosen.
			if (ans != 1) {
				if (file == null) {
					saveAs(actions[1]);
				} else {
					String text = TP.getText();
					LOGGER.info(text);
					try (PrintWriter writer = new PrintWriter(file);){
						if (!file.canWrite())
							throw new EditorSaveException(CANNOT_WRITE);
						writer.write(text);
						changed = false;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} else if (action.equals(actions[2])) {
			//New file 
			if (changed) {
				//Save file 
					// 0 means yes and no option, 2 Used for warning messages.
					int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", SAVE,
							0, 2);
					//1 value from class method if NO is chosen.
					if (ans == 1)
						return;

				if (file == null) {
					saveAs(actions[1]);
					return;
				}
				String text = TP.getText();
				LOGGER.info(text);
				try (PrintWriter writer = new PrintWriter(file);){
					if (!file.canWrite())
						throw new Exception(CANNOT_WRITE);
					writer.write(text);
					changed = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			file = null;
			TP.setText("");
			changed = false;
			setTitle("Editor");
		} else if (action.equals(actions[5])) {
			saveAs(actions[5]);
		} else if (action.equals("Select All")) {
			TP.selectAll();
		} else if (action.equals("Copy")) {
			TP.copy();
		} else if (action.equals("Cut")) {
			TP.cut();
		} else if (action.equals("Paste")) {
			TP.paste();
		} else if (action.equals("Find")) {
			FindDialog find = new FindDialog(this, true);
			find.showDialog();
		}
	}


	private void loadFile() {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setMultiSelectionEnabled(false);
		try {
			int result = dialog.showOpenDialog(this);
			if (result == 1) {// 1 value if cancel is chosen.
				return;
			}
			if (result == 0) {// value if approve (yes, ok) is chosen.
				if (changed){
					//Save file
						int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", SAVE,
								0, 2);//0 means yes and no question and 2 mean warning dialog
						if (ans == 1)// no option
							return;


					if (file == null) {
						saveAs(actions[1]);
						return;
					}
					String text = TP.getText();
					LOGGER.info(text);
					try {
						PrintWriter writer = new PrintWriter(file);
						if (!file.canWrite())
							throw new Exception(CANNOT_WRITE);
						writer.write(text);
						changed = false;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					LOGGER.info("No Change");
					return;
				}
				file = dialog.getSelectedFile();
				String rs = readFile(file);
				TP.setText(rs);
				changed = false;
				setTitle("Editor - " + file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e, "Error", 0);//0 means show Error Dialog
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
			JOptionPane.showMessageDialog(null, "Cannot read file!", "Error!", 0);//0 means show Error Dialog
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
		writer.write(TP.getText());
		changed = false;
		setTitle("Editor - " + file.getName());
	}

	private static PrintWriter getWriter(File file) {
		try {
			return new PrintWriter(file);
		} catch (Exception e){
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