 * some problems in FindDialog class  
 ![image](https://user-images.githubusercontent.com/100956629/225065182-f24c0655-8f0c-4fe4-9f6d-2338b9e4303a.png)
 
 These problems have been resolved:
1)	Change the name of the variable from (parent) to root
2)	To make the variable transient we add (transient) key world 
3)	+  
4)	 These functions are empty we add (sout) to print to tell the user It's empty, then sonarlint add another issue to replace (sout) to log
5)	+
6)	A comment we should remove it
7)	We should define each variable in separate line


* some problem in Editor class
![335566706_1251393735463380_8130140185361086812_n](https://user-images.githubusercontent.com/114495555/225697082-da860694-0a7c-422a-a97c-c1487659e847.jpg)
![image](https://user-images.githubusercontent.com/100956629/225733383-6ac0dcd7-1f27-4a1c-8f61-992fa0cfd8a0.png)


1) Type: Standard outputs should not be used directly to log anything  (Major)
Repetition times: 4
How this issue effect the code?  If sensitive information is being logged to standard outputs, then it can be easily accessible to unauthorized users.
Solution: Display messages in a logger instead of System.out.

2) Type: String literals should not be duplicated  (Critical)
repetition times : 4
How this issue effect the code?  it can lead to maintenance issues and bugs. For example, if a string literal is used in multiple places and needs to be updated, it can be easy to forget to update all occurrences of the literal.
Solution: Define a constant instead of duplicating the text .

3) Type: Cognitive Complexity of methods should not be too high (Critical)
repetition times : 2
How this issue effect the code?  it can become difficult to understand and maintain the code. Cognitive complexity measures the difficulty of understanding a method based on how many different paths there are through the code, how many logical operators are used, and how nested the code is.
Solution: breaking them down into smaller, more manageable parts, using functions, and use switch instead of deeply nested structures and complex if statements.


4) Type: Sections of cod should not be commented out (Major)
repetition times : 1
How this issue effect the code?  it bloats programs and reduces readability..
Solution: delete comments.


5) Type: class variable fields should not have public accessibility (Minor)
Repetition times: 7
How this issue effect the code? The issue of having public accessibility for class variable fields means that the fields can be accessed and modified by any code outside of the class.
Solution: Make the variable a static final constant or non-public and provide accessors if needed.


6) Type: field names should comply with a naming convention. (Minor)
Repetition times: 1
How this issue effect the code?  it can make it harder  to understand the code.
Solution: Rename this field to match the regular expression.


7) Type: multiple variables should not be declared on the same line. (Minor)
Repetition times: 1
How this issue effect the code?  it can make the code more difficult to read and understand and complexity to edit.
Solution: Separate them into line.


8) Type: method names should comply with a naming convention. (Minor)
Repetition times: 2
How this issue effect the code? can make the code less readable and more difficult to maintain.
Solution: Rename this method name to match the code convention


9) Type: @Deprecated” code should not be used.. (Minor)
Repetition times: 2
How this issue effect the code? it make code  compatibility, Security vulnerabilities, Reduced efficiency, Maintenance issues
Solution: use new version .


10) Type: Null pointers should not be dereferenced (Major)
Repetition times: 1
How this issue effect the code? code can result in a runtime error, result in hard-to-debug errors, security vulnerabilities.
Solution: Check for null values.

False Negatives:
1) While the code checks if a file can be written to before attempting to write to it, there may be other cases where a file cannot be read or written to due to file permissions or other issues, but the code does not handle these cases properly.
2) While the code attempts to validate user input when opening or saving files, there may be other cases where invalid input or unexpected user actions could cause the code to behave in unexpected ways or crash.
3) The method saveAs() called within newFile() can potentially throw an exception, but it is not caught or thrown.
* SonarQube doesn’t catch this issues because:
- it may not be aware of the inner workings of external libraries used in the code. As a result, it may flag issues that are not actually problematic.
- SonarQube may make assumptions about the code that are not true, leading it to miss certain issues.


