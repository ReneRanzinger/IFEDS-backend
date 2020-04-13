 CREATE SCHEMA IF NOT EXISTS core;
DROP TABLE IF EXISTS core.permissions;
DROP TABLE IF EXISTS core.settings;
DROP TABLE IF EXISTS core.funding_grant;
DROP TABLE IF EXISTS core.dataset_to_paper;
DROP TABLE IF EXISTS core.dataset_to_keyword;
DROP TABLE IF EXISTS core.dataset_to_experiment_type;
DROP TABLE IF EXISTS core.data_file;
DROP TABLE IF EXISTS core.data_type;
DROP TABLE IF EXISTS core.dataset;
DROP TABLE IF EXISTS core.funding_source;
DROP TABLE IF EXISTS core.paper;
DROP TABLE IF EXISTS core.keyword;
DROP TABLE IF EXISTS core.experiment_type;
DROP TABLE IF EXISTS core.sample_to_sample_descriptor;
DROP TABLE IF EXISTS core.sample_descriptor;
DROP TABLE IF EXISTS core.sample;
DROP TABLE IF EXISTS core.sample_type;
DROP TABLE IF EXISTS core.provider;
CREATE TABLE IF NOT EXISTS core.provider
(
	provider_id serial NOT NULL PRIMARY KEY,
	name VARCHAR(64) NOT NULL,
	provider_group VARCHAR(64),
	department VARCHAR(64),
	affiliation VARCHAR(64),
	url VARCHAR(256),
	contact VARCHAR(32),
	username VARCHAR(64) NOT NULL UNIQUE,
	password VARCHAR(64) NOT NULL,
	email VARCHAR(32) NOT NULL UNIQUE,
	auth_token VARCHAR(128),
	auth_time_out integer,
	password_reset_token VARCHAR(256) UNIQUE,
	active boolean DEFAULT true
);


CREATE TABLE IF NOT EXISTS core.sample_type
(
    sample_type_id serial PRIMARY KEY,
    name VARCHAR (50) NOT NULL UNIQUE,
    description VARCHAR (1000),
    url VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS core.sample
(
    sample_id serial PRIMARY KEY,
    name VARCHAR (50) UNIQUE NOT NULL,
    sample_type_id integer NOT NULL references core.sample_type ON UPDATE CASCADE ON DELETE CASCADE,
    provider_id integer NOT NULL REFERENCES core.provider ON UPDATE CASCADE ON DELETE CASCADE,
    description VARCHAR (1000),
    url VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS core.sample_descriptor
(
    sample_descriptor_id serial PRIMARY KEY,
    name VARCHAR (32) NOT NULL UNIQUE,
    description VARCHAR (1000) NOT NULL,
    namespace VARCHAR(1000),
    url VARCHAR(256),
    link_pattern VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS core.sample_to_sample_descriptor
( 
	sample_id integer REFERENCES core.sample ON UPDATE CASCADE ON DELETE CASCADE,
    sample_descriptor_id integer REFERENCES core.sample_descriptor ON UPDATE CASCADE ON DELETE CASCADE,
   sample_descriptor_value VARCHAR (64) NOT NULL,
    unit_of_measurement VARCHAR (256),
	PRIMARY KEY(sample_id,sample_descriptor_id,sample_descriptor_value )
);



CREATE TABLE IF NOT EXISTS core.experiment_type
(
	experiment_type_id serial NOT NULL PRIMARY KEY,	
	name VARCHAR(256) NOT NULL UNIQUE,
	description VARCHAR(1000),
	url VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS core.keyword
(
	keyword_id serial NOT NULL PRIMARY KEY,	
	name VARCHAR(64) NOT NULL UNIQUE,
	description VARCHAR(1000),
	url VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS core.paper
(
	paper_id serial NOT NULL PRIMARY KEY,	
	title VARCHAR(256) NOT NULL,
	author_list VARCHAR(1024) NOT NULL,
	journal_name VARCHAR(64),
	pmid integer NOT NULL UNIQUE,
	url VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS core.funding_source
(
	funding_source_id serial NOT NULL PRIMARY KEY,	
	name VARCHAR(64) NOT NULL UNIQUE,
	abbreviation VARCHAR(16),
	url VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS core.dataset
(
	dataset_id serial NOT NULL PRIMARY KEY,
	name VARCHAR(256) NOT NULL UNIQUE,
	sample_id integer NOT NULL REFERENCES core.sample ON UPDATE CASCADE ON DELETE CASCADE,
	provider_id integer NOT NULL REFERENCES core.provider ON UPDATE CASCADE ON DELETE CASCADE,
	description VARCHAR(1000),
	is_public BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS core.data_type
(
	data_type_id serial NOT NULL PRIMARY KEY,
	name VARCHAR(32) NOT NULL UNIQUE,
	description VARCHAR(1000),
	url VARCHAR(256) 
);

CREATE TABLE IF NOT EXISTS core.data_file
(
	data_file_id serial NOT NULL PRIMARY KEY,
	dataset_id integer NULL REFERENCES core.dataset ON UPDATE CASCADE ON DELETE CASCADE,
	data_type_id integer NOT NULL REFERENCES core.data_type ON UPDATE CASCADE ON DELETE CASCADE,
	original_file_name VARCHAR(64) NOT NULL,
	description VARCHAR(1000),
	data_file_size integer  NULL


);

CREATE TABLE IF NOT EXISTS core.dataset_to_experiment_type
(
	dataset_id integer REFERENCES core.dataset ON UPDATE CASCADE ON DELETE CASCADE,
	experiment_type_id integer REFERENCES core.experiment_type ON UPDATE CASCADE ON DELETE CASCADE,
	description VARCHAR(1000),
	PRIMARY KEY(dataset_id,experiment_type_id)
);

CREATE TABLE IF NOT EXISTS core.dataset_to_keyword
(
	dataset_id integer REFERENCES core.dataset ON UPDATE CASCADE ON DELETE CASCADE,
	keyword_id integer REFERENCES core.keyword ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(dataset_id,keyword_id)
);

CREATE TABLE IF NOT EXISTS core.dataset_to_paper
(
	dataset_id integer REFERENCES core.dataset ON UPDATE CASCADE ON DELETE CASCADE,
	paper_id integer REFERENCES core.paper ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY(dataset_id,paper_id)
);

CREATE TABLE IF NOT EXISTS core.funding_grant
(
	dataset_id integer REFERENCES core.dataset ON UPDATE CASCADE ON DELETE CASCADE,
	funding_source_id integer REFERENCES core.funding_source ON UPDATE CASCADE ON DELETE CASCADE,
	grant_number varchar(64) NOT NULL,
	url VARCHAR(256),
	PRIMARY KEY(dataset_id, funding_source_id,grant_number)
);

CREATE TABLE IF NOT EXISTS core.settings
(
    settings_id serial PRIMARY KEY,
    key VARCHAR (300) NOT NULL UNIQUE,
    value VARCHAR (2048)
);

CREATE TABLE IF NOT EXISTS core.permissions
(
    permissions_id serial PRIMARY KEY,
    provider_id integer NOT NULL REFERENCES core.provider ON UPDATE CASCADE ON DELETE CASCADE,
    permission_level varchar(256) NOT NULL,
    UNIQUE (provider_id, permission_level)
);
