/**
 * @author Susan George
 */
package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.view.bean.DatasetBean;

@RestController
public class DatasetController {

	@Autowired
	DatasetDAO datasetDAO;

	@RequestMapping(method = RequestMethod.GET, value = "/getDatasets", produces="application/json")
	public List<DatasetBean> findAllDatasets() {
		System.out.println("Retrieving datasets : findAllDatasets() ");
		List<DatasetBean> res=new ArrayList<DatasetBean>();
		for (Dataset ds : datasetDAO.findAll()) {
			DatasetBean b=new DatasetBean();
			b.setDatasetId(ds.getDatasetId());
			b.setDatasetName(ds.getName());
			b.setDescription(ds.getDescription());
			b.setSampleName(ds.getSample().getName());
			b.setProviderName(ds.getProvider().getName());
			res.add(b);
		}
		return res;
	}
}
