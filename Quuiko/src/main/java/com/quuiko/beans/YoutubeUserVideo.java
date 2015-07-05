package com.quuiko.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="youtubeUserVideo")
@Table(name="youtubeUserVideo")
public class YoutubeUserVideo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3306829582666977309L;

	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idYoutubeVideo")
	private YoutubeVideo video;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idYoutubeUserPlaylist")
	private YoutubeUserPlaylist userPlaylist;
	
	@Column(name="reproducido")
	private Integer reproducido;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public YoutubeVideo getVideo() {
		return video;
	}
	public void setVideo(YoutubeVideo video) {
		this.video = video;
	}
	public YoutubeUserPlaylist getUserPlaylist() {
		return userPlaylist;
	}
	public void setUserPlaylist(YoutubeUserPlaylist userPlaylist) {
		this.userPlaylist = userPlaylist;
	}
	public Integer getReproducido() {
		return reproducido;
	}
	public void setReproducido(Integer reproducido) {
		this.reproducido = reproducido;
	}
	
//	@Override
//	public int hashCode(){
//		int hashcode=0;
//		hashcode+=(id!=null)?id.hashCode():0;
//		hashcode+=(video!=null && video.getIdVideo()!=null)?video.getIdVideo().hashCode():0;
//		hashcode+=(userPlaylist!=null && userPlaylist.getId()!=null)?userPlaylist.getId().hashCode():0;
//		return hashcode;
//	}
//	
//	@Override
//	public boolean equals(Object o){
//		if(o==null && this==null){
//			return true;
//		}
//		//Si el objeto a comparar no es de estos 2 tipos entonces no es igual
//		if(! (o instanceof YoutubeVideo || o instanceof YoutubeUserVideo)){
//			return false;
//		}
//		if(o instanceof YoutubeVideo){
//			YoutubeVideo obj=(YoutubeVideo)o;
//			String idVideo=obj.getIdVideo();
//			if(this==null && o==null){
//				return true;
//			}else if(this.video.getIdVideo()!=null && idVideo!=null && (this.video.getIdVideo().equals(idVideo))){
//				return true;
//			}
//		}else if(o instanceof YoutubeUserVideo){
//			YoutubeUserVideo obj=(YoutubeUserVideo)o;
//			YoutubeVideo video=(obj!=null)?obj.getVideo():null;
//			String idVideo=(video!=null)?video.getIdVideo():null;
//			String currentIdVideo=(this.getVideo()!=null)?this.getVideo().getIdVideo():null;
//			if(currentIdVideo==null && idVideo==null){
//				return true;
//			}else if(currentIdVideo!=null && idVideo!=null && (currentIdVideo.equals(idVideo))){
//				return true;
//			}
//		}
//		return false;
//	}
	
}
