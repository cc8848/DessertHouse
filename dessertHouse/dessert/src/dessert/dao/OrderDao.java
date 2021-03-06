package dessert.dao;

import java.sql.Date;
import java.util.ArrayList;

import dessert.models.Order;
import dessert.models.OrderDetail;

public interface OrderDao {
	
	public Order findOrder(String orderId);
	
	public ArrayList<Order> findOrder(String[] columns,String[] values);
	
	public ArrayList<Order> findOrderForStore(String storeId, Date startDate, Date endDate);
	
	public ArrayList<OrderDetail> findOrderDetail(String orderId);
	
	public boolean saveOrder(Order order);

	public boolean saveOrderDetail(OrderDetail detail);
	
	public void updateOrder(Order order);
	
}
