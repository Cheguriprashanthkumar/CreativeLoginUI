import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.prefs.Preferences;
import java.util.regex.*;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton togglePasswordButton;
    private JCheckBox rememberMe;
    private JButton loginButton;
    private JLabel errorLabel;
    private int attemptCount = 0;
    private boolean isPasswordVisible = false;

    private final String validUser = "test@example.com";
    private final String validPass = "test123";
    private final boolean licenseValid = true;

    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    public LoginForm() {
        setTitle("✨ Creative Minds Login");
        setSize(850, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Load background image
        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg.png")));
        Image bgImage = bgIcon.getImage().getScaledInstance(850, 550, Image.SCALE_SMOOTH);

        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        background.setBounds(0, 0, 850, 550);
        background.setLayout(null);
        add(background);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBounds(250, 90, 350, 390);
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 250), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        background.add(formPanel);

        JLabel logo = new JLabel("✨", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        logo.setBounds(140, 10, 70, 50);
        formPanel.add(logo);

        JLabel welcome = new JLabel("👋 Welcome Back", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        welcome.setBounds(90, 60, 170, 30);
        formPanel.add(welcome);

        // Email Container
        JPanel emailContainer = new JPanel(null);
        emailContainer.setBounds(30, 100, 290, 50);
        emailContainer.setBackground(Color.WHITE);
        emailContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(160, 160, 220), 1, true),
                new EmptyBorder(0, 0, 0, 0)
        ));
        formPanel.add(emailContainer);

        JLabel emailIcon = new JLabel("📧");
        emailIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        emailIcon.setBounds(8, 13, 24, 24);
        emailContainer.add(emailIcon);

        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(null);
        emailField.setBounds(40, 12, 240, 26);
        emailField.setToolTipText("Enter your email");
        emailContainer.add(emailField);

        // Password Container
        JPanel passwordContainer = new JPanel(null);
        passwordContainer.setBounds(30, 160, 290, 50);
        passwordContainer.setBackground(Color.WHITE);
        passwordContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(160, 160, 220), 1, true),
                new EmptyBorder(0, 0, 0, 0)
        ));
        formPanel.add(passwordContainer);

        JLabel passwordIcon = new JLabel("🔒");
        passwordIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        passwordIcon.setBounds(8, 13, 24, 24);
        passwordContainer.add(passwordIcon);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(null);
        passwordField.setBounds(40, 12, 190, 26);
        passwordField.setEchoChar('•');
        passwordField.setToolTipText("Enter your password");
        passwordContainer.add(passwordField);

        togglePasswordButton = new JButton("👁️");
        togglePasswordButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        togglePasswordButton.setBounds(240, 10, 40, 28);
        togglePasswordButton.setFocusPainted(false);
        togglePasswordButton.setBorder(null);
        togglePasswordButton.setBackground(Color.WHITE);
        togglePasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        togglePasswordButton.addActionListener(e -> togglePasswordVisibility());
        passwordContainer.add(togglePasswordButton);

        rememberMe = new JCheckBox("Remember Me");
        rememberMe.setBounds(30, 220, 150, 20);
        rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rememberMe.setOpaque(false);
        formPanel.add(rememberMe);

        loginButton = new JButton("🚪 Sign In");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        loginButton.setBounds(30, 250, 290, 40);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setEnabled(false);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(0, 102, 204), 1, true),
                new EmptyBorder(5, 15, 5, 15)
        ));
        formPanel.add(loginButton);

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 80, 180));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 102, 204));
            }
        });

        JLabel forgot = new JLabel("<HTML><U>📝 Forgot Password?</U></HTML>");
        forgot.setForeground(new Color(80, 80, 220));
        forgot.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        forgot.setBounds(90, 295, 180, 30);
        forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formPanel.add(forgot);

        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setBounds(30, 325, 290, 20);
        formPanel.add(errorLabel);

        // Load saved email
        String savedEmail = prefs.get("rememberedEmail", "");
        if (!savedEmail.isEmpty()) {
            emailField.setText(savedEmail);
            rememberMe.setSelected(true);
        }

        // Validation
        DocumentListener listener = (SimpleDocumentListener) this::validateInputs;
        emailField.getDocument().addDocumentListener(listener);
        passwordField.getDocument().addDocumentListener(listener);

        loginButton.addActionListener(e -> performLogin());
        forgot.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showForgotPopup();
            }
        });

        setVisible(true);
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        passwordField.setEchoChar(isPasswordVisible ? (char) 0 : '•');
        togglePasswordButton.setText(isPasswordVisible ? "🙈" : "👁️");
    }

    private void validateInputs() {
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        boolean validEmail = Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", email);
        boolean validPass = pass.length() >= 6;
        loginButton.setEnabled(validEmail && validPass);
    }

    private void performLogin() {
        if (attemptCount >= 5) {
            JOptionPane.showMessageDialog(this,
                    "🚫 Account is locked. Please contact your organization.",
                    "Account Locked", JOptionPane.WARNING_MESSAGE);
            loginButton.setEnabled(false);
            return;
        }

        String user = emailField.getText();
        String pass = new String(passwordField.getPassword());

        if (!user.equals(validUser) || !pass.equals(validPass)) {
            attemptCount++;
            errorLabel.setText("❌ Invalid credentials. Attempt " + attemptCount + "/5.");
            if (attemptCount >= 5) {
                JOptionPane.showMessageDialog(this,
                        "🚫 Account locked. Please contact your organization.",
                        "Account Locked", JOptionPane.WARNING_MESSAGE);
                loginButton.setEnabled(false);
            }
            return;
        }

        if (!licenseValid) {
            JOptionPane.showMessageDialog(this, "🔔 License expired. Please renew.");
            return;
        }

        if (rememberMe.isSelected()) {
            prefs.put("rememberedEmail", user);
        } else {
            prefs.remove("rememberedEmail");
        }

        new Dashboard().setVisible(true);
        dispose();
    }

    private void showForgotPopup() {
        JDialog dialog = new JDialog(this, "🔁 Reset Password", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        JLabel msg = new JLabel("🔑 Contact admin to reset password.");
        JButton next = new JButton("⏭️ Next");
        JButton close = new JButton("❎ Close");

        next.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        close.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));

        next.addActionListener(e -> JOptionPane.showMessageDialog(this, "✅ Message sent to organization."));
        close.addActionListener(e -> dialog.dispose());

        dialog.add(msg);
        dialog.add(next);
        dialog.add(close);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginForm();
    }

    @FunctionalInterface
    private interface SimpleDocumentListener extends DocumentListener {
        void update();
        default void insertUpdate(DocumentEvent e) { update(); }
        default void removeUpdate(DocumentEvent e) { update(); }
        default void changedUpdate(DocumentEvent e) { update(); }
    }

    static class Dashboard extends JFrame {
        private JLabel timeLabel;

        public Dashboard() {
            setTitle("Dashboard");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JLabel welcome = new JLabel("👋 Welcome to Dashboard!", SwingConstants.CENTER);
            welcome.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
            add(welcome, BorderLayout.CENTER);

            timeLabel = new JLabel("", SwingConstants.CENTER);
            timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            add(timeLabel, BorderLayout.SOUTH);

            Timer timer = new Timer(1000, e -> updateTime());
            timer.start();
            updateTime();
        }

        private void updateTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            timeLabel.setText("📅 " + sdf.format(new Date()));
        }
    }
}
