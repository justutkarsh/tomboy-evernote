package com.evernote.client.utils;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class NoteParser {
	private List<ImportedNote> notesList;
	
	public NoteParser(){
		setNotesList(new ArrayList<ImportedNote>());
	}
	public void loadAndParse() throws MalformedURLException, IOException, DocumentException
	{
		
		File notesDir = new File("notes");
		directoryWalk(notesDir);
	}
	
	private void directoryWalk(File folder)
			throws MalformedURLException, DocumentException {
		for(File folderEntry:folder.listFiles()){
			if(folderEntry.isDirectory()) 
				directoryWalk(folderEntry);
			else{
				URL url = new URL("file:///"+folderEntry.getAbsolutePath());
				Document doc = parse(url);
				getNotesList().add(parseNote(doc));
			}
				
		}
	}
	public Document parse(URL url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}

	public ImportedNote parseNote(Document document) throws DocumentException {

		ImportedNote note = new ImportedNote();   
		Element root = document.getRootElement();
		StringBuilder sb = new StringBuilder();
		// iterate through child elements of root
		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
			Element element = (Element) i.next();
			if("title".equals(element.getName())){
				String title = element.getStringValue();
				note.setTitle(title);
				sb.append(title);
			}
			if("text".equals(element.getName())){
				String text=element.getStringValue();
				note.setText(text);
				sb.append(text);
			}
			
			if("tags".equals(element.getName())){
			String category = (String) element.element("tag").getData();
			   String[] data=category.split("system:notebook:",2);
			   try {
				String notebook = data[1];
				   note.setNotebook(notebook);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}


		}
		System.out.println(sb.toString());

		//        // iterate through child elements of root with element name "foo"
		//        for ( Iterator i = root.elementIterator( "Title" ); i.hasNext(); ) {
		//            Element foo = (Element) i.next();
		//            System.out.println(foo.asXML());	
		//        }

		//        // iterate through attributes of root 
		//        for ( Iterator i = root.attributeIterator(); i.hasNext(); ) {
		//            Attribute attribute = (Attribute) i.next();
		//           System.out.println(attribute.asXML());
		//        }
		return note;
	}
	
	void printNotes(){
		for (ImportedNote n:getNotesList()){
			System.out.println("title:"+n.getTitle());
			System.out.println("text:"+n.getText());
			System.out.print("\n \n \n");
		}
	}
	public static void main(String[] args) {
		NoteParser np = new NoteParser();
		try {
			np.loadAndParse();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		np.printNotes();
	}
	public List<ImportedNote> getNotesList() {
		return notesList;
	}
	public void setNotesList(List<ImportedNote> notesList) {
		this.notesList = notesList;
	}

}