package com.example.xmlparsing;

import java.io.Serializable;

public class Cricket implements Serializable
{
	private String title,link,pdate;

	
	public Cricket(String title, String link, String pdate) {
		super();
		this.title = title;
		this.link = link;
		this.pdate = pdate;
	}

	public Cricket() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title;
	}
	
}
