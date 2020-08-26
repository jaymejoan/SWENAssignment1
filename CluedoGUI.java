import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class CluedoGUI {

    public static Board board;
    public JComponent drawBoard;

    public int clickX = Integer.MAX_VALUE;
    public int clickY = Integer.MAX_VALUE;

    private static String[] cardNames = new String[]{"Miss Scarlett", "Mr. Green", "Colonel Mustard", "Professor Plum", "Mrs. Peacock", "Mrs. White",
            "Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner",
            "Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};
    private static String[] tokensName = {"Miss Scarlett", "Mr. Green", "Colonel Mustard", "Professor Plum", "Mrs. Peacock", "Mrs. White"};
    private static String[] weaponsName = {"Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"};
    private static String[] roomsName = {"Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};
    private static ArrayList<String> chosenCards = new ArrayList<>();


    private JFrame frame;
    private Game game;
    private int numOfPlayers = 0;
    private Dimension screenSize;
    private HashMap<String, JRadioButton> buttons = new HashMap<>();
    private ArrayList<String> selectedCharacters = new ArrayList<>();
    private HashMap<String, String> players = new HashMap<>();              // map containing player usernames and character names
    private ArrayList<String> selectedUserNames = new ArrayList<>();
    private String username;
//    public JPanel bottomLeft;


    public CluedoGUI(Board b, Game g) {
        game = g;
        board = b;
        runStartMenu();
        while (selectedCharacters.size() < numOfPlayers)
            System.out.flush();
        initialise();

//        for (int i = 0; i < selectedUserNames.size(); i++) {
//            System.out.println("i: " + i + " username: " + selectedUserNames.get(i));
//        }

//        for (Map.Entry<String, String> s : players.entrySet()) {
//            System.out.println(" user: " + s.getKey() + " character: " + s.getValue());
//        }
//        frame.setVisible(true);
    }

    public void initialise() {
        // creates frame
        frame = new JFrame("CLUEDO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 850);

        // draws board
        this.drawBoard = new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
                board.drawBoardGUI(g2d);
                System.out.println("Board printed.");
                // if greater than 0-24 or 0-25 don't accept.
            }
        };


        drawBoard.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                System.out.println("X = " + e.getX() + " Y = " + e.getY());
                ;
                System.out.println("BoardX = " + Math.floor((e.getX() - 10) / 22) + " BoardY = " + Math.floor((e.getY() - 10) / 22));
                onClick(e);
            }
        });


        //Creating the MenuBar and adding components
        JMenuBar menuBar = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        menuBar.add(m1);
        menuBar.add(m2);

        //Creating LHS panel and adding components
        JPanel leftPanel = new JPanel(); // the panel is not visible in output
        Border edge = BorderFactory.createEmptyBorder(15, 10, 10, 10);  //empty border so the button are not right up against the edge
        leftPanel.setBorder(edge);

        //component added will be layout along y axis
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // format PLAYER labels
        JPanel bottomLeft = new JPanel();
        bottomLeft.setLayout(new GridLayout(10, 1, 0, 15));
        bottomLeft.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel playersLB = new JLabel("PLAYERS");
        bottomLeft.add(playersLB);
        updatePlayerLabels(bottomLeft);

        // format ACTION buttons
        JPanel topLeft = new JPanel(new GridLayout(5, 1, 0, 15));
        topLeft.add(new JLabel("ACTIONS"));
        topLeft.add(new JButton("Roll Dice"));

        // SUGGEST button
        JButton suggestButton = new JButton("Suggest");
        suggestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsForPlayers("Suggest");
            }
        });

        // ACCUSE button
        JButton accuseButton = new JButton("Accuse");
        accuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsForPlayers("Accuse");
            }
        });

        // END TURN button
        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Please confirm you want to end your turn.", "End Turn", JOptionPane.INFORMATION_MESSAGE);
//                updatePlayerLabels(bottomLeft);
//                System.out.println("revalidating");
//                bottomLeft.revalidate();
//                bottomLeft.repaint();

            }
        });

        topLeft.add(suggestButton);
        topLeft.add(accuseButton);
        topLeft.add(endTurnButton);

        // combined panels on the left
        leftPanel.add(topLeft);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(bottomLeft);

        // bottom panel
        JPanel bottomPanel = new JPanel(new GridLayout(5, 1, 20, 15));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 120, 10, 10));

        JPanel namePanel = new JPanel(new GridLayout(1, 1, 10, 10));
        namePanel.add(new JLabel("Current Player: "));

        // format user's deck of cards
        GridLayout cardLayout = new GridLayout(1, 7, 15, 20);
        Border cardBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        JPanel charPanel = new JPanel();
        charPanel.setLayout(cardLayout);
        JPanel weaponPanel = new JPanel();
        weaponPanel.setLayout(cardLayout);
        JPanel roomPanelTop = new JPanel();
        roomPanelTop.setLayout(cardLayout);
        JPanel roomPanelBottom = new JPanel();
        roomPanelBottom.setLayout(cardLayout);

        // display Character cards
        charPanel.add(new JLabel("Character Cards: "));
        for (int i = 0; i <= 5; i++) {
            JLabel charName = new JLabel(cardNames[i], SwingConstants.CENTER);
            charName.setBorder(cardBorder);
            charPanel.add(charName);
        }

        // display Weapon cards
        weaponPanel.add(new JLabel("Weapons Cards: "));
        for (int i = 6; i <= 11; i++) {
            JLabel weaponName = new JLabel(cardNames[i], SwingConstants.CENTER);
            weaponName.setBorder(cardBorder);
            weaponPanel.add(weaponName);
        }

        // display Room cards (two rows required)
        roomPanelTop.add(new JLabel("Rooms Cards: "));
        for (int i = 12; i <= 17; i++) {
            JLabel roomName = new JLabel(cardNames[i], SwingConstants.CENTER);
            roomName.setBorder(cardBorder);
            roomPanelTop.add(roomName);
        }

        roomPanelBottom.add(new JLabel(""));
        for (int i = 18; i < cardNames.length; i++) {
            JLabel roomName = new JLabel(cardNames[i], SwingConstants.CENTER);
            roomName.setBorder(cardBorder);
            roomPanelBottom.add(roomName);
        }

        for (int i = 0; i < 3; i++)
            roomPanelBottom.add(new JLabel(""));

        bottomPanel.add(namePanel);
        bottomPanel.add(charPanel);
        bottomPanel.add(weaponPanel);
        bottomPanel.add(roomPanelTop);
        bottomPanel.add(roomPanelBottom);

        // add all components to the frame
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, drawBoard);
        frame.getContentPane().add(BorderLayout.WEST, leftPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
//        frame.setVisible(true);
    }

    /**
     * Displays the current (active) player for this turn.
     * The active player's name is highlighted in a green box.
     * The player being suggested to (has to refute card) is highlighted in red.
     * All other players are highlighted in black.
     *
     * @param bottomLeft -- the panel to be updated.
     */
    public void updatePlayerLabels(JPanel bottomLeft) {

//        JPanel newBottomLeft = new JPanel();
//        newBottomLeft.setLayout(new GridLayout(10, 1, 0, 15));
//        newBottomLeft.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        JLabel playersLB = new JLabel("PLAYERS");
//        newBottomLeft.add(playersLB);

        Border activePlayer = BorderFactory.createLineBorder(Color.GREEN, 4);
        Border refutedPlayer = BorderFactory.createLineBorder(Color.RED, 4);
        Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

        for (int i = 0; i < numOfPlayers; i++) {
            JLabel player = new JLabel(selectedUserNames.get(i), SwingConstants.CENTER);
            player.setBorder(defaultBorder);

            if (i == game.getCurrentPlayer()) {
                System.out.println("current player: " + i);
                player.setBorder(activePlayer);
                player.revalidate();
            }

            bottomLeft.add(player);
        }

//        return newBottomLeft;
    }


    /**
     * Draws the popup menu to start the game.
     * This includes asking the user for the number of players.
     */
    public void runStartMenu() {
        // popup
        JFrame startPopup = new JFrame();
        startPopup.setSize(new Dimension(500, 200));
        //position of the frame centre of the other frame
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        startPopup.setLocation(screenSize.width / 2 - startPopup.getWidth() / 2, screenSize.height / 2 - startPopup.getHeight() / 2);

        // cluedo title
        JLabel cluedo = new JLabel("CLUEDO", SwingConstants.CENTER);
        cluedo.setFont(new Font("Serif", Font.BOLD, 50));
        startPopup.getContentPane().add(BorderLayout.NORTH, cluedo);

        // displays number of player options
        JPanel selectPanel = new JPanel();
        JLabel question = new JLabel("Select numbers of players   ", SwingConstants.CENTER);
        question.setFont(new Font("Serif", Font.BOLD, 20));
        selectPanel.add(question);
        String[] choices = {"3", "4", "5", "6"};
        JComboBox<String> combobox = new JComboBox<>(choices);
        combobox.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectPanel.add(combobox);

        // creates start button
        startPopup.getContentPane().add(BorderLayout.CENTER, selectPanel);
        JButton startButton = new JButton("START");
        JPanel bottom = new JPanel();
        bottom.add(startButton);
        startPopup.getContentPane().add(BorderLayout.SOUTH, bottom);
        startPopup.setVisible(true);

        // gets number of players
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //when button is pressed
//                System.out.println("\n\nnnumber of players : " + Integer. valueOf((String) Objects.requireNonNull(combobox.getSelectedItem())));
                numOfPlayers = Integer.valueOf((String) Objects.requireNonNull(combobox.getSelectedItem()));
                startPopup.setVisible(false);
//                frame.setVisible(true);

                JFrame selectPlayersFrame = new JFrame();
                initialiseMap();
                selectPlayers(selectPlayersFrame, 1);

            }
        });

        while (numOfPlayers == 0) System.out.flush();
//        return;
    }

    public void onClick(MouseEvent e) {
        int xTile = (int) Math.floor((e.getX() - 10) / 22);
        int yTile = (int) Math.floor((e.getY() - 10) / 22);

        clickX = xTile;
        clickY = yTile;

//        for(int i = 0; i < 25; i++) {
//            for(int j = 0; j < 24; j++) {
//                if (b.board[i][j] instanceof Hallway)
//                ((Hallway)b.board[i][j]).visited = false;
//            }
//        }

//        ((Hallway)b.board[yTile][xTile]).visited = true;

//        board.tokenChars.get("miss scarlett").x = xTile;
//        board.tokenChars.get("miss scarlett").y = yTile;
//        drawBoard.repaint();
    }


    /**
     * Initialises Map containing Character names for the user to select.
     */
    public void initialiseMap() {
        for (int i = 0; i <= 5; i++) {
            JRadioButton b = new JRadioButton(cardNames[i]);
            buttons.put(cardNames[i], b);
            b.setActionCommand(cardNames[i]);
        }
    }

    /**
     * Allows users to choose their username and character name.
     */
    public void selectPlayers(JFrame selectPlayersFrame, int currentPlayer) {

        // format frame
        selectPlayersFrame.setVisible(true);
        selectPlayersFrame.setSize(new Dimension(500, 500));
        selectPlayersFrame.setLocation(screenSize.width / 2 - selectPlayersFrame.getWidth() / 2, screenSize.height / 2 - selectPlayersFrame.getHeight() / 2);
        JLabel setupLabel = new JLabel("SET UP", SwingConstants.CENTER);
        setupLabel.setFont(new Font("Serif", Font.BOLD, 30));

        // format username panel
        JPanel usernamePanel = new JPanel();
//        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        usernamePanel.setLayout(new GridLayout(3, 1, 20, 5));
        JLabel userHeader = new JLabel("Player " + currentPlayer + " please enter your username.", SwingConstants.CENTER);

        // get username input
        JTextField input = new JTextField(20);
        input.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                // don't fire an event on backspace or delete
//                if (e.getKeyCode() == 8 || e.getKeyCode() == 127)
//                    return;
                input.postActionEvent();
                username = input.getText();

            }
        });

//        System.out.println("username inputted: " + username);
        usernamePanel.add(userHeader, SwingConstants.CENTER);
        usernamePanel.add(setupLabel, SwingConstants.CENTER);
        usernamePanel.add(input);

        if (username != null) selectedUserNames.add(username);

        // format character panel
        JPanel characterPanel = new JPanel(new GridLayout(8, 1, 20, 10));
        JLabel charHeader = new JLabel("Player " + currentPlayer + " please select your character.", SwingConstants.CENTER);
        characterPanel.add(charHeader);
        ButtonGroup buttonGroup = new ButtonGroup();

        // displays character options
        for (JRadioButton b : buttons.values()) {
            characterPanel.add(b);
            buttonGroup.add(b);
        }

        // assign player to character
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // stop display once all players have selected
                if (currentPlayer == numOfPlayers) {
                    selectedCharacters.add(buttonGroup.getSelection().getActionCommand());
//                    System.out.println("final size: " + selectedCharacters.size());
                    if (username != null) selectedUserNames.add(username);
                    players.put(username, buttonGroup.getSelection().getActionCommand());
                    selectPlayersFrame.setVisible(false);
                    displaySelectedCharacters();
                    return;
                }

                // save username and selected character
                String selectedCharacter = buttonGroup.getSelection().getActionCommand();
                players.put(username, selectedCharacter);

                selectedCharacters.add(selectedCharacter);
//                System.out.println("add size: " + selectedCharacters.size());
                System.out.println((buttonGroup.getSelection().getActionCommand()));

                // remove selected character from list of options
                buttons.remove(selectedCharacter);
                selectPlayersFrame.setVisible(false);
                selectPlayers(new JFrame(), currentPlayer + 1);


            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submit);

        // remove selected character from options
        selectPlayersFrame.getContentPane().add(BorderLayout.NORTH, usernamePanel);
        selectPlayersFrame.getContentPane().add(BorderLayout.CENTER, characterPanel);
        selectPlayersFrame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);

    }

    /**
     * Display characters selected by each Player.
     */
    public void displaySelectedCharacters() {

        JFrame confirmedFrame = new JFrame();
        JPanel confirmedPanel = new JPanel();
        confirmedFrame.setSize(new Dimension(500, 250));
        confirmedFrame.setLocation(screenSize.width / 2 - confirmedPanel.getWidth() / 2, screenSize.height / 2 - confirmedPanel.getHeight() / 2);
        confirmedFrame.setVisible(true);

        confirmedPanel.setLayout(new BoxLayout(confirmedPanel, BoxLayout.Y_AXIS));
        confirmedPanel.setBorder(BorderFactory.createEmptyBorder(30, 160, 10, 10));

        for (int i = 0; i < selectedUserNames.size(); i++) {
            confirmedPanel.add(new JLabel("Player " + (i + 1) + " (" + selectedUserNames.get(i) + ") : " + selectedCharacters.get(i), SwingConstants.CENTER));
        }


        JButton startGame = new JButton("START GAME");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(startGame);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmedFrame.setVisible(false);
                frame.setVisible(true);

            }
        });

        confirmedFrame.getContentPane().add(BorderLayout.NORTH, new JLabel("These are the selected players:", SwingConstants.CENTER));
        confirmedFrame.getContentPane().add(BorderLayout.CENTER, confirmedPanel);
        confirmedFrame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);

    }

    /**
     * Displays the pop-up menu when Suggest or Accuse button is selected.
     *
     * @param action
     */
    public void optionsForPlayers(String action) {
        JFrame suggestFrame = new JFrame();
        suggestFrame.setSize(new Dimension(action.equalsIgnoreCase("suggest") ? 500 : 700, 250));
        //position of the frame centre of the other frame
        suggestFrame.setLocation(frame.getWidth() / 2 - suggestFrame.getWidth() / 2, frame.getHeight() / 2 - suggestFrame.getHeight() / 2);


        JPanel descriptPanel = new JPanel();
        descriptPanel.setLayout(new BoxLayout(descriptPanel, BoxLayout.Y_AXIS));
        descriptPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        //suggest title
        JLabel suggestLB = new JLabel(action, SwingConstants.CENTER);
        suggestLB.setFont(new Font("Serif", Font.BOLD, 30));

        //description
        descriptPanel.add(new JLabel("Please select the cards you would like to " + action.toLowerCase(), SwingConstants.CENTER));
        descriptPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        if (action.equalsIgnoreCase("suggest")) {
            descriptPanel.add(new JLabel("Room: ", SwingConstants.CENTER));
        }

        //drop down for choosing
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        optionsPanel.add(new JLabel("Character: "));
        JComboBox<String> tokensOption = new JComboBox<>(tokensName);
        optionsPanel.add(tokensOption);

        optionsPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        optionsPanel.add(new JLabel("Weapon: "));
        JComboBox<String> weaponsOption = new JComboBox<>(weaponsName);
        optionsPanel.add(weaponsOption);

        JComboBox<String> roomsOption = null;
        if (action.equalsIgnoreCase("accuse")) {
            optionsPanel.add(Box.createRigidArea(new Dimension(30, 0)));
            optionsPanel.add(new JLabel("Room: "));
            roomsOption = new JComboBox<>(roomsName);
            optionsPanel.add(roomsOption);
        }

        //centre panel
        JPanel centrePanel = new JPanel();
        centrePanel.add(BorderLayout.NORTH, descriptPanel);
        centrePanel.add(BorderLayout.CENTER, optionsPanel);

        //bottom panel
        JPanel bottomPanel = new JPanel();
        JButton confirmSuggest = new JButton("Confirm");
        bottomPanel.add(confirmSuggest);
        JComboBox<String> finalRoomsOption = roomsOption;
        confirmSuggest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chosenCards.add(String.valueOf(tokensOption.getSelectedItem()));
                chosenCards.add(String.valueOf(weaponsOption.getSelectedItem()));
                if (finalRoomsOption != null) {
                    chosenCards.add(String.valueOf(finalRoomsOption.getSelectedItem()));
                }
                suggestFrame.setVisible(false);
            }
        });

        suggestFrame.getContentPane().add(BorderLayout.NORTH, suggestLB);
        suggestFrame.getContentPane().add(BorderLayout.CENTER, centrePanel);
        suggestFrame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        suggestFrame.setVisible(true);
    }

    /**
     * Gets the selected Character names.
     *
     * @return
     */
    public ArrayList<String> getSelectedCharacters() {
//        System.out.println("get size: " + selectedCharacters.size());
        return selectedCharacters;
    }

    /**
     * Gets the number of players in the game.
     *
     * @return
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }


}