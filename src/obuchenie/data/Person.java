package obuchenie.data;

import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.08.2008
 * Time: 18:24:58
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class Person {
    private static final Logger log = Logger.getLogger(Person.class);
    private FIO fio;
    private Integer age;
    private boolean sex;

    public Person() {
    }

    public Person(FIO fio, Integer age, boolean sex) {
        this.fio = fio;
        this.age = age;
        this.sex = sex;
    }

    public FIO getFio() {
    return fio;
}

    public void setFio(FIO fio) {
        this.fio = fio;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
   public String  toString(){
       return getFio().toString()+" "+getAge()+" ago,"+(isSex()?"M":"�");
   }
}
