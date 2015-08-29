 package com.kingdrive.workflow.model.runtime;
 
 import com.kingdrive.workflow.exception.WorkflowException;
 import com.kingdrive.workflow.model.TableData;
 import com.kingdrive.workflow.service.db.WFRuntimeService;
 import com.kingdrive.workflow.utils.BusinessJuniorExp;
 import com.kingdrive.workflow.utils.WFUtil;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Comparator;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
import java.util.Set;
 
 public class BusinessJuniorModel
   implements Serializable, Comparator
 {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1889902169193533705L;

		public static String COMMON_CODE = "#"; // 表示对单位或组织或职位代码不做限制，全部单位|组织|职位的意思

		private static final String AND = "@AND@";

		/* 组织上级代码，由运行时决定 */
		public static String SUP_CODE = "0#";

		/* 同级代码，由运行时决定 */
		public static String SAME_CODE = "00#";
		
   private String id;
   private String projectName;
   private String desc;
   private String priority;
   private String junCoCode;
   private String junOrgCode;
   private String junPosiCode;
   private String junEmpCode;
   private String supCoCode;
   private String supOrgCode;
   private String supPosiCode;
   private String supEmpCode;
   private String supCondition;
   private String nd;
 
   public String getDesc()
   {
     return this.desc;
   }
 
   public void setDesc(String desc) {
     this.desc = desc;
   }
 
   public String getJunCoCode() {
     return this.junCoCode;
   }
 
   public void setJunCoCode(String junCoCode) {
     this.junCoCode = junCoCode;
   }
 
   public String getJunEmpCode() {
     return this.junEmpCode;
   }
 
   public void setJunEmpCode(String junEmpCode) {
     this.junEmpCode = junEmpCode;
   }
 
   public String getJunOrgCode() {
     return this.junOrgCode;
   }
 
   public void setJunOrgCode(String junOrgCode) {
     this.junOrgCode = junOrgCode;
   }
 
   public String getJunPosiCode() {
     return this.junPosiCode;
   }
 
   public void setJunPosiCode(String junPosiCode) {
     this.junPosiCode = junPosiCode;
   }
 
   public String getProjectName() {
     return this.projectName;
   }
 
   public void setProjectName(String projectName) {
     this.projectName = projectName;
   }
 
   public String getSupCoCode() {
     return this.supCoCode;
   }
 
   public void setSupCoCode(String supCoCode) {
     this.supCoCode = supCoCode;
   }
 
   public String getSupCondition() {
     return this.supCondition;
   }
 
   public void setSupCondition(String supCondition) {
     this.supCondition = supCondition;
   }
 
   public String getSupEmpCode() {
     return this.supEmpCode;
   }
 
   public void setSupEmpCode(String supEmpCode) {
     this.supEmpCode = supEmpCode;
   }
 
   public String getSupOrgCode() {
     return this.supOrgCode;
   }
 
   public void setSupOrgCode(String supOrgCode) {
     this.supOrgCode = supOrgCode;
   }
 
   public String getSupPosiCode() {
     return this.supPosiCode;
   }
 
   public void setSupPosiCode(String supPosiCode) {
     this.supPosiCode = supPosiCode;
   }
 
   public String getNd() {
     return this.nd;
   }
 
   public void setNd(String nd) {
     this.nd = nd;
   }
 
   public String getId() {
     return this.id;
   }
 
   public void setId(String id) {
     this.id = id;
   }
 
   public String getPriority()
   {
     return this.priority;
   }
 
   public void setPriority(String priority)
   {
     this.priority = priority;
   }
 
   public String[] getJuniorComs() {
     return WFUtil.split2(this.junCoCode, ",");
   }
 
   public String[] getJuniorOrgs() {
     return WFUtil.split2(this.junOrgCode, ",");
   }
 
   public String[] getJuniorPositions() {
     return WFUtil.split2(this.junPosiCode, ",");
   }
 
   public String[] getJuniorUsers() {
     return WFUtil.split2(this.junEmpCode, ",");
   }
 
   public String[] getSelfComs() {
     return WFUtil.split2(this.supCoCode, ",");
   }
 
   public String[] getSelfOrgs() {
     return WFUtil.split2(this.supOrgCode, ",");
   }
 
   public String[] getSelfPositions() {
     return WFUtil.split2(this.supPosiCode, ",");
   }
 
   public String[] getSelfUsers() {
     return WFUtil.split2(this.supEmpCode, ",");
   }
 
   public ArrayList getCompoConditions() {
     return getCompoConditions(getSupCondition());
   }
 
   public static ArrayList doFilter(List all, String junCoCode, String junOrgCode, String junPosiCode, String junior)
     throws WorkflowException
   {
     ArrayList result = new ArrayList();
     for (int i = 0; i < all.size(); i++) {
       BusinessJuniorModel bj = (BusinessJuniorModel)all.get(i);
       String[] jc = bj.getJuniorComs();
       String[] jo = bj.getJuniorOrgs();
       String[] jp = bj.getJuniorPositions();
       String[] ju = bj.getJuniorUsers();
       if ((!contains(jc, junCoCode)) || (!contains(jo, junOrgCode)) || 
         (!contains(jp, junPosiCode)) || (!contains(ju, junior))) continue;
       result.add(bj);
     }
     return result;
   }
 
   private static boolean contains(String[] arr, String str) {
     if (arr == null)
       return false;
     for (int i = 0; i < arr.length; i++)
     {
       if (COMMON_CODE.equals(arr[i]))
         return true;
       if (arr[i].equals(str))
         return true;
       if ("".equals(str))
         return true;
     }
     return false;
   }
 
   /**
	 * 从条件字串中抽取出与部件相关的条件
	 * 
	 * @param cons
	 * @return
	 */
	public static ArrayList getCompoConditions(String cons) {
		ArrayList cs = new ArrayList();
		if (cons == null)
			return new ArrayList();
		String[] conArr = cons.split(AND);
		for (int i = 0; i < conArr.length; i++) {
			String con = conArr[i];
			try {
				BusinessJuniorExp exp = new BusinessJuniorExp(con);
				// 如果是部件表达式
				if (exp.isCompoExpression()) {
					String compoAttribute = exp.getPara1();
					int idx = compoAttribute.indexOf(".");
					BusinessJuniorModel b = new BusinessJuniorModel();
					if (idx > 0) {
						CompoCondition cc = b.new CompoCondition(compoAttribute
								.substring(0, idx), compoAttribute
								.substring(idx + 1), exp.getSymbol(), exp
								.getPara2());
						cs.add(cc);
					} else {
						// 只是部件名
						if (compoAttribute.equals("@COMPOID")) {
							CompoCondition cc = b.new CompoCondition(
									compoAttribute, null, exp.getSymbol(), exp
											.getPara2());
							cs.add(cc);
						}
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return cs;
	}
 
   public static boolean isBelow(String compoName, TableData data, BusinessJuniorModel bj)
   {
     ArrayList compoConditions = bj.getCompoConditions();
     boolean isAllow = false;
 
     if ("AS_TEMP".equals(compoName))
       return true;
     if (compoConditions.size() == 0)
     {
       return true;
     }
 
     for (Iterator it = compoConditions.iterator(); it.hasNext(); ) {
       CompoCondition cc = (CompoCondition)it
         .next();
       BusinessJuniorExp exp = null;
       if (cc.getFieldCode() != null)
       {
         exp = new BusinessJuniorExp(cc.getCompoCode() + "." + 
           cc.getFieldCode(), cc.getVal(), cc.getSymbol());
       }
       else {
         exp = new BusinessJuniorExp(cc.getCompoCode(), cc.getVal(), 
           cc.getSymbol());
       }
 
       if (!exp.parse(data))
         return false;
       isAllow = true;
     }
 
     return isAllow;
   }
 
   public int compare(Object o1, Object o2)
   {
     BusinessJuniorModel b1 = (BusinessJuniorModel)o1;
     BusinessJuniorModel b2 = (BusinessJuniorModel)o2;
     int p1 = Integer.parseInt(b1.getPriority());
     int p2 = Integer.parseInt(b2.getPriority());
     if (p1 == p2)
       return 0;
     if (p2 < p1)
       return -1;
     return 1;
   }
 
   public static ArrayList getSmalls(List arr)
   {
     ArrayList ar = new ArrayList();
     if (arr != null) {
       ar.add(arr.get(0));
       for (int i = 1; i < arr.size(); i++) {
         if (new BusinessJuniorModel().compare(arr.get(0), arr.get(i)) == 0) {
           ar.add(arr.get(i));
         }
       }
     }
     return ar;
   }
 
   public Set fallbackSuperior(String junCoCode, String junOrgCode, String junPosiCode, String junior, String nd, WFRuntimeService service)
     throws RuntimeException
   {
     HashSet userList = new HashSet();
     String table = "AS_EMP_POSITION,AS_EMP";
     String clause = "AS_EMP.EMP_CODE=AS_EMP_POSITION.EMP_CODE";
     String whereClause = "";
     ArrayList val = new ArrayList();
 
     String[] selfComs = getSelfComs();
     for (int i = 0; i < selfComs.length; i++) {
       String supCom = selfComs[i];
       if (COMMON_CODE.equals(supCom))
         continue;
       if (SUP_CODE.equals(supCom))
       {
         table = table + ",AS_COMPANY";
         whereClause = whereClause + " OR AS_COMPANY.CO_CODE= ? AND AS_EMP_POSITION.CO_CODE=AS_COMPANY.PARENT_CO_CODE";
 
         val.add(junCoCode);
       } else if (SAME_CODE.equals(supCom)) {
         whereClause = whereClause + " OR AS_EMP_POSITION.CO_CODE= ?";
         val.add(junCoCode);
       } else {
         whereClause = whereClause + " OR AS_EMP_POSITION.CO_CODE= ?";
         val.add(supCom);
       }
     }
     if (whereClause.length() != 0) {
       clause = clause + " AND (" + whereClause.substring(" OR".length()) + ")";
       whereClause = "";
     }
 
     String[] selfOrgs = getSelfOrgs();
     for (int i = 0; i < selfOrgs.length; i++) {
       String supOrg = selfOrgs[i];
       if (COMMON_CODE.equals(supOrg))
         continue;
       if (SUP_CODE.equals(supOrg))
       {
         table = table + ",AS_ORG";
         whereClause = whereClause + " OR AS_ORG.ORG_CODE= ?' AND AS_EMP_POSITION.ORG_CODE=AS_ORG.PARENT_ORG_CODE";
 
         val.add(junOrgCode);
       } else if (SAME_CODE.equals(supOrg)) {
         whereClause = whereClause + " OR AS_EMP_POSITION.ORG_CODE= ?";
         val.add(junOrgCode);
       } else {
         whereClause = whereClause + " OR AS_EMP_POSITION.ORG_CODE= ?";
         val.add(supOrg);
       }
     }
     if (whereClause.length() != 0) {
       clause = clause + " AND (" + whereClause.substring(" OR".length()) + ")";
       whereClause = "";
     }
 
     String[] selfPositions = getSelfPositions();
     for (int i = 0; i < selfPositions.length; i++) {
       String supPosi = selfPositions[i];
       if (COMMON_CODE.equals(supPosi))
         continue;
       if (SUP_CODE.equals(supPosi))
       {
         table = table + ",AS_POSITION";
         whereClause = whereClause + " OR AS_POSITON.POSI_CODE= ?' AND AS_EMP_POSITION.POSI_CODE=AS_POSI.PARENT_POSI_CODE";
 
         val.add(junPosiCode);
       } else if (SAME_CODE.equals(supPosi)) {
         whereClause = whereClause + " OR AS_EMP_POSITION.POSI_CODE= ?";
         val.add(junPosiCode);
       } else {
         whereClause = whereClause + " OR AS_EMP_POSITION.POSI_CODE= ?";
         val.add(supPosi);
       }
     }
     if (whereClause.length() != 0) {
       clause = clause + " AND (" + whereClause.substring(" OR".length()) + ")";
       whereClause = "";
     }
 
     String[] selfUsers = getSelfUsers();
     for (int i = 0; i < selfUsers.length; i++) {
       String supUser = selfUsers[i];
       if (COMMON_CODE.equals(supUser))
         continue;
       if (SUP_CODE.equals(supUser))
         continue;
       if (SAME_CODE.equals(supUser)) {
         continue;
       }
       whereClause = whereClause + " OR AS_EMP_POSITION.EMP_CODE= ?";
       val.add(supUser);
     }
 
     if (whereClause.length() != 0) {
       clause = clause + " AND (" + whereClause.substring(" OR".length()) + ")";
       whereClause = "";
     }
     String sql = "select AS_EMP.USER_ID from " + table + " where " + clause;
     try
     {
       List userlist = service.queryForList(sql, val.toArray());
       for (int i = 0; i < userlist.size(); i++) {
         Map record = (Map)userlist.get(i);
         userList.add(record.get("USER_ID"));
       }
     } catch (Exception se) {
       throw new RuntimeException("查找业务上级出错，sql语句为" + sql + "," + 
         se.getMessage());
     }
     return userList;
   }
 
   public class CompoCondition
   {
     private String compoCode;
     private String compoName;
     private String fieldCode;
     private String fieldName;
     private String symbol;
     private String val = "";
 
     public CompoCondition(String compoCode, String compoName, String fieldCode, String fieldName, String symbol, String val)
     {
       this.compoCode = compoCode;
       this.compoName = compoName;
       this.fieldCode = fieldCode;
       this.fieldName = fieldName;
       this.symbol = symbol;
       this.val = val;
     }
 
     public CompoCondition(String compoCode, String fieldCode, String symbol, String val)
     {
       this.compoCode = compoCode;
       this.fieldCode = fieldCode;
       this.symbol = symbol;
       this.val = val;
     }
 
     public String toString() {
       if (this.compoCode == null)
         return "";
       String ret = this.symbol.concat("(\"").concat(this.compoCode);
       if (this.fieldCode != null)
         ret = ret.concat(".").concat(this.fieldCode);
       ret = ret.concat("\",\"").concat(this.val).concat("\")");
       return ret;
     }
 
     public String getCompoCode()
     {
       return this.compoCode;
     }
 
     public void setCompoCode(String compoCode)
     {
       this.compoCode = compoCode;
     }
 
     public String getCompoName()
     {
       return this.compoName;
     }
 
     public void setCompoName(String compoName)
     {
       this.compoName = compoName;
     }
 
     public String getFieldCode()
     {
       return this.fieldCode;
     }
 
     public void setFieldCode(String fieldCode)
     {
       this.fieldCode = fieldCode;
     }
 
     public String getFieldName()
     {
       return this.fieldName;
     }
 
     public void setFieldName(String fieldName)
     {
       this.fieldName = fieldName;
     }
 
     public String getSymbol()
     {
       return this.symbol;
     }
 
     public void setSymbol(String symbol)
     {
       this.symbol = symbol;
     }
 
     public String getVal()
     {
       return this.val;
     }
 
     public void setVal(String val)
     {
       this.val = val;
     }
   }
 
   public class SystemCondition
   {
     private String variableCode;
     private String variableName;
     private String symbol;
     private String val = "";
 
     public SystemCondition(String variableCode, String variableName, String symbol, String val)
     {
       this.variableCode = variableCode;
       this.variableName = variableName;
       this.symbol = symbol;
       this.val = val;
     }
 
     public SystemCondition(String variableCode, String symbol, String val)
     {
       this.variableCode = variableCode;
       this.symbol = symbol;
       this.val = val;
     }
 
     public String toString() {
       return this.symbol.concat("(\"").concat(this.variableCode).concat("\",\"")
         .concat(this.val).concat("\")");
     }
 
     public String getSymbol()
     {
       return this.symbol;
     }
 
     public void setSymbol(String symbol)
     {
       this.symbol = symbol;
     }
 
     public String getVal()
     {
       return this.val;
     }
 
     public void setVal(String val)
     {
       this.val = val;
     }
 
     public String getVariableCode()
     {
       return this.variableCode;
     }
 
     public void setVariableCode(String variableCode)
     {
       this.variableCode = variableCode;
     }
 
     public String getVariableName()
     {
       return this.variableName;
     }
 
     public void setVariableName(String variableName)
     {
       this.variableName = variableName;
     }
   }
 }

/* Location:           D:\temp\wf\wf.jar
 * Qualified Name:     com.kingdrive.workflow.model.runtime.BusinessJuniorModel
 * JD-Core Version:    0.6.0
 */