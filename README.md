# SimpleEditor

 problems in class FindDialog 
 ![image](https://user-images.githubusercontent.com/100956629/225065182-f24c0655-8f0c-4fe4-9f6d-2338b9e4303a.png)

some problem in class Editor
![335566706_1251393735463380_8130140185361086812_n](https://user-images.githubusercontent.com/114495555/225697082-da860694-0a7c-422a-a97c-c1487659e847.jpg)

this error and solve it:
error: Make TP a static final constant or non-public and provide accessors if needed,Rename this field "TP" to match the regular expression.
solve: static JEditorPane textPanel=new JEditorPane();(rename the variable (magic number) and make it static can be accessed using the class name without creating an object of the class.)
error: Make menu a static final constant or non-public and provide accessors if needed.
solve: static JMenuBar defaultMenu = new JMenuBar();(rename the variable (magic number) and make it static can be accessed using the class name without creating an object of the class.)
error: Make copy a static final constant or non-public and provide accessors if needed.
solve: private static final JMenuItem COPY = new JMenuItem("Copy");(rename the variable (magic number) and make it static can be accessed using the class name without creating an object of the class.)
error: Declare "paste" and all following declarations on a separate line.
solve: 	public JMenuItem paste;
        public JMenuItem cut;
        public JMenuItem move;
error: Make paste a static final constant or non-public and provide accessors if needed.
solve: private static final JMenuItem PASTE= new JMenuItem("Paste");(rename the variable (magic number) and make it satic and final(cannot be changed).)
error: Make cut a static final constant or non-public and provide accessors if needed.
solve: private static final JMenuItem CUT= new JMenuItem("Cut");(rename the variable (magic number) and make it satic and final(cannot be changed).)
error: Make move a static final constant or non-public and provide accessors if needed
solve: private static final JMenuItem MOVE= new JMenuItem("Move");(rename the variable (magic number) and make it satic and final(cannot be changed).)
error: Remove this unused MOVE private field.
solve: remove move variable(Because not used)
error: Make changed a static final constant or non-public and provide accessors if needed
solve: private boolean changed = false;(make the variable changed private It can be accessed in the editor class just)
error: Rename this method(BuildMenu()) name to match the regular expression.
solve: according the convintion the name of function should be fisrt letter small private void buildMenu() 
error: Remove this use of "CTRL_MASK", it is deprecated.
solve: InputEvent.SHIFT_DOWN_MASK(CTRL_MASK in old version) 
error: Remove this use of "SHIFT_MASK"; it is deprecated.
solve: InputEvent.CTRL_DOWN_MASK(SHIFT_MASK in old version)
error: "NullPointerException" could be thrown; "writer" is nullable here.
solve: file parameter can be null and if writer equals null (getWriter method may throw a NullPointerException)add the condition indicating that the check writer does not equal null before calling the write method and add condition that check file parameter is not null 



