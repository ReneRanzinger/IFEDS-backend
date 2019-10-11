/**
 * @author Susan George
 */
package edu.uga.ccrc.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sample_type", schema="core")
public class SampleType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="sample_type_id")
	private Long sampleTypeId;
	
	@Column(length=50, nullable = false, unique = true)
	private String name;
	
    @Column(length=1000)
    private String description;
    
    @Column(length=256)
    private String url;
    
    @OneToMany(mappedBy = "sampleType", cascade = CascadeType.ALL)
    Set<Sample> samples;
    
    protected SampleType() {}

	public Long getSampleTypeId() {
		return sampleTypeId;
	}

	public void setSampleTypeId(Long sampleTypeId) {
		this.sampleTypeId = sampleTypeId;
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

	public Set<Sample> getSamples() {
		return samples;
	}

	public void setSamples(Set<Sample> samples) {
		this.samples = samples;
	}
}
