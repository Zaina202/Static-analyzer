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





