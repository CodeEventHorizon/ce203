import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

// Complete ID class for CE203 Assignment 1
// Date: 11/11/2020
// Author: Nikoloz Muladze

public class CE203_0_Ass1 extends JComponent implements Comparable<CE203_0_Ass1>{

    /** Start: Variables */
    private JFrame f;
    private JPanel p1, p2, p3;
    private int width = 500;
    private int height = 500;
    private JTextField tf, r, g, b;
    private JTextArea ta;
    private ArrayList<String> ids;
    private String userInput = "000000";
    private Container container = null;
    /** End: Variables */

    /** Start: main() method*/
    public static void main(String[] args) {
        CE203_0_Ass1 id = new CE203_0_Ass1("000000");
    }
    /** End: main() method */

    /** This is a constructor, after creating an object ID, it asks for String input that will be displayed in the List (JTextArea)
    * I used constructor for creating a Frame/Window
    */
    public CE203_0_Ass1(String userInputArg)
    {
        this.userInput = userInputArg;

        f = new JFrame(); //Creating a Frame

        /**Start: creating JPanel, setting row and column sizes.*/
        p1 = new JPanel();
        p1.setLayout(new GridLayout(2,1));
        p1.setBackground(Color.LIGHT_GRAY);
        p1.setPreferredSize(new Dimension(200,300));

        p2 = new JPanel();
        p2.setLayout(new GridLayout(2,2));
        p2.setPreferredSize(new Dimension(200,200));

        p3 = new JPanel();
        p3.setLayout(new GridLayout(1,5));
        p3.setPreferredSize(new Dimension(200,50));
        /**End (I created 3 JPanels, 1 for TextArea and JLabel(that outputs actions),
         * 2nd for JTextField-s input & RGB values,
         * 3rd for Buttons.
         * */

        container = f.getContentPane(); //container


        /** Start: Creating a JTextField Object */
        tf = new JTextField("Input id (length must be 6)");
        r = new JTextField("Input r(0-255)");
        g = new JTextField("Input g(0-255)");
        b = new JTextField("Input b(0-255)");
        /** End */
        /** Start: Creating JButton Objects */
        JButton addB = new JButton("Add");
        JButton clearB = new JButton("Clear");
        JButton removeB = new JButton("Remove");
        JButton displayB = new JButton("Display");
        JButton sortB = new JButton("Sort");
        /** End */

        JLabel label1 = new JLabel(); // creating Label Object for Exception/Error handling and overall displaying actions

        ids = new ArrayList<>(); // initialising ArrayList from private variables
        ids.add(userInputArg);

        /** Start: ActionListeners for Buttons */

        /** Add button that appends IDs into the List from the JTextField called tf.
        * I used Iterator for looping through the ArrayList, every time the new ID Added, Removed or Sorted.
        * Iterator loops through the whole list and displays them in the in JTextArea.*/
        addB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = tf.getText();
                if (userInput.matches("[0-9]{6}")) { //regular expression stating that only characters within 0 to 9 are allowed with the length = 6
                    ids.add(userInput);
                    Iterator<String> idIterator = ids.iterator();
                    ta.setText("");
                    while (idIterator.hasNext()) {
                        ta.append(idIterator.next() + "\n");
                    }
                    label1.setText("ID ‘"+userInput+"’ has been added to the list.");
                } else {
                    label1.setText("The ID ‘"+userInput+"’ was not added to the list as it is not a valid ID.");
                }
//                System.out.println(ids); //DEBUG
            }
        });
        /** Remove Button that erases all occurrences of the ID in the List specified in JTextField called tf */
        removeB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.setText("");
                userInput = tf.getText();

                if (!ids.isEmpty()) {
                    ids.removeIf(s -> s.equals(userInput));
                    for (Iterator<String> idIterator = ids.iterator(); idIterator.hasNext();){
                        ta.append(idIterator.next() + "\n");
                    }
                    label1.setText("The ID(s) matching '"+userInput+"' was/were removed from the list.");
                } else {
                    label1.setText("List is Empty or there are no matching IDs to remove");
                }

//                System.out.println(ids); //DEBUG
            }
        });
        /** Clear Button that clears the whole JTextArea and ArrayList*/
        clearB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ids.isEmpty()) {
                    userInput = tf.getText();
                    ids.clear();
                    ta.setText("");
                    label1.setText("The list has been cleared.");
                } else {
                    label1.setText("The list is empty");
                }
            }
        });
        /** Button that displays RGB color specified in 3 JTextFields called r, g and b.
        * Text text should be from 0-255 otherwise the color changes to Black*/
        displayB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ta.getText().isEmpty()) {
                    if (!r.getText().isEmpty() && !g.getText().isEmpty() && !b.getText().isEmpty()) {
                        if (r.getText().matches("^[-]?[0-9]*$") && g.getText().matches("^[-]?[0-9]*$") && b.getText().matches("^[-]?[0-9]*$")) { // removing this line would throw an error because of Integer.ParseInt
                            int rC = Integer.parseInt(r.getText()); //above if statements first check if input fields are empty
                            int gC = Integer.parseInt(g.getText()); //then if input fields are numerical and in some occasions might have '-' symbol in front
                            int bC = Integer.parseInt(b.getText());
                            if ((rC >= 0 && rC <= 255) && (gC >= 0 && gC <= 255) && (bC >= 0 && bC <= 255)) {
                                ta.setForeground(new Color(rC, gC, bC));
                                label1.setText("Text Font color changed");
                            } else {
                                label1.setText("RGB values must be within 0-255! Color set to Black");
                                ta.setForeground(Color.BLACK);
                            }
                        } else {
                            label1.setText("RGB values must be integers!");
                        }
                    } else {
                        label1.setText("You must fill in all the RGB Fields");
                    }
                } else {
                    label1.setText("List is Empty");
                }
            }
        });
        /** Sort Button that sorts all the List IDs in ascending order from top to bottom using Collections.sort(<ArrayList name>)*/
        sortB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ids.isEmpty()) {
                    ta.setText("");
                    Collections.sort(ids);
                    for (Iterator<String> iterator = ids.iterator(); iterator.hasNext(); ) {
                        ta.append(iterator.next() + "\n");
                    }
                    label1.setText("The list was sorted in the ascending order");
                } else {
                    label1.setText("The list is empty");
                }
            }
        });
        /** End: ActionListener for Buttons */

        ta = new JTextArea(); //Creating a TextArea Object
        ta.setText(userInputArg); // ID that is set in the TextArea after running the program, that is inputted in the main() method after creating an object

        /**Start: container, adding panels to the container.*/
        container.add(p1, BorderLayout.NORTH);
        container.add(p2, BorderLayout.CENTER);
        container.add(p3, BorderLayout.SOUTH);
        /**End: container*/
        /**Start: Panel. adding all the objects to the JPanel so it gets displayed*/
        p1.add(ta);p1.add(label1);

        p2.add(tf);p2.add(r); p2.add(g); p2.add(b);

        p3.add(addB);p3.add(removeB);p3.add(clearB);p3.add(displayB);p3.add(sortB);
        /**End: Panel*/

        f.setTitle("Application Programming Assignment by Nikoloz Muladze");
        f.setSize(width,height);
        f.setLocationRelativeTo(null); // Window Location is set to the center of the monitor after running
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(true);
    }

    // gets a stored ID
    public String getID() {
        return userInput;
    }


    // sets the input parameter to an ID
    public void setID(String inputID) {
        userInput = inputID;
    }


    @Override
    // method used for comparing ID objects based on stored ids
    public int compareTo(CE203_0_Ass1 o) {
        if (Integer.parseInt(getID()) < Integer.parseInt(o.getID())) {
            return -1;
        }
        if (getID().equals(o.getID())) {
            return 0;
        } else {
            return 1;
        }
    }

    // outputs a string representation of the object
    public String toString()
    {
        return ("ID = "+userInput);
    }
}
