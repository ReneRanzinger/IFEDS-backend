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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.DataFile;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.DatasetToExperimentType;
import edu.uga.ccrc.entity.DatasetToKeyword;
import edu.uga.ccrc.entity.DatasetToPaper;
import edu.uga.ccrc.entity.FundingGrant;
import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.Paper;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.ForbiddenException;
import edu.uga.ccrc.view.bean.DataFileBean;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.DatasetDetailBean;
import edu.uga.ccrc.view.bean.DatasetToExperimentTypeBean;
import edu.uga.ccrc.view.bean.FundingGrantBean;
import edu.uga.ccrc.view.bean.ProviderBean;
import edu.uga.ccrc.view.bean.SampleWithDescriptorListBean;
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
	ProviderDAO providerDao;

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

			Provider provider = providerDao.findByUsername(username);

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
			Provider provider = providerDao.findByUsername(username);
			
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
}
