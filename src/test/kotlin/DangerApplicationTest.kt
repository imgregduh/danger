import com.doubleu.danger.DangerApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes=[DangerApplication::class])
@ActiveProfiles("test")
class DangerApplicationTest{
    @Test
    fun contextLoads(){

    }
}