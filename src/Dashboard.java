import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;

public class Dashboard extends JFrame {
    private JLabel timeLabel;

    public Dashboard() {
        setTitle("Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("👋 Welcome to Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.CENTER);

        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(timeLabel, BorderLayout.SOUTH);

        updateTime(); // Initial call
        startClock(); // Start timer to update every second
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentTime = sdf.format(new Date());
        timeLabel.setText("📅 " + currentTime);
    }

    private void startClock() {
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
    }
}
