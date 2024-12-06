package dev.kmunton.utils.data;

import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CopyUtils {

  public static <K, V> Map<K, V> copy(Map<K, V> map) {
    return new HashMap<>(map);
  }

}
