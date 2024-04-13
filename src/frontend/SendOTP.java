package frontend;

import OTP.*;
import RepetitionCount.GeneratingOTPs;
import SendingOTPAndStoring.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendOTP {
    static long generate(long s1, long s2, long s3, long s4) {
        return (s1 + s4 * s2 + s3) % 1000000;
    }

    private static Timer timer;

    public static String sendingOTP() {
        // BBS
        long p = 18913;
        long q = 85729;
        long seed = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("blumblum.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                seed = Long.parseLong(line);
            } else{
                seed = 123456789;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        BlumBlumShub b = new BlumBlumShub(p, q, seed);
        UpdateSeed blum = new UpdateSeed(seed, "blumblum");

        // LCM
        long m = 189913;
        long a = 985729;
        long c = 12345;
        long initial = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("blumblum.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                initial = Long.parseLong(line);
            } else{
                initial = 987654321;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        LCM l = new LCM(m, a, c, initial);
        UpdateSeed lcm = new UpdateSeed(seed, "lcm");

        long updateNewBlum = Integer.parseInt(blum.getNumber());
        long updateNewLCM = Integer.parseInt(lcm.getNumber());
        blum.updateNumber((updateNewBlum + b.generateNext()) % 1000000);
        lcm.updateNumber((updateNewLCM + l.generation()) % 1000000);


        String balancedOTP = String.format("%06d", generate(b.generateNext() + updateNewBlum, l.generation() + updateNewLCM, updateNewBlum, updateNewLCM));

        String finalOTP = String.valueOf(balancedOTP);
        System.out.println("generated otp" + finalOTP);


        return finalOTP;
    }

    public SendOTP() {
        Font poppinsFont = null;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Poppins-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsFont);
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("OTP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Enter Email:");
        JTextField nameField = new JTextField(20);
        JButton sendButton = new JButton("Send OTP");
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(52, 152, 219));

        nameLabel.setFont(poppinsFont.deriveFont(Font.PLAIN, 14f));
        nameField.setFont(poppinsFont.deriveFont(Font.PLAIN, 14f));
        sendButton.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));

        nameLabel.setBounds(20, 20, 100, 30);
        nameField.setBounds(140, 20, 300, 30);
        sendButton.setBounds(20, 80, 420, 30);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredEmail = nameField.getText();
                String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
                Pattern pattern = Pattern.compile(emailPattern);
                Matcher matcher = pattern.matcher(enteredEmail);

                if (matcher.matches()) {
                    String getThatOtp = sendingOTP();
                    EmailSender.sendOTP(getThatOtp, enteredEmail);
                    new Database(enteredEmail, getThatOtp);
                    OTPVerification.otpVerification(enteredEmail, 60);
                    System.out.println("OTP verification page has been opened");
                    frame.dispose();
                    startTimer(enteredEmail, 60);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Email", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(sendButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void startTimer(String enteredEmail, int countdownTime) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Database.deletionOperation(enteredEmail);
            }
        }, 1000 * countdownTime);
    }

    public static void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    public static void restartTimer(String enteredEmail) {
        stopTimer();
        startTimer(enteredEmail, 60);
    }
}
