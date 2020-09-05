package cn.edu.zucc.personplan.comtrol.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.personplan.itf.IPlanManager;
import cn.edu.zucc.personplan.model.BeanPlan;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.BusinessException;
import cn.edu.zucc.personplan.util.DbException;

public class ExamplePlanManager implements IPlanManager {

	@Override
	public BeanPlan addPlan(String name) throws BaseException {
		// TODO Auto-generated method stub

		/* 判断计划名称为空否 */
		if(name.length()==0||name==null)throw new BusinessException("计划的名称不能为空！");

		/* 建立与数据库链接 */
		Connection conn = null;

		try{
			String user_id = BeanUser.currentLoginUser.getUserid();
			int plan_ord = 0;//?
			String sql="select max(plan_ord) from tbl_plan where user_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1,user_id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()){
				plan_ord=rs.getInt(1)+1;
			}else{
				plan_ord=1;
			}

			rs.close();
			pst.close();
			sql="insert into tbl_plan(" +
					"user_id,plan_order,plan_name," +
					"creat_time,step_count,start_step_count,finished_step_count)" +
					"values (?,?,?,?,0,0,0)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user_id);
			pst.setInt(2,plan_ord);
			pst.setString(3,name);
			pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
			pst.execute();
			pst.close();

			BeanPlan p=new BeanPlan();
			sql="select max(plan_id) from tbl_plan where user_id = ?";
			pst= conn.prepareStatement(sql);
			pst.setString(1.user_id);
			rs= pst.executeQuery();
			if(rs.next()){
				int pid=rs.getInt(1);
			}else{

			}
			rs.close();
			pst.close();

			return p;

		}catch (SQLException ex){
			ex.printStackTrace();
			throw DbException(ex);
		}
		return null;
	}

	@Override
	public List<BeanPlan> loadAll() throws BaseException {
		List<BeanPlan> result=new ArrayList<BeanPlan>();
		BeanPlan p=new BeanPlan();
		result.add(p);
		return result;	
	}

	@Override
	public void deletePlan(BeanPlan plan) throws BaseException {
		
	}

}
