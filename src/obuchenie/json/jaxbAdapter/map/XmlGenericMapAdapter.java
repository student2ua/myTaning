package obuchenie.json.jaxbAdapter.map;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * User: tor
 * Date: 20.05.14
 * Time: 16:45
 * http://javaevangelist.blogspot.com/2011/12/java-tip-of-day-generic-jaxb-map-v.html
 */
public class XmlGenericMapAdapter <K, V> extends XmlAdapter<MapType<K, V>, Map<K, V>> {
    @Override
    public Map<K, V> unmarshal(MapType<K, V> v) throws Exception {
        HashMap<K,V>map=new HashMap<K, V>();
        for (MapEntryType<K, V> entryType : v.getEntry()) {
            map.put(entryType.getKey(),entryType.getValue());
        }
        return map;
    }

    @Override
    public MapType<K, V> marshal(Map<K, V> v) throws Exception {
        MapType<K,V> type =new MapType<K, V>(v);
        return type;
    }
}
