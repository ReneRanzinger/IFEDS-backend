package edu.uga.ccrc.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sample_descriptor", schema="core")
public class SampleDescriptor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="sample_descriptor_id", nullable=false)
	private Long sampleDescriptorId;
	
	@Column(length=32, nullable = false, unique = true)
	private String name;
	
	@Column(length=1000, nullable=false)
	private String description;
	
	@Column(length=1000)
	private String namespace;
	
	@Column(length=256)
	private String url;
	
	@Column(name="link_pattern", length=256)
	private String linkPattern;
	
	@OneToMany(mappedBy = "sampleDescriptor")
    Set<SampleToSampleDescriptor> sampleToSampleDescriptors;
	
	protected SampleDescriptor() {}

	public Long getSampleDescriptorId() {
		return sampleDescriptorId;
	}

	public void setSampleDescriptorId(Long sampleDescriptorId) {
		this.sampleDescriptorId = sampleDescriptorId;
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

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLinkPattern() {
		return linkPattern;
	}

	public void setLinkPattern(String linkPattern) {
		this.linkPattern = linkPattern;
	}
}
