package com.tor.swing.treetable.jjar;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.io.*;
import java.util.*;
import java.util.jar.*;      // needed to load a JAR file
import javax.swing.*;        // most Swing components
import javax.swing.tree.*;  
import javax.swing.border.*;

/**
 * An application that can open and display JAR files
 * in a tree structure.
 */
public class JarViewer extends JFrame {
   public JarViewer( String contentsType ) {
      // Set the frame title
      super( "JAR Viewer" );

      this.contentsType = contentsType;

      // Create a WindowListener to exit the programm when
      // closing the window
      WindowListener l = new WindowAdapter() {
	 public void windowClosing( WindowEvent e ) { 
	    System.exit(0);
	 }
      };
      addWindowListener( l );

      // define all the actions we need
      clearAction = new ClearAction( this );
      openAction = new OpenAction( this );
      exitAction = new ExitAction();
      aboutAction = new AboutAction( this );

      // to add components to a JFrame, we must add them to
      // the frame's content pane
      Container contentPane = getContentPane();

      // create and set the menu bar
      JMenuBar menuBar = createMenuBar();    
      setJMenuBar( menuBar );

      // create the tool bar
      JToolBar toolBar = createToolBar();
      contentPane.add( toolBar, BorderLayout.NORTH );

      JarFile jf = null;

      // Create empty contents and a scroll pane
      JScrollPane scrollPane;
      if( contentsType.equals( "tree" ) ) {
	 tree = new JarTree( jf );
	 scrollPane = new JScrollPane( tree );
      }
      else if( contentsType.equals( "table" ) ) {
	 table = new JarTable( jf );
	 scrollPane = new JScrollPane( table );
      }
      else {
	 treeTable = new JarTreeTable( jf );
	 scrollPane = new JScrollPane( treeTable );
      }

      contentPane.add( scrollPane, BorderLayout.CENTER );

      // Create the status bar
      JPanel statusBar = createStatusBar();
      contentPane.add( statusBar, BorderLayout.SOUTH );

      // layout all components according their preferred sizes
      pack();
      setVisible(true);
   }

   /**
    * Creates the menu bar
    */
   public JMenuBar createMenuBar() {
      // Create the menu bar
      JMenuBar menuBar = new JMenuBar();

      // The first menu is the file menu
      JMenu fileMenu = new JMenu( "File" );
      fileMenu.setMnemonic( 'F' );

      // Contruct the items from the Action objects,
      // set Mnemonics and accelerators
      JMenuItem clearItem = fileMenu.add( clearAction );
      clearItem.setMnemonic( 'C' );
      clearItem.setAccelerator( KeyStroke.getKeyStroke( 'C',
				java.awt.Event.CTRL_MASK ) );

      JMenuItem openItem = fileMenu.add( openAction );
      openItem.setMnemonic( 'O' );
      openItem.setAccelerator( KeyStroke.getKeyStroke( 'O',
			       java.awt.Event.CTRL_MASK ) ); 

      fileMenu.addSeparator();

      JMenuItem exitItem = fileMenu.add( exitAction );
      exitItem.setMnemonic( 'E' );
      exitItem.setAccelerator( KeyStroke.getKeyStroke( 'E',
			       java.awt.Event.CTRL_MASK ) );

      // add the file menu to the menu bar
      menuBar.add( fileMenu );

      // Menu for the look and feels (lnfs).
      UIManager.LookAndFeelInfo[] lnfs = 
	 UIManager.getInstalledLookAndFeels();

      ButtonGroup lnfGroup = new ButtonGroup();
      JMenu optionsMenu = new JMenu( "Options" );
      
      menuBar.add( optionsMenu );
      
      for( int i = 0; i < lnfs.length; i++ ) {
	 JRadioButtonMenuItem rbmi = new JRadioButtonMenuItem( lnfs[i].getName() ); 
	 optionsMenu.add( rbmi );

	 // preselect the current Look & feel
	 rbmi.setSelected( UIManager.getLookAndFeel().getName().equals
			   ( lnfs[i].getName() ) );

	 // store lool & feel info as client property
	 rbmi.putClientProperty( "lnf name", lnfs[i] );

	 // create and add the item listener
	 rbmi.addItemListener( new ItemListener() {
	    public void itemStateChanged( ItemEvent ie ) {
	       JRadioButtonMenuItem rbmi2 = (JRadioButtonMenuItem) ie.getSource();
	       if( rbmi2.isSelected() ) {
		  // get the stored look & feel info
		  UIManager.LookAndFeelInfo info =
		     (UIManager.LookAndFeelInfo) rbmi2.getClientProperty( "lnf name" );
		  try {
		     UIManager.setLookAndFeel( info.getClassName() );
		     // update the complete application's look & feel
		     SwingUtilities.updateComponentTreeUI( JarViewer.this );
		  }
		  catch( Exception e ) {
		     System.err.println( " unable to set UI " + e.getMessage() );
		  }
	       }
	    }
	 } );
	 lnfGroup.add( rbmi );
      }

      // the help menu
      JMenu helpMenu = new JMenu( "Help" );
      helpMenu.setMnemonic( 'H' );

      JMenuItem aboutItem = helpMenu.add( aboutAction );
      aboutItem.setMnemonic( 'A' );
      aboutItem.setAccelerator( KeyStroke.getKeyStroke( 'A',
				java.awt.Event.CTRL_MASK ) );

      menuBar.add( helpMenu );

      return menuBar;
   }

   /**
    * Creates the tool bar
    */
   public JToolBar createToolBar(){
      // We need a file separator because our icon images
      // are placed in the "images" directory
      String fileSeparator = 
	 System.getProperty( "file.separator" );

      // Create a toolbar with rollover look & feel
      JToolBar toolBar = new JToolBar();

      // This does only work with the Metal look & feel
      toolBar.putClientProperty( "JToolBar.isRollover",
				 Boolean.TRUE );

      // Construct the button from the Action object
      // JButton clearButton = toolBar.add( clearAction );

      RolloverButton clearButton = new RolloverButton();
      toolBar.add( clearButton );

      // Get the URL relativly to the location of this class.
      // If JarViewer.class is in directory JarViewer,
      // clear.gif is loaded from JarViewer/images/clear.gif
      URL clearURL =
	 this.getClass().getResource( "images" + fileSeparator + "clear.gif" );
       System.out.println("clearURL = " + clearURL);
      // Set the icon
      clearButton.setIcon( new ImageIcon( clearURL ) );

      // Set the tool tip text
      clearButton.setToolTipText( "Clear JAR tree" );

      // Create open button
      RolloverButton openButton = new RolloverButton();

      URL openURL =
	 this.getClass().getResource( "images" + fileSeparator + "open.gif" );

      // Set the icon
      openButton.setIcon( new ImageIcon( openURL ) );

      openButton.setToolTipText( "Open JAR file" );

      // In this case, we have to add the Action object
      openButton.addActionListener( openAction );

      // Add the button to the tool bar
      toolBar.add( openButton );

      return toolBar;
   }

   /**
    * Creates the status bar
    */
   public JPanel createStatusBar() {
      // We take a JPanel with BoxLayout and a 
      // lowered BevelBorder
      JPanel statusBar = new JPanel();
      statusBar.setLayout( new BoxLayout( statusBar, BoxLayout.X_AXIS ) );
      statusBar.setBorder( new BevelBorder( BevelBorder.LOWERED ) );

      // For the status information, a simple JLabel is enough
      fileNameLabel = new JLabel( "No JAR file opened" );
      statusBar.add( fileNameLabel );

      return statusBar;
   }

   /**
    * Clears the tree
    */
   public void clear() {
      if( contentsType.equals( "tree" ) )
	 tree.setJarFile( null );
      else if( contentsType.equals( "table" ) )
	 table.setJarFile( null );
      else
	 treeTable.setJarFile( null );
   }

   /**
    * Sets the new Jar file
    */
   public void setJarFile( JarFile jarFile ) {
      if( contentsType.equals( "tree" ) )
	 tree.setJarFile( jarFile );
      else if( contentsType.equals( "table" ) )
	 table.setJarFile( jarFile );
      else
	 treeTable.setJarFile( jarFile );
   }


   /**
    * Sets the file name in the status bar
    */
   public void setFileName( String fileName ) {
      fileNameLabel.setText( fileName );

      // This is necessary to set the labal's size
      // to fit the new text size
      fileNameLabel.setSize( fileNameLabel.getPreferredSize() );
   }

   /**
    * The main program simply creates a JarViewer
    */
   public static void main( String s[] ) {
      String type = "treeTable";
      if( s.length > 0 )
	 type = s[0].toLowerCase();

      JarViewer jarViewer = new JarViewer( type );
   }

   private JarTable table;          // The JAR table
   private JarTree tree;            // The JAR tree
   private JarTreeTable treeTable;  // The JAR tree table
   private JLabel fileNameLabel;    // In status line
   private ClearAction clearAction; // Clear the contents
   private OpenAction openAction;   // Open a JAR file
   private ExitAction exitAction;   // Exit the program
   private AboutAction aboutAction; // Show the About dialog
   private String contentsType;     // tree, table or treeTable
}

/**
 * Action to clear the tree
 */
class ClearAction extends AbstractAction {
   ClearAction( JarViewer jarViewer ) {
      super( "Clear" );
      this.jarViewer = jarViewer;
   }

   public void actionPerformed( ActionEvent event ) {
      // Clear the tree
      jarViewer.clear();
      // Reset the file name
      jarViewer.setFileName( " " );
   }

   private JarViewer jarViewer;
}

/**
 * Action to open a JAR file
 */
class OpenAction extends AbstractAction {
   OpenAction( JarViewer jarViewer ) {
      super( "Open..." );
      this.jarViewer = jarViewer;
   }

   public void actionPerformed( ActionEvent event ) {
      // Create and show a file dialog
      FileDialog fileDialog = new FileDialog( jarViewer );
      fileDialog.setMode( FileDialog.LOAD );
      fileDialog.show();

      String file = fileDialog.getFile();
      if( file == null )
	 return;

      // Load the JAR file, create the tree
      // and set the file name
      String fileName = null;
      try {
	 String directory = fileDialog.getDirectory();
	 fileName = directory + file;
	 JarFile jarFile = new JarFile( fileName );

	 jarViewer.setJarFile( jarFile );

	 jarViewer.setFileName( fileName );
      }
      catch( FileNotFoundException e ) {
	 System.out.println( 
			    "File \"" + fileName + "\" not found!" );
      }
      catch( IOException e ) {
	 e.printStackTrace();
      }
   }

   private JarViewer jarViewer;
}

/**
 * Action to exit the program
 */
class ExitAction extends AbstractAction {
   public ExitAction() {
      super( "Exit" );
   }

   public void actionPerformed( ActionEvent event ) {
      System.exit( 0 );
   }
}

/**
 * Action to show the About dialog
 */
class AboutAction extends AbstractAction {
   AboutAction( JarViewer jarViewer ) {
      super( "About..." );
      this.jarViewer = jarViewer;
   }

   public void actionPerformed( ActionEvent event ) {
      JOptionPane.showMessageDialog( jarViewer,
				     "JAR Viewer\nVersion 1.0",
				     "Product Information",
				     JOptionPane.INFORMATION_MESSAGE );
   }

   private JarViewer jarViewer;
}

class RolloverButton extends JButton
{
   public RolloverButton() {
      // We don't want to see focus rectangles
      setRequestFocusEnabled( false );

      // Rollover is cool
      setRolloverEnabled( true );
   }

   protected void paintBorder( Graphics g ) {
      if( model.isRollover() ) {
	 super.paintBorder( g );
      }
   }
}
