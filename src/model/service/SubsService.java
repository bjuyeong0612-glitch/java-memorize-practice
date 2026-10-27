package model.service;

import java.util.ArrayList;
import java.util.List;

import exception.DuplicateNameException;
import exception.InvalidPaymentDayException;
import exception.SubscriptionNotFoundException;
import model.dto.Subscription;

public class SubsService {
	private List<Subscription> subscriptions = new ArrayList<>();
	
	{
		subscriptions.add( new Subscription("넷플릭스", 14000, 14, "OTT") );
		subscriptions.add( new Subscription("알라딘 만권당", 8450, 9, "기타") );
		subscriptions.add( new Subscription("벅스뮤직", 8460, 18, "음악") );
	}
	
	public List<Subscription> readSubscription(){
		return new ArrayList<>(subscriptions);
		//밖에서 고칠 수 없게 복사본을 건네도록 수정(캡슐화)
	}
	
	public void save(Subscription s){
		nameCheck(s.getName());
		paymentDayCheck(s.getPaymentDay());
		subscriptions.add(s);
	}
	
	public void nameCheck(String name) {
		boolean exists = subscriptions.stream()
						.anyMatch(s -> s.getName().equals(name));
		if(exists) {
			throw new DuplicateNameException("이미 구독 중인 서비스입니다.");
		}
		
		
		/*for(Subscription s : subscriptions) {
			if(s.getName().equals(name)) {
				throw new DuplicateNameException("이미 구독 중인 서비스입니다.");
			}
			
		}*/
	}
	
	public void paymentDayCheck(int day) {
		if(day < 1 || day > 31) {
			throw new InvalidPaymentDayException("결제일은 1일 부터 31일 사이여야 합니다.");
		}
	}
	
	public void delete(String name) {
		for(int i = 0; i < subscriptions.size(); i++) {
			if(subscriptions.get(i).getName().equals(name)) {
				subscriptions.remove(i);
				return;
			}
			
		}
		throw new SubscriptionNotFoundException("해당 서비스명의 구독이 없습니다.");
		
		
	}
	
	
	public List<Subscription> findByCategory(String category) {
		return subscriptions.stream()
							.filter(s -> s.getCategory().equals(category))
							.toList();
	}//참고 : toList는 수정불가 리스트
	
	
	public void updateFee(String name, int newFee) {
		for(Subscription s : subscriptions) {
			if(s.getName().equals(name)) {
				s.setMonthlyFee(newFee);
				return;
			}
		}
		throw new SubscriptionNotFoundException("해당 서비스의 구독이 없습니다.");
	}
	
	public int totalFee() {
		return subscriptions.stream()
						    .mapToInt(s -> s.getMonthlyFee())
						    .sum();
	}
	
}
