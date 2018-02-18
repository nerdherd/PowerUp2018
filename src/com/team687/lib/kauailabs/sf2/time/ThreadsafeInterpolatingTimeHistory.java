/* ============================================
SF2 source code is placed under the MIT license
Copyright (c) 2017 Kauai Labs

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
===============================================
*/

package com.team687.lib.kauailabs.sf2.time;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.team687.lib.kauailabs.sf2.interpolation.IValueInterpolator;
import com.team687.lib.kauailabs.sf2.quantity.ICopy;
import com.team687.lib.kauailabs.sf2.quantity.IQuantity;
import com.team687.lib.kauailabs.sf2.units.Unit.IUnit;

/**
 * The ThreadsafeInterpolatingTimeHistory class implements an array of
 * timestamped objects which can be inserted from one thread and accessed by
 * another thread. The accessing thread can lookup objects within the
 * ThreadsafeInterpolatingTimeHistory based upon a timesatamp, and in cases
 * where an exact timestamp match is not found, and object with interpolated
 * values is returned.
 * <p>
 * This class is a template class, meaning that it can be used to contain any
 * type of object which implements the ITimestampedValue and IValueInterpolator
 * interfaces.
 * <p>
 * The implementation of this class is such that the contained objects are
 * statically allocated to avoid memory allocation when objects are added.
 * 
 * @author Scott
 */

public class ThreadsafeInterpolatingTimeHistory<T extends ICopy<T> & ITimestampedValue & IValueInterpolator<T>> {
    ArrayList<T> history;
    int history_size;
    int curr_index;
    int num_valid_samples;
    T default_obj;
    TimestampInfo ts_info;
    String value_name;
    IUnit[] value_units;

    /**
     * Constructs a ThreadsafeInterpolatingTimeHihstory to hold up to a specified
     * number of objects of the specified class.
     * 
     * @param _class
     *            - the Java class of the objects to be contained.
     * @param num_samples
     *            - the maximum number of objects to be contained.
     */

    public ThreadsafeInterpolatingTimeHistory(T default_obj, int num_samples, TimestampInfo ts_info, String name,
	    IUnit[] units) {
	history_size = num_samples;
	history = new ArrayList<T>(num_samples);

	this.default_obj = default_obj;

	for (int i = 0; i < history_size; i++) {
	    T new_t = default_obj.instantiate_copy();
	    if (new_t != null) {
		history.add(i, new_t);
	    }
	}
	curr_index = 0;
	num_valid_samples = 0;
	this.ts_info = ts_info;
	this.value_name = name;
    }

    /**
     * Clears all contents of the ThreadsafeInterpolatingTimeHistory by marking all
     * contained objects as invalid.
     */
    public void reset() {
	synchronized (this) {
	    for (int i = 0; i < history_size; i++) {
		T t = history.get(i);
		t.setValid(false);
	    }
	    curr_index = 0;
	    num_valid_samples = 0;
	}
    }

    /**
     * Returns the current count of valid objects in this
     * ThreadsafeInterpolatingTimeHistory.
     * 
     * @return
     */
    public int getValidSampleCount() {
	return num_valid_samples;
    }

    /**
     * Adds the provided object to the ThreadsafeInterpolatingTimeHistory.
     * 
     * @param t
     *            - the object to add
     */
    public void add(T t) {
	synchronized (this) {
	    T existing = history.get(curr_index);
	    existing.copy(t);
	    curr_index++;
	    if (curr_index >= history_size) {
		curr_index = 0;
	    }
	    if (num_valid_samples < history_size) {
		num_valid_samples++;
	    }
	}
    }

    /**
     * Retrieves the object in the ThreadsafeInterpolatingTimeHistory which matches
     * the provided timestamp. If an exact match is not found, a new object will be
     * created using interpolated values, based upon the nearest objects preceding
     * and following the requested timestamp.
     * 
     * @param requested_timestamp
     *            - the timeatamp for which to return an object
     * @return - returns the object (either actual or interpolated) matching the
     *         requested timestamp. If no object could be located or interpolated,
     *         null is returned.
     */
    public boolean get(long requested_timestamp, T out) {
	boolean success = false;
	T match = null;
	long nearest_preceding_timestamp = Long.MIN_VALUE;
	long nearest_preceding_timestamp_delta = Long.MIN_VALUE;
	T nearest_preceding_obj = null;
	long nearest_following_timestamp_delta = Long.MAX_VALUE;
	T nearest_following_obj = null;
	boolean copy_object = true;
	synchronized (this) {
	    int entry_index = curr_index;
	    for (int i = 0; i < num_valid_samples; i++) {
		T obj = history.get(entry_index);
		long entry_timestamp = obj.getTimestamp();
		long delta = entry_timestamp - requested_timestamp;
		if (delta < 0) {
		    if (delta > nearest_preceding_timestamp_delta) {
			nearest_preceding_timestamp_delta = delta;
			nearest_preceding_timestamp = entry_timestamp;
			nearest_preceding_obj = obj;
			/*
			 * To optimize, break out once both nearest preceding and following entries are
			 * found. This optimization relies on entries being in descending timestamp
			 * order, beginning with the current entry.
			 */
			if (nearest_following_obj != null)
			    break;
		    }
		} else if (delta > 0) {
		    if (delta < nearest_following_timestamp_delta) {
			nearest_following_timestamp_delta = delta;
			nearest_following_obj = obj;
		    }
		} else { /* entry_timestamp == requested_timestamp */
		    match = obj;
		    break;
		}
		entry_index--;
		if (entry_index < 0) {
		    entry_index = history_size - 1;
		}
	    }

	    /*
	     * If a match was not found, and the requested timestamp falls within two
	     * entries in the history, interpolate an intermediate value.
	     */
	    if ((match == null) && (nearest_preceding_obj != null) && (nearest_following_obj != null)) {
		double timestamp_delta = nearest_following_timestamp_delta - nearest_preceding_timestamp_delta;
		double requested_timestamp_offset = requested_timestamp - nearest_preceding_timestamp;
		double requested_timestamp_ratio = requested_timestamp_offset / timestamp_delta;

		nearest_preceding_obj.interpolate(nearest_following_obj, requested_timestamp_ratio, out);
		out.setInterpolated(true);
		copy_object = false;
		success = true;
	    }

	    if ((match != null) && copy_object) {
		/*
		 * Make a copy of the object, so that caller does not directly reference an
		 * object within the volatile (threadsafe) history.
		 */
		out.copy(match);
		out.setInterpolated(false);
		success = true;
	    }
	}

	return success;
    }

    /**
     * Retrieves the most recently-added object in the
     * ThreadsafeInterpolatingTimeHistory.
     * 
     * @return - the most recently-added object, or null if no valid objects exist
     */
    public boolean getMostRecent(T out) {
	T most_recent_t = null;
	synchronized (this) {
	    if (num_valid_samples > 0) {
		int curr_idx = this.curr_index;
		curr_idx--;
		if (curr_idx < 0) {
		    curr_idx = (history_size - 1);
		}
		most_recent_t = history.get(curr_idx);
		if (!most_recent_t.getValid()) {
		    most_recent_t = null;
		} else {
		    /*
		     * Make a copy of the object, so that caller does not directly reference an
		     * object within the volatile (threadsafe) history.
		     */
		    T new_t = default_obj.instantiate_copy();
		    if (new_t != null) {
			new_t.copy(most_recent_t);
		    }
		    most_recent_t = new_t;
		}
	    }
	}
	if (most_recent_t != null) {
	    out.copy(most_recent_t);
	    return true;
	} else {
	    return false;
	}
    }

    public boolean writeToDirectory(String directory) {

	File dir = new File(directory);
	if (!dir.isDirectory() || !dir.canWrite()) {
	    if (!dir.mkdirs()) {
		System.out.println("Directory parameter '" + directory + "' must be a writable directory.");
		return false;
	    }
	}

	if ((directory.charAt(directory.length() - 1) != '/') && (directory.charAt(directory.length() - 1) != '\\')) {
	    directory += File.separatorChar;
	}

	String filename_prefix = value_name + "History";
	String filename_suffix = "csv";

	File f = new File(directory);
	File[] matching_files = f.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
		return name.startsWith(filename_prefix) && name.endsWith(filename_suffix);
	    }
	});

	int next_available_index = -1;

	for (File matching_file : matching_files) {
	    String file_name = matching_file.getName();
	    String file_name_prefix = file_name.replaceFirst("[.][^.]+$", "");
	    String file_counter = file_name_prefix.substring(filename_prefix.length());
	    Integer counter = Integer.decode(file_counter);
	    if (counter.intValue() > next_available_index) {
		next_available_index = counter.intValue();
	    }
	}

	next_available_index++;

	String new_filename = filename_prefix + Integer.toString(next_available_index);
	return writeToFile(directory + new_filename + "." + filename_suffix);
    }

    public boolean writeToFile(String file_path) {
	try {
	    PrintWriter out = new PrintWriter(file_path);
	    boolean success = writeToDiskInternal(out);
	    out.close();
	    return success;
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    private boolean writeToDiskInternal(PrintWriter out) {
	boolean success = true;
	// Write header
	int oldest_index;
	int num_to_write;
	if (num_valid_samples > 0) {
	    if (num_valid_samples == history_size) {
		oldest_index = curr_index + 1;
		if (oldest_index >= history_size) {
		    oldest_index = 0;
		}
		num_to_write = num_valid_samples;
	    } else { /* List is not completely filled */
		oldest_index = 0;
		num_to_write = curr_index + 1;
	    }
	    T first_entry = history.get(oldest_index);
	    ArrayList<String> quantity_names = new ArrayList<String>();
	    IQuantity quantity = first_entry.getQuantity();
	    boolean is_quantity_container = quantity.getContainedQuantityNames(quantity_names);
	    /* Write Header */
	    String header = "Timestamp";
	    if (is_quantity_container) {
		for (String quantity_name : quantity_names) {
		    header += "," + value_name + "." + quantity_name;
		}
	    } else {
		header += "," + value_name;
	    }
	    out.println(header);

	    for (int i = 0; i < num_to_write; i++) {
		StringBuilder value_string = new StringBuilder();
		T entry_to_write = history.get(oldest_index++);
		quantity = entry_to_write.getQuantity();
		value_string.append(Long.toString(entry_to_write.getTimestamp()));
		value_string.append(',');
		if (is_quantity_container) {
		    ArrayList<IQuantity> contained_quantities = new ArrayList<IQuantity>();
		    quantity.getContainedQuantities(contained_quantities);
		    int index = 0;
		    for (IQuantity contained_quantity : contained_quantities) {
			StringBuilder printable_string = new StringBuilder();
			if (index++ != 0) {
			    value_string.append(',');
			}
			contained_quantity.getPrintableString(printable_string);
			value_string.append(printable_string);
		    }
		} else {
		    quantity.getPrintableString(value_string);
		}
		out.println(value_string.toString());
		if (oldest_index >= history_size) {
		    oldest_index = 0;
		}
		if (oldest_index == curr_index) {
		    break;
		}
	    }
	}
	return success;
    }
}
