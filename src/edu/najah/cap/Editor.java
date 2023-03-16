package edu.najah.cap;


import edu.najah.cap.ex.EditorException;
import edu.najah.cap.ex.ExceptionType;

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
	private static final String SAVE = "Save file";
	private static final String CANNOT_WRITE="Cannot write file!";
	private static final String SAVE_CHANGE="The file has changed. You want to save it?";
	private String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};
	
	protected JMenu jmfile;

	public Editor() {
		//Editor the name of our application
		super("Editor");
		// center means middle of container.
		add(new JScrollPane(textPanel), "Center");
		textPanel.getDocument().addDocumentListener(this);
		setJMenuBar(defaultMenu);
		buildMenu();
		//The size of window
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void buildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {
		jmfile = new JMenu("File");
		jmfile.setMnemonic('F');
		defaultMenu.add(jmfile);
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
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
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
				ans = JOptionPane.showConfirmDialog(null, SAVE_CHANGE, SAVE, 0, 2);
			}
			//1 value from class method if NO is chosen.
			if (ans != 1) {
				if (file == null) {
					saveAs(actions[1]);
				} else {
					String text = textPanel.getText();
					LOGGER.info(text);
					try (PrintWriter writer = new PrintWriter(file);){
						if (!file.canWrite())
							throw new EditorException(CANNOT_WRITE, ExceptionType.SAVE_EXCEPTION);
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
					int ans = JOptionPane.showConfirmDialog(null, SAVE_CHANGE, SAVE,
							0, 2);
					//1 value from class method if NO is chosen.
					if (ans == 1)
						return;

				if (file == null) {
					saveAs(actions[1]);
					return;
				}
				String text = textPanel.getText();
				LOGGER.info(text);
				try (PrintWriter writer = new PrintWriter(file);){
					if (!file.canWrite())
						throw new EditorException(CANNOT_WRITE,ExceptionType.WRITE_EXCEPTION);
					writer.write(text);
					changed = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			file = null;
			textPanel.setText("");
			changed = false;
			setTitle("Editor");
		} else if (action.equals(actions[5])) {
			saveAs(actions[5]);
		} else if (action.equals("Select All")) {
			textPanel.selectAll();
		} else if (action.equals("Copy")) {
			textPanel.copy();
		} else if (action.equals("Cut")) {
			textPanel.cut();
		} else if (action.equals("Paste")) {
			textPanel.paste();
		} else if (action.equals("Find")) {
			FindDialog find = new FindDialog(this, true);
			find.showDialog();
		}
	}

	private void loadFile() {
		JFileChooser dialog = new JFileChooser();
		dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = dialog.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = dialog.getSelectedFile();
			String content = readFile(selectedFile);
			if (content != null) {
				textPanel.setText(content);
				file = selectedFile;
				changed = false;
			}
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
			JOptionPane.showMessageDialog(null, CANNOT_WRITE, "Error!", 0);//0 means show Error Dialog
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
		writer.write(textPanel.getText());
		changed = false;
		setTitle("Editor - " + file.getName());
	}
	public boolean isNameEmpty() {
		return getName().length() == 0; // Noncompliant; the result of getName() could be null, but isn't null-checked
	}
	private static PrintWriter getWriter(File file) {
		try {
			return new PrintWriter(file);
		} catch (NullPointerException | FileNotFoundException e){
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