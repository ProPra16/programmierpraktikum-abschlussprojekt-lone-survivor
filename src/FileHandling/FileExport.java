package FileHandling;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Code fuer das Zippen von:
 * http://javabeginners.de/Dateien_und_Verzeichnisse/Zip-Operationen/Zip-Archiv_erstellen.php
 * Angepasst, um mehrere Dateien in ein Archiv zu packen.
 * 
 * @author Andreas Luepertz
 * @version 1.0
 *
 */


public class FileExport {
	public FileExport(){};
	
	public FileExport(ArrayList<String> inFileNames, String outFileName){
		ExportZip(inFileNames, outFileName);
	}
	
	private void ExportZip(ArrayList<String> inFileNames, String outFileName){
		ZipOutputStream zipOutputStream=null;
		FileInputStream fileInputStream=null;
		try {
			zipOutputStream = new ZipOutputStream(new FileOutputStream(outFileName));
			for(String fileName: inFileNames){
				fileInputStream = new FileInputStream(fileName);
				zipOutputStream.putNextEntry(new ZipEntry(new File(fileName).getName()));
				int length;
				byte[] buffer = new byte[1024];
				while ((length = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
					zipOutputStream.write(buffer, 0, length);
				}
			}
		}
		catch (FileNotFoundException e) {} 
		catch (IOException e) {}
		finally{
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				}
				catch (IOException e) {}
			}
			if(zipOutputStream != null){
				try {
					zipOutputStream.closeEntry();
					zipOutputStream.close();
				}
				catch (IOException e) {}
			}
		}
	}
}