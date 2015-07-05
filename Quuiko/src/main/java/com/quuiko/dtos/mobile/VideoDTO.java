package com.quuiko.dtos.mobile;

import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.Thumbnail;
import com.quuiko.beans.YoutubeVideo;

public class VideoDTO extends JSONDTO{
	private String videoId;
	private String videoTitle;
	private String videoDescription;
	private String urlImgDefault;
	private String urlImgMedium;
	private String urlImgLarge;
	
	public VideoDTO(){
	}
	
	public VideoDTO(PlaylistItem v){
		this.videoTitle=v.getSnippet().getTitle();
		this.videoDescription=v.getSnippet().getDescription();
		String videoId=v.getSnippet().getResourceId().getVideoId();
		this.videoId=videoId;
		Thumbnail imgDefault=v.getSnippet().getThumbnails().getStandard();
		Thumbnail imgMedium=v.getSnippet().getThumbnails().getMedium();
		Thumbnail imgLarge=v.getSnippet().getThumbnails().getHigh();
		if(imgDefault!=null){
			this.urlImgDefault=imgDefault.getUrl();
		}
		if(imgMedium!=null){
			this.urlImgMedium=imgMedium.getUrl();
		}
		if(imgLarge!=null){
			this.urlImgLarge=imgLarge.getUrl();
		}
	}
	
	public VideoDTO(YoutubeVideo v){
		this.videoTitle=v.getTitulo();
		this.videoDescription=v.getDescripcion();
		String videoId=v.getIdVideo();
		this.videoId=videoId;
		this.urlImgDefault=v.getUrlImagenDefault();
		this.urlImgMedium=v.getUrlImagenMediana();
		this.urlImgLarge=v.getUrlImagenGrande();
	}
	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public String getVideoDescription() {
		return videoDescription;
	}
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
	public String getUrlImgDefault() {
		return urlImgDefault;
	}
	public void setUrlImgDefault(String urlImgDefault) {
		this.urlImgDefault = urlImgDefault;
	}
	public String getUrlImgMedium() {
		return urlImgMedium;
	}
	public void setUrlImgMedium(String urlImgMedium) {
		this.urlImgMedium = urlImgMedium;
	}
	public String getUrlImgLarge() {
		return urlImgLarge;
	}
	public void setUrlImgLarge(String urlImgLarge) {
		this.urlImgLarge = urlImgLarge;
	}
}
