package io.utacfreak.psycogest.back.AESoap;

import io.utacfreak.psycogest.back.Const;
import io.utacfreak.psycogest.back.Logger.Logger;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;

public class ValidatoreXml730 
{
	public static boolean validateXMLSchema(String xmlPath) {
		Logger.Log(ValidatoreXml730.class, "START - validateXMLSchema -> " + xmlPath);
		try
		{
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(Const.getPath(Const.PATH_730_XSD)));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
		} 
		catch (Exception e) 
		{
			Logger.Log(ValidatoreXml730.class, "ERR - CONTROLLO KO -> " + e);
			Logger.Log(ValidatoreXml730.class, "ERR - CONTROLLO KO-> " + e.getMessage());
			Logger.Log(ValidatoreXml730.class, "CONTROLLO KO");
			return false;
		}

		Logger.Log(ValidatoreXml730.class, "CONTROLLO OK");
		Logger.Log(ValidatoreXml730.class, "END - validateXMLSchema");
		return true;
	}

	public static byte[] getByteFileToFileLocale(String fileInput) throws Exception
	{
		FileInputStream fileInputStream = null;
		byte[] file;
		byte[] fileTMP = new byte[6000000];
		byte[] buffer = new byte[65*1024];
		int readedByte = 0;
		int dimensione = 0;

		try
		{
			fileInputStream = new FileInputStream(fileInput); 

			while((readedByte = fileInputStream.read(buffer, 0, buffer.length))>=0)
			{
				for (int i=dimensione, j=0;i<(dimensione + readedByte);i++,j++)
				{
					fileTMP[i] = buffer[j];	
				}

				dimensione = dimensione + readedByte;
			}

			file = new byte[dimensione];

			for (int i=0;i<dimensione;i++) 
			{
				file[i] = fileTMP[i];
			}
		}
		finally
		{
			if(fileInputStream!=null)
			{
				try
				{
					fileInputStream.close();
				}
				catch(Exception eignore)
				{};
			}
		}

		return file;
	}
}
