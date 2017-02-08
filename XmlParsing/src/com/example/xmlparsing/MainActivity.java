package com.example.xmlparsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	ListView lv;
	ArrayList<Cricket> clist;
	ArrayAdapter<Cricket> ad;
	ProgressDialog pd;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	lv=getListView();
	new XmlTask().execute();
	clist=new ArrayList<Cricket>();
	ad=new ArrayAdapter<Cricket>(this,
		android.R.layout.simple_list_item_1,clist);
	readXML();
	
}
void readXML()
{
	File sdcard=Environment.getExternalStorageDirectory();
	File f=new File(sdcard,"cric.xml");
	FileInputStream fin=null;
	try {
		fin=new FileInputStream(f);
		XmlPullParser xml= Xml.newPullParser();
		xml.setInput(fin, "UTF-8");
		int event=xml.getEventType();
		String title="",link="",pdate="";
		while(event!=xml.END_DOCUMENT)
		{
			if(event==xml.START_TAG && xml.getName().equals("item"))
			{
				while(true)
				{
					if(event==xml.START_TAG && xml.getName()
							.equals("title"))
					{
						event=xml.next();
						title=xml.getText();
					}
					if(event==xml.START_TAG && xml.getName()
							.equals("link"))
					{
						event=xml.next();
						link=xml.getText();
					}
					if(event==xml.START_TAG && xml.getName()
							.equals("pubDate"))
					{
						event=xml.next();
						pdate=xml.getText();
					}
					if(event==xml.END_TAG && xml.getName()
							.equals("item"))
					{
						break;
					}
					event=xml.next();
				}
				Cricket ck=new Cricket(title, link, pdate);
				clist.add(ck);
				ad.notifyDataSetChanged();
			}
			event=xml.next();
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
	lv.setAdapter(ad);
}
class XmlTask extends AsyncTask<Void,Void,String>
{

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd=new ProgressDialog(MainActivity.this);
		pd.setMessage("Plz Wait..!!!");
		pd.show();
	}
	@Override
	protected String doInBackground(Void... params) {
		String url="http://zeenews.india.com/rss/cricket-news.xml";
		String data="";
		HttpGet get=new HttpGet(url);
		try {
			HttpClient cl=new DefaultHttpClient();
			HttpResponse res= cl.execute(get);
			HttpEntity et= res.getEntity();
			InputStream in= et.getContent();
			while(true)
			{
				int x=in.read();
				if(x==-1)
					break;
				data += (char)x;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return data;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.cancel();
		File sdcard=Environment.getExternalStorageDirectory();
		File f=new File(sdcard,"cric.xml");
		try {
			FileWriter fw=new FileWriter(f);
			fw.write(result);
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
}
