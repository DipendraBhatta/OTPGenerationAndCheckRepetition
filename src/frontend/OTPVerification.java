package frontend;

import SendingOTPAndStoring.*;
import OTP.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OTPVerification {
    private static JTextField[] otpFields;
    private static JButton verifyButton;
    private static JButton changeEmailButton;
    private static JButton resendButton;
    private static JLabel timingLabel;
    public static int countdownTime;
    public static Timer timer;

    public static void otpVerification(String enteredEmail, int countDown) {
        JFrame frame = new JFrame("OTP Verification");
        frame.setLayout(null);

        countdownTime = countDown;

        Font poppinsFont = new Font("Poppins", Font.PLAIN, 14);

        otpFields = new JTextField[6];
        for (int i = 0; i < 6; i++) {
            otpFields[i] = new JTextField();
            otpFields[i].setFont(poppinsFont);
            otpFields[i].setHorizontalAlignment(JTextField.CENTER);
            otpFields[i].setBounds(50 + i * 50, 30, 40, 30);
            final int index = i;
            otpFields[i].addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (otpFields[index].getText().length() >= 1 || !Character.isDigit(e.getKeyChar())) {
                        e.consume();
                    } else if (index < 5 && Character.isDigit(e.getKeyChar())) {
                        otpFields[index + 1].requestFocus();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && index > 0) {
                        otpFields[index - 1].requestFocus();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
            frame.add(otpFields[i]);
        }

        timingLabel = new JLabel("Time remaining: " + countdownTime + " seconds");
        timingLabel.setFont(poppinsFont);
        timingLabel.setBounds(50, 70, 200, 30);

        verifyButton = new JButton("Verify");
        verifyButton.setFont(poppinsFont);
        verifyButton.setBounds(50, 110, 100, 30);
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder otp = new StringBuilder();
                for (JTextField field : otpFields) {
                    otp.append(field.getText());
                }

                System.out.println("you entered: " + otp.toString());
                System.out.println("your email : " + enteredEmail);
                System.out.println("your encrypted otp " + Encryption.encryptOTP(otp.toString()));

                new Receive().verification(enteredEmail, otp.toString(), frame);

//                new WelcomePage();
            }
        });

        changeEmailButton = new JButton("Change Email");
        changeEmailButton.setFont(poppinsFont);
        changeEmailButton.setBounds(170, 110, 150, 30);
        changeEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SendOTP();
            }
        });

        resendButton = new JButton("Resend");
        resendButton.setFont(poppinsFont);
        resendButton.setBounds(330, 110, 100, 30);
        resendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownTime = 60;
                String getThatOtp = SendOTP.sendingOTP();
                EmailSender.sendOTP(getThatOtp, enteredEmail);
                new Database(enteredEmail, getThatOtp);
                System.out.println("OTP has been sent again");
                System.out.println("OTP time has been reset let's see...");
                SendOTP.restartTimer(enteredEmail);
                timer.start();
            }
        });

        frame.add(timingLabel);
        frame.add(verifyButton);
        frame.add(changeEmailButton);
        frame.add(resendButton);

        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countdownTime > 0) {
                    countdownTime--;
                    timingLabel.setText("Time remaining: " + countdownTime + " seconds");
                } else {
                    ((Timer) e.getSource()).stop();
                    timingLabel.setText("OTP has been expired!");
                }
            }
        });
        timer.start();
    }
}
