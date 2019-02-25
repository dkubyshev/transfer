package com.revolut;

import org.glassfish.grizzly.http.server.HttpServer;
import org.jooq.generated.tables.pojos.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class AccountTransferTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
        new Setup().run();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void testTransfer() throws InterruptedException {
        Runnable transferFrom1To2 = () -> {
            Form form = new Form();
            form.param("idFrom", "1");
            form.param("idTo", "2");
            form.param("amount", "2");
            target.path("/accounts/transfer").request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        };

        Runnable transferFrom2To1 = () -> {
            Form form = new Form();
            form.param("idFrom", "2");
            form.param("idTo", "1");
            form.param("amount", "0.5");
            target.path("/accounts/transfer").request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        };
        new MultithreadedStressTester(100).stress(transferFrom1To2, transferFrom2To1);
        Account account1Info = target.path("/accounts/1").request().get(Account.class);
        assertEquals(new BigDecimal("850.00"),account1Info.getBalance());
        Account account2Info = target.path("/accounts/2").request().get(Account.class);
        assertEquals(new BigDecimal("650.00"),account2Info.getBalance());
    }

    private class MultithreadedStressTester {

        final ExecutorService executor = Executors.newFixedThreadPool(10);

        private final int numCount;

        public MultithreadedStressTester(int numCount) {
            this.numCount = numCount;
        }

        public void stress(final Runnable action1, final Runnable action2) throws InterruptedException {
            spawnThreads(action1, action2).await();
        }

        private CountDownLatch spawnThreads(final Runnable action1, final Runnable action2) {
            final CountDownLatch finished = new CountDownLatch(numCount*2);

            for (int i = 0; i < numCount; i++) {
                executor.execute(() -> {
                    try {
                        action1.run();
                    }
                    finally {
                        finished.countDown();
                    }
                });

                executor.execute(() -> {
                    try {
                        action2.run();
                    }
                    finally {
                        finished.countDown();
                    }
                });

            }
            return finished;
        }
    }
}
