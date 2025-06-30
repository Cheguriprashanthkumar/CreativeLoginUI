# ✨ Creative Login UI - Java Swing Desktop Application

A beautiful and functional desktop login interface built using **Java Swing**.  
Includes features like email & password validation, "Remember Me", password visibility toggle, 5-attempt lockout, and a minimal dashboard.

---

## 💻 Technologies Used

- Java (JDK 17+)
- Swing (Java GUI Framework)
- IntelliJ IDEA (Recommended IDE)

---

##  How to Run the Application

###  Option 1: Using IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Go to `File` → `Open...` → Select the `CreativeLoginUI` project folder.
3. Ensure the background image file `bg.png` is located in:  
   `src/resources/bg.png`
4. Right-click `LoginForm.java` → `Run 'LoginForm.main()'`

---

### 🔧 Option 2: Compile via Command Line

1. Open terminal and navigate to your project root.
2. Run the following:

```bash
javac -d out src/LoginForm.java
java -cp out LoginForm

###LOGIN EMAIL : test@example.com
###PASSWORD : test123
