package com.tor.swing.treetable.jjar;

import java.awt.*;
import java.text.*; 
import java.util.*; 
import java.util.jar.*;      // needed to load a JAR file
import javax.swing.*;
import javax.swing.tree.*;

public class JarTree extends JTree {

   public JarTree( JarFile jarFile ) {
      setEditable( true );
      this.jarFile = jarFile;
      setRootVisible( true );
      setCellRenderer( new JarTreeCellRenderer() );
      initData();
   }

   public void setJarFile( JarFile jarFile ) {
      this.jarFile = jarFile;
      initData();
      revalidate();
   }

   public void initData() {
      // First create the rood tree node
      JarEntryTreeNode root =
	 new JarEntryTreeNode( "root" );

      if( jarFile != null ) {

	 int size = jarFile.size();
	 Enumeration jarEntries = jarFile.entries();

	 // We need a hash table to store the tree nodes
	 // representing directories
	 Hashtable nodes = new Hashtable();

	 // A loop over all vector elements
	 for( int i = 0; i < size; i++ ) {
	    // Get the next entry
	    JarEntry jarEntry = (JarEntry) jarEntries.nextElement();
	    String entryName = jarEntry.getName();

	    // Get the position of the last '/'
	    int cutIndex = entryName.lastIndexOf( '/' );

	    // Get the directory part if there is one
	    String dir = null;
	    if( cutIndex != -1 ) {  
	       dir = entryName.substring( 0, cutIndex );

	       // Separate the file part
	       entryName = entryName.substring( cutIndex + 1);
	    }

	    if( !entryName.equals( "" ) ) {
	       // This is not a directory entry, e.g. a class
	       JarEntryTreeNode parent = null;
	       JarEntryTreeNode lastParent = root;

	       if( dir == null )
		  parent = root;
	       else {
		  StringTokenizer tokenizer = new StringTokenizer( dir, "/" );
		  String dirpath = "";
		  while( tokenizer.hasMoreTokens() )
		  {
		     String dirName = tokenizer.nextToken();
		     dirpath += dirName;
		     parent =
			(JarEntryTreeNode) nodes.get( dirpath );

		     // Is the directory in our hash table already?
		     if( parent == null ) {
			parent = new JarEntryTreeNode( dirName );
			lastParent.insert( parent, 0 );
			nodes.put( dirpath, parent );
		     }
		     lastParent = parent;
		     dirpath += "/";
		  }
	       }

	       // Add the entry to the directory node
	       JarEntryTreeNode node = 
		  new JarEntryTreeNode( entryName );
	       node.setJarEntry( jarEntry );
	       lastParent.add( node );
	    }
	 }
      }

      // Create a default tree model with root as tree root
      DefaultTreeModel model = new DefaultTreeModel( root );

      // set the model
      setModel( model );
   }

   /**
    * updateUI is overridden to set the colors of the Tree's renderer
    * to match that of the table.
    */
   public void updateUI() {
      super.updateUI();
      JarTreeCellRenderer jtcr = new JarTreeCellRenderer();
      setCellRenderer( jtcr );

      // The tree should use the table's selection colors 
      if( table != null ) {
	 jtcr.setTextSelectionColor(
	     UIManager.getColor( "Table.selectionForeground" ) );
	 jtcr.setBackgroundSelectionColor(
	     UIManager.getColor( "Table.selectionBackground" ) );
      }
   }

   /**
    * Sets the row height of the tree
    * and forwards it to the table.
    */
   public void setRowHeight(int rowHeight) {
      if (rowHeight > 0) {
	 super.setRowHeight(rowHeight); 
	 if ( table != null &&
	      table.getRowHeight() != rowHeight) {
	    table.setRowHeight(getRowHeight()); 
	 }
      }
   }

   public void setTable( JarTreeTable table ) {
      this.table = table;
   }

   class JarTreeCellRenderer extends DefaultTreeCellRenderer {

      ImageIcon classIcon = new ImageIcon( "images/class.gif" );
      ImageIcon imageIcon = new ImageIcon( "images/image.gif" );

      public Component getTreeCellRendererComponent(JTree tree,
						    Object value,
						    boolean selected,
						    boolean expanded,
						    boolean leaf,
						    int row,
						    boolean hasFocus) {
	 
	 String name = value.toString().toLowerCase();

	 if( leaf && name.endsWith( ".class" ) )
	    setLeafIcon( classIcon );
	 else if( leaf && name.endsWith( ".gif" ) )
	    setLeafIcon( imageIcon );

	 return super.getTreeCellRendererComponent( tree, value, selected,
						    expanded, leaf, row,
						    hasFocus );
      }

      public void paint( Graphics g ) {
	 Rectangle bounds = g.getClipBounds();
	 int textWidth = g.getFontMetrics().stringWidth( getText() );
	 // int iconOffset = getHorizontalAlignment() + 
	 //   + getHorizontalTextPosition() + getIcon().getIconWidth();
	 int iconOffset = getHorizontalAlignment() + getIcon().getIconWidth() + 1;
	 if( bounds.x == 0 && bounds.y == 0 ) {
	    bounds.width -= iconOffset;
	    String labelStr = layout( this, g.getFontMetrics(),
				      getText(), bounds );
	    setText( labelStr );
	 }
	 super.paint( g );
      }

      private String layout( JLabel label,
			     FontMetrics fontMetrics,
			     String text,
			     Rectangle viewR )
      {
	 Rectangle iconR = new Rectangle();
	 Rectangle textR = new Rectangle();
	 return SwingUtilities.layoutCompoundLabel( fontMetrics,
						    text,
						    null,
						    SwingConstants.RIGHT,
						    SwingConstants.RIGHT,
						    SwingConstants.RIGHT,
						    SwingConstants.RIGHT,
						    viewR,
						    iconR,
						    textR,
						    0 );
      }
   }

   private JarFile jarFile = null;
   private JarTreeTable table = null;
}





