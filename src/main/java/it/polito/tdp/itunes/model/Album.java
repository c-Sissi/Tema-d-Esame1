package it.polito.tdp.itunes.model;

import java.util.LinkedList;
import java.util.List;

public class Album implements Comparable <Album>{
	private Integer albumId;
	private String title;
	private List<Track> tracks ;
	private double bilancio  = 0.0;
	
	public Album(Integer albumId, String title) {
		super();
		this.albumId = albumId;
		this.title = title;
		this.tracks = new LinkedList<Track> () ;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addTrack(Track t) {
		if(tracks.equals(null)|| !tracks.contains(t)) {
			if(t.getAlbumId().equals(t.getAlbumId())) {
				tracks.add(t) ;
			}	
		}
	}
	public List<Track> getTracks(){
		if(tracks.equals(null)) {
			tracks = new LinkedList<Track> ();
			return tracks;
		}
		else 
			return tracks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (albumId == null) {
			if (other.albumId != null)
				return false;
		} else if (!albumId.equals(other.albumId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return title + "\n";
	}
	
	
	public void setBilancio(double bilancio) {
		this.bilancio = bilancio;
	}
	public double getBilancio() {
		return this.bilancio;
	}

	@Override
	public int compareTo(Album a1) {
		if(a1.getBilancio() - this.getBilancio() > 0  ) {
			return 1;
		}
		if(a1.getBilancio() - this.getBilancio() < 0 ) {
			return -1 ;
		}
		else {
			return 0;
		}
	}

	
	
}
