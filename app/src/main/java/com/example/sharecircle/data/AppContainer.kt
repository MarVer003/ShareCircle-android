import android.content.Context
import com.example.sharecircle.data.MyDatabase
import com.example.sharecircle.data.MyRepository
import com.example.sharecircle.data.MyRepositoryImpl

interface AppContainer {
    val myRepository: MyRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val myRepository: MyRepository by lazy {
        MyRepositoryImpl(MyDatabase.getDatabase(context).userDAO(),
            MyDatabase.getDatabase(context).groupDAO(),
            MyDatabase.getDatabase(context).expenseDAO(),
            MyDatabase.getDatabase(context).paymentDAO(),
            MyDatabase.getDatabase(context).groupMemberDAO(),
            MyDatabase.getDatabase(context).expenseDetailsDAO() )
    }
}