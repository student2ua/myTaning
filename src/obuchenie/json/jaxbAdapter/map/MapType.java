package obuchenie.json.jaxbAdapter.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: tor
 * Date: 20.05.14
 * Time: 16:40
 */
public class MapType<K,V> {
 private List<MapEntryType<K,V>> entry=new ArrayList<MapEntryType<K, V>>();

    public MapType() {
    }
    public MapType(Map<K,V> map ){
        for (Map.Entry<K,V> entry1 : map.entrySet()) {
            entry.add(new MapEntryType<K, V>(entry1));
        }

    }
    public List<MapEntryType<K, V>> getEntry() {
        return entry;
    }

    public void setEntry(List<MapEntryType<K, V>> entry) {
        this.entry = entry;
    }
}
