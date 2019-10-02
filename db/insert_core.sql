INSERT INTO core.provider(name,username,password,email)
VALUES
	('CCRC','ccrc_user','ccrc_pwd','ccrc@gmail.com');

INSERT INTO core.sample_type(name,description)
VALUES
	('biological','Sample derived from a biological source.'),
	('stem cell derived','Sample derived from a stem cell or differentiated stem cell.'),
	('synthetic','Synthetic sample');

INSERT INTO core.experiment_type(name,description,url)
VALUES 
	('MS profile','Mass spectrometry study using overview MS.',null),	
	('Tandem MS','Mass spectrometry study using MS/MS techniques.','https://en.wikipedia.org/wiki/Tandem_mass_spectrometry'),
	('LC-MS/MS','Structural study using liquid chromatography tandem mass spectrometry.','https://en.wikipedia.org/wiki/Liquid_chromatography%E2%80%93mass_spectrometry'),
	('NMR','Structural study using nuclear magnetic resonance.','https://en.wikipedia.org/wiki/Nuclear_magnetic_resonance');

INSERT INTO core.sample_descriptor(name,description,url,namespace)
VALUES
	('Species','Organism that the sample was derived from.','https://www.ncbi.nlm.nih.gov/taxonomy','Homo Sapiens'),
	('Tissue','Tissue the sample was derived from.','https://www.nlm.nih.gov/mesh/meshhome.html','Smooth muscle'),
	('Disease','Disease that might affect the sample.','https://en.wikipedia.org/wiki/Disease',null);

INSERT INTO core.data_type(name,description)
VALUES
	('MS','Mass spectrometry data file.'),
	('NMR','NMR data file.'),
	('LC','Liquid chromatography data file.'),
	('Gel','Gel image'),
	('MS Annotation','Datafile with the annotation of an MS experiment'	);


INSERT INTO core.funding_source(name,abbreviation,url)
VALUES 
	('National Institutes of Health','NIH','https://www.nih.gov/'),
	('Department of Energy','DOE','https://www.energy.gov/'),
	('National Science Foundation','NSF','http://www.nsf.org/');


INSERT INTO core.keyword(name,description,url)
VALUES 
	('glycomics','Data related to the expresion of carbhydrate structures, their attachement to glycoconjugates and their function.','https://en.wikipedia.org/wiki/Glycomics'),
	('glycoproteomics','Data related to the expression of glycoproteins/glycopeptides, their abundance and function.','https://en.wikipedia.org/wiki/Glycoproteomics');


INSERT INTO core.paper(title,author_list,pmid,url)
VALUES
	('gFinder: A Web-Based Bioinformatics Tool for the Analysis of N-Glycopeptides.','Kim JW, Hwang H, Lim JS, Lee HJ, Jeong SK, Yoo JS, Paik YK',27573070,'https://pubs.acs.org/doi/10.1021/acs.jproteome.6b00772'),
	('GlycomeDB','Ranzinger R, York WS',25753706,'https://link.springer.com/protocol/10.1007%2F978-1-4939-2343-4_8');

INSERT INTO core.sample(name,sample_type_id,provider_id,description)
VALUES
	('Differenciated smooth muscle cell',2,1,'Smooth muscle cell derived after differentiation from human embryonic stem cell.');

INSERT INTO core.sample_to_sample_descriptor(sample_id,sample_descriptor_id,sample_descriptor_value)
VALUES
	(1,1,'Homo Sapiens'),
	(1,2,'Smooth muscle');

INSERT INTO core.dataset(name,sample_id,provider_id,description)
VALUES
	('Stem Cell Data 10',1,1,'Glycomics analysis performed with the stem cell data set 10.');

INSERT INTO core.dataset_to_experiment_type
VALUES
	(1,2,'Tandem MS was performed to identify the pool of N-glycans'),
	(1,3,'LC-MS/MS was performed to identify the pool of O-Glycans');

INSERT INTO core.dataset_to_paper
VALUES
	(1,1),
	(1,2);

INSERT INTO core.dataset_to_keyword
VALUES
	(1,1),
	(1,2);

INSERT INTO core.funding_grant
VALUES
	(1,1,'1U01GM125267 - 01');

INSERT INTO core.data_file(dataset_id,data_type_id,original_file_name,description)
VALUES
	(1,1,'MP-hStem10stdfrag12-2_120503120725.raw','Instrument file for the Tandem MS N-Glycan Run'),
	(1,1,'MP-hStem10stdfrag2-2_120503120725.mzXML','MzXML standard file for the Tandem MS N-glycan Run'),
	(1,1,'MP-hStem10std_120424130807_xt.raw','eXtract quantification file for the MS run'),
	(1,5,'MP-hStem10std-GRITS-annotation.zip','GRITS Toolbox annotation of the MS run'),
	(1,5,'MP-hStem10std-SimGlycan.csv','SimGlycan annotaiton of the MS run');




	





	
