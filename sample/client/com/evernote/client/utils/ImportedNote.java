package com.evernote.client.utils;

public class ImportedNote {
private String title;
private String text;
private String notebook;
public String getNotebook() {
	return notebook;
}
public void setNotebook(String notebook) {
	this.notebook = notebook;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public ImportedNote(String title, String text) {
	super();
	this.title = title;
	this.text = text;
}
public ImportedNote() {
}

}
