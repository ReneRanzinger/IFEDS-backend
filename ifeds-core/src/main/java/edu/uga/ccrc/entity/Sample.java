package edu.uga.ccrc.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sample", schema="core")
public class Sample {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="sample_id")
	private Long sampleId;
	
	@Column(length=50, nullable = false, unique = true)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="sample_type_id")
	private SampleType sampleType;

	@ManyToOne
	@JoinColumn(name="provider_id")
    private Provider provider;
	
	@Column(length=1000)
    private String description;
	
	@Column(length = 256)
    private String url;
	
	@OneToMany(mappedBy = "sample", cascade = CascadeType.ALL)
	Set<Dataset> datasets;
	
	protected Sample() {}

	public Long getSampleId() {
		return sampleId;
	}

	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(SampleType sampleType) {
		this.sampleType = sampleType;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
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

	public Set<Dataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(Set<Dataset> datasets) {
		this.datasets = datasets;
	}

}
