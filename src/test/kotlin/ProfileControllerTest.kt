import com.doubleu.danger.DangerApplication
import com.doubleu.danger.UserProfileController
import com.doubleu.danger.entities.ProfileEntity
import com.doubleu.danger.managers.ProfileManager
import com.doubleu.danger.repositories.ProfilesRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.MongoClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [DangerApplication::class])
@ActiveProfiles("test")
class ProfileControllerTest {

    @Autowired
    lateinit var mongoClient: MongoClient

    @Autowired
    lateinit var profileRepo: ProfilesRepository

    @InjectMocks
    @Autowired
    lateinit var profileManager: ProfileManager

    @Autowired
    lateinit var subject: UserProfileController

    private var mockMvc: MockMvc? = null

    private val mapper = ObjectMapper()

    @Value("\${spring.data.mongodb.database}")
    private val databaseName: String = ""

    @Before
    fun setup() {
        mongoClient
                .getDatabase(databaseName)
                .getCollection("profiles")
                .drop()
        mockMvc = standaloneSetup(subject).build()
    }

    @Test
    fun `returns a single profile when profile get request with path variable id`() {
        val newProfile = profileRepo.insert(
                ProfileEntity(
                        null,
                        "someEmail@gmail.com",
                        "firstName",
                        "lastName")
        )


        mockMvc!!.perform(get("/profile/${newProfile._id}"))
                .andExpect(status().isOk)


    }

    @Test
    fun `returns data when profile get request`() {
        mockMvc!!.perform(get("/profiles"))
                .andExpect(status().isOk)
    }

    @Test
    fun `successfully post new profile`() {
        val random = Random()
        val payload = mapOf(
                "email" to "deleteme_${random.nextInt(1000)}@gmail.com",
                "firstName" to "firstname",
                "lastName" to "lastname"
        )
        val result = mockMvc!!.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(payload)))
                .andReturn()
        assertThat(result.response.status).isEqualTo(HttpStatus.OK.value())

        val actualPayload = mapper.readValue(result.response.contentAsString, Map::class.java)
        val lastSavedProfile = profileRepo.findAll().last()
        assertThat(actualPayload["_id"]).isEqualTo(lastSavedProfile._id.toString())
    }
}