
package edu.uga.ccrc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DataFileDAO;
import edu.uga.ccrc.dao.DataTypeDAO;
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
import edu.uga.ccrc.entity.DataType;
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
import edu.uga.ccrc.exception.NoResposneException;
import edu.uga.ccrc.exception.SQLException;
import edu.uga.ccrc.view.bean.DataFileBean;
import edu.uga.ccrc.view.bean.DataFileInfoBean;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.DatasetDetailBean;
import edu.uga.ccrc.view.bean.DatasetToExperimentTypeBean;
import edu.uga.ccrc.view.bean.FileUploadBean;
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
@CrossOrigin
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
	DataFileDAO dataFileDAO;
	
	@Autowired
	DataTypeDAO dataTypeDAO;
	
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

	/*
	 *  this method returns the dataset details. Not information in m-n tables
	 * 
	 * If user is logged in (authorization token present) : returns private (owned by current user) and public datasets
	 * If user is not logged in (authorization token null) : returns only public datasets
	 * 
	 * 
	 * 
	 * 
	 * */
	@ApiOperation(value = "View a list of available datasets", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@GetMapping(value = "/datasets", produces = "application/json")
	// http://localhost:8080/datasets
	public List<DatasetBean> getAllDatasets(HttpServletRequest request) throws SQLException, EntityNotFoundException {

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
			
			if(provider == null)
				throw new SQLException("Provider not found. Please check the credentials");

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
	
	/*
	 * Delete the dataset with the particular id
	 * 
	 * 
	 * */

	@RequestMapping(method = RequestMethod.DELETE, value = "/datasets/{id}", produces = "application/json")
	@ApiOperation(value = "Delete a dataset")
	public void deleteDataset(@PathVariable long id) throws SQLException {
		System.out.println("Deleting datasets : deleteDataset() id : " + id);
		Dataset d = datasetDAO.findById(id).orElse(null);
		if(d == null)
			throw new SQLException("Dataset id not valid");
		
		datasetDAO.deleteById(id);
	
	}
	
	/*
	 * this method returns the dataset details. This method returns all the information about the dataset
	 * and the information in m-n tables.
	 * 
	 * If user is logged in (authorization token present) : returns private (owned by current user) and public datasets
	 * If user is not logged in (authorization token null) : returns only public datasets
	 * 
	 * */
	
	@CrossOrigin
	@GetMapping(value = "/dataset/{datasetId}", produces = "application/json")
	@ApiOperation(value = "View dataset details", response = DatasetDetailBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Accessing the dataset is forbidden"),
			@ApiResponse(code = 404, message = "The dataset resource is not found") })
	// http://localhost:8080/dataset/1;
	public DatasetDetailBean getDatasetDetail(HttpServletRequest request, @PathVariable long datasetId) throws EntityNotFoundException, ForbiddenException{

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
	 * Create the new  dataset. 
	 * 
	 * Following are the steps:
	 * 
	 * 1. Save the new dataset ( this step is first, because we need new dataset to present in database
	 * 							 before making entries in m-n tables)
	 * 2. Save dataset-to-funding-grant 
	 * 3. Save dataset-to-experimentType
	 * 4. Save dataset-to-keyWords
	 * 5. Save dataset-to-paper
	 * 
	 *  
	 * */
	@RequestMapping(method = RequestMethod.POST, value = "/datasets", produces="application/json")
	@ApiOperation(value = "Create a dataset", response = Dataset.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Creating the dataset is forbidden"),
			@ApiResponse(code = 404, message = "The dataset resource is not created") })
	public String createDataset(HttpServletRequest request, @Valid  @RequestBody  CreateDatasetHelperBean createDatasetHelperBean) throws EntityNotFoundException, SQLException, NoResposneException {
//		System.out.println("In create dataset : ");
		final String requestTokenHeader = request.getHeader("Authorization");
		//1)Save dataset
		Dataset dataset = new Dataset();
		dataset.setName(createDatasetHelperBean.getDatasetName());
		dataset.setSample(sampleDAO.findById(createDatasetHelperBean.getSampleIds()).orElse(null));

		if(dataset.getSample() == null)
			throw new EntityNotFoundException("Sample id doesn't exists");
		
		//token starts after 7th position as token is appnended with 'Bearer' 
		String jwtToken = requestTokenHeader.substring(7);
		//get username from token
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		//get provider data by username
		Provider provider = providerDAO.findByUsername(username); 	
		
		dataset.setProvider(provider);
		dataset.setDescription(createDatasetHelperBean.getDescription());
		dataset.setIsPublic(createDatasetHelperBean.isIs_public());
		
		if(!datasetDAO.findByDatasetName(createDatasetHelperBean.getDatasetName()).isEmpty())
			throw new SQLException("Duplicate Name. Dataset with same name already exists!");
		
		try {
			dataset = datasetDAO.save(dataset);
		}catch(Exception e) {
			throw new NoResposneException("Something went Wrong could not save the dataset "+ e.getLocalizedMessage());
		}
		
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
				throw new EntityNotFoundException("Funding source doesn't exists");
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
				throw new EntityNotFoundException("Experiment type doesn't exists : " + expTypeIdAndDes.getExperiment_type_id());
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
				throw new EntityNotFoundException("Keyword doesn't exists : " + keyWordId);
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
				throw new EntityNotFoundException("Paper doesn't exists : " + paperId);
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
	
	/*
	 * 
	 * Update the already existing dataset. The steps are pretty similar to create dataset except, in this case the dataset
	 * is already present in the database
	 * 
	 * Following are the steps:
	 * 
	 * 1. Reterive the dataset with the particular id (id is @input param)
	 * 2. Save dataset-to-funding-grant 
	 * 3. Save dataset-to-experimentType
	 * 4. Save dataset-to-keyWords
	 * 5. Save dataset-to-paper
	 * 
	 *  Note: Before updating the entries, we are clearing all earlier entries in m-n tables
	 * 
	 * */
	
	@RequestMapping(method = RequestMethod.PUT, value = "/datasets/{id}", produces="application/json")
	@ApiOperation(value = "Update Dataset", response = Dataset.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Updating the dataset is forbidden"),
			@ApiResponse(code = 404, message = "The dataset resource is not found") })
	public String updateDataset(HttpServletRequest request, @PathVariable Long id, @Valid  @RequestBody  CreateDatasetHelperBean createDatasetHelperBean) throws EntityNotFoundException {
		
		System.out.println("In update dataset : ");
		
		//1. Reterive the dataset with the particular id
		Dataset dataset = datasetDAO.findById(id).orElse(null);
		
		if(dataset == null) {
			throw new EntityNotFoundException("Dataset Not found : "+id);
			
		}
		dataset.setDescription(createDatasetHelperBean.getDescription());
		
		dataset.setIsPublic(createDatasetHelperBean.isIs_public());

		dataset = datasetDAO.save(dataset);
		
		fundingGrantDAO.deleteFundingGrantByDatasetId(id);
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
						throw new EntityNotFoundException("Funding source doesn't exists");
					}
					
					fundingGrant.setDataset(dataset);
					fundingGrant.setFundingGrantPK(fundingGrantPK);
					fundingGrant.setFundingSource(fundingSource);
					fundingGrant.setUrl(fundGrant.getUrl());
					
					fundingGrant = fundingGrantDAO.save(fundingGrant);
					
		}
				
				
		datasetToExperimentTypeDAO.deleteDatasetToExperimentTypeByDatasetId(id);
		//3)Save dataset-to-experimentType
		for(CreateDatasetToExperimentTypeHelperBean expTypeIdAndDes :createDatasetHelperBean.getExperiment_types()) {
					
					//get experiment type
					ExperimentType experimentType = experimentTypeDAO.findById(expTypeIdAndDes.getExperiment_type_id()).orElse(null);
					
					if(experimentType == null) {
						System.out.println("deleteing dataset" + dataset.getDatasetId());
						datasetDAO.deleteById(dataset.getDatasetId());
						throw new EntityNotFoundException("Experiment type doesn't exists");
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
					
		//4)Save dataset-to-keyWords
				datasetToKeywordDAO.deleteDatasetToKeywordByDatasetId(id);
				for(Long keyWordId :createDatasetHelperBean.getKeywordsIds()) {
					
					//get keyword
					Keyword keyword = keywordDAO.findById(keyWordId).orElse(null);
					if(keyword == null) {
						datasetDAO.deleteById(dataset.getDatasetId());
						throw new EntityNotFoundException("Keyword doesn't exists");
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
		 datasetToPaperDAO.deleteDatasetToPaperByDatasetId(id);
		 for(Long paperId : createDatasetHelperBean.getPaperIds()) {
					
					//get Paper
					Paper paper = paperDAO.findById(paperId).orElse(null);
					if(paper == null) {
						datasetDAO.deleteById(dataset.getDatasetId());
						throw new EntityNotFoundException("Paper doesn't exists");
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
	
	/*
	 * This method implements file upload mechansim in the multipart file. Please note, that files
	 * info get saved in two parts
	 * Part 1: Save uploaded file in temporary file, still all chunks are not arrived
	 * Part 2: When its a last chunk, do the following:
	 * 			1. Save the file in the database : which returns the file_id
	 * 			2. Rename the temp file with file_id (to avoid duplicate file names) on the server
	 * 
	 * Definitions : 
	 * META_INFO - It is the information of the file : dataset, description, data_type
	 * 
	 */
	@ApiOperation(value = "Upload file", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "File uploaded successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the dataset"),
			@ApiResponse(code = 403, message = "Accessing the dataset is forbidden"),
			@ApiResponse(code = 404, message = "The dataset resource is not found") })
	@CrossOrigin
	@RequestMapping(value = "/dataset/file/upload", consumes = {"multipart/form-data"}, method = RequestMethod.POST, produces="application/json")

	public FileUploadBean uploadFile(@RequestParam("file") MultipartFile file, 
			@RequestParam("resumableFilename") String resumableFilename,
			@RequestParam("resumableRelativePath") String relativePath,
			@RequestParam("resumableChunkSize") long resumableChunkSize,
            @RequestParam("resumableChunkNumber") int resumableChunkNumber,
            @RequestParam("resumableTotalChunks") int resumableTotalChunks)throws IOException, InterruptedException, SQLException {
		
		
		//save the file in the temporary file
		 Path tempFile = Paths.get("datasetFile", resumableFilename + ".tmp");
		 ByteBuffer out = ByteBuffer.wrap(file.getBytes());
		 
		 try (FileChannel channel = FileChannel.open(tempFile, StandardOpenOption.WRITE,StandardOpenOption.CREATE)) {
	            channel.position((resumableChunkNumber - 1) * resumableChunkSize);
	            while (out.hasRemaining()) {
	                channel.write(out);
	            }
	      }
		
		 //if its a last chunk, then save the file in db and rename the file name
		 if (resumableTotalChunks == resumableChunkNumber) {
			 	
			    
	            System.out.println("File uploaded successfuly");
	            long file_size = FileChannel.open(tempFile).size();
	            
	            //save the file in db
	            long dataFileId = saveUploadedFile("datasetFile", resumableFilename.split(",")[0], file_size);
	            
	            //save the file in system with the name as file id
	            Files.move(tempFile, Paths.get("datasetFile", ""+dataFileId), StandardCopyOption.REPLACE_EXISTING);
	            
	            //prepare result bean with success message
	            FileUploadBean result = new FileUploadBean(dataFileId, "File Uploaded Successfully");
	            
	            //return
	            return result;
	            
	        } else {
	        	System.out.println("File uploaded in progress");
	        	FileUploadBean result = new FileUploadBean((long)-1, "File uploaded in progress");
	        	return result;
	        }
		 
	
		
	}
	
	/*
	 * Helper Method to saveUploadedFile to the database. When the file is saved in db, the file gets
	 * file_id. The file_id is return by this method. The Invoking method then use this file_id to 
	 * rename the file with file_id and store on the server
	 * 
	 * SAVE THE UPLOADED FILE
	 */
	
	private long saveUploadedFile(String filePath, String orginalFileName, long file_size) throws SQLException {
		
		DataFile dataFile = new DataFile();
	

		//in this case it is always 1. 1 === in_progress. As at this point of time we don't have meta information of the file
		long dataset_type_id = 1;
		
		DataType dataType = dataTypeDAO.findById(dataset_type_id).orElse(null); //in progress
		
		dataFile.setOrigFileName(orginalFileName);//save original file name	
		
		dataFile.setSize(file_size);//set file_size
		
		dataFile.setDataType(dataType); //in_progress
		
		dataFile = dataFileDAO.save(dataFile); //save
		
	
		//return id
		return dataFile.getDataFileId();
		
	}
	/*
	 * This method is called when file has been uploaded to the server. This is the method, to provide the meta 
	 * information of the file.
	 * SAVE THE META INFO of the file. (dataset_id, description, data_type)
	 */


	@RequestMapping(method = RequestMethod.POST, value = "/dataset/file/save_info", produces="application/json")
	@ApiOperation(value = "Save File information", response = DataFileInfoBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Accessing the dataset is forbidden"),
			@ApiResponse(code = 404, message = "The dataset resource is not found") })
	public String saveMetaInformation( @RequestBody DataFileInfoBean dataFileInfo ) throws NoResposneException, SQLException, EntityNotFoundException {
		System.out.println("Inside save file");
		
		//get dataFile
		DataFile dataFile = dataFileDAO.findById(dataFileInfo.getFile_id()).orElse(null);
		
		//get dataset
		Dataset dataSet = datasetDAO.findById(dataFileInfo.getDataset_id()).orElse(null);
		
		//get dataType
		DataType dataType = dataTypeDAO.findById(dataFileInfo.getData_type_id()).orElse(null);
		//System.out.println(dataFileInfo.getData_type_id());
		
		//check for exceptions
		if(dataFile == null)
			throw new EntityNotFoundException("File id not valid");
		
		if(dataSet == null)
			throw new EntityNotFoundException("Dataset id not valid");
		
	
		if(dataType == null)
			throw new EntityNotFoundException("DataType id not valid");
		
		
		//set the entries
		dataFile.setDataset(dataSet);
		dataFile.setDataType(dataType);
		dataFile.setDescription(dataFileInfo.getDescription());

		//check if dataset has same file already exist in database
		DataFile duplicateDataFile = dataFileDAO.checkIfDuplicateFile(dataFileInfo.getDataset_id(), dataFile.getOrigFileName());
		
		//duplicate entry
		if(duplicateDataFile != null)
			throw new SQLException("Duplicate file name! File with same name, assigned to same dataset exists already");
		
		try {
			dataFileDAO.save(dataFile);
			return "Success";
		}
		catch(Exception e) {
			throw new NoResposneException("Something went wrong. Please try after sometime");
		}
		
	}
	
	/*
	 * 
	 * Return data-type list
	 */


	@RequestMapping(method = RequestMethod.GET, value = "/dataTypes", produces="application/json")
	@ApiOperation(value = "Return Data-Type List", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Accessing the data-type list is forbidden"),
			@ApiResponse(code = 404, message = "The resource is not found") })
	public List<DataType> getDataTypeList() {
		
		List<DataType> dataTypes = dataTypeDAO.findAll();
		return dataTypes;
	}
	
	/*
	 * Deletes the uploaded file
	 * 
	 */


	@RequestMapping(method = RequestMethod.DELETE, value = "/dataFiles/{id}", produces="application/json")
	@ApiOperation(value = "Delete a file")
	public String deleteUploadedFile(@PathVariable Long id) throws NoResposneException, EntityNotFoundException {
		System.out.println("In delete file");
		
			if(!dataFileDAO.existsById(id))
				throw new EntityNotFoundException("File id doesn't exist");
			
			deletePhysicalFile(id);
			try {
			dataFileDAO.deleteById(id);
			}catch(Exception e){
				throw new NoResposneException("Something went wrong. Please try after sometime");
			}
			
			return "Deleted";
			
		
		
	}
	/*
	 * Helper Method
	 * The method deletes the file from the server
	 * */
	private void deletePhysicalFile(Long id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		try
        { 
			System.out.println("In delete physical file" +id);
            Files.delete(Paths.get("datasetFile/"+id)); 
        } 
        catch(NoSuchFileException e) 
        { 
            throw new EntityNotFoundException("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            throw new EntityNotFoundException("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            throw new EntityNotFoundException("Invalid permissions."); 
        } 
          
	}
	
	
}
