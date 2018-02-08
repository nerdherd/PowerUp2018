package com.team687.frc2018.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Log to CSV file on flash drive or RoboRIO file system
 */

public class CSVLogger {

    private static CSVLogger instance = null;

    private String m_flashDrivePath;
    private String m_roborioPath;
    private String m_fileBaseName;
    private boolean m_writeException;

    private File m_file;
    private FileWriter m_writer;

    private double m_logStartTime;

    ArrayList<CSVDatum> loggedData = new ArrayList<CSVDatum>();

    private CSVLogger() {
	m_flashDrivePath = "/media/sda1/logs/";
	m_roborioPath = "/home/lvuser/logs/";
	m_fileBaseName = "2018_02_09_drive";
    }

    public static CSVLogger getInstance() {
	if (instance == null) {
	    instance = new CSVLogger();
	}
	return instance;
    }

    public void addCSVDatum(CSVDatum datum) {
	loggedData.add(datum);
    }

    public void startLog() {
	m_writeException = false;
	// Check to see if flash drive is mounted.
	File logFolder1 = new File(m_flashDrivePath);
	File logFolder2 = new File(m_roborioPath);
	Path filePrefix = Paths.get("");
	if (logFolder1.exists() && logFolder1.isDirectory())
	    filePrefix = Paths.get(logFolder1.toString(), SmartDashboard.getString("log_file_name", m_fileBaseName));
	else if (logFolder2.exists() && logFolder2.isDirectory())
	    filePrefix = Paths.get(logFolder2.toString(), SmartDashboard.getString("log_file_name", m_fileBaseName));
	else
	    m_writeException = true;

	if (!m_writeException) {
	    int counter = 0;
	    while (counter <= 99) {
		m_file = new File(filePrefix.toString() + String.format("%02d", counter) + ".csv");
		if (m_file.exists()) {
		    counter++;
		} else {
		    break;
		}
		if (counter == 99) {
		    System.out.println("file creation counter at 99!");
		}
	    }
	    try {
		m_writer = new FileWriter(m_file);
		m_writer.append("Time,");
		for (int i = 0; i <= loggedData.size() - 2; i++) {
		    m_writer.append(loggedData.get(i).getName() + ",");
		}
		m_writer.append(loggedData.get(loggedData.size() - 1).getName() + "\n");
		m_writer.flush();
		m_logStartTime = Timer.getFPGATimestamp();
	    } catch (IOException e) {
		e.printStackTrace();
		m_writeException = true;
	    }
	}
    }

    public void stopLog() {
	try {
	    if (null != m_writer)
		m_writer.close();
	} catch (IOException e) {
	    e.printStackTrace();
	    m_writeException = true;
	}
    }

    public void logToCSV() {
	if (!m_writeException) {
	    try {
		double timestamp = Timer.getFPGATimestamp() - m_logStartTime;
		m_writer.append(String.valueOf(timestamp) + ",");
		for (int i = 0; i <= loggedData.size() - 2; i++) {
		    m_writer.append(String.valueOf(loggedData.get(i).getValue()) + ",");
		}
		m_writer.append(String.valueOf(loggedData.get(loggedData.size() - 1).getValue()) + "\n");
		m_writer.flush();
	    } catch (IOException e) {
		e.printStackTrace();
		m_writeException = true;
	    }
	}
    }

}
