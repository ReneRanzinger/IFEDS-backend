/**
 * @author Susan George
 */
package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.dao.DatasetToExperimentTypeDAO;
import edu.uga.ccrc.dao.DatasetToKeywordDAO;
import edu.uga.ccrc.dao.DatasetToPaperDAO;
import edu.uga.ccrc.dao.ExperimentTypeDAO;
import edu.uga.ccrc.dao.FundingGrantDAO;
import edu.uga.ccrc.dao.FundingSourceDAO;
import edu.uga.ccrc.dao.KeywordDAO;
import edu.uga.ccrc.dao.PaperDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.dao.SampleDAO;
import edu.uga.ccrc.entity.DataFile;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.DatasetToExperimentType;
import edu.uga.ccrc.entity.DatasetToExperimentTypePK;
import edu.uga.ccrc.entity.DatasetToKeyword;
import edu.uga.ccrc.entity.DatasetToKeywordPK;
import edu.uga.ccrc.entity.DatasetToPaper;
import edu.uga.ccrc.entity.DatasetToPaperPK;
import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.entity.FundingGrant;
import edu.uga.ccrc.entity.FundingGrantPK;
import edu.uga.ccrc.entity.FundingSource;
import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.Paper;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.view.bean.CreateSampleHelperBean;

import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.ForbiddenException;

import edu.uga.ccrc.view.bean.DataFileBean;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.DatasetDetailBean;
import edu.uga.ccrc.view.bean.DatasetToExperimentTypeBean;
import edu.uga.ccrc.view.bean.FundingGrantBean;
import edu.uga.ccrc.view.bean.ProviderBean;
import edu.uga.ccrc.view.bean.SampleWithDescriptorListBean;
import edu.uga.ccrc.view.bean.DatasetBeans.CreateDatasetHelperBean;
import edu.uga.ccrc.view.bean.DatasetBeans.CreateDatasetToExperimentTypeHelperBean;
import edu.uga.ccrc.view.bean.DatasetBeans.CreateFundingGrantHelperBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Api(value = "ifeds", description = "Operations pertaining to dataset")
@RestController
public class DatasetController {

	@Autowired
	DatasetDAO datasetDAO;

	@Autowired
	ProviderDAO providerDAO;
	
	@Autowired
	SampleDAO sampleDAO;
	
	@Autowired
	KeywordDAO keywordDAO; 
	
	@Autowired
	ExperimentTypeDAO experimentTypeDAO;
	
	@Autowired
	DatasetToExperimentTypeDAO datasetToExperimentTypeDAO;
	
	@Autowired
	DatasetToKeywordDAO datasetToKeywordDAO;
	
	@Autowired
	DatasetToPaperDAO datasetToPaperDAO;
	
	@Autowired
	PaperDAO paperDAO;
	
	@Autowired
	FundingGrantDAO fundingGrantDAO;
	
	@Autowired
	FundingSourceDAO fundingSourcetDAO;
	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@ApiOperation(value = "View a list of available datasets", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "You are not authorized to view the dataset"),
			@ApiResponse(code = 403, message = "Accessing the dataset is forbidden"),
			@ApiResponse(code = 404, message = "The dataset resource is not found") })
	@CrossOrigin
	@GetMapping(value = "/datasets", produces = "application/json")
	// http://localhost:8080/datasets
	public List<DatasetBean> getAllDatasets(HttpServletRequest request) {

		System.out.println("Retrieving datasets : getAllDatasets() ");
		List<DatasetBean> res = new ArrayList<DatasetBean>();

		// Check if request header contains authorization token
		final String requestTokenHeader = request.getHeader("Authorization");

		// If token header is null
		if (requestTokenHeader == null) {
			System.out.println("Retrieving public datasets");

			// View all public datasets
			for (Dataset ds : datasetDAO.findPublicDatasets()) {
				DatasetBean b = new DatasetBean();
				b.setDatasetId(ds.getDatasetId());
				b.setDatasetName(ds.getName());
				b.setDescription(ds.getDescription());
				b.setSampleName(ds.getSample().getName());
				b.setProviderName(ds.getProvider().getName());
				res.add(b);
			}
		} else {
			System.out.println("Retrieving public and owner-specific datasets");
			String jwtToken = requestTokenHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

			Provider provider = providerDAO.findByUsername(username);

			if (provider != null) {
				// View all public datasets and private datasets owned by the provider
				for (Dataset ds : datasetDAO.findPublicAndProviderDatasets(provider.getProviderId())) {
					DatasetBean b = new DatasetBean();
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/datasets/{id}", produces = "application/json")
	@ApiOperation(value = "Delete a dataset")
	public void deleteDataset(@PathVariable long id) {
		System.out.println("Deleting datasets : deleteDataset() id : " + id);
		datasetDAO.deleteById(id);
	}

	@CrossOrigin
	@GetMapping(value = "/dataset/{datasetId}", produces = "application/json")
	@ApiOperation(value = "View dataset details", response = DatasetDetailBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "You are not authorized to view the dataset"),
			@ApiResponse(code = 403, message = "Accessing the dataset is forbidden"),
			@ApiResponse(code = 404, message = "The dataset resource is not found") })
	// http://localhost:8080/dataset/1;
	public DatasetDetailBean getDatasetDetail(HttpServletRequest request, @PathVariable long datasetId) {

		System.out.println("Retrieving dataset detail : getDatasetDetail() ");

		DatasetDetailBean b = new DatasetDetailBean();

		// Check if request header contains authorization token
		final String requestTokenHeader = request.getHeader("Authorization");

		// If there is no token header, display dataset detail only if dataset is public
		if (requestTokenHeader == null) {
			System.out.println("Get dataset detail " + datasetId);
			Optional<Dataset> ds = datasetDAO.findById(datasetId);
			
			//Throw EntityNotFoundException if ds is null
			if (!ds.isPresent()) {
				throw new EntityNotFoundException("The dataset resource with id "+datasetId+" is not found");
			} else {
				if (ds.get().isPublic())
					b = populateDatasetDetailBean(b, ds.get());
				else
					throw new ForbiddenException("Accessing the dataset with id "+datasetId+" is forbidden");
			}
		} else {
			System.out.println("Token header present");

			// If token header is present, fetch provider from token
			String jwtToken = requestTokenHeader.substring(7);
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

			Provider provider = providerDAO.findByUsername(username);

			
			System.out.println("Provider id :" + provider.getProviderId() + " " + "Dataset id :" + datasetId);

			// Allow provider to view dataset detail if he's the owner or if the dataset is public
			Iterator<Dataset> dsIter = datasetDAO.findPublicOrProviderDataset(datasetId, provider.getProviderId())
					.iterator();
			if (dsIter.hasNext()) {
				b = populateDatasetDetailBean(b, dsIter.next());
			} else {
				Optional<Dataset> ds = datasetDAO.findById(datasetId);
				if (!ds.isPresent()) {
					throw new EntityNotFoundException("The dataset resource with id "+datasetId+" is not found");
				} else {
					throw new ForbiddenException("Accessing the dataset with id "+datasetId+" is forbidden");
				}
			}
		}
		return b;
	}

	private DatasetDetailBean populateDatasetDetailBean(DatasetDetailBean b, Dataset ds) {

		b.setDatasetId(ds.getDatasetId());
		b.setDatasetName(ds.getName());
		b.setDescription(ds.getDescription());

		Sample sample = ds.getSample();
		SampleWithDescriptorListBean sb = new SampleWithDescriptorListBean(sample);
		b.setSample(sb);

		Provider provider = ds.getProvider();
		ProviderBean pb = new ProviderBean(provider);
		b.setProvider(pb);

		Set<DatasetToExperimentType> de = ds.getDatasetToExperimentTypes();
		Set<DatasetToExperimentTypeBean> expSet = new HashSet<DatasetToExperimentTypeBean>();
		for (DatasetToExperimentType dx : de) {
			DatasetToExperimentTypeBean deb = new DatasetToExperimentTypeBean();
			deb.setExperimentType(dx.getExperimentType());
			deb.setDescription(dx.getDescription());
			expSet.add(deb);
		}
		b.setExperimentTypes(expSet);

		Set<DatasetToPaper> dp = ds.getDatasetToPapers();
		Set<Paper> paperSet = new HashSet<Paper>();
		for (DatasetToPaper p : dp) {
			paperSet.add(p.getPaper());
		}
		b.setPapers(paperSet);

		Set<DatasetToKeyword> dk = ds.getDatasetToKeywords();
		Set<Keyword> kwSet = new HashSet<Keyword>();
		for (DatasetToKeyword k : dk) {
			kwSet.add(k.getKeyword());
		}
		b.setKeywords(kwSet);

		Set<FundingGrant> fg = ds.getFundingGrant();
		Set<FundingGrantBean> fgBeanSet = new HashSet<FundingGrantBean>();
		for (FundingGrant f : fg) {
			FundingGrantBean fgb = new FundingGrantBean();
			fgb.setFundingSource(f.getFundingSource());
			fgb.setGrantNumber(f.getFundingGrantPK().getGrantNumber());
			fgBeanSet.add(fgb);
		}
		b.setFundingSources(fgBeanSet);

		Set<DataFile> df = ds.getDataFiles();
		Set<DataFileBean> dfBeanSet = new HashSet<DataFileBean>();
		for (DataFile f : df) {
			DataFileBean dfBean = new DataFileBean(f);
			dfBeanSet.add(dfBean);
		}
		b.setDataFiles(dfBeanSet);

		return b;
	}
	
	
	/*
	 * 
	 * CREATE DATASET
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/datasets", produces="application/json")
	public String createDataset(HttpServletRequest request, @Valid  @RequestBody  CreateDatasetHelperBean createDatasetHelperBean) {
		
		
		System.out.println("In create dataset : ");
		final String requestTokenHeader = request.getHeader("Authorization");

		//1)Save dataset
		
		Dataset dataset = new Dataset();
	
		dataset.setName(createDatasetHelperBean.getDatasetName());
		
		dataset.setSample(sampleDAO.findById(createDatasetHelperBean.getSampleIds()).orElse(null));
		
		if(dataset.getSample() == null)
			return "{\n\tError : Sample doesn't exists}";
		
		
		//token starts after 7th position as token is appnended with 'Bearer' 
		String jwtToken = requestTokenHeader.substring(7);
		
		//get username from token
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

		//get provider data by username
		Provider provider = providerDAO.findByUsername(username); 	
		dataset.setProvider(provider);
		
		dataset.setDescription(createDatasetHelperBean.getDescription());
		
		dataset.setIsPublic(createDatasetHelperBean.isIs_public());

		dataset = datasetDAO.save(dataset);
		
		
		
		//2)Save dataset-to-funding-grant 
		
		for(CreateFundingGrantHelperBean fundGrant :createDatasetHelperBean.getFunding_grant()) {
			
			//create composite PK
			FundingGrantPK fundingGrantPK = new FundingGrantPK(dataset.getDatasetId(), fundGrant.getFunding_source_id(), fundGrant.getGrant_number());
			
			//create m-n object
			FundingGrant fundingGrant = new FundingGrant();
			
			//find funding source
			FundingSource fundingSource = fundingSourcetDAO.findById(fundGrant.getFunding_source_id()).orElse(null);
			
			if(fundingSource == null) {
				datasetDAO.deleteById(dataset.getDatasetId());
				return "{\n\tError : Funding Source doesn't exists}";
			}
			
			fundingGrant.setDataset(dataset);
			fundingGrant.setFundingGrantPK(fundingGrantPK);
			fundingGrant.setFundingSource(fundingSource);
			fundingGrant.setUrl(fundGrant.getUrl());
			
			fundingGrant = fundingGrantDAO.save(fundingGrant);
			
		}
		
		
		
		//3)Save dataset-to-experimentType
		
		for(CreateDatasetToExperimentTypeHelperBean expTypeIdAndDes :createDatasetHelperBean.getExperiment_types()) {
			
			//get experiment type
			ExperimentType experimentType = experimentTypeDAO.findById(expTypeIdAndDes.getExperiment_type_id()).orElse(null);
			
			if(experimentType == null) {
				System.out.println("deleteing dataset" + dataset.getDatasetId());
				datasetDAO.deleteById(dataset.getDatasetId());
				return "{\n\tError : Experiment Type doesn't exists}";
			}
			
			//build composite PK
			DatasetToExperimentTypePK datasetToExperimentTypePK = new DatasetToExperimentTypePK(dataset.getDatasetId(), experimentType.getExperimentTypeId());
			
			//create object for m-n entry
			DatasetToExperimentType datasetToExperimentType = new DatasetToExperimentType();
			
			datasetToExperimentType.setDataset(dataset);
			datasetToExperimentType.setDatasetToExperimentTypePK(datasetToExperimentTypePK);
			datasetToExperimentType.setExperimentType(experimentType);
			datasetToExperimentType.setDescription(expTypeIdAndDes.getDescription());
			datasetToExperimentType = datasetToExperimentTypeDAO.save(datasetToExperimentType);
			
		}
		
		//build composite pk
		
		//4)Save dataset-to-keyWords
		for(Long keyWordId :createDatasetHelperBean.getKeywordsIds()) {
			
			//get keyword
			Keyword keyword = keywordDAO.findById(keyWordId).orElse(null);
			if(keyword == null) {
				datasetDAO.deleteById(dataset.getDatasetId());
				return "{\n\tError : keyword doesn't exists}";
			}
			
			//build compositePk
			DatasetToKeywordPK datasetToKeywordPK = new DatasetToKeywordPK(dataset.getDatasetId(), keyword.getKeywordId());
			
			//create object for m-n entry
			DatasetToKeyword datasetToKeyword = new DatasetToKeyword();
			
			datasetToKeyword.setDataset(dataset);
			datasetToKeyword.setDatasetToKeywordPK(datasetToKeywordPK);
			datasetToKeyword.setKeyword(keyword);
			
			datasetToKeyword = datasetToKeywordDAO.save(datasetToKeyword);
			
		}
		
		
		//5)Save dataset-to-paper
		for(Long paperId : createDatasetHelperBean.getPaperIds()) {
			
			//get Paper
			Paper paper = paperDAO.findById(paperId).orElse(null);
			if(paper == null) {
				datasetDAO.deleteById(dataset.getDatasetId());
				return "{\n\tError : Paper doesn't exists}";
			}
			
			//build composite PK
			DatasetToPaperPK datasetToPaperPK = new DatasetToPaperPK(dataset.getDatasetId(), paper.getPaperId());
			
			//create object for m-n entry
			DatasetToPaper datasetToPaper = new DatasetToPaper();
			
			datasetToPaper.setDataset(dataset);
			datasetToPaper.setDatasetToPaperPK(datasetToPaperPK);
			datasetToPaper.setPaper(paper);
			
			datasetToPaper = datasetToPaperDAO.save(datasetToPaper);
			
		}
		
		return "{\n\tCreated new Dataset with id : " + dataset.getDatasetId() + "}";
		
	
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/datasets/{id}", produces="application/json")
	public String updateDataset(HttpServletRequest request, @PathVariable Long id, @Valid  @RequestBody  CreateDatasetHelperBean createDatasetHelperBean) {
		
		System.out.println("In update dataset : ");
		Dataset dataset = datasetDAO.findById(id).orElse(null);
		if(dataset == null) {
			
			new EntityNotFoundException("Not found");
			return null;
		}
		dataset.setDescription(createDatasetHelperBean.getDescription());
		
		dataset.setIsPublic(createDatasetHelperBean.isIs_public());

		dataset = datasetDAO.save(dataset);
		
		//2)Save dataset-to-funding-grant 
		
				for(CreateFundingGrantHelperBean fundGrant :createDatasetHelperBean.getFunding_grant()) {
					
					//create composite PK
					FundingGrantPK fundingGrantPK = new FundingGrantPK(dataset.getDatasetId(), fundGrant.getFunding_source_id(), fundGrant.getGrant_number());
					
					//create m-n object
					FundingGrant fundingGrant = new FundingGrant();
					
					//find funding source
					FundingSource fundingSource = fundingSourcetDAO.findById(fundGrant.getFunding_source_id()).orElse(null);
					
					if(fundingSource == null) {
						datasetDAO.deleteById(dataset.getDatasetId());
						return "{\n\tError : Funding Source doesn't exists}";
					}
					
					fundingGrant.setDataset(dataset);
					fundingGrant.setFundingGrantPK(fundingGrantPK);
					fundingGrant.setFundingSource(fundingSource);
					fundingGrant.setUrl(fundGrant.getUrl());
					
					fundingGrant = fundingGrantDAO.save(fundingGrant);
					
				}
				
				
				
				//3)Save dataset-to-experimentType
				
				for(CreateDatasetToExperimentTypeHelperBean expTypeIdAndDes :createDatasetHelperBean.getExperiment_types()) {
					
					//get experiment type
					ExperimentType experimentType = experimentTypeDAO.findById(expTypeIdAndDes.getExperiment_type_id()).orElse(null);
					
					if(experimentType == null) {
						System.out.println("deleteing dataset" + dataset.getDatasetId());
						datasetDAO.deleteById(dataset.getDatasetId());
						return "{\n\tError : Experiment Type doesn't exists}";
					}
					
					//build composite PK
					DatasetToExperimentTypePK datasetToExperimentTypePK = new DatasetToExperimentTypePK(dataset.getDatasetId(), experimentType.getExperimentTypeId());
					
					//create object for m-n entry
					DatasetToExperimentType datasetToExperimentType = new DatasetToExperimentType();
					
					datasetToExperimentType.setDataset(dataset);
					datasetToExperimentType.setDatasetToExperimentTypePK(datasetToExperimentTypePK);
					datasetToExperimentType.setExperimentType(experimentType);
					datasetToExperimentType.setDescription(expTypeIdAndDes.getDescription());
					datasetToExperimentType = datasetToExperimentTypeDAO.save(datasetToExperimentType);
					
				}
				
				//build composite pk
				
				//4)Save dataset-to-keyWords
				for(Long keyWordId :createDatasetHelperBean.getKeywordsIds()) {
					
					//get keyword
					Keyword keyword = keywordDAO.findById(keyWordId).orElse(null);
					if(keyword == null) {
						datasetDAO.deleteById(dataset.getDatasetId());
						return "{\n\tError : keyword doesn't exists}";
					}
					
					//build compositePk
					DatasetToKeywordPK datasetToKeywordPK = new DatasetToKeywordPK(dataset.getDatasetId(), keyword.getKeywordId());
					
					//create object for m-n entry
					DatasetToKeyword datasetToKeyword = new DatasetToKeyword();
					
					datasetToKeyword.setDataset(dataset);
					datasetToKeyword.setDatasetToKeywordPK(datasetToKeywordPK);
					datasetToKeyword.setKeyword(keyword);
					
					datasetToKeyword = datasetToKeywordDAO.save(datasetToKeyword);
					
				}
				
				
				//5)Save dataset-to-paper
				for(Long paperId : createDatasetHelperBean.getPaperIds()) {
					
					//get Paper
					Paper paper = paperDAO.findById(paperId).orElse(null);
					if(paper == null) {
						datasetDAO.deleteById(dataset.getDatasetId());
						return "{\n\tError : Paper doesn't exists}";
					}
					
					//build composite PK
					DatasetToPaperPK datasetToPaperPK = new DatasetToPaperPK(dataset.getDatasetId(), paper.getPaperId());
					
					//create object for m-n entry
					DatasetToPaper datasetToPaper = new DatasetToPaper();
					
					datasetToPaper.setDataset(dataset);
					datasetToPaper.setDatasetToPaperPK(datasetToPaperPK);
					datasetToPaper.setPaper(paper);
					
					datasetToPaper = datasetToPaperDAO.save(datasetToPaper);
					
				}
				
				return "{\n\t Updated Dataset with id : " + dataset.getDatasetId() + "}";
				
		
	}
	
}
