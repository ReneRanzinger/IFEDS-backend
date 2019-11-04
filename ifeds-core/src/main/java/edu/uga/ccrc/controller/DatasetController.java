/**
 * @author Susan George
 */
package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.DataFile;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.DatasetToExperimentType;
import edu.uga.ccrc.entity.DatasetToKeyword;
import edu.uga.ccrc.entity.DatasetToPaper;
import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.entity.FundingGrant;
import edu.uga.ccrc.entity.FundingSource;
import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.Paper;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.view.bean.DataFileBean;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.DatasetDetailBean;
import edu.uga.ccrc.view.bean.ProviderBean;
import edu.uga.ccrc.view.bean.SampleBean;

@RestController
public class DatasetController {

	@Autowired
	DatasetDAO datasetDAO;
	
	@Autowired
	ProviderDAO providerDao;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/getDatasets", produces="application/json")
	public List<DatasetBean> findAllDatasets(HttpServletRequest request) {
		
		System.out.println("Retrieving datasets : findAllDatasets() ");
		List<DatasetBean> res=new ArrayList<DatasetBean>();
		
		//Check if request header contains authorization token
		final String requestTokenHeader = request.getHeader("Authorization");
		
		//If token header is null
		if(requestTokenHeader==null) {
			System.out.println("Retrieving datasets : findPublicDatasets() ");
			
			//View all public datasets
			for (Dataset ds : datasetDAO.findPublicDatasets()) {
				DatasetBean b=new DatasetBean();
				b.setDatasetId(ds.getDatasetId());
				b.setDatasetName(ds.getName());
				b.setDescription(ds.getDescription());
				b.setSampleName(ds.getSample().getName());
				b.setProviderName(ds.getProvider().getName());
				res.add(b);
			}
		}else {
			System.out.println("Retrieving datasets : findPublicAndOwnerDatasets() ");
			String jwtToken = requestTokenHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			
			Provider provider = providerDao.findByUsername(username);
			
			if(provider!=null) {
				//View all public datasets and private datasets owned by the provider
				for (Dataset ds : datasetDAO.findPublicAndProviderDatasets(provider.getProviderId())) {
					DatasetBean b=new DatasetBean();
					b.setDatasetId(ds.getDatasetId());
					b.setDatasetName(ds.getName());
					b.setDescription(ds.getDescription());
					b.setSampleName(ds.getSample().getName());
					b.setProviderName(ds.getProvider().getName());
					res.add(b);
				}	
			}		
		}
		return res;
	}
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/datasets/{id}", produces="application/json")
	public void deleteDataset(@PathVariable long id) {
		System.out.println("Deleting datasets : deleteDataset() id : " + id);
		datasetDAO.deleteById(id);	
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/getDatasetDetail", produces="application/json")
	//http://localhost:8080/getDatasetDetail?datasetId=1;
	public DatasetDetailBean findDatasetDetail(HttpServletRequest request,  @RequestParam String datasetId) {
		
		System.out.println("Retrieving dataset detail : getDatasetDetail() ");
		Long dId=Long.valueOf(datasetId);
		DatasetDetailBean b=new DatasetDetailBean();
		
		//Check if request header contains authorization token
		final String requestTokenHeader = request.getHeader("Authorization");
		
		//If there is no token header, display dataset detail only if dataset is public
		if(requestTokenHeader==null) {
			System.out.println("Get dataset detail "+ datasetId);	
			for(Dataset ds : datasetDAO.findByDatasetId(dId)) {
				if(ds.isPublic())
					b=populateDatasetDetailBean(b,ds);	
				else
					System.out.println("Dataset cannot be accessed without authorization");
			}
		}else {
			System.out.println("Token header present");
			
			//If token header is present, fetch provider from token
			String jwtToken = requestTokenHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			Provider provider = providerDao.findByUsername(username);
			
			System.out.println("Provider id :"+provider.getProviderId()+" "+"Dataset id :"+ dId);
			
			//Allow provider to view dataset detail if he's the owner
			Iterator<Dataset> dsIter=datasetDAO.checkforProviderDataset(dId,provider.getProviderId()).iterator();	
			if(dsIter.hasNext()) {
				b=populateDatasetDetailBean(b,dsIter.next());
			}else {
				//Else unauthorized to view the dataset
				System.out.println("Dataset cannot be accessed without authorization");
			}
		}
		
		return b;
	} 
	
	private DatasetDetailBean populateDatasetDetailBean(DatasetDetailBean b, Dataset ds) {
		
		b.setDatasetId(ds.getDatasetId());
		b.setDatasetName(ds.getName());
		b.setDescription(ds.getDescription());
		
		Sample sample = ds.getSample();
		SampleBean sb=new SampleBean(sample);
		b.setSample(sb);
		
		Provider provider = ds.getProvider();
		ProviderBean pb=new ProviderBean(provider);
		b.setProvider(pb);
		
		Set<DatasetToExperimentType> de=ds.getDatasetToExperimentTypes();
		Set<ExperimentType> expSet=new HashSet<ExperimentType>();
		for(DatasetToExperimentType dx : de) {
			expSet.add(dx.getExperimentType());
		}
		b.setExperimentTypeSet(expSet);
			
		Set<DatasetToPaper> dp=ds.getDatasetToPapers();
		Set<Paper> paperSet=new HashSet<Paper>();
		for(DatasetToPaper p : dp) {
			paperSet.add(p.getPaper());
		}
		b.setPapers(paperSet);
		
		Set<DatasetToKeyword> dk=ds.getDatasetToKeywords();
		Set<Keyword> kwSet=new HashSet<Keyword>();
		for(DatasetToKeyword k : dk) {
			kwSet.add(k.getKeyword());
		}
		b.setKeywords(kwSet);
		
		Set<FundingGrant> fg=ds.getFundingGrant();
		Set<FundingSource> fsSet=new HashSet<FundingSource>();
		for(FundingGrant f : fg) {
			fsSet.add(f.getFundingSource());
		}
		b.setFundingSource(fsSet);
		
		Set<DataFile> df=ds.getDataFiles();
		Set<DataFileBean> dfBeanSet=new HashSet<DataFileBean>();
		for(DataFile f : df) {
			DataFileBean dfBean=new DataFileBean(f);
			dfBeanSet.add(dfBean);
		}
		b.setDataFiles(dfBeanSet);
		
		return b;
	}
}
