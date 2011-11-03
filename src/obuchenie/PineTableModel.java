package obuchenie;

import obuchenie.data.DataToTest;
import obuchenie.data.Person;
import org.apache.log4j.Logger;

import javax.swing.table.DefaultTableModel;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.08.2008
 * Time: 13:05:29
 * To change this template use File | Settings | File Templates.
 */
public class PineTableModel extends DefaultTableModel {
    private static final Logger log = Logger.getLogger(PineTableModel.class);
    protected String [] colName=new String[]{"fn","ln","mn","age","sex"};
    public PineTableModel() {
        super(DataToTest.getPersonCollection().size(),5);
    }
    public String getColumnName(int col){return colName[col];}
    public Class getColumnClass(int col){
        switch(col){
        case 3:return Integer.class;
        case 4:return Boolean.class;
        default:return String.class;
        }
       }
    public Object getValueAt(int row,int col){
        Person p= (Person) DataToTest.getPersonCollection().toArray()[row];
        switch (col){
            case 0: return p.getFio().getFirstName();
            case 1: return p.getFio().getLastName();
            case 2: return p.getFio().getMidllName();
            case 3: return p.getAge();
            case 4: return new Boolean( p.isSex());
            default:return null;
        }
    }
}
                      