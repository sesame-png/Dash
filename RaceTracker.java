import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import java.util.Calendar;

public class RaceTracker extends JPanel{
   public static Race[] raceList;
   public static int raceCounter;
   static int size;
   private int target;
   private int active;
   private JTextField nameBox;
   private JTextField monthBox;
   private JTextField dayBox;
   private JTextField yearBox;
   private JComboBox typeBox;
   private JTextField distBox;
   private JTextField hourBox;
   private JTextField minBox;
   private JTextField secBox;
   private JTextField tagBox;
   private JComboBox sortBox;
   private JComboBox adBox;
   private String[] sortStrings;
   private JLabel message;
   private JTextField searchBox;
   private String[][] raceGrid;
   private static JFrame RaceFrame;
   private static JFrame AddFrame;
   private static JFrame DeleteFrame;
   private static JFrame SearchFrame;
   private static JFrame EditFrame;
   private static JFrame ViewFrame;
   private JLabel nameLabel;
   private JLabel dateLabel;
   private JLabel typeLabel;
   private JLabel distLabel;
   private JLabel timeLabel;
   private JLabel tagLabel;

   //GUI************************************
   public RaceTracker(){ 
      setLayout(new BorderLayout());
      
      String[] columnNames = {"Name",
                        "Date",
                        "Type", 
                        "Distance",
                        "Time",
                        "Tags"};                 
      raceGrid = new String[size][6];
      
      for (int i = 0; i < raceCounter; i++){
         raceGrid[i][0] = "" + raceList[i].getName();
         raceGrid[i][1] = "" + raceList[i].getDate().getMonth() + "/" + raceList[i].getDate().getDay() + "/" + raceList[i].getDate().getYear();
         raceGrid[i][2] = "" + raceList[i].getType();
         raceGrid[i][3] = "" + raceList[i].getDistance();
         raceGrid[i][4] = "" + raceList[i].getTime().getHour() + ":" + raceList[i].getTime().getMin() + ":" + raceList[i].getTime().getSec();
         raceGrid[i][5] = "" + raceList[i].getTags();
      }
      
      JScrollPane scrollList = new JScrollPane();
      scrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      add(scrollList);
      
      JTable raceTable = new JTable(raceGrid, columnNames);
      scrollList.add(raceTable);
      scrollList.setViewportView(raceTable);
      raceTable.setPreferredScrollableViewportSize(new Dimension(850, 230));
      
      //buttons
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(0, 6));
      
      String[] sortStrings = {"Name",
                          "Date",
                          "Type",
                          "Distance",
                          "Time"};
      sortBox = new JComboBox(sortStrings);
      sortBox.addActionListener(new sortSelected());
      buttonPanel.add(sortBox);
      
      String[] adStrings = {"Ascending",
                          "Descending"};
      adBox = new JComboBox(adStrings);
      adBox.addActionListener(new adSelected());
      buttonPanel.add(adBox);
      
      JButton addButton = new JButton("Add");
      addButton.addActionListener(new addOpened());
      buttonPanel.add(addButton);
      JButton viewButton = new JButton("View");
      viewButton.addActionListener(new viewOpened());
      buttonPanel.add(viewButton);
      JButton searchButton = new JButton("Search");
      searchButton.addActionListener(new searchOpened());
      buttonPanel.add(searchButton);
      JButton exitButton = new JButton("Exit");
      exitButton.addActionListener(new exitPressed());
      buttonPanel.add(exitButton);
      
      add(scrollList, BorderLayout.NORTH);
      add(buttonPanel, BorderLayout.SOUTH);
   }
   
   //sort**********************************************
   private class sortSelected implements ActionListener{
      public void actionPerformed(ActionEvent e){
         String sortType = sortBox.getSelectedItem().toString();
         
         if(sortType.equals("Name")){
            for(int i = 0; i < raceCounter; i++){
               for(int j = 1; j < (raceCounter - i); j++){
                  if(raceList[j - 1].getName().compareTo(raceList[j].getName()) > 0){
                     Race temp = raceList[j - 1];
                     raceList[j - 1] = raceList[j];
                     raceList[j] = temp;
                  }
               }
            }
         }
         if(sortType.equals("Date")){
            for(int i = 0; i < raceCounter; i++){
               for(int j = 1; j < (raceCounter - i); j++){
                  if(compareDate(raceList[j - 1].getDate(), raceList[j].getDate())){
                     Race temp = raceList[j - 1];
                     raceList[j - 1] = raceList[j];
                     raceList[j] = temp;
                  }
               }
            }
         }
         if(sortType.equals("Type")){
            for(int i = 0; i < raceCounter; i++){
               for(int j = 1; j < (raceCounter - i); j++){
                  if(raceList[j - 1].getType().compareTo(raceList[j].getType()) > 0){
                     Race temp = raceList[j - 1];
                     raceList[j - 1] = raceList[j];
                     raceList[j] = temp;
                  }
               }
            }
         }
         if(sortType.equals("Distance")){
            for(int i = 0; i < raceCounter; i++){
               for(int j = 1; j < (raceCounter - i); j++){
                  if(raceList[j - 1].getDistance() > raceList[j].getDistance()){
                     Race temp = raceList[j - 1];
                     raceList[j - 1] = raceList[j];
                     raceList[j] = temp;
                  }
               }
            }
         }
         if(sortType.equals("Time")){
            for(int i = 0; i < raceCounter; i++){
               for(int j = 1; j < (raceCounter - i); j++){
                  if(raceList[j - 1].getTime().getTotal() > raceList[j].getTime().getTotal()){
                     Race temp = raceList[j - 1];
                     raceList[j - 1] = raceList[j];
                     raceList[j] = temp;
                  }
               }
            }
         }
         
         for (int i = 0; i < raceCounter; i++){
            raceGrid[i][0] = "" + raceList[i].getName();
            raceGrid[i][1] = "" + raceList[i].getDate().getMonth() + "/" + raceList[i].getDate().getDay() + "/" + raceList[i].getDate().getYear();
            raceGrid[i][2] = "" + raceList[i].getType();
            raceGrid[i][3] = "" + raceList[i].getDistance();
            raceGrid[i][4] = "" + raceList[i].getTime().getHour() + ":" + raceList[i].getTime().getMin() + ":" + raceList[i].getTime().getSec();
            raceGrid[i][5] = "" + raceList[i].getTags();
         }
         RaceFrame.setSize(900, 318);
         RaceFrame.setSize(900, 317);
         adBox.setSelectedIndex(0);
      }
   }
   
   public boolean compareDate(Dates a, Dates b){
      Calendar calendar1 = Calendar.getInstance();
      Calendar calendar2 = Calendar.getInstance();
      calendar1.clear(); 
      calendar2.clear();
      calendar1.set(Calendar.MONTH, a.getMonth() - 1);
      calendar1.set(Calendar.DAY_OF_MONTH, a.getDay());
      calendar1.set(Calendar.YEAR, a.getYear());
      calendar2.set(Calendar.MONTH, b.getMonth() - 1);
      calendar2.set(Calendar.DAY_OF_MONTH, b.getDay());
      calendar2.set(Calendar.YEAR, b.getYear());
        
      boolean bool = false;
      long miliSecondForDate1 = calendar1.getTimeInMillis();
      long miliSecondForDate2 = calendar2.getTimeInMillis();
      if(miliSecondForDate1 > miliSecondForDate2){
         bool = true;
      }
      
      return bool;
   }
   
   //ascending/descending******************************
   private class adSelected implements ActionListener{
      public void actionPerformed(ActionEvent e){
         String adType = adBox.getSelectedItem().toString();
         int index = raceCounter - 1;
         
         if(adType.equals("Descending")){
            for (int i = 0; i < raceCounter; i++){
               raceGrid[i][0] = "" + raceList[index].getName();
               raceGrid[i][1] = "" + raceList[index].getDate().getMonth() + "/" + raceList[i].getDate().getDay() + "/" + raceList[i].getDate().getYear();
               raceGrid[i][2] = "" + raceList[index].getType();
               raceGrid[i][3] = "" + raceList[index].getDistance();
               raceGrid[i][4] = "" + raceList[index].getTime().getHour() + ":" + raceList[i].getTime().getMin() + ":" + raceList[i].getTime().getSec();
               raceGrid[i][5] = "" + raceList[index].getTags();
               index--;
            }
         }
         if(adType.equals("Ascending")){
            for (int i = 0; i < raceCounter; i++){
               raceGrid[i][0] = "" + raceList[i].getName();
               raceGrid[i][1] = "" + raceList[i].getDate().getMonth() + "/" + raceList[i].getDate().getDay() + "/" + raceList[i].getDate().getYear();
               raceGrid[i][2] = "" + raceList[i].getType();
               raceGrid[i][3] = "" + raceList[i].getDistance();
               raceGrid[i][4] = "" + raceList[i].getTime().getHour() + ":" + raceList[i].getTime().getMin() + ":" + raceList[i].getTime().getSec();
               raceGrid[i][5] = "" + raceList[i].getTags();
            }
         }
         RaceFrame.setSize(900, 318);
         RaceFrame.setSize(900, 317);
      }
   }

   //view**********************************************
   private class viewOpened implements ActionListener{
      public void actionPerformed(ActionEvent e){
         SearchFrame = new JFrame("Search");
         SearchFrame.setSize(250, 150);
         SearchFrame.setLocation(550, 200);
         SearchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         SearchFrame.setContentPane(new searchNameWindow());
         SearchFrame.setVisible(true); 
         SearchFrame.setIconImage(new ImageIcon("Icon.png").getImage());
      }
   }
   
   public class searchNameWindow extends JPanel{
      public searchNameWindow(){ 
         setLayout(new GridLayout(4, 0));
      
         JLabel textBox = new JLabel("Enter Name:");
         textBox.setHorizontalAlignment(JLabel.CENTER);
         add(textBox);
         
         searchBox = new JTextField("", 20);
         add(searchBox);
         
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(0, 2));
         
         JButton cancelButton = new JButton("Cancel");
         cancelButton.addActionListener(new closeNameSearch());
         buttonPanel.add(cancelButton);
         JButton searchButton = new JButton("View");
         searchButton.addActionListener(new viewPressed());
         buttonPanel.add(searchButton);
         add(buttonPanel);
         
         message = new JLabel(" ");
         message.setHorizontalAlignment(JLabel.CENTER);
         add(message);
      }
   }
   
   private class closeNameSearch implements ActionListener{
      public void actionPerformed(ActionEvent e){
         SearchFrame.setVisible(false);
      }
   }
   
   //view*********************************************
   private class viewPressed implements ActionListener{
      public void actionPerformed(ActionEvent e){
         String target = searchBox.getText();
         boolean found = false;
         for (int i = 0; i < raceCounter; i++){
            if (target.equalsIgnoreCase(raceList[i].getName())){
               active = i;
               found = true;
               
               ViewFrame = new JFrame(raceList[i].getName());
               ViewFrame.setSize(300, 300);
               ViewFrame.setLocation(500, 200);
               ViewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
               ViewFrame.setContentPane(new viewWindow());
               ViewFrame.setVisible(true);
               ViewFrame.setIconImage(new ImageIcon("Icon.png").getImage());
               SearchFrame.setVisible(false);
               break;
            }
         }
         if (found == false){
            message.setText("\"" + target + "\" was not found.");
         } 
      }
   }
   
   public class viewWindow extends JPanel{
      public viewWindow(){ 
         setLayout(new BorderLayout());
      
         JPanel labelPanel = new JPanel();
         labelPanel.setLayout(new GridLayout(6, 2));
      
         nameLabel = new JLabel(" " + raceList[active].getName());
         dateLabel = new JLabel(" " + raceList[active].getDate().getMonth() + "/" + raceList[active].getDate().getDay() + "/" + raceList[active].getDate().getYear());
         typeLabel = new JLabel(" " + raceList[active].getType());
         distLabel = new JLabel(" " + raceList[active].getDistance());
         timeLabel = new JLabel(" " + raceList[active].getTime().getHour() + ":" + raceList[active].getTime().getMin() + ":" + raceList[active].getTime().getSec());
         tagLabel = new JLabel(" " + raceList[active].getTags());
      
         JLabel[] labelArray = new JLabel[6];
         String[] columnNames = {"Name:",
                     "Date:", 
                     "Type:",
                     "Distance:",
                     "Time:",
                     "Tags:"};                
         for (int i = 0; i < 6; i++){
            labelArray[i] = new JLabel(columnNames[i]);
            labelArray[i].setHorizontalAlignment(JLabel.RIGHT);
         }
            
         labelPanel.add(labelArray[0]);
         labelPanel.add(nameLabel);
         labelPanel.add(labelArray[1]);
         labelPanel.add(dateLabel);
         labelPanel.add(labelArray[2]);
         labelPanel.add(typeLabel);
         labelPanel.add(labelArray[3]);
         labelPanel.add(distLabel);
         labelPanel.add(labelArray[4]);
         labelPanel.add(timeLabel);
         labelPanel.add(labelArray[5]);
         labelPanel.add(tagLabel);
         
         add(labelPanel, BorderLayout.CENTER);
         
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(3, 0));
      
         JButton editButton = new JButton("Edit");
         editButton.addActionListener(new editOpened());
         buttonPanel.add(editButton, BorderLayout.SOUTH);
         JButton deleteButton = new JButton("Delete");
         deleteButton.addActionListener(new deleteOpened());
         buttonPanel.add(deleteButton, BorderLayout.SOUTH);
         JButton closeButton = new JButton("Close");
         closeButton.addActionListener(new closeView());
         buttonPanel.add(closeButton, BorderLayout.SOUTH);
         
         add(buttonPanel, BorderLayout.SOUTH);
      }
   }
   
   private class closeView implements ActionListener{
      public void actionPerformed(ActionEvent e){
         ViewFrame.setVisible(false);
      }
   }
   
   //search**********************************************
   private class searchOpened implements ActionListener{
      public void actionPerformed(ActionEvent e){
         SearchFrame = new JFrame("Search");
         SearchFrame.setSize(250, 130);
         SearchFrame.setLocation(550, 200);
         SearchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         SearchFrame.setContentPane(new searchWindow());
         SearchFrame.setVisible(true); 
         SearchFrame.setIconImage(new ImageIcon("Icon.png").getImage());
      }
   }
   
   public class searchWindow extends JPanel{
      public searchWindow(){ 
         setLayout(new GridLayout(3, 0));
      
         JLabel textBox = new JLabel("Search by name or tags");
         textBox.setHorizontalAlignment(JLabel.CENTER);
         add(textBox);
         
         searchBox = new JTextField("", 20);
         add(searchBox);
                  
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(0, 2));
         
         JButton cancelButton = new JButton("Cancel");
         cancelButton.addActionListener(new closeSearch());
         buttonPanel.add(cancelButton);
         JButton searchButton = new JButton("Search");
         searchButton.addActionListener(new searchPressed());
         buttonPanel.add(searchButton);
         add(buttonPanel);
      }
   }
   
   private class searchPressed implements ActionListener{
      public void actionPerformed(ActionEvent e){
         String searchTerm = searchBox.getText().toString().trim().toLowerCase();
         int x = 0;
         
         for(int i = 0; i < raceCounter; i++){
            if(raceList[i].getName().toLowerCase().contains(searchTerm) || raceList[i].getTags().toLowerCase().contains(searchTerm)){
               raceGrid[x][0] = "" + raceList[i].getName();
               raceGrid[x][1] = "" + raceList[i].getDate().getMonth() + "/" + raceList[i].getDate().getDay() + "/" + raceList[i].getDate().getYear();
               raceGrid[x][2] = "" + raceList[i].getType();
               raceGrid[x][3] = "" + raceList[i].getDistance();
               raceGrid[x][4] = "" + raceList[i].getTime().getHour() + ":" + raceList[i].getTime().getMin() + ":" + raceList[i].getTime().getSec();
               raceGrid[x][5] = "" + raceList[i].getTags();
               x++;
            }
         }
         
         for(int i = x; i < raceCounter; i++){
            raceGrid[i][0] = "";
            raceGrid[i][1] = "";
            raceGrid[i][2] = "";
            raceGrid[i][3] = "";
            raceGrid[i][4] = "";
            raceGrid[i][5] = "";
         }
         
         RaceFrame.setSize(900, 318);
         RaceFrame.setSize(900, 317);
         SearchFrame.setVisible(false);
      }
   }
   
   private class closeSearch implements ActionListener{
      public void actionPerformed(ActionEvent e){
         SearchFrame.setVisible(false);
      }
   }
   
   
   //edit**********************************************
   private class editOpened implements ActionListener{
      public void actionPerformed(ActionEvent e){
         EditFrame = new JFrame("Edit " + raceList[active].getName());
         EditFrame.setSize(400, 300);
         EditFrame.setLocation(450, 200);
         EditFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         EditFrame.setContentPane(new editWindow());
         EditFrame.setVisible(true); 
         EditFrame.setIconImage(new ImageIcon("Icon.png").getImage());
         ViewFrame.setVisible(false);
      }
   }
   
   public class editWindow extends JPanel{
      public editWindow(){ 
         setLayout(new BorderLayout());
      
         JPanel labelPanel = new JPanel();
         labelPanel.setLayout(new GridLayout(6, 2));
      
         nameBox = new JTextField("" + raceList[active].getName(), 20);
         JPanel datePanel = new JPanel();
         datePanel.setLayout(new GridLayout(0, 3));
         monthBox = new JTextField("" + raceList[active].getDate().getMonth(), 20);
         dayBox = new JTextField("" + raceList[active].getDate().getDay(), 20);
         yearBox = new JTextField("" + raceList[active].getDate().getYear(), 20);
         datePanel.add(monthBox);
         datePanel.add(dayBox);
         datePanel.add(yearBox);
         
         String[] typeStrings = {"Run",
                          "Bike",
                          "Swim",
                          "Triathlon"};
         typeBox = new JComboBox(typeStrings);
         for(int i = 0; i < 4; i++){
            if(raceList[active].getType().equals(typeStrings[i])){
               typeBox.setSelectedIndex(i);
            }
         }
         
         distBox = new JTextField("" + raceList[active].getDistance(), 20);
         JPanel timePanel = new JPanel();
         timePanel.setLayout(new GridLayout(0, 3));
         hourBox = new JTextField("" + raceList[active].getTime().getHour(), 20);
         minBox = new JTextField("" + raceList[active].getTime().getMin(), 20);
         secBox = new JTextField("" + raceList[active].getTime().getSec(), 20);
         timePanel.add(hourBox);
         timePanel.add(minBox);
         timePanel.add(secBox);
         tagBox = new JTextField("" + raceList[active].getTags(), 20);
      
         JLabel[] labelArray = new JLabel[6];
         String[] columnNames = {"Name:",
                     "Date:", 
                     "Type:",
                     "Distance:",
                     "Time:",
                     "Tags:"};                
         for (int i = 0; i < 6; i++){
            labelArray[i] = new JLabel(columnNames[i]);
            labelArray[i].setHorizontalAlignment(JLabel.RIGHT);
         }
            
         labelPanel.add(labelArray[0]);
         labelPanel.add(nameBox);
         labelPanel.add(labelArray[1]);
         labelPanel.add(datePanel);
         labelPanel.add(labelArray[2]);
         labelPanel.add(typeBox);
         labelPanel.add(labelArray[3]);
         labelPanel.add(distBox);
         labelPanel.add(labelArray[4]);
         labelPanel.add(timePanel);
         labelPanel.add(labelArray[5]);
         labelPanel.add(tagBox);
         
         add(labelPanel, BorderLayout.CENTER);
         
         JPanel southPanel = new JPanel();
         southPanel.setLayout(new GridLayout(2, 0));
      
         message = new JLabel("");
         message.setHorizontalAlignment(JLabel.CENTER);
         southPanel.add(message);
         
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(0, 2));
         
         JButton cancelButton = new JButton("Cancel");
         cancelButton.addActionListener(new cancelEdit());
         buttonPanel.add(cancelButton);
         JButton saveButton = new JButton("Save");
         saveButton.addActionListener(new savePressed());
         buttonPanel.add(saveButton);
         southPanel.add(buttonPanel);
         
         add(southPanel, BorderLayout.SOUTH);
      }
   }
   
   private class cancelEdit implements ActionListener{
      public void actionPerformed(ActionEvent e){
         EditFrame.setVisible(false);
      }
   }

   private class savePressed implements ActionListener{
      public void actionPerformed(ActionEvent e){
         try {
            raceList[active].setName(nameBox.getText().trim());
            
            int month = Integer.parseInt(monthBox.getText().trim());
            int day = Integer.parseInt(dayBox.getText().trim());
            int year = Integer.parseInt(yearBox.getText().trim());
            Dates dt = new Dates(month, day, year);
            raceList[active].setDate(dt);
            
            raceList[active].setType(typeBox.getSelectedItem().toString());
            raceList[active].setDistance(Double.parseDouble(distBox.getText().trim()));
            
            int hour = Integer.parseInt(hourBox.getText().trim());
            int minute = Integer.parseInt(minBox.getText().trim());
            int second = Integer.parseInt(secBox.getText().trim());
            Times ti = new Times(hour, minute, second);
            raceList[active].setTime(ti);
            
            raceList[active].setTags(tagBox.getText().trim());
            
            EditFrame.setVisible(false);
            
            for (int i = 0; i < raceCounter; i++){
               raceGrid[i][0] = "" + raceList[i].getName();
               raceGrid[i][1] = "" + raceList[i].getDate().getMonth() + "/" + raceList[i].getDate().getDay() + "/" + raceList[i].getDate().getYear();
               raceGrid[i][2] = "" + raceList[i].getType();
               raceGrid[i][3] = "" + raceList[i].getDistance();
               raceGrid[i][4] = "" + raceList[i].getTime().getHour() + ":" + raceList[i].getTime().getMin() + ":" + raceList[i].getTime().getSec();
               raceGrid[i][5] = "" + raceList[i].getTags();
            }
            RaceFrame.setSize(900, 318);
            RaceFrame.setSize(900, 317);
         }
         catch(Exception g){
            message.setText("Error occured while saving.");
         }
      }
   }
   
   //delete******************************************
   private class deleteOpened implements ActionListener{
      public void actionPerformed(ActionEvent e){
         DeleteFrame = new JFrame("Confirm");
         DeleteFrame.setSize(350, 200);
         DeleteFrame.setLocation(500, 200);
         DeleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         DeleteFrame.setContentPane(new deleteWindow());
         DeleteFrame.setVisible(true);
         DeleteFrame.setIconImage(new ImageIcon("Icon.png").getImage());
      }
   }
   
   public class deleteWindow extends JPanel{
      public deleteWindow(){ 
         setLayout(new GridLayout(3, 0));
      
         JLabel label = new JLabel("Are you sure you want to delete \"" + raceList[active].getName() + "?\"");
         label.setHorizontalAlignment(JLabel.CENTER);
         
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new FlowLayout());
         JButton yesButton = new JButton("Delete \"" + raceList[active].getName() + "\"");
         yesButton.addActionListener(new yesPressed());
         buttonPanel.add(yesButton);
         JButton noButton = new JButton("Cancel");
         noButton.addActionListener(new noPressed());
         buttonPanel.add(noButton);
         
         add(label);
         add(buttonPanel);
      }
   }

   private class yesPressed implements ActionListener{
      public void actionPerformed(ActionEvent e){
         for(int i = active; i < raceCounter; i++){
            if(i < raceCounter - 1){
               raceList[i].setName(raceList[i + 1].getName());
            
               int month = raceList[i + 1].getDate().getMonth();
               int day = raceList[i + 1].getDate().getDay();
               int year = raceList[i + 1].getDate().getYear();
               Dates dt = new Dates(month, day, year);
               raceList[i].setDate(dt);
            
               raceList[i].setType(raceList[i + 1].getType());
               raceList[i].setDistance(raceList[i + 1].getDistance());
            
               int hour = raceList[i + 1].getTime().getHour();
               int minute = raceList[i + 1].getTime().getMin();
               int second = raceList[i + 1].getTime().getSec();
               Times ti = new Times(hour, minute, second);
               raceList[i].setTime(ti);
            
               raceList[i].setTags(raceList[i + 1].getTags());
            }
            else if(i == raceCounter - 1){
               raceList[i] = null;
            }
         }
         
         DeleteFrame.setVisible(false);
         ViewFrame.setVisible(false);
         for (int i = 0; i < raceCounter - 1; i++){
            raceGrid[i][0] = "" + raceList[i].getName();
            raceGrid[i][1] = "" + raceList[i].getDate().getMonth() + "/" + raceList[i].getDate().getDay() + "/" + raceList[i].getDate().getYear();
            raceGrid[i][2] = "" + raceList[i].getType();
            raceGrid[i][3] = "" + raceList[i].getDistance();
            raceGrid[i][4] = "" + raceList[i].getTime().getHour() + ":" + raceList[i].getTime().getMin() + ":" + raceList[i].getTime().getSec();
            raceGrid[i][5] = "" + raceList[i].getTags();
         }
         raceGrid[raceCounter - 1][0] = "";
         raceGrid[raceCounter - 1][1] = "";
         raceGrid[raceCounter - 1][2] = "";
         raceGrid[raceCounter - 1][3] = "";
         raceGrid[raceCounter - 1][4] = "";
         raceGrid[raceCounter - 1][5] = "";
         
         RaceFrame.setSize(900, 318);
         RaceFrame.setSize(900, 317);
         
         raceCounter--;
      }
   }
   
   private class noPressed implements ActionListener{
      public void actionPerformed(ActionEvent e){
         DeleteFrame.setVisible(false);
      }
   }

   //add**********************************************
   private class addOpened implements ActionListener{
      public void actionPerformed(ActionEvent e){
         AddFrame = new JFrame("Add Race");
         AddFrame.setSize(400, 300);
         AddFrame.setLocation(500, 200);
         AddFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         AddFrame.setContentPane(new addWindow());
         AddFrame.setVisible(true); 
         AddFrame.setIconImage(new ImageIcon("Icon.png").getImage());
      }
   }
   
   public class addWindow extends JPanel{
      public addWindow(){ 
         setLayout(new BorderLayout());
      
         JPanel labelPanel = new JPanel();
         labelPanel.setLayout(new GridLayout(6, 2));
      
         nameBox = new JTextField("", 20);
         
         JPanel datePanel = new JPanel();
         datePanel.setLayout(new GridLayout(0, 3));
         monthBox = new JTextField("", 20);
         dayBox = new JTextField("", 20);
         yearBox = new JTextField("", 20);
         datePanel.add(monthBox);
         datePanel.add(dayBox);
         datePanel.add(yearBox);
         
         String[] typeStrings = {"Run",
                          "Bike",
                          "Swim",
                          "Triathalon"};
         typeBox = new JComboBox(typeStrings);
         
         distBox = new JTextField("", 20);
         JPanel timePanel = new JPanel();
         timePanel.setLayout(new GridLayout(0, 3));
         hourBox = new JTextField("", 20);
         minBox = new JTextField("", 20);
         secBox = new JTextField("", 20);
         timePanel.add(hourBox);
         timePanel.add(minBox);
         timePanel.add(secBox);
         tagBox = new JTextField("", 20);
      
         JLabel[] labelArray = new JLabel[6];
         String[] columnNames = {"Name:",
                     "Date:", 
                     "Type:",
                     "Distance:",
                     "Time:",
                     "Tags:"};                
         for (int i = 0; i < 6; i++){
            labelArray[i] = new JLabel(columnNames[i]);
            labelArray[i].setHorizontalAlignment(JLabel.RIGHT);
         }
            
         labelPanel.add(labelArray[0]);
         labelPanel.add(nameBox);
         labelPanel.add(labelArray[1]);
         labelPanel.add(datePanel);
         labelPanel.add(labelArray[2]);
         labelPanel.add(typeBox);
         labelPanel.add(labelArray[3]);
         labelPanel.add(distBox);
         labelPanel.add(labelArray[4]);
         labelPanel.add(timePanel);
         labelPanel.add(labelArray[5]);
         labelPanel.add(tagBox);
         
         add(labelPanel, BorderLayout.CENTER);
         
         JPanel southPanel = new JPanel();
         southPanel.setLayout(new GridLayout(2, 0));
      
         message = new JLabel("");
         message.setHorizontalAlignment(JLabel.CENTER);
         southPanel.add(message);
         
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(0, 2));
         
         JButton cancelButton = new JButton("Cancel");
         cancelButton.addActionListener(new cancelAdd());
         buttonPanel.add(cancelButton);
         JButton addButton = new JButton("Add");
         addButton.addActionListener(new addPressed());
         buttonPanel.add(addButton);
         southPanel.add(buttonPanel);
         
         add(southPanel, BorderLayout.SOUTH);
      }
   }
   
   private class cancelAdd implements ActionListener{
      public void actionPerformed(ActionEvent e){
         AddFrame.setVisible(false);
      }
   }
   
   private class addPressed implements ActionListener{
      public void actionPerformed(ActionEvent e){
         try {
            String nm = nameBox.getText().trim();
            
            int month = Integer.parseInt(monthBox.getText().trim());
            int day = Integer.parseInt(dayBox.getText().trim());
            int year = Integer.parseInt(yearBox.getText().trim());
            Dates dt = new Dates(month, day, year);
            
            String tp = typeBox.getSelectedItem().toString();
            double di = Double.parseDouble(distBox.getText().trim());
            
            int hour = Integer.parseInt(hourBox.getText().trim());
            int minute = Integer.parseInt(minBox.getText().trim());
            int second = Integer.parseInt(secBox.getText().trim());
            Times ti = new Times(hour, minute, second);
            String tg = tagBox.getText().trim();
            
            raceList[raceCounter] = new Race(nm, dt, tp, di, ti, tg);
            raceCounter++;
            message.setText(nm + " was added.");
            
            for (int i = 0; i < raceCounter; i++){
               raceGrid[i][0] = "" + raceList[i].getName();
               raceGrid[i][1] = "" + raceList[i].getDate().getMonth() + "/" + raceList[i].getDate().getDay() + "/" + raceList[i].getDate().getYear();
               raceGrid[i][2] = "" + raceList[i].getType();
               raceGrid[i][3] = "" + raceList[i].getDistance();
               raceGrid[i][4] = "" + raceList[i].getTime().getHour() + ":" + raceList[i].getTime().getMin() + ":" + raceList[i].getTime().getSec();
               raceGrid[i][5] = "" + raceList[i].getTags();
            }
            RaceFrame.setSize(900, 318);
            RaceFrame.setSize(900, 317);
         
            AddFrame.setVisible(false);
         }
         catch(Exception g){
            message.setText("Error occured while adding.");
         }
      }
   }
   
   //exit**********************************************
   private class exitPressed implements ActionListener{
      public void actionPerformed(ActionEvent e){
         PrintWriter outfile = null;
         try{
            outfile = new PrintWriter(new FileWriter("race.txt"));
         }
         catch (Exception f)
         {
            System.out.println("Could not open file for saving");
         }
         
         outfile.print(raceCounter + "%");
         
         for (int i = 0; i < raceCounter; i++) {
            outfile.println("");
            outfile.print(raceList[i].getName() + "%");
            outfile.print(raceList[i].getDate().getMonth() + "%");
            outfile.print(raceList[i].getDate().getDay() + "%");
            outfile.print(raceList[i].getDate().getYear() + "%");
            outfile.print(raceList[i].getType() + "%");
            outfile.print(raceList[i].getDistance() + "%");
            outfile.print(raceList[i].getTime().getHour() + "%");
            outfile.print(raceList[i].getTime().getMin() + "%");
            outfile.print(raceList[i].getTime().getSec() + "%");
            outfile.print(raceList[i].getTags() + "%");
         }
         outfile.close();
         System.exit(0);
      }
   }

   //main*********************************
   public static void main(String[] args){      
      Scanner infile = null;
      try{
         infile = new Scanner (new File("race.txt"));
      }
      catch(Exception e){
         System.out.println("Error: Filename not found");
         System.exit(0);
      }
      
      infile.useDelimiter("%");
      size = infile.nextInt() + 5;
         
      raceList = new Race[size];
      
      raceCounter = 0;
      while (infile.hasNext()){
         infile.nextLine();
         String nm = infile.next();
         Dates dt = new Dates(infile.nextInt(), infile.nextInt(), infile.nextInt());
         String tp = infile.next();
         double di = infile.nextDouble();
         Times ti = new Times(infile.nextInt(), infile.nextInt(), infile.nextInt());
         String tg = infile.next();            
         
         raceList[raceCounter] = new Race(nm, dt, tp, di, ti, tg);
         raceCounter++;
      }
      infile.close();
      
      RaceFrame = new JFrame("Dash™ - Race Tracker");
      RaceFrame.setSize(900, 317);
      RaceFrame.setLocation(300, 200);
      RaceFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      RaceFrame.setContentPane(new RaceTracker());
      RaceFrame.setVisible(true);
      RaceFrame.setIconImage(new ImageIcon("Icon.png").getImage());
   }
}