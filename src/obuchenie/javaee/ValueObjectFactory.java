package obuchenie.javaee;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.04.2010
 * Time: 17:08:57
 * Value Object Factory Strategy Р Factory Class
 * The factory class that creates a value object for a given EJB.
 */
public class ValueObjectFactory {
    private static final Logger log = Logger.getLogger(ValueObjectFactory.class);

    /**
     * Use a HashMap to cache class information for
     * value object classes
     */
    private static HashMap classDataInfo = new HashMap();

    /**
     * Create a value object for the given object. The
     * given object must be an EJB Implementation and have
     * a superclass that acts as the class for the entityХs
     * value object. Only the fields defined in this
     * superclass are copied in to the value object.
     */
    public static java.io.Serializable createValueObject(Object ejb, String whichVOType, String completeVOType) {
        try {
            // Get the class data for the complete value object type
            ClassData cData = getClassData(completeVOType);

            // Get class data for the requested VO type
            ClassData voCData = getClassData(whichVOType);

            // Create the value object of the requested value object type...
            java.lang.Object whichVO = Class.forName(whichVOType).newInstance();

            // get the VO fields for the requested VO
            // from the ClassData for the requested VO
            java.lang.reflect.Field[] voFields = voCData.arrFields;

            // get all fields for the complete VO
            // from the ClassData for complete VO
            java.lang.reflect.Field[] beanFields = cData.arrFields;

            // copy the common fields from the complete VO
            // to the fields of the requested VO
            for (int i = 0; i < voFields.length; i++) {
                try {
                    String voFieldName = voFields[i].getName();
                    for (int j = 0; j < beanFields.length; j++) {
                        // if the field names are same, copy value
                        if (voFieldName.equals(beanFields[j].getName())) {
                            // Copy value from matching field
                            // from the bean instance into the new
                            // value object created earlier
                            voFields[i].set(whichVO, beanFields[j].get(ejb));
                            break;
                        }
                    }
                } catch (Exception e) {
                    // handle exceptions that may be thrown
                    // by the reflection methods...
                }
            }
            // return the requested value object
            return (java.io.Serializable) whichVO;
        } catch (Exception ex) {
            // Handle all exceptions here...
        }
        return null;
    }

    /**
     * Return a ClassData object that contains the
     * information needed to create
     * a value object for the given class. This information
     * is only obtained from the
     * class using reflection once, after that it will be
     * obtained from the classDataInfo HashMap.
     */
    private static ClassData getClassData(String className) {
       //приспособленец?
        ClassData cData = (ClassData) classDataInfo.get(className);

        try {
            if (cData == null) {
                // Get the class of the given object and the
                // value object to be created
                java.lang.reflect.Field[] arrFields;
                java.lang.Class ejbVOClass = Class.forName(className);

                // Determine the fields that must be copied
                arrFields = ejbVOClass.getDeclaredFields();

                cData = new ClassData(ejbVOClass, arrFields);
                classDataInfo.put(className, cData);
            }
        } catch (Exception e) {
            // handle exceptions here...
        }
        return cData;
    }
}

/**
 * Inner Class that contains class data for the
 * value object classes
 */
class ClassData {
    // value object Class
    public Class clsValueObject;

    // value object fields
    public java.lang.reflect.Field[] arrFields;

    // Constructor
    public ClassData(Class cls, java.lang.reflect.Field[] fields) {
        clsValueObject = cls;
        arrFields = fields;
    }
}
