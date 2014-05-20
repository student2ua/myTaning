package obuchenie.json.jaxbAdapter.map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Map;

/**
 * User: tor
 * Date: 20.05.14
 * Time: 16:36
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MapEntryType<K, V> {
    private K key;
    private V value;

    public MapEntryType() {
    }

    public MapEntryType(Map.Entry<K, V> entry) {
        this.key = entry.getKey();
        this.value = entry.getValue();
    }
    @XmlElement
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
    @XmlElement
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
