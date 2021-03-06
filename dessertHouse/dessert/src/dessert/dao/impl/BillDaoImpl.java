package dessert.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dessert.dao.BaseDao;
import dessert.dao.BillDao;
import dessert.models.Bill;
import dessert.models.BillDetail;
import dessert.utility.DayTransformer;

@Repository
public class BillDaoImpl implements BillDao{

	@Autowired
	private BaseDao baseDao;
	private Session session;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Bill> findBillForMember(String memberId) {
		session = baseDao.getNewSession();
		Query query = session.createQuery("select b from Bill b "
							+ "where b.billMember.memberId=?");
		query.setString(0, memberId);
		List list = query.list();
		session.close();
		return (ArrayList<Bill>) list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Bill> findBillByDate(String storeId,Date date){
		Date newDate = DayTransformer.transform(DayTransformer.transform(date));
		session = baseDao.getNewSession();
		Query query = session.createQuery("select b from Bill b "
						+ "where b.billStore.storeId=? and b.billTime=?");
		query.setString(0, storeId);
		query.setDate(1, newDate);
		List list = query.list();
		return (ArrayList<Bill>) list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Bill> findBillByStore(String storeId,Date startDate,Date endDate){
		session = baseDao.getNewSession();
		Query query = session.createQuery("select b from Bill b "
						+ "where b.billStore.storeId=? and b.billTime>=? and b.billTime<?");
		query.setString(0, storeId);
		query.setDate(1, startDate);
		query.setDate(2, endDate);
		List list = query.list();
		return (ArrayList<Bill>) list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<BillDetail> findBillDetailByStore(String storeId,Date startDate,Date endDate){
		session = baseDao.getNewSession();
		Query query = session.createQuery("select bd from BillDetail bd "
						+ "where bd.bill.billStore.storeId=? and bd.bill.billTime>=? and bd.bill.billTime<?");
		query.setString(0, storeId);
		query.setDate(1, startDate);
		query.setDate(2, endDate);
		List list = query.list();
		return (ArrayList<BillDetail>) list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<BillDetail> findBillDetailAll(Date startDate,Date endDate){
		session = baseDao.getNewSession();
		Query query = session.createQuery("select bd from BillDetail bd "
						+ "where bd.bill.billTime>=? and bd.bill.billTime<?");
		query.setDate(0, startDate);
		query.setDate(1, endDate);
		List list = query.list();
		return (ArrayList<BillDetail>) list;
	}

	public boolean saveBill(Bill bill) {
		try {
			baseDao.save(bill);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void saveBillDetail(BillDetail billDetail){
		try {
			baseDao.save(billDetail);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
