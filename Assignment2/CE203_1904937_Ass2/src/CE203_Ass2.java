import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class CE203_0_Ass2 extends JPanel {

    /**
     * Start: Variables
     * */
    private final int NUM_IMAGES = 13;
    private final int sizeOfCell = 24;
    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;
    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;
    private final int N_MINES = 45;
    private final int numberOfRows = 16;
    private final int numberOfColumns = 16;
    private final int WIDTH = numberOfColumns * sizeOfCell;
    private final int HEIGHT = numberOfRows * sizeOfCell;
    private int[] field;
    public static boolean insideGame;
    private int minesLeft;
    private Image[] img;
    private int allCells;
    private long startTime;
    private long endTime;
    private float estimatedTime;
    private ArrayList<Double> updatedScores;
    /**
     * End: Variables
     * */

    public CE203_0_Ass2() { //Constructor
        init(); //calling a method below
    }

    private void init() { //initiating the Panel size, and saving images in an Object array
        setPreferredSize(new Dimension(WIDTH, HEIGHT+100));
        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {
            var path = "src/images/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }
        addMouseListener(new MouseDetector()); //adding a mouse listener to react to mouse events
        updateGame(); //creating a game into the Frame
    }

    private void updateGame() { //method that randomizes the position of mines every time it is called

        int cell;

        var random = new Random();
        insideGame = true;
        minesLeft = N_MINES;

        allCells = numberOfRows * numberOfColumns;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {

            field[i] = COVER_FOR_CELL;
        }

        System.out.println("Mines left: " + minesLeft);

        int i = 0;
        startTime = System.currentTimeMillis();

        while (i < N_MINES) { //while there are mines left

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != COVERED_MINE_CELL)) {

                int current_col = position % numberOfColumns;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - numberOfColumns;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + numberOfColumns - 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - numberOfColumns;
                if (cell >= 0) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + numberOfColumns;
                if (cell < allCells) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (numberOfColumns - 1)) {
                    cell = position - numberOfColumns + 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + numberOfColumns + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void emptyCellsFinder(int j) { //method for identifying empty cells, that have no mines around it.

        int current_col = j % numberOfColumns;
        int cell;

        if (current_col > 0) {
            cell = j - numberOfColumns - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        emptyCellsFinder(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        emptyCellsFinder(cell);
                    }
                }
            }

            cell = j + numberOfColumns - 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        emptyCellsFinder(cell);
                    }
                }
            }
        }

        cell = j - numberOfColumns;
        if (cell >= 0) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    emptyCellsFinder(cell);
                }
            }
        }

        cell = j + numberOfColumns;
        if (cell < allCells) {
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    emptyCellsFinder(cell);
                }
            }
        }

        if (current_col < (numberOfColumns - 1)) {
            cell = j - numberOfColumns + 1;
            if (cell >= 0) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        emptyCellsFinder(cell);
                    }
                }
            }

            cell = j + numberOfColumns + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        emptyCellsFinder(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        emptyCellsFinder(cell);
                    }
                }
            }
        }
    }
    /**
     * As the class extend the JPanel we call the paintComponent to render the game.
     * paintComponent is executed on itself, it is not called anywhere in the class.
     * repaint() controls the update() -> paint() cycle (it repaints the components).
     * */
    @Override
    public void paintComponent(Graphics g) {
        updatedScores = new ArrayList<>();

        int uncovered = 0;

        for (int i = 0; i < numberOfRows; i++) {

            for (int j = 0; j < numberOfColumns; j++) {

                int cell = field[(i * numberOfColumns) + j];

                if (insideGame && cell == MINE_CELL) {
                    insideGame = false;
                }

                if (!insideGame) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }
                } else {
                    endTime = System.currentTimeMillis(); //here is the time when player loses/wins the game
                    estimatedTime = (endTime - startTime) / 1000F / 60F; //calculates estimated time (in minutes)
                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncovered++;
                    }
                }

                g.drawImage(img[cell], (j * sizeOfCell), //draws images to the panel
                        (i * sizeOfCell), this);
            }
        }
        g.setColor(Color.GRAY); //creating the gray rectangle at the bottom
        g.fillRect(0,384,384,100);
        if (uncovered == 0 && insideGame) { // WIN
            insideGame = false;
            Scoreboard sb = new Scoreboard();
            sb.setWinTime(estimatedTime); //sending estimated time to be entered in the text file
            sb.writeFile(); // creating and writing into the text file
            updatedScores = sb.readFile(); //reading out the data from the text file
            System.out.println("You have won!");


            g.setColor(Color.GREEN); //color for victory
            g.drawString("VICTORY", (WIDTH/2) - (60 / 2), 400); //(BORDER_WIDTH/2) - (STRING_WIDTH / 2)
            for (int i = 0; i < 5; i++) { // i < 5 for top five scores to be displayed
                Random random = new Random();
                g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200))); //setting color for score boxes in the range of rgb(0-200,0-200,0-200);
                g.fillRect(0,HEIGHT + 20 + (i*16)  ,WIDTH,100);
                if (i < updatedScores.size()) {
                    BigDecimal bigDecimal = new BigDecimal(String.valueOf(updatedScores.get(i))); //stores the double value from the ArrayList to BigDecimal as the String
                    int minutes = bigDecimal.intValue(); //minutes simply become the integer part because estimated time is in minutes
                    g.setColor(new Color(255,255,255)); //setting text color as white then drawing a string, minutes as an integer value and then subtracting double by an integer value leaving only decimal places and multiplying by 60 for displaying seconds
                    g.drawString(i+1 + ") " + minutes + " minutes and " + bigDecimal.subtract(new BigDecimal(minutes)).multiply(new BigDecimal(60)).toPlainString() + " seconds", 25, HEIGHT + (20 + (i*16)) + 14 );
                } else {
                    g.setColor(new Color(255,255,255)); //just draws "None" simply if there are not enough score data in the Text File
                    g.drawString(i+1 + ") " + "None", 25, HEIGHT + (20 + (i*16)) + 14 );
                }
            }
        } else if (!insideGame) { // GAME OVER
            Scoreboard sb = new Scoreboard();
            updatedScores = sb.readFile();
            System.out.println("Game Over!");

            g.setColor(Color.RED); //color for game over
            g.drawString("GAME OVER!", (WIDTH/2) - (60 / 2), 400); //(BORDER_WIDTH/2) - (STRING_WIDTH / 2)
            //next for loop is the same as the one above for winning a game
            for (int i = 0; i < 5; i++) {
                Random random = new Random();
                g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));
                g.fillRect(0,HEIGHT + 20 + (i*16)  ,WIDTH,100);
                if (i < updatedScores.size()) {
                    BigDecimal bigDecimal = new BigDecimal(String.valueOf(updatedScores.get(i)));
                    int minutes = bigDecimal.intValue();
                    g.setColor(new Color(255,255,255));
                    g.drawString(i+1 + ") " + minutes + " minutes and " + bigDecimal.subtract(new BigDecimal(minutes)).multiply(new BigDecimal(60)).toPlainString() + " seconds", 25, HEIGHT + (20 + (i*16)) + 14 );
                } else {
                    g.setColor(new Color(255,255,255));
                    g.drawString(i+1 + ") " + "None", 25, HEIGHT + (20 + (i*16)) + 14 );
                }
            }
        } else {
            g.setColor(Color.YELLOW); //Covid Awareness
            g.drawString("Stay Home!", (WIDTH/2) - (60 / 2), 435); //(BORDER_WIDTH/2) - (STRING_WIDTH / 2)
        }
    }

    private class MouseDetector extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) { //detects if mouse is pressed, and where is pressed.
            //coordinates on x and y on the panel
            int x = e.getX();
            int y = e.getY();

            int cCol = x / sizeOfCell;
            int cRow = y / sizeOfCell;

            boolean doRepaint = false;

            if (!insideGame) { //starts the game all over again after losing and clicking cell
                updateGame();
                repaint(); //repaints the panel
            }

            if ((x < numberOfColumns * sizeOfCell) && (y < numberOfRows * sizeOfCell)) { //if the range of x and y coords is in the cells part of panel

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[(cRow * numberOfColumns) + cCol] > MINE_CELL) {

                        doRepaint = true;

                        if (field[(cRow * numberOfColumns) + cCol] <= COVERED_MINE_CELL) {

                            if (minesLeft > 0) {
                                field[(cRow * numberOfColumns) + cCol] += MARK_FOR_CELL;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                System.out.println("Mines left: " + msg);
                            } else {
                                System.out.println("No marks left");
                            }
                        } else {

                            field[(cRow * numberOfColumns) + cCol] -= MARK_FOR_CELL;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            System.out.println("Mines left: " + msg);
                        }
                    }

                } else {

                    if (field[(cRow * numberOfColumns) + cCol] > COVERED_MINE_CELL) {
                        return;
                    }

                    if ((field[(cRow * numberOfColumns) + cCol] > MINE_CELL)
                            && (field[(cRow * numberOfColumns) + cCol] < MARKED_MINE_CELL)) {

                        field[(cRow * numberOfColumns) + cCol] -= COVER_FOR_CELL;
                        doRepaint = true;

                        if (field[(cRow * numberOfColumns) + cCol] == MINE_CELL) {
                            insideGame = false;
                        }

                        if (field[(cRow * numberOfColumns) + cCol] == EMPTY_CELL) {
                            emptyCellsFinder((cRow * numberOfColumns) + cCol);
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}

class Main extends JFrame { //class for creating a JFrame/Window

    public Main() {
        add(new CE203_0_Ass2());
        setResizable(false);
        pack();
        setTitle("0");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Keyboard keyListener = new Keyboard();
        addKeyListener(keyListener);
    }

    public static void main(String[] args) {
        new Main();
    }
}

class Scoreboard{ //class for reading, writing a file, storing winTime for scores.

    private float winTime;
    private ArrayList<Double> scores;

    public ArrayList<Double> readFile() { //reading the file
        scores = new ArrayList<>();
        try {
            File openFile = new File("scoreboard.txt");
            if (openFile.exists()) {
                Scanner readFile = new Scanner(openFile);

                while (readFile.hasNextLine()) {
                    String line = readFile.nextLine();
                    String[] arrayScores = line.split("\n");
                    scores.add(Double.parseDouble(arrayScores[0]));
                    Collections.sort(scores);
                }
                readFile.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public void writeFile() { //creating and writing into the text file
        try { //append win times (scores) to the text file
            File scoreBoard = new File("scoreboard.txt");
            FileWriter scoreBoardWriter = new FileWriter(scoreBoard, true);
            BufferedWriter bufferedScoreWriter = new BufferedWriter(scoreBoardWriter);
            bufferedScoreWriter.write(winTime + "\n");
            bufferedScoreWriter.close();
            scoreBoardWriter.close();
        } catch (IOException e) {
            System.err.println("Something is wrong with the Text file!");
            e.printStackTrace();
        }
    }

    public void setWinTime(float winTime) {
        this.winTime = winTime;
    }
}

class Keyboard extends KeyAdapter { //Keybindings

    @Override
    public void keyPressed(KeyEvent e) { //detects if the specific keys are pressed and then executes...
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                System.out.println("This is minesweeper!");
                break;
            case KeyEvent.VK_S:
                System.out.println("Keybindings are in this class");
                break;
            default:
                break;
        }
    }
}
