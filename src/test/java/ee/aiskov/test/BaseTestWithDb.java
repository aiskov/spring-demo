package ee.aiskov.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@SpringBootTest
@TestPropertySource("/test.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTestWithDb {
}
