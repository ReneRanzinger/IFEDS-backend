package edu.uga.ccrc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uga.ccrc.dao.PaperDAO;
import edu.uga.ccrc.entity.Paper;
import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.NoResponeException;
import edu.uga.ccrc.view.bean.PaperBean;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RestController
public class PaperController {
	@Autowired
	PaperDAO paperDAO;
	@ApiOperation(value = "Returns meta information about paper", response = PaperBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"), 
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 404, message = "Paper with given pubmed id not found/valid")}
	)
	
	@CrossOrigin
	@GetMapping(value = "/paper_mata_data/{pmid}", produces = "application/json")
	// http://localhost:8080/datasets
	public PaperBean getPaperMetaData(HttpServletRequest request, @PathVariable long pmid) throws IOException, JSONException, EntityNotFoundException, NoResponeException  {
		
		if(paperDAO.existsById(pmid))
		{
			System.out.println("Paper already in db. Returning the meta-info");
			Paper paper = paperDAO.findById(pmid).orElse(null);
			
			PaperBean paperBean = new PaperBean(paper.getTitle(),paper.getAuthorList(),paper.getJournalName(),pmid, paper.getUrl());
			
			return paperBean;
		}
		
		System.out.println("New paper. Saving to db and returning meta-info. \n User accessed getPaperMetaData : " + pmid);
		String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=pubmed&id="+pmid+"&retmode=json&tool=my_tool";
		JSONObject json = readJsonFromUrl(url);
		
		//if any error
		if(json.toString().contains("error"))
			throw new EntityNotFoundException("No result found for PubMed Id: "+pmid);
	    
		//Decode multilevel JSON Object
		JSONObject result = json.getJSONObject("result");
		JSONObject pm_id = result.getJSONObject(""+pmid);
		String title = (String) pm_id.get("title");
		String journalName = (String) pm_id.get("fulljournalname");
		
		//Get authors from JSON array of Authors
		JSONArray authorsJson = (JSONArray)pm_id.get("authors");
		int len = authorsJson.length();
		StringBuilder author_list = new StringBuilder();
		for(int author_num = 0; author_num < len; author_num++) {
			String author_name = (String)authorsJson.getJSONObject(author_num).get("name");
			author_list.append(author_name+",");
		}
		
		//Save information of paper
		PaperBean paperBean = new PaperBean(title,author_list.toString(),journalName,pmid, "https://pubmed.ncbi.nlm.nih.gov/"+pmid);
		
		//save paper to database
		savePaperToDB(paperBean, pmid);
		
		//return paper meta info
		return paperBean;
	}
	
	private void savePaperToDB(PaperBean paperBean, long pmid) throws NoResponeException {

		if(paperDAO.findById(pmid) != null || paperDAO.findByPMId(pmid) != null) {
		
			Paper paper = new Paper(paperBean);
			try {
				paperDAO.save(paper);
			}catch (Exception e){
				System.out.println(e.getLocalizedMessage());
				throw new NoResponeException("Cannot save the paper");
			}
		}
		
	}

	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	
}


