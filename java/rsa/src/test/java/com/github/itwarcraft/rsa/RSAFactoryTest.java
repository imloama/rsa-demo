package com.github.itwarcraft.rsa;

import java.security.KeyPair;

import org.junit.Test;

public class RSAFactoryTest {

	@Test
	public void test() throws InterruptedException {
		// 原始
		final String str = "admin";
		// 客户端1，获取当前生成的rsa密钥
		Thread client1 = new Thread() {
			@Override
			public void run() {
				KeyPair keys = RSAKeyFactory.getInstance().getKeyPair();
				System.out.println("client1:" + keys.getPublic());
				System.out.println("client1:" + keys.getPrivate());
				try {
					// 加密
					byte[] en_test = RSAUtil.encrypt(keys.getPublic(),
							str.getBytes());
					System.out.println(new String(en_test));
					// 解密
					byte[] de_test = RSAUtil
							.decrypt(keys.getPrivate(), en_test);
					System.out.println(new String(de_test));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// 客户端2
		Thread client2 = new Thread() {
			@Override
			public void run() {
				KeyPair keys = RSAKeyFactory.getInstance().getKeyPair();
				System.out.println(keys.getPublic());
				System.out.println(keys.getPrivate());
				try {
					// 加密
					byte[] en_test = RSAUtil.encrypt(keys.getPublic(),
							str.getBytes());
					System.out.println(new String(en_test));
					// 解密
					byte[] de_test = RSAUtil
							.decrypt(keys.getPrivate(), en_test);
					System.out.println(new String(de_test));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// 客户端2
		Thread client3 = new Thread() {
			@Override
			public void run() {
				System.out.println("client--3");
				KeyPair keys = RSAKeyFactory.getInstance().getKeyPair();
				System.out.println("client3:" + keys.getPublic());
				System.out.println("client3:" + keys.getPrivate());
				try {
					// 加密
					byte[] en_test = RSAUtil.encrypt(keys.getPublic(),
							str.getBytes());
					System.out.println(new String(en_test));
					// 解密
					byte[] de_test = RSAUtil
							.decrypt(keys.getPrivate(), en_test);
					System.out.println(new String(de_test));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		client1.start();
		client2.start();
		client3.start();

		Thread.sleep(100000);

	}

}
