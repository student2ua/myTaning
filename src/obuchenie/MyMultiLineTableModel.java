package obuchenie;

import obuchenie.data.DataToTest;
import obuchenie.data.Person;
import org.apache.log4j.Logger;

import javax.swing.table.DefaultTableModel;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.08.2008
 * Time: 20:27:49
 * To change this template use File | Settings | File Templates.
 */
public class MyMultiLineTableModel extends DefaultTableModel {

   private static final Logger log = Logger.getLogger(MyMultiLineTableModel.class);
    protected String [] colName=new String[]{"fio","age","sex"};
    public MyMultiLineTableModel() {
        super(DataToTest.getPersonCollection().size(),3);
    }
    public String getColumnName(int col){return colName[col];}
    public Class getColumnClass(int col){
        switch(col){
        case 1:return Integer.class;
        case 2:return String.class;
        default:return String.class;
        }
       }
     public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 1:
                        return false;
                    default:
                        return true;
                }
            }
    public Object getValueAt(int row,int col){
        Person p= (Person) DataToTest.getPersonCollection().toArray()[row];
        switch (col){
            case 0: return p.getFio().getLastName()+",\n "+p.getFio().getFirstName()+" "+p.getFio().getMidllName();
            case 1: return p.getAge();
            case 2: return  p.isSex()?"M":"Æ";
            default:return null;
        }
    }
}
