package com.foxconn.springcloud.lb;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

//自己实现负载均衡的轮询算法
//rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标  ，每次服务重启动后rest接口计数从1开始

@Component//每次返回的对象都是重新创建的，而@configuration则返回的是同一个对象
public class Mylb implements MyLoadBalancer {
	//默认初始值为0
	private AtomicInteger integer = new AtomicInteger(0);
	
	//获取客户端请求次数
	public final int getRequestCount() {
		int current,next;
		do {
			current = this.integer.get();//get()中的value被volatile修饰，保证可见，有序性
			next = current > 2147483647 ?0: current+1;
			//读取的内存值this始终和next是一致的。
			//因为底层实现compareAndSwapInt类逻辑就是：
			//if(this == expect/current){
			// this = update/next}
		} while (!this.integer.compareAndSet(current, next));
		 System.out.println("*****第几次访问，次数next: "+next);
		 return next;
	}
	@Override
	public ServiceInstance getInstances(List<ServiceInstance> serviceInstances) {
		// TODO Auto-generated method stub
		int index = getRequestCount() % serviceInstances.size();
		return serviceInstances.get(index);
	}

}
