package com.mohred.studentnetwork.connection;

import com.mohred.studentnetwork.interfaces.HTTPObserver;
import com.mohred.studentnetwork.interfaces.ConnectionObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static com.mohred.studentnetwork.common.AppConstants.ConcurrentConstants.THREAD_POOL_SIZE;

/**
 * Created by Redan on 12/16/2016.
 */
public class HttpExecutionPool
{
    private ExecutorService executorService;

    public HttpExecutionPool()
    {
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }


    public void sentGETMessage(final GetMessage getMessage,final HTTPObserver observer)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ConnectionObject respond;
                respond = getMessage.sendMessage();
                if(observer != null)
                    observer.update(respond);
            }
        });


    }

    public void sendPostMessage(final PostMessage postMessage,final HTTPObserver observer,final ConnectionObject toPost)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ConnectionObject respond;
                respond = postMessage.sendMessage(toPost);
                if(observer != null)
                    observer.update(respond);
            }
        });


    }

}
