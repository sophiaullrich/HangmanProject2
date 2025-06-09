HangmanProject
A cute, fully interactive version of the classic Hangman game built with Java Swing. This project includes login and registration functionality using an embedded Apache Derby database, dynamic word list management, hints, and player statistics — all wrapped in an aesthetic pink GUI!

Features
Classic Hangman gameplay with visual drawing updates

Login & Registration (using Derby database)

Word list management: add, update, or remove words

Hint system to reveal letters

Tracks wins, losses, and games played

Cute pink-themed interface with animated feedback

Technologies Used
Java (JDK 11+)

Java Swing (GUI)

Apache Derby (Embedded Database)

Object-Oriented Programming Principles

Project Structure
arduino
Copy
Edit
hangmanproject/
├── FileManager.java
├── Game.java
├── HangmanDrawing.java
├── HangmanGame.java
├── HangmanGUI.java
├── Hints.java
├── LoginGUI.java
├── DatabaseSetup.java
├── InsertUser.java
├── words.txt (word list)
└── databases/
    └── HangmanDB (Derby database)
Getting Started
Prerequisites
JDK 11 or higher

Apache NetBeans or any Java IDE

Derby included in the JDK or added as a library

Installation & Run
Clone or download this repository.

Ensure words.txt is located in the project directory or within your classpath.

Set up the database:

Run DatabaseSetup.java once to create the users table and insert sample users.

Alternatively, you can use InsertUser.java to add individual users.

Launch the game:

Run LoginGUI.java to open the login screen.

Use a registered username/password or create a new account.

Default Test Users
testUser1 / password123

testUser2 / securepass456

Word List Editing
Once logged in, click Manage Word List:

Add new words

Update a word by line number

Remove a word by line number

Author
Sophia Ullrich
Bachelor of Computer & Information Sciences, AUT
Software Development Major
