package it.polito.tdp.itunes.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao ;
	private Graph<Album, DefaultWeightedEdge> graph ;
	private int bestLength = 0 ;
	private List<Album> bestPath ;
	
	public Model() {
		this.dao = new ItunesDAO () ;
		
	}
	
	public List<Track> getAllTracks(Album a){
		List<Track> trackList = new LinkedList<Track>();
		for(Track t: this.dao.getAllTracks()) {
			if(a.getAlbumId().equals(t.getAlbumId())) {
				trackList.add(t);
				a.addTrack(t);
			
			}
		}
		return trackList;
	}
	
	public List<Album> listaAlbum(){
		return this.dao.getAllAlbums();
	}
	
	public void creaGrafo(int n) {
		this.graph = new SimpleDirectedWeightedGraph<Album,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		for(Album album: this.dao.getAllAlbums()) {
			if(this.getAllTracks(album).size() > n) {
				this.graph.addVertex(album) ;
			}
		}
		
	}
	
	public void addArco() {
		Album a1 = null;
		Album a2 = null;
		int tracks1 = 0 ;
		int tracks2 = 0 ;
		for(Album a: this.graph.vertexSet()) {
			tracks1 = this.getAllTracks(a).size() ;
			a1 = a ;
			for(Album a0 : this.graph.vertexSet()) {
				if(!a0.equals(a1)) {
					tracks2 = this.getAllTracks(a0).size() ;
					a2 = a0;
					if(tracks1-tracks2 > 0) {
						double delta = Math.abs(tracks1-tracks2) ;
						Graphs.addEdge(this.graph, a2, a1, delta);
					}
				}
			}
		}
		
		
	}
	
	public Double calcoloBilancio(Album a) {
		
		Set<DefaultWeightedEdge> edgesIn = this.graph.incomingEdgesOf(a) ;
		Set<DefaultWeightedEdge> edgesOut = this.graph.outgoingEdgesOf(a) ;
		double weightIn = 0.0 ;
		double weightOut = 0.0 ;
		
		
		for(DefaultWeightedEdge e: edgesIn) {
			weightIn += this.graph.getEdgeWeight(e);
		}
		for(DefaultWeightedEdge e: edgesOut) {
			weightOut += this.graph.getEdgeWeight(e);
		}
		a.setBilancio(weightIn-weightOut);
		
		return a.getBilancio();
	}
	
	public String elencoSuccessori(Album  a){
		String res = "" ;
		for(Album album : this.graph.vertexSet()) {
			this.calcoloBilancio(album) ;
		}
		List<Album> elencoSuccessori = new LinkedList<Album> (Graphs.successorListOf(this.graph, a)) ;
		Collections.sort(elencoSuccessori);
		
		if(elencoSuccessori != null) {
			for(Album alb: elencoSuccessori) {
				
				res += alb.getTitle() + "\t bilancio = " + alb.getBilancio() + "\n" ;
			}
		}
		
		return res ;
	}
	
	public List<Album> simplePath(int x, Album source, Album a2){
		List<Album> path = new LinkedList<Album> () ;
		this.bestLength = 0 ;
		this.bestPath = new LinkedList<Album> () ;
		path.add(source) ;
		
		ricorsione(x,a2,path) ;
		
		return this.bestPath ;
	}
	
	public void ricorsione(int bilancio, Album target , List<Album> parziale) {
		
		Album source = parziale.get(parziale.size()-1) ;
		
//		CONDIZIONE DI TEMINAZIONE
		if(source.equals(target) || Graphs.successorListOf(this.graph, source).equals(null)) {
			if(getScore(parziale) > this.bestLength ) {
				this.bestLength = getScore(parziale);
				this.bestPath = new LinkedList<>(parziale);
			}
			return ;
		}
		
//		ALGORITMO RICORSIVO
		for(Album a : Graphs.successorListOf(this.graph, source)) {
			if(!parziale.contains(a) && a.getBilancio() >= bilancio) {
				parziale.add(a);
				ricorsione(bilancio,target, parziale);
				parziale.remove(parziale.size()-1) ;
			}
		}	
	}
	
	private int getScore(List<Album> parziale) {
		int score = 0;
		Album source = parziale.get(0);
		
		for (Album a : parziale.subList(1, parziale.size()-1)) {
			if (a.getBilancio() > source.getBilancio())
				score += 1;
		}
		
		return score;
		
	}
	
	public String bestPath() {
		String res = "" ;
		for(Album a : this.bestPath) {
			res += a.getTitle() + " " + a.getBilancio() + "\n" ;
		}
		return res ;
	}
	
	public int getVertexSet() {
		return this.graph.vertexSet().size();
	}
	
	public Set<Album> getVertex(){
		return this.graph.vertexSet() ;
	}

	public int getEdgeSet() {
		return this.graph.edgeSet().size();
	}
	
	public String toStringSimplePath() {
		String res = "" ;
		for(Album a: this.bestPath) {
			res += a.getTitle() + " " + a.getBilancio() + "\n" ;
 		}
		return res ;
	}
}
