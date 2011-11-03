package com.tor.swing.treetable.jjar;

import java.awt.*;
import java.awt.event.*; 
import java.text.*; 
import java.util.*; 
import java.util.jar.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class JarTreeTable extends JTable {

   final String[] header = { "Entry", "Size", "Date", "Attributes" };

   public JarTreeTable( JarFile jarFile ) {
      this.jarFile = jarFile;
      dateFormat = DateFormat.getDateTimeInstance();
      initData();
      setShowGrid( false );
      tree.setTable( this );
   }

   /* We overide setBounds() of the JarTreeTableCellRenderer 
    * to move and resize our tree to the right position 
    * in our table row. This method is also used by the UI to
    * paint the editor. To ensure the editor is never painted by the UI, 
    * we must return -1 for the row containig our tree.
    */
   public int getEditingRow() {
      if( getColumnClass( editingColumn ) == JarTreeTableCellRenderer.class )
  	 return -1;

      return editingRow;  
   }

   public void setJarFile( JarFile jarFile ) {
      this.jarFile = jarFile;
      tree.setJarFile( jarFile );
      revalidate();
   }

   /**
    * Overridden to message super and forward the method to the tree.
    * Since the tree is not actually in the component hieachy it will
    * never receive this unless we forward it in this manner.
    */
   public void updateUI() {
      super.updateUI();
      if( tree != null ) {
	 tree.updateUI();
      }
      // Use the tree's default foreground and background colors in the
      // table. 
      LookAndFeel.installColorsAndFont( this, "Tree.background",
					"Tree.foreground", "Tree.font" );
   }

    /**
     * Overridden to pass the new rowHeight to the tree.
     */
    public void setRowHeight(int rowHeight) {
        super.setRowHeight(rowHeight);
	if (tree != null )
	   tree.setRowHeight( rowHeight );
	/*
	if (tree != null && tree.getRowHeight() != rowHeight) {
            tree.setRowHeight(getRowHeight()); 
	}
	*/
    }

   private void initData() {
      tree = new JarTreeTableCellRenderer( jarFile );

      // Important: JTable and JTree must share their row selection models. 
      tree.setSelectionModel( new DefaultTreeSelectionModel() { 
	 // Extend the implementation of the 
	 // DefaultTreeSelectionModel's constructor: 
	 {
	    // setSelectionModel() of JarTreeTable
	    // listSelectionModel of DefaultTreeSelectionModel
	    setSelectionModel( listSelectionModel ); 
	 } 
      } ); 

      JarTreeTableModel model = new JarTreeTableModel( tree );

      setModel( model );
      setAutoResizeMode( AUTO_RESIZE_LAST_COLUMN );
      setIntercellSpacing( new Dimension( 0, 0 ) );

      // Set the column width
      getColumnModel().getColumn( 0 ).setPreferredWidth( 400 );
      getColumnModel().getColumn( 1 ).setPreferredWidth( 70 );
      getColumnModel().getColumn( 2 ).setPreferredWidth( 200 );
      getColumnModel().getColumn( 3 ).setPreferredWidth( 300 );

      // Install the tree renderer
      setDefaultRenderer( JarTreeTableCellRenderer.class, tree ); 
      setDefaultEditor( JarTreeTableCellRenderer.class, 
			new JarTreeTableCellEditor() );  
      tree.setRowHeight( getRowHeight() );
   }

   private JarFile jarFile = null;
   private DateFormat dateFormat;
   private JarTreeTableCellRenderer tree;

   class JarTreeTableModel extends AbstractTableModel {
      public JarTreeTableModel( JTree tree ) {
	 this.tree = tree;
      }

      public int getColumnCount() { 
	 return header.length; 
      }

      public int getRowCount() {
	 if( jarFile == null )
	    return 0;

	 return tree.getRowCount();
      }

      public Object getValueAt(int row, int col) {
	 if( col == 0 )
	    return tree;

	 TreePath treePath = tree.getPathForRow( row );
	 JarEntryTreeNode node = 
	    (JarEntryTreeNode) treePath.getLastPathComponent();

	 JarEntry jarEntry = node.getJarEntry();
	 if( jarEntry != null ) {
	    switch( col ) {
	    case 1:
	       return new Long( jarEntry.getSize() );
	    case 2:
	       return dateFormat.format( new Date( jarEntry.getTime() ) );
	    case 3:
	       try {
		  Attributes attr = jarEntry.getAttributes();
		  if( attr != null )
		  {
		     return attr.entrySet();
		  }
	       }
	       catch( Exception e ) {
	       }
	       return "";  
	    }
	 }

	 return "";
      }

      public String getColumnName(int column) {
	 return header[column];
      }

      public Class getColumnClass(int c) {
	 if( c == 0 )
	    return tree.getClass();
	 
	 return getValueAt( 0, c ).getClass();
      }

      public boolean isCellEditable(int row, int col) {
	 if( col == 0 )
	    return true;

	 return false;
      }

      private JTree tree;
   };

   class JarTreeTableCellRenderer extends JarTree implements TableCellRenderer {

      protected int rowToPaint;
   
      public JarTreeTableCellRenderer( JarFile jarFile ) { 
	 super( jarFile ); 
      }

      // Move and resize the tree to the table position
      public void setBounds( int x, int y, int w, int h ) {
	 super.setBounds( x, 0, w, JarTreeTable.this.getHeight() );
      }

      // start painting at the rowToPaint
      public void paint( Graphics g ) {
	 g.translate( 0, -rowToPaint * getRowHeight() );
	 super.paint( g );
      }

      public Component getTableCellRendererComponent(JTable table,
						     Object value,
						     boolean isSelected,
						     boolean hasFocus,
						     int row, int column) {
      
	 if(isSelected)
	    setBackground( table.getSelectionBackground() );
	 else
	    setBackground( table.getBackground() );
             
	 rowToPaint = row;
	 return this;
      }
   }


   // The editor is needed to forward mouse and key events 
   // inside a table cell to the tree   
   public class JarTreeTableCellEditor extends AbstractCellEditor 
                                       implements TableCellEditor {
      public Component getTableCellEditorComponent(JTable table,
						   Object value,
						   boolean isSelected,
						   int r, int c) {
	 return tree;
      }

      /**
       * This method is copied from JTreeTable.java 
       * by Philip Milne and Scott Violet
       * 
       * Overridden to return false, and if the event is a mouse event
       * it is forwarded to the tree.<p>
       * The behavior for this is debatable, and should really be offered
       * as a property. By returning false, all keyboard actions are
       * implemented in terms of the table. By returning true, the
       * tree would get a chance to do something with the keyboard
       * events. For the most part this is ok. But for certain keys,
       * such as left/right, the tree will expand/collapse where as
       * the table focus should really move to a different column. Page
       * up/down should also be implemented in terms of the table.
       * By returning false this also has the added benefit that clicking
       * outside of the bounds of the tree node, but still in the tree
       * column will select the row, whereas if this returned true
       * that wouldn't be the case.
       * <p>By returning false we are also enforcing the policy that
       * the tree will never be editable (at least by a key sequence).
       */
      public boolean isCellEditable( EventObject e ) {
	 if( e instanceof MouseEvent ) {
	    for( int counter = 0; counter < getColumnCount(); counter++ ) {
	       if( getColumnClass( counter ) == JarTreeTableCellRenderer.class ) {
		  MouseEvent me = (MouseEvent)e;

		  // transform the x value according to current column position
		  int transX = me.getX() - getCellRect( 0, counter, true ).x;

		  MouseEvent newMouseEvent = 
		     new MouseEvent( tree, me.getID(),
				     me.getWhen(), me.getModifiers(),
				     transX,
				     me.getY(), me.getClickCount(),
				     me.isPopupTrigger() );

		  tree.dispatchEvent( newMouseEvent );

		  // update the table
		  JarTreeTable.this.revalidate();
		  break;
	       }
	    }
	 }
	 return false;
      }
   }
}

