package ie.davidlynch.soundj.model;

import java.util.Date;

public class Play 
{
	private String artist;
	private String title;
	private String album;
	private String genere;
	private Date time;
	
	public boolean isNull() {
		return (artist==null && title==null && album==null && genere==null);
	}
	
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
