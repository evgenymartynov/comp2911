import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Presents a phone GUI for the voice mail system.
 */
public class Telephone {
    /**
     * Constructs a telephone with a speaker, keypad, and microphone.
     */
    public Telephone() {
        // Initialise all the Swing bs.
        JPanel bag = new JPanel();
        bag.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        // End init.

        // Speaker things.
        speakerField = new JTextArea(10, 25);

        c.gridwidth = 6;
        c.gridy = 0;
        bag.add(new JLabel("Speaker:"), c);
        c.gridy++;
        bag.add(speakerField, c);

        // Key things.
        String keyLabels = "123456789*0#";
        for (int i = 0; i < keyLabels.length(); i++) {
            final String label = keyLabels.substring(i, i + 1);
            JButton keyButton = new JButton(label);
            keyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    connect.dial(label);
                }
            });

            c.gridwidth = 2;
            c.gridx = (i % 3) * 2;
            c.gridy = 2 + i / 3;
            bag.add(keyButton, c);
        }

        // Microphone and speech things.
        final JTextArea microphoneField = new JTextArea(10, 25);
        JButton speechButton = new JButton("Send speech");
        speechButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                connect.record(microphoneField.getText());
                microphoneField.setText("");
            }
        });

        // Hanging up things.
        JButton hangupButton = new JButton("Hangup");
        hangupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                connect.hangup();
            }
        });

        //
        // Putting it all together.
        //
        c.fill = GridBagConstraints.BOTH;

        // Mic.
        c.gridwidth = 6;
        c.gridx = 0;
        c.gridy++;
        bag.add(new JLabel("Microphone:"), c);
        c.gridy++;
        bag.add(microphoneField, c);

        // Buttons.
        c.gridwidth = 2;
        c.gridy++;
        c.gridx = 0;
        bag.add(speechButton, c);
        c.gridx = 4;
        bag.add(hangupButton, c);

        // And display.
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(bag);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Give instructions to the mail system user.
     */
    public void speak(String output) {
        speakerField.setText(output);
    }

    public void run(Connection c) {
        connect = c;
    }

    private JTextArea speakerField;
    private Connection connect;
}
