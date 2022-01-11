package com.project.lakshmi.model;

public class DatabaseName {
	
	public static final String ID = "id";
	
	public class CHECKLIST {
		
		public static final String TABLE = "checklist";

		public static final String CREATION_DATE = "creation_date";
		
		public static final String END_DATE = "end_date";
	}	
	
	public class CHECKLIST_STEP {
		
		public static final String TABLE = "checklist_step";
		
		public static final String CHECKLIST_ID = "checklist_id";

		public static final String CHECKLIST_STEP_INFOS_ID = "checklist_step_infos_id";
		
		public static final String STARTED = "started";
		
		public static final String ENDED = "ended";
	}	
	
	public class CHECKLIST_STEP_INFOS {
		
		public static final String TABLE = "checklist_step_infos";
		
		public static final String ORDER = "order";
		
		public static final String LABEL = "label";
	}
}
