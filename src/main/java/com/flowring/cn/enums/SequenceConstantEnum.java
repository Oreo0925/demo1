package com.flowring.cn.enums;

public enum SequenceConstantEnum {
	MEM {
        public String getDescription() {
            return "MEM_SEQUENCE";
        }
        public String getPrefixDescription() {
            return "MEM_PREFIX_SEQUENCE";
        }
        public String getPrefixAndDateDescription() {
            return "MEM_PREFIX_DATE_SEQUENCE";
        }
    },  
	
	PWD {
    	 public String getDescription() {
             return "PWD_SEQUENCE";
         }
         public String getPrefixDescription() {
             return "PWD_PREFIX_SEQUENCE";
         }
         public String getPrefixAndDateDescription() {
             return "PWD_PREFIX_DATE_SEQUENCE";
         }
    },
	
	DEV {
    	 public String getDescription() {
             return "DEV_SEQUENCE";
         }
         public String getPrefixDescription() {
             return "DEV_PREFIX_SEQUENCE";
         }
         public String getPrefixAndDateDescription() {
             return "DEV_PREFIX_DATE_SEQUENCE";
         }
    };

	public abstract String getDescription();
	public abstract String getPrefixDescription();
	public abstract String getPrefixAndDateDescription();
		
}
