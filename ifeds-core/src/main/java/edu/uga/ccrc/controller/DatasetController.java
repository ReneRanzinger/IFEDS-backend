/**
 * @author Susan George
 */
package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.view.bean.DatasetBean;

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
		
		if(requestTokenHeader==null) {
			System.out.println("Retrieving datasets : findPublicDatasets() ");
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
		
		
		
		
		return res;
	}
	@RequestMapping(method = RequestMethod.DELETE, value = "/datasets/{id}", produces="application/json")
	public void deleteDataset(@PathVariable long id) {
		System.out.println("Deleting datasets : deleteDataset() id : " + id);
		datasetDAO.deleteById(id);
		
		
	}
}
