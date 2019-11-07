package com.next.digip.dbf.reader;

import java.io.FileInputStream;
import java.io.IOException;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;

public class Reader {

	private DBFReader reader;

	public Reader() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public void readFile(String file) {

		reader = null;

		try {

			// create a DBFReader object
			reader = new DBFReader(new FileInputStream(file));

			// get the field count if you want for some reasons like the following

			int numberOfFields = reader.getFieldCount();


			// use this count to fetch all field information
			// if required

			for (int i = 0; i < numberOfFields; i++) {

				DBFField field = reader.getField(i);

				// do something with it if you want
				// refer the JavaDoc API reference for more details
				//
				System.out.println("field name:" + field.getName());
			}

			// Now, lets us start reading the rows

			Object[] rowObjects;

			while ((rowObjects = reader.nextRecord()) != null) {

				for (int i = 0; i < rowObjects.length; i++) {
					System.out.println(rowObjects[i]);
				}
				System.out.println("--------------------------------");

			}
			System.out.println("number of fields: " + numberOfFields);

			// By now, we have iterated through all of the rows

		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			DBFUtils.close(reader);
		}
	}
	

	


	
}

