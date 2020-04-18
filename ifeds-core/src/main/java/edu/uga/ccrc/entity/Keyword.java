package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="keyword", schema="core")
public class Keyword {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="keyword_id", nullable=false)
	private Long keywordId;
	
	@Column(length=64, nullable = false, unique = true)
	private String name;
	
	@Column(length=1000)
	private String description;
		
	@Column(length=256)
	private String url;
	
	public Keyword() {}
	public Keyword(String name, String description, String url) {
		
		this.name = name;
		this.description = description;
		this.url = url;
	}

	public Long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
