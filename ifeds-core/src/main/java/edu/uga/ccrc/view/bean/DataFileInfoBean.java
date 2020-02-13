package edu.uga.ccrc.view.bean;

public class DataFileInfoBean {

		long file_id;
		long data_type_id;
		long dataset_id;
		String description;
		
		public long getFile_id() {
			return file_id;
		}
		public void setFile_id(long file_id) {
			this.file_id = file_id;
		}
		public long getData_type_id() {
			return data_type_id;
		}
		public void setData_type_id(long data_type_id) {
			this.data_type_id = data_type_id;
		}
		public long getDataset_id() {
			return dataset_id;
		}
		public void setDataset_id(long dataset_id) {
			this.dataset_id = dataset_id;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
}
