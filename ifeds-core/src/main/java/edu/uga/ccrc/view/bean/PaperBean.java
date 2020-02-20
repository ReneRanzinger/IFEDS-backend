package edu.uga.ccrc.view.bean;
public class PaperBean {

	public PaperBean(String title, String authorList, String journalName, Long pmid, String url) {
		super();
		this.title = title;
		this.authorList = authorList;
		this.journalName = journalName;
		this.pmid = pmid;
		this.url = url;
	}

	
	private String title;
	
	private String authorList;
		
	private String journalName;
	
	private Long pmid;
	
	private String url;
	


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
