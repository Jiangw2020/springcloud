package jw.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer{
    private AtomicInteger atomicInteger =new AtomicInteger(0);
    public final int getAndIncrement(){
        int current;
        int next;
        do{
            current=this.atomicInteger.get();
            next=current>=Integer.MAX_VALUE?0:current+1;
        }while (!this.atomicInteger.compareAndSet(current,next));
        return next;
    }
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstance) {
        int index =getAndIncrement() % serviceInstance.size();
        return serviceInstance.get(index);
    }
}
