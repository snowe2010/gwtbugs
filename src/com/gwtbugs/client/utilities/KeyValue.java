package com.gwtbugs.client.utilities;

import java.io.Serializable;
import java.util.Comparator;


/**
 * Re-evaluated for use in select lists
 * 
 * @author chris.loucks 
 * @author tyler.thrailkill
 *
 */
public class KeyValue implements Serializable, Comparator<KeyValue> {
	private static final long serialVersionUID = 1L;

	public String key;
	public String value;
	public boolean selected;
	
	
	public KeyValue() {
	}
	
	public KeyValue(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public KeyValue(String key, String value, boolean required) {
	  this.key = key;
	  this.value = value;
	  this.selected = required;
	}
	
	public String toString() {
		return key + "=" + value;
	}

	public int compare(KeyValue kv1, KeyValue kv2) {
		
		int ret = 0;
		if ((kv1 != null) && ( kv2 != null)) {
			String v1 = kv1.value;
			if (v1 == null) {
				v1 = "";
			}
			
			String v2 = kv2.value;
			if (v2 == null) {
				v2 = "";
			}
			
			ret = v1.compareTo(v2);
		}
		return ret;
	}
}