package com.bp.wallet.server.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bp.wallet.proto.CURRENCY;
import com.bp.wallet.server.enity.Wallet;
import com.bp.wallet.server.enity.WalletPK;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class WalletRepositoryTest {

	@Autowired
	private WalletRepository repository;

	@Before
	public void setUp() throws Exception {
		repository.deleteAll();
		Random random = new Random(123L);
		for (Long userID = 100L; userID < 110; userID++) {
			repository.save(new Wallet(new WalletPK(userID, CURRENCY.EUR), BigDecimal.valueOf(random.nextInt(3213))));
			repository.save(new Wallet(new WalletPK(userID, CURRENCY.USD), BigDecimal.valueOf(random.nextInt(3213))));
			repository.save(new Wallet(new WalletPK(userID, CURRENCY.GBP), BigDecimal.ZERO));
		}
	}

	@Test
	public void testSetupWithFindAll() {
		List<Wallet> wallets = repository.findAll();
		assertThat(wallets).hasSize(30);
	}

	@Test
	public void testFindByUserID() {
		Optional<List<Wallet>> wallets = repository.findByWalletPK_UserID(100L);
		assertThat(wallets.get()).hasSize(3);
	}

	@Test
	public void testFindByUserIDAndCurrency() {
		Optional<Wallet> wallets = repository.getUserWalletsByCurrencyAndUserID(100L, CURRENCY.EUR);
		assertThat(wallets.get()).isNotNull();
		assertThat(wallets.get().getWalletPK().getCurrency().name()).contains(CURRENCY.EUR.name());
	}

	@Test
	public void testGetUserWalletsByCurrencyAndUserIDWhereBalanceIsGreaterThanZero() throws Exception {
		Optional<Wallet> wallets = repository.getUserWalletsByCurrencyAndUserIDWhereBalanceIsGreaterThanZero(101L,
				CURRENCY.GBP);
		assertThat(wallets.isPresent()).isFalse();

	}
}
