import com.example.financeapp.controller.AppController;
import com.example.financeapp.repository.TransactionRepository;
import com.example.financeapp.service.TransactionService;

public class FinanceAppApplication {
    public static void main(String[] args) {
        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);
        AppController controller = new AppController(service);

        controller.run();
    }
}