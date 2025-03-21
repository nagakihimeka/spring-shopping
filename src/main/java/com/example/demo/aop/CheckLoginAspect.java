package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Account;

@Aspect
@Component
public class CheckLoginAspect {

	@Autowired
	Account account;

	//	ログ出力処理
	@Before("execution(* com.example.demo.Controller.*Controller.*(..))")
	public void writeLog(JoinPoint jp) {
		//		ログインしたアカウント情報を取得
		if (account == null || account.getName() == null || account.getName().length() == 0) {
			System.out.print("ゲスト:");
		} else {
			System.out.print(account.getName() + ":");
		}
		System.out.println(jp.getSignature());
	}

	//	未ログインの場合ログインページにリダイレクト
	@Around("execution(* com.example.demo.Controller.CartController.*(..)) ||"
			+ "execution(* com.example.demo.Controller.ItemController.*(..)) ||"
			+ "execution(* com.example.demo.Controller.OrderController.*(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {

		if (account == null || account.getName() == null || account.getName().length() == 0) {
			System.err.println("ログインしていません"); //ログ出力

			return "redirect:/login?error=notLoggedIn";
		}
		//コントローラーのメソッド実行
		return jp.proceed();
	}
}
