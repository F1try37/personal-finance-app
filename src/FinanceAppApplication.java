import com.example.financeapp.controller.AppController;
import com.example.financeapp.repository.TransactionRepository;
import com.example.financeapp.service.TransactionService;
import com.example.financeapp.util.InputHandler;

public class FinanceAppApplication {
    public static void main(String[] args) {
        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);
        InputHandler handler = new InputHandler(service);
        AppController controller = new AppController(handler);

        controller.run();
    }
}