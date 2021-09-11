package com.foxconn.springcloud.lb;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

public interface MyLoadBalancer {
	ServiceInstance getInstances(List<ServiceInstance> serviceInstances);
}
