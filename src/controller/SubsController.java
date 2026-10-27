package controller;

import java.util.List;

import model.dto.Subscription;
import model.service.SubsService;

public class SubsController {
	private SubsService ss = new SubsService();
	public List<Subscription> readSubscription(){
		return ss.readSubscription();
	}
	
	public void save(String name, int monthlyFee, int paymentDay, String category) {
		
		ss.save( new Subscription(name, monthlyFee, paymentDay,category) );
	}
	
	public void delete(String name) {
		ss.delete( name );
	}
	
	public List<Subscription> findByCategory(String category){
		return ss.findByCategory(category);
	}
	
	public void updateFee(String name, int newFee) {
		ss.updateFee(name, newFee);
	}
	
	public int totalFee() {
		return ss.totalFee();
	}
}
