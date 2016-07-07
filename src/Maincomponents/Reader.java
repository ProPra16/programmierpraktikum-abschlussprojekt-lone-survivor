package Maincomponents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Reader {

	String destination = "";
	boolean xml = false;

	public Reader() {
	}

	public Reader(String des) {
		destination = des;
	}

	public void setDestination(String des) {
		destination = des;
	}

	/*
	 * Prüft ob es sich um eine gültige Datei handelt, gibt ansonsten false
	 * zurück Ebenso prüft es ob es sich um ein gültiges Dokument, also XML oder
	 * txt handelt.
	 */
	public boolean check() {
		if (destination != "") {
			if (destination.endsWith(".txt")) {
				if (destination.endsWith(".xml")){
					System.out.println(destination);
					xml = true;}
					

				File file = new File(destination);

				try {
					file.exists();
					if (file.exists())
						return true;
				} catch (Exception e) {
					return false;
				}

			}
		}

		return false;
	}

	public String readTxt() // liest die Textdatei ein
	{
		System.out.println(destination);
		if (check() == false)
			return "Falscher Pfad/Name";
		if (xml == true)
			return "Es handelt sich um den falschen Typ, benutzen sie den XML- Reader";
		ArrayList<String> zeilenliste = new ArrayList<>();
		try {
			FileReader fr = new FileReader(destination);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String zeile = "";

			do {
				zeile = br.readLine();
				if(!zeile.equals(null))zeilenliste.add(zeile);
				

			} while (zeile != null);
		} catch (Exception e) {
		}

		return refactor(zeilenliste);
	}

	private String refactor(ArrayList<String> zeilenliste) {
		String out = "";
		int index = 0;
		for (@SuppressWarnings("unused")
		String tmp : zeilenliste) {
			out = out + zeilenliste.get(index) + " \n ";
			index++;
		}

		return out;
	}
}
