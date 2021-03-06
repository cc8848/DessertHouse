package dessert.remoteService.scheduleManage;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dessert.dao.ScheduleDao;
import dessert.models.ScheduleDetail;
import dessert.models.WeekSchedule;
import dessert.utility.DayTransformer;
import dessert.utility.FormulationNumber;

@Service
public class ScheduleManage implements ScheduleManageService{
	
	@Autowired
	private ScheduleDao scheduleDao;

	public boolean addSchedule(WeekSchedule schedule) {
		return scheduleDao.saveSchedule(schedule);
	}
	
	public boolean deleteSchedule(String scheduleId){
		WeekSchedule sc = getScheduleInfo(scheduleId);
		if(sc!=null){
			boolean res = false;
			res = scheduleDao.removeScheduleDetail(scheduleId);
			if(res){
				return scheduleDao.removeSchedule(scheduleId);
			}
			else{
				return false;
			}
		}
		else{return false;}
	}

	public WeekSchedule getScheduleInfo(String scheduleId) {
		return scheduleDao.findSchedule(scheduleId);
	}
	
	public ArrayList<WeekSchedule> retrieveSchedule(String storeId, Date startTime, Date endTime) {
		if(storeId!=null){
			if(startTime==null||endTime==null){
				return scheduleDao.retrieveScheduleForStore(storeId,FormulationNumber.scheduleApproved);
			}
			else{
				Date[] period = {startTime,endTime};
				return scheduleDao.retrieveScheduleForStore(storeId, period,FormulationNumber.scheduleApproved);
			}
		}
		else{
			return null;
		}
	}

	public WeekSchedule retrieveSchedule(String storeId, Date date){
		if(storeId!=null||date==null){
			Date[] period = {date,date};
			ArrayList<WeekSchedule> sList = scheduleDao.retrieveScheduleForStore(storeId, period, FormulationNumber.scheduleApproved);
			if(sList!=null&&sList.size()==1){
				return sList.get(0);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	public ArrayList<ScheduleDetail> retrieveScheduleDetail(String scheduleId){
		return scheduleDao.findScheduleDetail(scheduleId);
	}

	public ArrayList<ScheduleDetail> retrieveScheduleDetail(String scheduleId,Date date){
		ArrayList<ScheduleDetail> detailList = new ArrayList<ScheduleDetail>();
		for(ScheduleDetail detail:scheduleDao.findScheduleDetail(scheduleId)){
			if(DayTransformer.transform(detail.getScheduleDate()).equals(DayTransformer.transform(date))){
				detailList.add(detail);
			}
		}
		return detailList;
	}

	public ArrayList<WeekSchedule> retrieveScheduleForApprove() {
		return scheduleDao.retrieveScheduleByState(FormulationNumber.scheduleMade);
	}

	public ArrayList<WeekSchedule> retrieveApprovedSchedule(){
		return scheduleDao.retrieveScheduleByState(FormulationNumber.scheduleApproved);
	}

	public ArrayList<WeekSchedule> retrieveDisapprovedSchedule(){
		return scheduleDao.retrieveScheduleByState(FormulationNumber.scheduleDisapproved);
	}

	public void approveSchedule(WeekSchedule schedule) {
		scheduleDao.updateSchedule(schedule);
	}

	public void updateSchedule(WeekSchedule schedule){
		scheduleDao.updateSchedule(schedule);
	}
	
	public boolean addScheduleDetail(ScheduleDetail scheduleDetail){
		return scheduleDao.saveScheduleDetail(scheduleDetail);
	}
	
	public boolean updateScheduleDetail(ScheduleDetail scheduleDetail){
		return scheduleDao.updateScheduleDetail(scheduleDetail);
	}

}
