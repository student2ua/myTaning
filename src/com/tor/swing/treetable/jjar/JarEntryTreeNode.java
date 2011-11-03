package com.tor.swing.treetable.jjar;

import java.util.jar.*;      // needed to load a JAR file
import javax.swing.tree.*;


public class JarEntryTreeNode extends DefaultMutableTreeNode {
   public JarEntryTreeNode( String name ) {
      super( name );
   }

   public void setJarEntry( JarEntry jarEntry ) {
      this.jarEntry = jarEntry;
   }

   public JarEntry getJarEntry() {
      return jarEntry;
   }

   private JarEntry jarEntry = null;
}
