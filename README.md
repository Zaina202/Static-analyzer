 * some problems in FindDialog class  
 ![image](https://user-images.githubusercontent.com/100956629/225065182-f24c0655-8f0c-4fe4-9f6d-2338b9e4303a.png)
 
* Type: child class fields should not shadow parent class fields
(Blocker) 
Repetition times: 1
How this issue effect the code? Confusion and errors , Unexpected behavior, Maintenance issues
Solution: Change the name of the variable from (parent) to root
* Type: Fields in a “serializable” class should either be transient or serializable
(critical) 
Repetition times: 1
How this issue effect the code? If a field is not transient or serializable, it will not be included in the serialization process, which can result in data loss. When the object is deserialized, the missing field will either be assigned a default value or, if the field is an object, it will throw a NotSerializableException. This can cause unexpected behavior in the code and can lead to bugs and errors.
Solution: To make the variable transient we add (transient) key world
* Type: Methods should not be empty
(critical) 
Repetition times: 2
How this issue effect the code? Debugging issues, Maintenance issues, Unexpected behavior
Solution: These functions are empty we add (sout) to print to tell the user It's empty, then sonarlint add another issue to replace (sout) to log 
* Type: sections of code should not be commented out
(major) 
Repetition times: 2
How this issue effect the code? Code bloat, Confusion, Version control issues, Debugging issues
Solution: delete unused code instead of commenting it out.
* Type: multiple variables should not be declared on the same line
(minor) 
Repetition times: 1
How this issue effect the code? Maintenance, Debugging, Scoping
Solution: it is generally better to declare each variable on a separate line. This will make the code easier to read and modify



*some problem in Editor class

![335566706_1251393735463380_8130140185361086812_n](https://user-images.githubusercontent.com/114495555/225697082-da860694-0a7c-422a-a97c-c1487659e847.jpg)
![image](https://user-images.githubusercontent.com/100956629/225733383-6ac0dcd7-1f27-4a1c-8f61-992fa0cfd8a0.png)


*Type: inheritance tree of classes should not be too deep
(major) 
Repetition times: 1
How this issue effect the code? Readability and maintainability, Complexity, Performance, Coupling
Solution: This can be achieved by using composition instead of inheritance in some cases, and by favoring interfaces over abstract classes where appropriate. It is also helpful to use inheritance only when it makes sense from a design perspective and to avoid using inheritance simply to save typing or to achieve code reuse. By keeping the inheritance tree shallow, you can improve the readability, maintainability, and performance of the code, and reduce coupling between classes


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

11)Type: resources should be closed
(blocker) 
Repetition times: 1
How this issue effect the code? Resource leaks, Inconsistent behavior, Data corruption, Security issues
Solution: This can be done using a try-with-resources statement or by explicitly calling the close() method on the resource.

12)Type: string literals should not be duplicated
(critical) 
Repetition times: 2
How this issue effect the code? Maintenance, Consistency, Efficiency, Debugging
Solution: it is generally better to define string literals as constants or variables and reuse them throughout the code.

13) Type: cognitive complexity of methods should not be too high
(critical) 
Repetition times: 1
How this issue effect the code? Readability, Debugging, Testing, Maintainability
Solution: This can be achieved by breaking up complex methods into smaller, more manageable methods that are easier to understand and test. It is also helpful to use descriptive names for methods and variables, and to limit the number of conditional statements and loops in the code. By keeping the cognitive complexity of methods low, you can improve the readability, maintainability, and testability of the code, and reduce the likelihood of issues arising in the future.

problems in EditorException
![image](https://user-images.githubusercontent.com/100956629/226057230-2a0ff121-945c-496b-998b-c93ca1033d5a.png)


False Negatives:
1) While the code checks if a file can be written to before attempting to write to it, there may be other cases where a file cannot be read or written to due to file permissions or other issues, but the code does not handle these cases properly.
2) While the code attempts to validate user input when opening or saving files, there may be other cases where invalid input or unexpected user actions could cause the code to behave in unexpected ways or crash.
3) The method saveAs() called within newFile() can potentially throw an exception, but it is not caught or thrown.
>>SonarQube doesn’t catch this issues because:
- it may not be aware of the inner workings of external libraries used in the code. As a result, it may flag issues that are not actually problematic.
- SonarQube may make assumptions about the code that are not true, leading it to miss certain issues.


