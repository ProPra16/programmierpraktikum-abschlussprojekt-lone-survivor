package Maincomponents;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Dowonloadcode von:
 * http://stackoverflow.com/questions/8253852/how-to-download-a-zip-file-from-a-
 * url-and-store-them-as-zip-file-only Jedoch modifiziert und geändert
 * 
 * Code zum Unzippen von:
 * http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/ Jedoch
 * modifiziert und geändert
 * 
 * @author Sebastian Stock
 * @version 1.0
 *
 */
public class ImportManager {

	List<String> fileList;
	final SimpleDoubleProperty files = new SimpleDoubleProperty(0);
	double filestotal = 5000;
	double filesAnzahl = 0;

	public ImportManager() {} //Standardkonstruktor
	
	/*
	 * Konstruktor für einen Donwnload mit Ladeschirmanzeige. Übergabe der Stage mit new ImportManager(..., new Stage());
	 */
	public ImportManager(String destination, String finalDestination, String outputName, Stage stage)
	{
		stage.initStyle(StageStyle.UNDECORATED);
		BorderPane borderPane = new BorderPane();
		borderPane.setMaxSize(200, 50);
		borderPane.setMinSize(200, 50);
		ProgressBar pb = new ProgressBar();

		pb.setProgress(-1);
		pb.setMinSize(200, 50);

		pb.setMinSize(200, 50);
		pb.progressProperty().bind(files);

		HBox hb = new HBox();

		hb.getChildren().addAll(pb);

		borderPane.getChildren().addAll(hb);
		stage.setScene(new Scene(borderPane, 200, 50));
		stage.show();
		
		Service service = new Service() {
            @Override
            public Task createTask() {
                return new Task() {
                    @Override
                    public Object call() throws Exception {               
                    	downloadZIP(destination, finalDestination);
                    	while(files.get()<0.5)
                    	{
                    		Thread.sleep(100);
                    		files.set(files.get()+0.01);
                    	}
                		unzip(finalDestination, outputName);
                		while(files.get()<1)
                    	{
                    		Thread.sleep(100);
                    		files.set(files.get()+0.05);
                    	}
                		remove(finalDestination);
 
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        
                       Platform.exit();
                        return null;
                    }
                };
            }
        };
        pb.progressProperty().bind(files);
        service.start();
	}

	/*
	 * Führt einen vollständigen Donwload und Entpackvorgang an der vorgegeben
	 * Stelle aus
	 *
	 * destination = url ,finalDestination = Zieladresse+Name
	 * output Name = neuer Name des Zips
	 */
	public ImportManager(String destination, String finalDestination, String outputName) {
		downloadZIP(destination, finalDestination);
		unzip(finalDestination, outputName);
		remove(finalDestination);
	}
	
	/*
	 * destination = url
	 * finalDestination = Zielpfad + Name
	 */

	public void downloadZIP(String destination, String finalDestination) {
		try {
			URL url = new URL(destination);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			InputStream in = connection.getInputStream();

			if (!finalDestination.endsWith("/"))
				finalDestination = finalDestination + "/";
			FileOutputStream out = new FileOutputStream(finalDestination);

			copy(in, out, 1024);
			out.close();
		} catch (Exception e) {
		}
	}
	
	/*
	 * Entpackt downloaded die Zip
	 */

	private void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
		System.out.println("writing");
		byte[] buf = new byte[bufferSize];
		int n = input.read(buf);
		//files.setValue(-1);
		
		
		while (n >= 0) {
			output.write(buf, 0, n);
			
			System.out.println("progress " + files.get());
			filesAnzahl ++;
			files.set(((filesAnzahl*100)/filestotal)/100);
			
			n = input.read(buf);
		}
		output.flush();
		output.close();
		System.out.println("done");
	}

	private boolean check(File f) {
		if (f.exists() == true)
			return true;
		return false;
	}

	/**
	 * Unzip it API: zipFile + pfad = target ;
	 * destination = absoluter Pfad mit Name des Zielordners
	 * 
	 */
	public void unzip(String target, String destination) {
		try {

			System.out.println(destination);
			byte[] buffer = new byte[1024];

			try {

				// create output directory is not exists
				System.out.println(target);
				if (check(new File(target)) == true) {
					File folder = new File(destination);

					if (!folder.exists()) {
						folder.mkdir();
					}

					// get the zip file content
					System.out.println(destination);
					System.out.println(target);
					ZipInputStream zis = new ZipInputStream(new FileInputStream(target));
					// get the zipped file list entry
					ZipEntry ze = zis.getNextEntry();
					if (check(new File(destination)) == true) 
					{
						filestotal = filesAnzahl*2;
						files.set(((filesAnzahl*100))/100);
						System.out.println(files.get());
						System.out.println("filestotalwefsdferwf:" +filesAnzahl);
						while (ze != null) {

						String fileName = ze.getName();
						
							File newFile = new File(destination + File.separator + fileName);

							System.out.println("file unzip : " + newFile.getAbsoluteFile());

							// create all non exists folders
							// else you will hit FileNotFoundException for
							// compressed folder
							new File(newFile.getParent()).mkdirs();

							FileOutputStream fos = new FileOutputStream(newFile);

							int len;
							while ((len = zis.read(buffer)) > 0) {
								fos.write(buffer, 0, len);
							}
							fos.close();

							ze = zis.getNextEntry();
							filesAnzahl++;
							files.set(((filesAnzahl*100)/filestotal)/100);
							
						}
							zis.closeEntry();
							zis.close();

							System.out.println("Done");
							
							
						}
					
						
					
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (Exception e) {
		}
	}

	/*
	 * Löscht das angegeben Zip File destination = path,
	 * 
	 */
	public void remove(String destination) {
		try {

			if (check(new File(destination)) == true) {
				File file = new File(destination);
				file.delete();
				files.set(filestotal/filestotal);
			}
			else{System.out.println("Falscher Pfad/Name");}
		} catch (Exception e) {
		}
	}
}
