INSERT INTO core.provider(name,username,password,email)
VALUES
	('CCRC1','ccrc_user1','$2a$04$wKp57dviLiap9j4qN3HdI.3Q0ssJfWZ8UQsyoeB39XnKRGYwEML5G','ccrc1@gmail.com'),
	('CCRC2','ccrc_user2','$2a$04$4PeVgm9WuH76DG3rCqsgBuwMhdU./eQK6Qh6LT51lJsUuxmvO8Biu','ccrc2@gmail.com'),
	('aaaaaaaaaabbbbbbbbbbbbbbbb','ccrc_user3','$2a$04$4PeVgm9WuH76DG3rCqsgBuwMhdU./eQK6Qh6LT51lJsUuxmvO8Biu','ccrc3@gmail.com');

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
	('in progress','in progress data file.'),
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
	('Differenciated smooth muscle cell',2,1,'Smooth muscle cell derived after differentiation from human embryonic stem cell.'),
	('Hair - Used by forensic teams for investigation',1,3,'Hair is a protein filament that grows from follicles found in the dermis. Hair is one of the defining characteristics of mammals. The human body, apart from areas of glabrous skin, is covered in follicles which produce thick terminal and fine vellus hair.');

INSERT INTO core.sample_to_sample_descriptor(sample_id,sample_descriptor_id,sample_descriptor_value)
VALUES
	(1,1,'Homo Sapiens'),
	(1,2,'Smooth muscle');

INSERT INTO core.dataset(name,sample_id,provider_id,description,is_public)
VALUES
	('Stem Cell Dataset for Infrasturucture of Experimental Data Analysis 1',1,1,'Glycomics analysis performed with the stem cell data set 1- comprehensive study of glycomes including genetic, physiologic, pathologic, and other aspects',TRUE),
	('Stem Cell Data 2',1,1,'Glycomics analysis performed with the stem cell data set 2.',FALSE),
	('Hair Data 3',2,1,'Hair analysis performed with the hair data set 3- Hair analysis may refer to the chemical analysis of a hair sample, but can also refer to microscopic analysis or comparison. Chemical hair analysis may be considered for retrospective purposes when blood and urine are no longer expected to contain a particular contaminant, typically three months or less.',TRUE),
	('Stem Cell Data 4',1,2,'Glycomics analysis performed with the stem cell data set 4.',TRUE),
	('Stem Cell Data 5',1,2,'Glycomics analysis performed with the stem cell data set 5.',TRUE),
	('Hair Data 6',2,2,'Hair analysis performed with the hair data set 6.',TRUE),
	('Stem Cell Data 7',1,3,'Glycomics analysis performed with the stem cell data set 7.',TRUE),
	('Stem Cell Data 8',1,3,'Glycomics analysis performed with the stem cell data set 8.',FALSE),
	('Stem Cell Data 9',1,3,'Glycomics analysis performed with the stem cell data set 9.',FALSE);

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

INSERT INTO core.settings(
		key, value)
	VALUES ('header.name', 'IFED');
	
INSERT INTO core.settings(
		key, value)
	VALUES ('header.logo1', 'https://pngimage.net/wp-content/uploads/2018/06/logo-placeholder-png-2.png');
	
INSERT INTO core.permissions(
		provider_id, permission_level)
	VALUES (1, 'admin');