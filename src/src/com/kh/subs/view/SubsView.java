package src.com.kh.subs.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controller.SubsController;
import exception.DuplicateNameException;
import exception.InvalidPaymentDayException;
import exception.SubscriptionNotFoundException;
import model.dto.Subscription;

public class SubsView {
	Scanner sc = new Scanner(System.in);
	
	SubsController ctrl = new SubsController();
	
	
	public void mainmenu() {
		while(true) {
			System.out.println();
			System.out.println("구독 서비스 관리 프로그램입니다.");
			System.out.println( """
									1. 구독 전체 조회하기
									2. 구독 추가하기
									3. 구독 해지하기
									4. 카테고리로 검색하기
									5. 월 요금 변경하기
									6. 월 총액
									0. 프로그램 종료하기
									
									사용하실 메뉴번호를 입력해 주세요 > 
									
								""");
			int menu = 0;
			try {
				menu = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("숫자만 입력해 주세요.");
				continue;
			} finally {
				sc.nextLine();
			}
			
			switch(menu) {
				case 1 : readSubscription(); break;
				case 2 : save(); break;
				case 3 : delete(); break;
				case 4 : findByCategory(); break;
				case 5 : updateFee(); break;
				case 6 : totalFee(); break;
				case 0 : System.out.println("프로그램을 종료합니다."); sc.close(); return; 
				default : System.out.println("없는 번호를 선택하셨습니다.");
			}
		}
	}
	
	
	private void readSubscription() {
		List<Subscription> list = ctrl.readSubscription();
		if(!list.isEmpty()) {
			System.out.println();
			System.out.println("구독 정보를 확인하세요.");
			System.out.println();
			for (Subscription s : list) {
				System.out.println("서비스명 : " + s.getName());
				System.out.println("--------------------------------------------------------");
				System.out.println("월 요금 : " + s.getMonthlyFee());
				System.out.println("--------------------------------------------------------");
				System.out.println("결제일 : 매월 " + s.getPaymentDay() + "일");
				System.out.println("--------------------------------------------------------");
				System.out.println("카테고리 : " + s.getCategory());
				System.out.println("========================================================");
			}
		} else {
			System.out.println("========================================================");
			System.out.println("아직 구독중인 서비스가 없습니다!");
			System.out.println("========================================================");
		}
	}
	
	
	
	private void save() {
		
		System.out.println();
		System.out.println("서비스명을 입력해 주세요.");

		String name = sc.nextLine();
		System.out.println("월 요금을 입력해 주세요.(원)");
		int monthlyFee = 0;
		try {
			monthlyFee = sc.nextInt();
		} catch (InputMismatchException e){
			System.out.println("잘못된 금액입니다.");
			return;
		}
		sc.nextLine();
		System.out.println("결제일을 입력해 주세요. (1~31)");
		int paymentDay = 0;
		try {
			paymentDay = sc.nextInt();
		} catch (InputMismatchException e){
			System.out.println("잘못된 날짜입니다.");
			return;
		}
		sc.nextLine();
		System.out.println("카테고리를 입력해 주세요(OTT/음악/쇼핑/기타)");
		String category = sc.nextLine();
		try {
			ctrl.save(name, monthlyFee, paymentDay, category);
			System.out.println("구독이 등록되었습니다!");
	
		} catch (DuplicateNameException e) {
			System.out.println(e.getMessage());
			System.out.println("다른 이름을 입력해 주세요.");
		} catch (InvalidPaymentDayException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	private void delete() {
		System.out.println();
		System.out.println("구독을 해지하겠습니다.");
		readSubscription();
		System.out.print("해지할 서비스명을 입력해 주세요.");
		String name = sc.nextLine();
		
		if(name.isBlank()) {
			System.out.println("서비스명을 입력하지 않았습니다.");
			return;
		}
		
		try {
			ctrl.delete(name);
			System.out.println(name + " 구독을 해지하였습니다.");
		} catch (SubscriptionNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	private void findByCategory() {
		System.out.println();
		System.out.print("검색할 카테고리를 입력해 주세요(OTT/음악/쇼핑/기타) > ");
		String category = sc.nextLine();
		List<Subscription> searched = ctrl.findByCategory(category);
		
		if(searched.isEmpty()) {
			System.out.println(category + " 카테고리에 구독 중인 서비스가 없습니다.");
			return;
		}
		
		System.out.println();
		System.out.println(category + " 카테고리 검색 결과입니다.");
		
		for(Subscription s : searched) {
			System.out.println(s.getName() + " / " 
							 + s.getMonthlyFee() + "원 / 매월 " 
							 + s.getPaymentDay() + "일");
			
		}
	}
	
	private void updateFee() {
		System.out.println();
		System.out.println("요금을 변경할 서비스명을 입력해 주세요 > ");
		String name = sc.nextLine();
		System.out.println("변경할 월 요금을 입력해 주세요(원) > ");
		int newFee = 0;
		try {
			newFee = sc.nextInt();
		} catch (InputMismatchException e){
			System.out.println("잘못된 요금입니다.");
			return;
		}
		sc.nextLine();
		
		try {
			ctrl.updateFee(name, newFee);
			System.out.println(name + " 요금이 " + newFee + "원으로 변경되었습니다.");
		} catch (SubscriptionNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private void totalFee() {
		System.out.println("이번 달 구독료 합계 : " + ctrl.totalFee());
	}
}
