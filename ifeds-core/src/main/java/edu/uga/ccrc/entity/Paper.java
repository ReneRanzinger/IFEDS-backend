package edu.uga.ccrc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="paper", schema="core")
public class Paper {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="paper_id", nullable=false)
	private Long paperId;
	
	@Column(length=256, nullable = false)
	private String title;
	
	@Column(name="author_list", length=1024, nullable=false)
	private String authorList;
		
	@Column(name="journal_name", length=64)
	private String journalName;
	
	@Column(nullable=false, unique=true)
	private Long pmid;
	
	@Column(length=256, nullable=false)
	private String url;
	
	protected Paper() {}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorList() {
		return authorList;
	}

	public void setAuthorList(String authorList) {
		this.authorList = authorList;
	}

	public String getJournalName() {
		return journalName;
	}

	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}

	public Long getPmid() {
		return pmid;
	}

	public void setPmid(Long pmid) {
		this.pmid = pmid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
